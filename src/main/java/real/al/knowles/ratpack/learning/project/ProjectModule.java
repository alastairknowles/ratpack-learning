package real.al.knowles.ratpack.learning.project;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ProjectModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public ProjectRepository projectRepository() {
        return new ProjectRepository();
    }

    @Provides
    @Singleton
    public ProjectService projectService(ProjectRepository projectRepository) {
        return new ProjectService(projectRepository);
    }

    @Provides
    @Singleton
    public ProjectChain projectChain(ProjectService projectService) {
        return new ProjectChain(projectService);
    }

}
