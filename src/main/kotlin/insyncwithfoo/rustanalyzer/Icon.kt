package insyncwithfoo.rustanalyzer

import com.intellij.openapi.util.IconLoader


private fun loadIcon(path: String) = IconLoader.getIcon(path, Icon::class.java)


internal object Icon {
    val TINY_16_LIGHT by lazy { loadIcon("icons/16-light.svg") }
    val TINY_16_DARK by lazy { loadIcon("icons/16-dark.svg") }
}
