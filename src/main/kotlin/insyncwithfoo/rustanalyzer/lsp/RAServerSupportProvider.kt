package insyncwithfoo.rustanalyzer.lsp

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.LspServerSupportProvider.LspServerStarter
import insyncwithfoo.rustanalyzer.configurations.RunningMode
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerConfigurations
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerExecutable
import insyncwithfoo.rustanalyzer.isRustFile


@Suppress("UnstableApiUsage")
internal class RAServerSupportProvider : LspServerSupportProvider {
    
    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?) =
        WidgetItem(lspServer, currentFile)
    
    override fun fileOpened(project: Project, file: VirtualFile, serverStarter: LspServerStarter) {
        val configurations = project.rustAnalyzerConfigurations
        val runningModeIsLSP = configurations.runningMode == RunningMode.LSP
        
        if (runningModeIsLSP && file.isRustFile) {
            val executable = project.rustAnalyzerExecutable ?: return
            serverStarter.ensureServerStarted(RAServerDescriptor(project, executable))
        }
    }
    
}
