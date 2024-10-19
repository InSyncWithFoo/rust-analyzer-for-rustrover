package insyncwithfoo.rustanalyzer.configurationfile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


internal class RefreshConfigurationFileSchemaCache : AnAction(), DumbAware {
    
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        
        project.cachedRRRASchema = null
        
        project.service<Coroutine>().scope.launch {
            CacheConfigurationFileSchema().execute(project)
        }
    }
    
    @Service(Service.Level.PROJECT)
    private class Coroutine(val scope: CoroutineScope)
    
}
