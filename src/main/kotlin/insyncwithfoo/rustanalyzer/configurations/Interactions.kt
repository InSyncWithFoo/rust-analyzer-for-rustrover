package insyncwithfoo.rustanalyzer.configurations

import com.intellij.openapi.project.Project
import insyncwithfoo.rustanalyzer.findExecutableInPath
import insyncwithfoo.rustanalyzer.toPathIfItExists
import java.nio.file.Path


internal val Project.rustAnalyzerExecutable: Path?
    get() = rustAnalyzerConfigurations.executable?.toPathIfItExists()
        ?: findExecutableInPath("rust-analyzer")
