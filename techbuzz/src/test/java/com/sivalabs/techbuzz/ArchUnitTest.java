package com.sivalabs.techbuzz;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

class ArchUnitTest {

    JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sivalabs.techbuzz");

    @Test
    void shouldNotUseFieldInjection() {
        noFields().should().beAnnotatedWith(Autowired.class).check(importedClasses);
    }

    @ParameterizedTest
    @CsvSource({"posts", "users"})
    void domainShouldNotDependOnOtherPackages(String module) {
        noClasses()
                .that()
                .resideInAnyPackage("com.sivalabs.techbuzz." + module + ".domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "com.sivalabs.techbuzz." + module + ".adapter..", "com.sivalabs.techbuzz." + module + ".web..")
                .because("Domain classes should not depend on web or adapter layer")
                .check(importedClasses);
    }

    @Test
    void shouldNotUseJunit4Classes() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.sivalabs.techbuzz");

        noClasses()
                .should()
                .accessClassesThat()
                .resideInAnyPackage("org.junit")
                .because("Tests should use Junit5 instead of Junit4")
                .check(classes);

        noMethods()
                .should()
                .beAnnotatedWith("org.junit.Test")
                .orShould()
                .beAnnotatedWith("org.junit.Ignore")
                .because("Tests should use Junit5 instead of Junit4")
                .check(classes);
    }
}
