package insyncwithfoo.rustanalyzer

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.intellij.openapi.project.Project
import kotlin.io.path.readText


internal fun Project.createInitializationOptionsObject(): Any {
    val configurationFile = findConfigurationFile() ?: return Object()
    val content = configurationFile.readText()
    
    return try {
        Gson().fromJson(content, Map::class.java)
    } catch (_: JsonSyntaxException) {
        Object()
    }
}
