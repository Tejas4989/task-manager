package org.demo.task.manager.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.GeneralCodingRules;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.demo.task.manager.TaskManagerApplication;

@AnalyzeClasses(packagesOf = TaskManagerApplication.class,
    importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
@DisplayName("Basic coding conventions should be applied")
class CodingRulesTest {

  @ArchTest
  private static final ArchRule NO_USAGE_OF_STDOUT_OR_STDERR = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

  @ArchTest
  private static final ArchRule NO_JDK_LOGGING_INSTEAD_OF_SLF4J = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

  @ArchTest
  private static final ArchRule NO_CLASSES_SHOULD_USE_JODATIME = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

  @ArchTest
  private static final ArchRule NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  private static final ArchRule LOGGERS_SHOULD_BE_PRIVATE_STATIC_FINAL =
      ArchRuleDefinition.fields().that().haveRawType(Logger.class)
          .should().bePrivate()
          .andShould().beStatic()
          .andShould().beFinal()
          .andShould().haveName("log")
          .because("We agreed on this convention.");
}
