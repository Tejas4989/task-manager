/*
package org.demo.task.manager.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.demo.task.manager.TaskManagerApplication;
import org.junit.jupiter.api.DisplayName;

@AnalyzeClasses(packagesOf = TaskManagerApplication.class, importOptions = {
    ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
@DisplayName("Basic cyclic dependencies test")
class CyclicDependencyRulesTest {

  @ArchTest
  static final ArchRule NO_CYCLES_BY_METHOD_CALLS_BETWEEN_SLICES = SlicesRuleDefinition.slices()
      .matching("..(simplecycle).(*)..").namingSlices("$2 of $1").should().beFreeOfCycles();

  @ArchTest
  static final ArchRule NO_CYCLES_BY_CONSTRUCTOR_CALLS_BETWEEN_SLICES = SlicesRuleDefinition.slices()
      .matching("..(constructorcycle).(*)..").namingSlices("$2 of $1").should().beFreeOfCycles();

  @ArchTest
  static final ArchRule NO_CYCLES_BY_INHERITANCE_BETWEEN_SLICES = SlicesRuleDefinition.slices()
      .matching("..(inheritancecycle).(*)..").namingSlices("$2 of $1").should().beFreeOfCycles();

  @ArchTest
  static final ArchRule NO_CYCLES_BY_FIELD_ACCESS_BETWEEN_SLICES = SlicesRuleDefinition.slices()
      .matching("..(fieldaccesscycle).(*)..").namingSlices("$2 of $1").should().beFreeOfCycles();

}
*/
