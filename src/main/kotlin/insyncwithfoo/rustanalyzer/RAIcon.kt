package insyncwithfoo.rustanalyzer

import com.intellij.openapi.util.IconLoader
import com.intellij.ui.JBColor
import javax.swing.Icon


private fun loadIcon(path: String) = IconLoader.getIcon(path, RAIcon::class.java)


internal object RAIcon {
    private val TINY_16_LIGHT by lazy { loadIcon("icons/16-light.svg") }
    private val TINY_16_DARK by lazy { loadIcon("icons/16-dark.svg") }
    
    val TINY_16: Icon
        get() = when (JBColor.isBright()) {
            true -> TINY_16_LIGHT
            else -> TINY_16_DARK
        }
}
