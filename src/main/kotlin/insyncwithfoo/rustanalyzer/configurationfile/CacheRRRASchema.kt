package insyncwithfoo.rustanalyzer.configurationfile

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.application.readAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import insyncwithfoo.rustanalyzer.completedAbnormally
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerExecutable
import insyncwithfoo.rustanalyzer.message
import insyncwithfoo.rustanalyzer.propertiesComponent
import insyncwithfoo.ryecharm.runInBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private typealias JSON = String

private const val STORAGE_KEY = "insyncwithfoo.rustanalyzer.configurationFileSchema"


internal var Project.cachedRRRASchema: JSON?
    get() = propertiesComponent.getValue(STORAGE_KEY)
    set(value) = propertiesComponent.setValue(STORAGE_KEY, value)


internal class CacheRRRASchema : ProjectActivity {
    
    override suspend fun execute(project: Project) {
        retrieveAndStoreSchemaContent(project)
    }
    
    fun executeInBackgroundThread(project: Project) =
        project.service<Coroutine>().scope.launch { execute(project) }
    
    private suspend fun retrieveAndStoreSchemaContent(project: Project): String? {
        val executable = project.rustAnalyzerExecutable?.toString() ?: return null
        
        return when (val cachedSchema = project.cachedRRRASchema) {
            null -> project.retrieveNewSchemaContent(executable)?.also { project.cachedRRRASchema = it }
            else -> cachedSchema
        }
    }
    
    private suspend fun Project.retrieveNewSchemaContent(executable: String): String? {
        val command = CapturingProcessHandler(getPrintConfigSchemaCommand(executable))
        val output = runInBackground(message("progresses.retrieveSchema")) {
            command.runProcess(COMMAND_TIMEOUT_IN_MILLISECONDS)
        }
        
        if (output.completedAbnormally) {
            return null
        }
        
        return readAction { reconstructSchema(output.stdout) }
    }
    
    private fun getPrintConfigSchemaCommand(executable: String) =
        GeneralCommandLine(executable, "--print-config-schema").withCharset(Charsets.UTF_8)
    
    @Service(Service.Level.PROJECT)
    private class Coroutine(val scope: CoroutineScope)
    
    companion object {
        private const val COMMAND_TIMEOUT_IN_MILLISECONDS = 10_000
    }
    
}
