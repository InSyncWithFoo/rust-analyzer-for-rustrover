package insyncwithfoo.rustanalyzer.configurations

import com.intellij.openapi.components.RoamingType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import insyncwithfoo.rustanalyzer.configurations.models.ConfigurationService
import insyncwithfoo.rustanalyzer.configurations.models.getMergedState


@State(name = "insyncwithfoo.rustanalyzer.configurations.Global", storages = [Storage("rust-analyzer.xml")])
@Service(Service.Level.APP)
internal class RAGlobalService : ConfigurationService<RAConfigurations>(RAConfigurations()) {
    
    companion object {
        fun getInstance() = service<RAGlobalService>()
    }
    
}


@State(name = "insyncwithfoo.rustanalyzer.configurations.Local", storages = [Storage("rust-analyzer.xml")])
@Service(Service.Level.PROJECT)
internal class RALocalService : ConfigurationService<RAConfigurations>(RAConfigurations()) {
    
    companion object {
        fun getInstance(project: Project) = project.service<RALocalService>()
    }
    
}


@State(
    name = "insyncwithfoo.rustanalyzer.configurations.Override",
    storages = [Storage("rust-analyzer-overrides.xml", roamingType = RoamingType.DISABLED)]
)
@Service(Service.Level.PROJECT)
internal class RAOverrideService : ConfigurationService<RAOverrides>(RAOverrides()) {
    
    companion object {
        fun getInstance(project: Project) = project.service<RAOverrideService>()
    }
    
}


internal val globalRustAnalyzerConfigurations: RAConfigurations
    get() = RAGlobalService.getInstance().state


internal val Project.rustAnalyzerConfigurations: RAConfigurations
    get() = getMergedState<RAGlobalService, RALocalService, RAOverrideService, _>()
