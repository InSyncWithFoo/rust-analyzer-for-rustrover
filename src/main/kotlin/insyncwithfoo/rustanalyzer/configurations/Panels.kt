package insyncwithfoo.rustanalyzer.configurations

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.panel
import insyncwithfoo.rustanalyzer.bindSelected
import insyncwithfoo.rustanalyzer.bindText
import insyncwithfoo.rustanalyzer.configurationFileNames
import insyncwithfoo.rustanalyzer.configurations.models.AdaptivePanel
import insyncwithfoo.rustanalyzer.configurations.models.Overrides
import insyncwithfoo.rustanalyzer.configurations.models.PanelBasedConfigurable
import insyncwithfoo.rustanalyzer.configurations.models.projectAndOverrides
import insyncwithfoo.rustanalyzer.emptyText
import insyncwithfoo.rustanalyzer.findExecutableInPath
import insyncwithfoo.rustanalyzer.isRRRA
import insyncwithfoo.rustanalyzer.lsp4ijIsAvailable
import insyncwithfoo.rustanalyzer.lspIsAvailable
import insyncwithfoo.rustanalyzer.makeFlexible
import insyncwithfoo.rustanalyzer.message
import insyncwithfoo.rustanalyzer.radioButtonFor
import insyncwithfoo.rustanalyzer.singleFileTextField
import insyncwithfoo.rustanalyzer.toPathOrNull


private class RAPanel(state: RAConfigurations, overrides: Overrides?, project: Project?) :
    AdaptivePanel<RAConfigurations>(state, overrides, project)


private fun Row.executableInput(block: Cell<TextFieldWithBrowseButton>.() -> Unit) =
    singleFileTextField().makeFlexible().apply(block)


private fun Row.configurationFileInput(block: Cell<TextFieldWithBrowseButton>.() -> Unit) =
    singleFileTextField().makeFlexible().comment(message("configurations.configurationFile.comment")).apply(block)


private fun Panel.runningModeInputGroup(block: Panel.() -> Unit) =
    buttonsGroup(init = block)


private fun RAPanel.makeComponent() = panel {
    
    row(message("configurations.executable.label")) {
        executableInput {
            val detected = findExecutableInPath("rust-analyzer")?.toString()
            
            bindText(state::executable) { detected.orEmpty() }
            emptyText = detected.orEmpty()
        }
        overrideCheckbox(state::executable)
    }
    
    row(message("configurations.configurationFile.label")) {
        configurationFileInput {
            bindText(state::configurationFile)
            
            validationOnApply { field ->
                val value = field.text.takeIf { it.isNotBlank() } ?: return@validationOnApply null
                val path = value.toPathOrNull()
                val expectedFileNames = configurationFileNames.joinToString(", ")
                
                when {
                    path == null -> error(message("configurations.configurationFile.invalidPath"))
                    !path.isRRRA -> error(message("configurations.configurationFile.unknown", expectedFileNames))
                    else -> null
                }
            }
        }
        overrideCheckbox(state::configurationFile)
    }
    
    val runningModeInputGroup = runningModeInputGroup {
        row(message("configurations.runningMode.label")) {
            radioButtonFor(RunningMode.DISABLED)
            radioButtonFor(RunningMode.LSP4IJ) { label ->
                message("configurations.runningMode.unavailable", label).takeUnless { lsp4ijIsAvailable }
            }
            radioButtonFor(RunningMode.LSP) { label ->
                message("configurations.runningMode.unavailable", label).takeUnless { lspIsAvailable }
            }
            
            overrideCheckbox(state::runningMode)
        }
    }
    runningModeInputGroup.bindSelected(state::runningMode)
    
}


internal fun PanelBasedConfigurable<RAConfigurations>.createPanel(state: RAConfigurations): DialogPanel {
    val (project, overrides) = projectAndOverrides
    return RAPanel(state, overrides, project).makeComponent()
}
