package insyncwithfoo.rustanalyzer.configurationfile

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware


internal class RefreshRRRASchemaCache : AnAction(), DumbAware {
    
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        
        project.cachedRRRASchema = null
        
        CacheRRRASchema().executeInBackgroundThread(project)
    }
    
}
