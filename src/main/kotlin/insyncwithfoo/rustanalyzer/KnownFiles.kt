package insyncwithfoo.rustanalyzer

import com.intellij.openapi.vfs.VirtualFile


internal val configurationFileNames = listOf(
    ".rr-rust-analyzer.json",
    "rr-rust-analyzer.json"
)


internal val VirtualFile.isRustFile: Boolean
    get() = extension == "rs"


internal val VirtualFile.isConfigurationFile: Boolean
    get() = name in configurationFileNames
