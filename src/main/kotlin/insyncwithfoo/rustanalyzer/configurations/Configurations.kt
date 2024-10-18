package insyncwithfoo.rustanalyzer.configurations

import insyncwithfoo.rustanalyzer.Labeled
import insyncwithfoo.rustanalyzer.configurations.models.Copyable
import insyncwithfoo.rustanalyzer.configurations.models.DisplayableState
import insyncwithfoo.rustanalyzer.configurations.models.ProjectOverrideState
import insyncwithfoo.rustanalyzer.configurations.models.SettingName
import insyncwithfoo.rustanalyzer.message


internal enum class RunningMode(override val label: String) : Labeled {
    DISABLED(message("configurations.runningMode.disabled")),
    LSP4IJ(message("configurations.runningMode.lsp4ij")),
    LSP(message("configurations.runningMode.lsp"));
}


internal class RAConfigurations : DisplayableState(), Copyable {
    var executable by string(null)
    var runningMode by enum(RunningMode.LSP)
}


internal class RAOverrides : DisplayableState(), ProjectOverrideState {
    override var names by map<SettingName, Boolean>()
}
