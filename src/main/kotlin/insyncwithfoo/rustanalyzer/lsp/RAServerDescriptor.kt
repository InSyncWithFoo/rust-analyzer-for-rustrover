package insyncwithfoo.rustanalyzer.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerConfigurations
import insyncwithfoo.rustanalyzer.createInitializationOptionsObject
import insyncwithfoo.rustanalyzer.isRustFile
import insyncwithfoo.rustanalyzer.message
import insyncwithfoo.rustanalyzer.path
import java.nio.file.Path


@Suppress("UnstableApiUsage")
internal class RAServerDescriptor(project: Project, private val executable: Path) :
    ProjectWideLspServerDescriptor(project, PRESENTABLE_NAME) {
    
    private val configurations = project.rustAnalyzerConfigurations
    
    init {
        LOGGER.info(configurations.toString())
    }
    
    override fun isSupportedFile(file: VirtualFile) =
        file.isRustFile
    
    override fun createInitializationOptions() =
        project.createInitializationOptionsObject()
    
    override fun createCommandLine() = GeneralCommandLine().apply {
        withWorkingDirectory(project.path)
        withCharset(Charsets.UTF_8)
        
        withExePath(executable.toString())
    }
    
    companion object {
        private val LOGGER by ::LOG
        private val PRESENTABLE_NAME = message("languageServer.presentableName")
    }
    
}
