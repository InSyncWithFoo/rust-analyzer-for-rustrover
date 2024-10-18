package insyncwithfoo.rustanalyzer.configurations

import com.intellij.openapi.project.Project
import insyncwithfoo.rustanalyzer.configurations.models.PanelBasedConfigurable
import insyncwithfoo.rustanalyzer.configurations.models.ProjectBasedConfigurable
import insyncwithfoo.rustanalyzer.configurations.models.copy
import insyncwithfoo.rustanalyzer.lsp.RAServerSupportProvider
import insyncwithfoo.rustanalyzer.lsp4ij.SERVER_ID
import insyncwithfoo.rustanalyzer.message
import insyncwithfoo.rustanalyzer.openProjects
import insyncwithfoo.rustanalyzer.restartNativeServers
import insyncwithfoo.rustanalyzer.toggleLSP4IJServers


private fun Project.toggleServers() {
    restartNativeServers<RAServerSupportProvider>()
    toggleLSP4IJServers(SERVER_ID, restart = rustAnalyzerConfigurations.runningMode == RunningMode.LSP4IJ)
}


internal class RAConfigurable : PanelBasedConfigurable<RAConfigurations>() {
    
    private val service = RAGlobalService.getInstance()
    
    override val state = service.state.copy()
    override val panel by lazy { createPanel(state) }
    
    override fun getDisplayName() = message("configurations.displayName")
    
    override fun afterApply() {
        syncStateWithService(state, service.state)
        
        openProjects.forEach { project ->
            project.toggleServers()
        }
    }
    
}


internal class RAProjectConfigurable(override val project: Project) :
    PanelBasedConfigurable<RAConfigurations>(), ProjectBasedConfigurable {
    
    private val service = RALocalService.getInstance(project)
    private val overrideService = RAOverrideService.getInstance(project)
    private val overrideState = overrideService.state.copy()
    
    override val state = service.state.copy()
    override val overrides by lazy { overrideState.names }
    override val panel by lazy { createPanel(state) }
    
    override fun afterApply() {
        syncStateWithService(state, service.state)
        syncStateWithService(overrideState, overrideService.state)
        
        project.toggleServers()
    }
    
}
