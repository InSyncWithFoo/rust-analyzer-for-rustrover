package insyncwithfoo.rustanalyzer.other.schema

import com.google.gson.Gson
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import insyncwithfoo.rustanalyzer.PlatformTestCase
import insyncwithfoo.rustanalyzer.configurationfile.reconstructSchema
import org.junit.Test
import kotlin.test.assertContains


internal class RRRASchemaTest : PlatformTestCase() {
    
    private val originalSchemaContent by lazy {
        val arguments = listOf("rust-analyzer", "--print-config-schema")
        val command = GeneralCommandLine(arguments).withCharset(Charsets.UTF_8)
        
        CapturingProcessHandler(command).runProcess().stdout
    }
    
    @Test
    fun `test reconstructuring`() {
        val original = originalSchemaContent
        val reconstructed = project.reconstructSchema(original)
        
        val schema = Gson().fromJson(reconstructed, Map::class.java)
        
        assertEquals("http://json-schema.org/draft-04/schema#", schema["\$schema"])
        checkSubschema(schema)
    }
    
    private fun checkSubschema(subschema: Map<*, *>) {
        val properties = subschema["properties"] as? Map<*, *>
        
        if (properties == null) {
            assertContains(subschema.keys, "description")
            assertContains(subschema.keys, "x-intellij-html-description")
            return
        }
        
        assertEquals(2, subschema.keys.filter { it != "\$schema" }.size)
        assertEquals(false, subschema["additionalProperties"])
        
        properties.forEach { (_, value) -> checkSubschema(value as Map<*, *>) }
    }
    
}
