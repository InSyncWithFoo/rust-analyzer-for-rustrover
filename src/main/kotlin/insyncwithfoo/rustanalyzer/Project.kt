package insyncwithfoo.rustanalyzer

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.toNioPathOrNull
import java.nio.file.Path


private val projectManager: ProjectManager
    get() = ProjectManager.getInstance()


internal val openProjects: Sequence<Project>
    get() = projectManager.openProjects.asSequence()


internal val defaultProject: Project
    get() = projectManager.defaultProject


internal val Project.path: Path?
    get() = guessProjectDir()?.toNioPathOrNull()?.toNullIfNotExists()


internal val Project.isNormal: Boolean
    get() = !this.isDefault && !this.isDisposed
