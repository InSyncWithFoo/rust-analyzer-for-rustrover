package insyncwithfoo.rustanalyzer.lsp4ij

import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl


internal class RAServerClient(project: Project) : LanguageClientImpl(project) {
    
    override fun createSettings() = Object()
    
}
