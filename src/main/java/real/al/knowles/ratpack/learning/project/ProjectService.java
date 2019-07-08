package real.al.knowles.ratpack.learning.project;

import com.google.inject.Inject;
import ratpack.exec.Promise;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class ProjectService {

    private final ProjectRepository projectRepository;

    @Inject
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Promise<ProjectRepresentation> createProject(ProjectRepresentation projectRepresentation) {
        LocalDateTime createdOn = now();
        return Promise.value(
                new ProjectRepresentation(1L, "externalId", createdOn, createdOn));
    }

}
