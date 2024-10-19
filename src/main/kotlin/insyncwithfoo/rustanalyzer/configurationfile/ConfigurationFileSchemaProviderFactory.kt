package insyncwithfoo.rustanalyzer.configurationfile

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory
import com.jetbrains.jsonSchema.extension.SchemaType
import insyncwithfoo.rustanalyzer.isConfigurationFile
import insyncwithfoo.rustanalyzer.message


private class RRRASchemaProvider(private val project: Project) : JsonSchemaFileProvider {
    
    override fun getName() = message("languageServer.settingsSchema.name")
    
    override fun isUserVisible() = false
    
    override fun getSchemaType() = SchemaType.schema
    
    override fun isAvailable(file: VirtualFile) =
        file.isConfigurationFile && project.cachedRRRASchema != null
    
    override fun getSchemaFile() =
        LightVirtualFile("rr-rust-analyzer.schema.json", project.cachedRRRASchema!!)
    
}


internal class ConfigurationFileSchemaProviderFactory : JsonSchemaProviderFactory {
    
    override fun getProviders(project: Project): List<JsonSchemaFileProvider> {
        return listOf(RRRASchemaProvider(project))
    }
    
}
