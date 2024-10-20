package insyncwithfoo.rustanalyzer.lsp

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem
import insyncwithfoo.rustanalyzer.RAIcon
import insyncwithfoo.rustanalyzer.configurations.RAConfigurable


@Suppress("UnstableApiUsage")
internal class WidgetItem(lspServer: LspServer, currentFile: VirtualFile?) :
    LspServerWidgetItem(lspServer, currentFile, RAIcon.TINY_16, RAConfigurable::class.java)
