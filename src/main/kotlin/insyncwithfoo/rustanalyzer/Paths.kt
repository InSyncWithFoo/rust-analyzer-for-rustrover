package insyncwithfoo.rustanalyzer

import com.intellij.execution.configurations.PathEnvironmentVariableUtil
import com.intellij.openapi.util.SystemInfo
import java.nio.file.InvalidPathException
import java.nio.file.Path
import kotlin.io.path.exists


private val Path.isEmpty: Boolean
    get() = this.toString() == ""


internal fun String.toPathOrNull() =
    try {
        Path.of(this).takeUnless { it.isEmpty }
    } catch (_: InvalidPathException) {
        null
    }


internal fun String.toPathIfItExists() =
    this.toPathOrNull()?.normalize()?.toNullIfNotExists()


internal fun String.toOSDependentFileName() = when {
    SystemInfo.isWindows -> "$this.exe"
    else -> this
}


internal fun Path.toNullIfNotExists() =
    this.takeIf { it.exists() }


internal fun findExecutableInPath(name: String): Path? {
    return PathEnvironmentVariableUtil.findInPath(name.toOSDependentFileName())?.toPath()
}
