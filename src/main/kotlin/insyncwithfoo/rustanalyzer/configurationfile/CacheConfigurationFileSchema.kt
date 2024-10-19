package insyncwithfoo.rustanalyzer.configurationfile

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.application.readAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerExecutable
import insyncwithfoo.rustanalyzer.propertiesComponent


private typealias JSON = String

private const val STORAGE_KEY = "insyncwithfoo.rustanalyzer.configurationFileSchema"


internal var Project.cachedRRRASchema: JSON?
    get() = propertiesComponent.getValue(STORAGE_KEY)
    set(value) = propertiesComponent.setValue(STORAGE_KEY, value)


internal class CacheConfigurationFileSchema : ProjectActivity {
    
    override suspend fun execute(project: Project) {
        readAction { retrieveAndStoreSchemaContent(project) }
    }
    
    private fun retrieveAndStoreSchemaContent(project: Project): String? {
        val executable = project.rustAnalyzerExecutable?.toString() ?: return null
        
        return when (val cachedSchema = project.cachedRRRASchema) {
            null -> project.retrieveNewSchemaContent(executable)?.also { project.cachedRRRASchema = it }
            else -> cachedSchema
        }
    }
    
    private fun Project.retrieveNewSchemaContent(executable: String): String? {
        val command = CapturingProcessHandler(getPrintConfigSchemaCommand(executable))
        val output = command.runProcess(COMMAND_TIMEOUT_IN_MILLISECONDS)
        
        if (output.isTimeout || output.isCancelled || output.exitCode != 0) {
            return null
        }
        
        return reconstructSchema(output.stdout)
    }
    
    private fun getPrintConfigSchemaCommand(executable: String) =
        GeneralCommandLine(executable, "--print-config-schema").withCharset(Charsets.UTF_8)
    
    companion object {
        private const val COMMAND_TIMEOUT_IN_MILLISECONDS = 10_000
    }
    
}
