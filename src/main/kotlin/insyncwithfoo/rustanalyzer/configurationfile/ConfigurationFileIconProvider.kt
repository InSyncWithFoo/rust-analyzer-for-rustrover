package insyncwithfoo.rustanalyzer.configurationfile

import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import insyncwithfoo.rustanalyzer.RAIcon
import insyncwithfoo.rustanalyzer.isConfigurationFile


internal class ConfigurationFileIconProvider : FileIconProvider {
    
    override fun getIcon(file: VirtualFile, flags: Int, project: Project?) =
        when {
            file.isConfigurationFile -> RAIcon.TINY_16
            else -> null
        }
    
}
