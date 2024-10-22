package insyncwithfoo.rustanalyzer

import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.google.gson.Gson
import com.intellij.openapi.project.Project
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.readText


private fun interface Invocable<T> {
    operator fun invoke(): T
}


private fun Path.parseContent(): Result<Any> {
    val text = readText()
    val parser = when (extension.lowercase()) {
        "toml" -> Invocable { TomlMapper().readValue(text, Map::class.java) }
        else -> Invocable { Gson().fromJson(text, Map::class.java) }
    }
    
    return try {
        Result.success(parser())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}


private fun Project.reportInvalidInitializationOptions(file: Path) {
    val title = message("notifications.invalidInitializationOptions.title")
    val content = message("notifications.invalidInitializationOptions.content", file)
    
    reportError(title, content)
}


internal fun Project.createInitializationOptionsObject(): Any {
    val configurationFile = findConfigurationFile() ?: return Object()
    
    return configurationFile.parseContent().getOrElse {
        reportInvalidInitializationOptions(configurationFile)
        Object()
    }
}
