package insyncwithfoo.rustanalyzer.lsp

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem
import insyncwithfoo.rustanalyzer.Icon
import insyncwithfoo.rustanalyzer.configurations.RAConfigurable


@Suppress("UnstableApiUsage")
internal class WidgetItem(lspServer: LspServer, currentFile: VirtualFile?) :
    LspServerWidgetItem(lspServer, currentFile, Icon.TINY_16_DARK, RAConfigurable::class.java)
