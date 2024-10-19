package insyncwithfoo.rustanalyzer.configurationfile

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.ToNumberPolicy
import com.intellij.lang.Language
import com.intellij.markdown.utils.doc.DocMarkdownToHtmlConverter
import com.intellij.openapi.project.Project


private typealias JSONObject = Map<String, Any?>
private typealias MutableJSONObject = MutableMap<String, Any?>


private fun makeJSONObject(): MutableJSONObject =
    mutableMapOf()


@Suppress("UNCHECKED_CAST")
internal fun Project.reconstructSchema(content: String): String? {
    val gson = GsonBuilder().run {
        serializeNulls()
        disableHtmlEscaping()
        setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        
        create()
    }
    
    val malformed = try {
        gson.fromJson(content, List::class.java)
    } catch (_: JsonSyntaxException) {
        return null
    }
    
    val reconstructed = makeJSONObject()
    reconstructed["\$schema"] = "http://json-schema.org/draft-04/schema#"
    
    val properties = malformed.flatMap {
        ((it as JSONObject)["properties"] as JSONObject).entries
    }

    for ((dotSeparatedName, propertySubschema) in properties) {
        val keyChain = dotSeparatedName.split(".")

        val leafSchema = keyChain.fold(reconstructed) { subschema, fragment ->
            subschema.putIfAbsent("additionalProperties", false)
            subschema.putIfAbsent("properties", makeJSONObject())
            
            val subschemaProperties = subschema["properties"] as MutableJSONObject
            subschemaProperties.getOrPut(fragment) { makeJSONObject() } as MutableJSONObject
        }

        val newSubschema = (propertySubschema as JSONObject).toMutableMap()
        val description = newSubschema.remove("markdownDescription") ?: continue
        
        newSubschema["description"] = description
        newSubschema["x-intellij-html-description"] = DocMarkdownToHtmlConverter.convert(
            project = this,
            markdownText = description as String,
            defaultLanguage = Language.findLanguageByID("rust")
        )
        
        newSubschema.forEach { (key, value) ->
            leafSchema[key] = value
        }
    }
    
    return gson.toJson(reconstructed)
}
