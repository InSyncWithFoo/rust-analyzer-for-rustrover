package insyncwithfoo.rustanalyzer

import com.intellij.openapi.vfs.VirtualFile


internal val VirtualFile.isRustFile: Boolean
    get() = extension == "rs"
