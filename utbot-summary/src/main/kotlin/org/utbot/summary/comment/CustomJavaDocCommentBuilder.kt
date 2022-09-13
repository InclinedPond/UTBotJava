package org.utbot.summary.comment

import org.utbot.framework.plugin.api.DocCustomTagStatement
import org.utbot.framework.plugin.api.DocStatement
import org.utbot.framework.plugin.api.exceptionOrNull
import org.utbot.summary.ast.JimpleToASTMap
import org.utbot.summary.tag.TraceTagWithoutExecution
import soot.SootMethod

/**
 * Builds JavaDoc comments for generated tests using plugin's custom JavaDoc tags.
 */
class CustomJavaDocCommentBuilder(
    traceTag: TraceTagWithoutExecution,
    sootToAST: MutableMap<SootMethod, JimpleToASTMap>
) : SimpleCommentBuilder(traceTag, sootToAST, stringTemplates = StringsTemplatesPlural()) {

    /**
     * Collects statements for final JavaDoc comment.
     */
    fun buildDocStatements(method: SootMethod): List<DocStatement> {
        val comment = buildCustomJavaDocComment(method)
        val docStatementList =
            CustomJavaDocTagProvider().getPluginCustomTags().mapNotNull { it.generateDocStatement(comment) }
        return listOf(DocCustomTagStatement(docStatementList))
    }

    private fun buildCustomJavaDocComment(currentMethod: SootMethod): CustomJavaDocComment {
        val methodReference = getMethodReference(
            currentMethod.declaringClass.name,
            currentMethod.name,
            currentMethod.parameterTypes
        )
        val classReference = getClassReference(currentMethod.declaringClass.javaStyleName)

        val comment = CustomJavaDocComment(
            classUnderTest = classReference,
            methodUnderTest = methodReference,
        )

        val rootSentenceBlock = SimpleSentenceBlock(stringTemplates = stringTemplates)
        skippedIterations()
        buildSentenceBlock(traceTag.rootStatementTag, rootSentenceBlock, currentMethod)
        rootSentenceBlock.squashStmtText()

        // builds Throws exception section
        val thrownException = traceTag.result.exceptionOrNull()
        if (thrownException != null) {
            val exceptionName = thrownException.javaClass.name
            val reason = findExceptionReason(currentMethod, thrownException)

            comment.throwsException = "{@link $exceptionName} $reason"
        }

        generateSequence(rootSentenceBlock) { it.nextBlock }.forEach {
            it.stmtTexts.forEach { statement ->
                processStatement(statement, comment)
            }

            it.invokeSentenceBlock?.let {
                comment.invokes += it.first
                it.second.stmtTexts.forEach { statement ->
                    processStatement(statement, comment)
                }
            }

            it.iterationSentenceBlocks.forEach { (loopDesc, sentenceBlocks) ->
                comment.iterates += stringTemplates.iterationSentence.format(
                    stringTemplates.codeSentence.format(loopDesc),
                    numberOccurrencesToText(
                        sentenceBlocks.size
                    )
                )
            }
        }

        return comment
    }

    private fun processStatement(
        statement: StmtDescription,
        comment: CustomJavaDocComment
    ) {
        when (statement.stmtType) {
            StmtType.Invoke -> comment.invokes += "{@code ${statement.description}}"
            StmtType.Condition -> comment.executesCondition += "{@code ${statement.description}}"
            StmtType.Return -> comment.returnsFrom = "{@code ${statement.description}}"
            StmtType.CaughtException -> comment.caughtException = "{@code ${statement.description}}"
            StmtType.SwitchCase -> comment.switchCase = "{@code case ${statement.description}}"
            StmtType.CountedReturn -> comment.countedReturn = "{@code ${statement.description}}"
            StmtType.RecursionAssignment -> comment.recursion = "of {@code ${statement.description}}"
        }
    }
}