package org.utbot.examples.codegen

import org.junit.jupiter.api.Test
import org.utbot.testcheckers.eq
import org.utbot.testing.DoNotCalculate
import org.utbot.testing.UtValueTestCaseChecker

@Suppress("INACCESSIBLE_TYPE")
internal class ClassWithStaticAndInnerClassesTest : UtValueTestCaseChecker(
    testClass = ClassWithStaticAndInnerClasses::class,
    configurations = ignoreKotlinCompilationConfigurations,
) {
    @Test
    fun testUsePrivateStaticClassWithPrivateField() {
        check(
            ClassWithStaticAndInnerClasses::usePrivateStaticClassWithPrivateField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePrivateStaticClassWithPublicField() {
        check(
            ClassWithStaticAndInnerClasses::usePrivateStaticClassWithPublicField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePublicStaticClassWithPrivateField() {
        check(
            ClassWithStaticAndInnerClasses::usePublicStaticClassWithPrivateField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePublicStaticClassWithPublicField() {
        check(
            ClassWithStaticAndInnerClasses::usePublicStaticClassWithPublicField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePrivateInnerClassWithPrivateField() {
        check(
            ClassWithStaticAndInnerClasses::usePrivateInnerClassWithPrivateField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePrivateInnerClassWithPublicField() {
        check(
            ClassWithStaticAndInnerClasses::usePrivateInnerClassWithPublicField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePublicInnerClassWithPrivateField() {
        check(
            ClassWithStaticAndInnerClasses::usePublicInnerClassWithPrivateField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePublicInnerClassWithPublicField() {
        check(
            ClassWithStaticAndInnerClasses::usePublicInnerClassWithPublicField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePackagePrivateFinalStaticClassWithPackagePrivateField() {
        check(
            ClassWithStaticAndInnerClasses::usePackagePrivateFinalStaticClassWithPackagePrivateField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testUsePackagePrivateFinalInnerClassWithPackagePrivateField() {
        check(
            ClassWithStaticAndInnerClasses::usePackagePrivateFinalInnerClassWithPackagePrivateField,
            eq(2),
            coverage = DoNotCalculate
        )
    }

    @Test
    fun testGetValueFromPublicFieldWithPrivateType() {
        check(
            ClassWithStaticAndInnerClasses::getValueFromPublicFieldWithPrivateType,
            eq(2),
            coverage = DoNotCalculate
        )
    }
}