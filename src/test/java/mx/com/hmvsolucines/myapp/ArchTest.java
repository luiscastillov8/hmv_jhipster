package mx.com.hmvsolucines.myapp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("mx.com.hmvsolucines.myapp");

        noClasses()
            .that()
            .resideInAnyPackage("mx.com.hmvsolucines.myapp.service..")
            .or()
            .resideInAnyPackage("mx.com.hmvsolucines.myapp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..mx.com.hmvsolucines.myapp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
