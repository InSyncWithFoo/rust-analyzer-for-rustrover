package insyncwithfoo.rustanalyzer

import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Path
import kotlin.io.path.name


internal val configurationFileNames = listOf(
    ".rr-rust-analyzer.toml",
    "rr-rust-analyzer.toml",
    ".rr-rust-analyzer.json",
    "rr-rust-analyzer.json"
)


internal val VirtualFile.isRustFile: Boolean
    get() = extension == "rs"


internal val VirtualFile.isRRRA: Boolean
    get() = name in configurationFileNames


internal val Path.isRRRA: Boolean
    get() = name in configurationFileNames
