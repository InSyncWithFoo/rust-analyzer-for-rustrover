package insyncwithfoo.rustanalyzer

import com.intellij.openapi.util.IconLoader


private fun loadIcon(path: String) = IconLoader.getIcon(path, Icon::class.java)


internal object Icon {
    val TINY_16 by lazy { loadIcon("icons/16.svg") }
}
