package insyncwithfoo.rustanalyzer.lsp4ij

import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.server.ProcessStreamConnectionProvider
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerExecutable
import insyncwithfoo.rustanalyzer.path


internal class RAServerConnectionProvider(commands: List<String>, workingDirectory: String?) :
    ProcessStreamConnectionProvider(commands, workingDirectory) {
    
    companion object {
        fun create(project: Project): RAServerConnectionProvider {
            val executable = project.rustAnalyzerExecutable!!
            
            val fragments: List<String> = listOf(executable.toString(), "lsp", "stdio")
            val workingDirectory = project.path?.toString()
            
            return RAServerConnectionProvider(fragments, workingDirectory)
        }
    }
    
}
