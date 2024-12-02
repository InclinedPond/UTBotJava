package org.utbot.maven.plugin

import org.apache.maven.plugin.testing.AbstractMojoTestCase
import org.apache.maven.project.MavenProject
import org.junit.jupiter.api.*
import org.utbot.common.PathUtil.toPath
import org.utbot.framework.util.GeneratedSarif
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenerateTestsAndSarifReportMojoTest : AbstractMojoTestCase() {

    @BeforeAll
    override fun setUp() {
        super.setUp()
    }

    @AfterAll
    override fun tearDown() {
        super.tearDown()
    }
    // internal

    private val testMavenProject: TestMavenProject =
        TestMavenProject("src/test/resources/project-to-test".toPath())

    private val sarifReportMojo by lazy {
        configureSarifReportMojo(testMavenProject.mavenProject).apply {
            this.execute()
        }
    }

    private fun configureSarifReportMojo(mavenProject: MavenProject): GenerateTestsAndSarifReportMojo {
        val generateTestsAndSarifReportMojo = configureMojo(
            GenerateTestsAndSarifReportMojo(), "utbot-maven", mavenProject.file
        ) as GenerateTestsAndSarifReportMojo
        generateTestsAndSarifReportMojo.mavenProject = mavenProject
        return generateTestsAndSarifReportMojo
    }

    private fun directoryExistsAndNotEmpty(directory: File): Boolean =
        directory.exists() && directory.isDirectory && directory.list()?.isNotEmpty() == true
}