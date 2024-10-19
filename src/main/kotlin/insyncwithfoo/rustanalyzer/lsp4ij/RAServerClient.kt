package insyncwithfoo.rustanalyzer.lsp4ij

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl
import insyncwithfoo.rustanalyzer.createInitializationOptionsObject


internal class RAServerClient(project: Project) : LanguageClientImpl(project) {
    
    override fun createSettings() =
        project.createInitializationOptionsObject().also { LOGGER.info(it.toString()) }
    
    companion object {
        val LOGGER = Logger.getInstance(RAServerClient::class.java)
    }
    
}
