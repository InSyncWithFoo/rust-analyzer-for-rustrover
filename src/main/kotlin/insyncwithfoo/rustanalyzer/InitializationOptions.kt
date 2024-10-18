package insyncwithfoo.rustanalyzer

import com.intellij.openapi.project.Project


internal interface Builder


internal operator fun <B : Builder> B.invoke(block: B.() -> Unit) {
    this.apply(block)
}


internal class InitializationOptions


// TODO: Allow configuring initialization options
internal fun Project.createInitializationOptionsObject() = InitializationOptions()
