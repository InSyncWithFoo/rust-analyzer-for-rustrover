package insyncwithfoo.rustanalyzer

import com.intellij.openapi.project.Project
import insyncwithfoo.rustanalyzer.configurations.rustAnalyzerConfigurations
import java.nio.file.Path
import kotlin.io.path.div


private val Project.specified: Path?
    get() {
        val filePath = rustAnalyzerConfigurations.configurationFile?.toPathOrNull()
        val projectPath = this.path
        
        return when {
            filePath?.isAbsolute == true -> filePath
            else -> filePath?.let { projectPath?.resolve(it) }
        }
    }


private fun Project.detect(): Path? {
    val projectPath = this.path ?: return null
    
    return configurationFileNames.firstNotNullOfOrNull { (projectPath / it).toNullIfNotExists() }
}


internal fun Project.findConfigurationFile() =
    specified ?: detect()
