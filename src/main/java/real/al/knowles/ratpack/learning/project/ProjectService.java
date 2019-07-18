package real.al.knowles.ratpack.learning.project;

import com.google.inject.Inject;
import ratpack.exec.Promise;
import real.al.knowles.ratpack.learning.database.DatabaseExecutor;

import java.time.LocalDateTime;

public class ProjectService {

    private final ProjectRepository projectRepository;

    private final DatabaseExecutor databaseExecutor;

    @Inject
    public ProjectService(ProjectRepository projectRepository, DatabaseExecutor databaseExecutor) {
        this.projectRepository = projectRepository;
        this.databaseExecutor = databaseExecutor;
    }

    public Promise<ProjectRepresentation> createProject(ProjectRepresentation projectRepresentation) {
        LocalDateTime now = LocalDateTime.now();
        projectRepresentation.setCreatedOn(now);
        projectRepresentation.setUpdatedOn(now);

        return databaseExecutor.executeInTransaction(() ->
                projectRepository.createProject(projectRepresentation))
                .map(id -> {
                    projectRepresentation.setId(id);
                    return projectRepresentation;
                });
    }

    public Promise<ProjectRepresentation> getProject(Long id) {
        return databaseExecutor.execute(() ->
                projectRepository.getProject(id));
    }

}
