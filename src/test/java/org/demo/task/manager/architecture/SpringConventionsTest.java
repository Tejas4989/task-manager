package org.demo.task.manager.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import org.demo.task.manager.TaskManagerApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packagesOf = TaskManagerApplication.class, importOptions = {
    ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
class SpringConventionsTest {

  @ArchTest
  static final ArchRule SPRING_CONFIG_CLASSES_SHOULD_BE_PACKAGE_PRIVATE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Configuration.class).should().bePackagePrivate();

  @ArchTest
  static final ArchRule SPRING__CONFIG_CLASSES_SHOULD_BE_IN_A_COMMON_CONFIG_PACKAGE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Configuration.class).should().resideInAPackage("..common.config..");

  @ArchTest
  static final ArchRule SPRING_CONTROLLERS_SHOULD_BE_PACKAGE_PRIVATE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(RestController.class).should().bePackagePrivate();

  @ArchTest
  static final ArchRule SPRING_CONTROLLERS_SHOULD_BE_IN_A_WEB_PACKAGE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(RestController.class).should().resideInAPackage("..web..");

  @ArchTest
  static final ArchRule SPRING_CONTROLLERS_NAMES_SHOULD_END_WITH_CONTROLLER = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(RestController.class).should()
      .haveSimpleNameEndingWith("Controller");

  @ArchTest
  static final ArchRule SPRING_CONTROLLER_SHOULD_HAVE_REQUEST_MAPPING_ANNOTATION = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(RestController.class).should().beAnnotatedWith(RequestMapping.class);

  @ArchTest
  static final ArchRule SPRING_SERVICES_SHOULD_BE_IN_A_SERVICE_PACKAGE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Service.class).should().resideInAPackage("..service..");

  @ArchTest
  static final ArchRule SPRING_SERVICE_NAMES_SHOULD_END_WITH_SERVICE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Service.class).should().haveSimpleNameEndingWith("Service");

  @ArchTest
  static final ArchRule SPRING_REPOSITORIES_SHOULD_BE_IN_A_DOMAIN_REPOSITORY_PACKAGE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Repository.class).should().resideInAPackage("..domain.repository..");

  @ArchTest
  static final ArchRule SPRING_REPOSITORY_NAMES_SHOULD_END_WITH_REPOSITORY = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Repository.class).should().haveSimpleNameEndingWith("Repository");

  @ArchTest
  static final ArchRule SPRING_REPOSITORIES_SHOULD_BE_ANNOTATED_WITH_REPOSITORY = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Repository.class).and().haveSimpleNameEndingWith("Repository")
      .should().beAnnotatedWith(Repository.class);

  @ArchTest
  static final ArchRule SPRING_CONFIGURATIONS_SHOULD_BE_IN_A_COMMON_CONFIG_PACKAGE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Configuration.class).should().resideInAPackage("..common.config..");

  @ArchTest
  static final ArchRule SPRING_CONFIGURATION_NAMES_SHOULD_END_WITH_CONFIG = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Configuration.class).should().haveSimpleNameEndingWith("Config");

  @ArchTest
  static final ArchRule VALUEOBJECT_NAMES_SHOULD_NOT_HAVE_ANY_SUFFIXES = ArchRuleDefinition.classes()
      .that().haveSimpleNameEndingWith("VO").and()
      .resideOutsideOfPackage("..domain.dto..external..").or()
      .haveSimpleNameEndingWith("ValueObject").and()
      .resideOutsideOfPackage("..domain.dto..external..").or()
      .haveSimpleNameEndingWith("DTO").and()
      .resideOutsideOfPackage("..domain.dto..external..").should()
      .haveSimpleNameNotEndingWith("VO").andShould().haveSimpleNameNotEndingWith("ValueObject")
      .andShould().haveSimpleNameNotEndingWith("DTO").allowEmptyShould(true);


  @ArchTest
  static final ArchRule VALIDATION_CONSTRAINTS_SHOULD_BE_IN_A_DOMAIN_VALIDATION_CONSTRAINT_PACKAGE = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Constraint.class).should()
      .resideInAPackage("..domain.validation.constraint..").allowEmptyShould(true);

  @ArchTest
  static final ArchRule VALIDATION_CONSTRAINT_NAMES_SHOULD_END_WITH_CONSTRAINT = ArchRuleDefinition.classes()
      .that().areAnnotatedWith(Constraint.class).should().haveSimpleNameEndingWith("Constraint").allowEmptyShould(true);

  @ArchTest
  static final ArchRule VALIDATION_VALIDATORS_SHOULD_BE_IN_A_DOMAIN_VALIDATION_VALIDATOR_PACKAGE = ArchRuleDefinition.classes()
      .that().implement(ConstraintValidator.class).should()
      .resideInAPackage("..domain.validation.validator..").allowEmptyShould(true);
}
