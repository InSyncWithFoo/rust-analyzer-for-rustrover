package insyncwithfoo.rustanalyzer.lsp4ij

import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.LanguageServerEnablementSupport
import com.redhat.devtools.lsp4ij.LanguageServerFactory
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider
import insyncwithfoo.rustanalyzer.configurations.RALocalService
import insyncwithfoo.rustanalyzer.configurations.RunningMode
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerConfigurations
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerExecutable


internal const val SERVER_ID = "insyncwithfoo.rustanalyzer"


internal class RAServerFactory : LanguageServerFactory, LanguageServerEnablementSupport {
    
    override fun isEnabled(project: Project): Boolean {
        val configurations = project.rustAnalyzerConfigurations
        val executable = project.rustAnalyzerExecutable
        
        return configurations.runningMode == RunningMode.LSP4IJ && executable != null
    }
    
    override fun setEnabled(enabled: Boolean, project: Project) {
        RALocalService.getInstance(project).state.apply {
            runningMode = when {
                enabled -> RunningMode.LSP4IJ
                else -> RunningMode.DISABLED
            }
        }
    }
    
    override fun createConnectionProvider(project: Project): StreamConnectionProvider {
        return RAServerConnectionProvider.create(project)
    }
    
    override fun createLanguageClient(project: Project): LanguageClientImpl {
        return RAServerClient(project)
    }
    
}
