package insyncwithfoo.rustanalyzer.configurations

import insyncwithfoo.rustanalyzer.configurations.models.fields
import insyncwithfoo.rustanalyzer.message
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance


internal class RAConfigurationsTest {
    
    private val configurationClass = RAConfigurations::class
    
    private lateinit var state: RAConfigurations
    private lateinit var fields: Map<String, KProperty1<RAConfigurations, *>>
    
    @Before
    fun setUp() {
        state = configurationClass.createInstance()
        fields = configurationClass.fields
    }
    
    @Test
    fun `test shape`() {
        assertEquals(2, fields.size)
        
        assertEquals(null, state.executable)
        assertEquals(RunningMode.LSP, state.runningMode)
    }
    
    @Test
    fun `test messages`() {
        fields.forEach { (name, _) ->
            val key = "configurations.$name.label"
            
            assertNotEquals("!$key!", message(key))
        }
    }
    
}
