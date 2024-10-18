package insyncwithfoo.rustanalyzer.lsp4ij

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.redhat.devtools.lsp4ij.AbstractDocumentMatcher
import insyncwithfoo.rustanalyzer.isRustFile


internal class RAServerDocumentMatcher : AbstractDocumentMatcher() {
    
    override fun match(file: VirtualFile, project: Project) =
        file.isRustFile
    
}
