package real.al.knowles.ratpack.learning.project;

import com.google.inject.Inject;
import ratpack.exec.Promise;
import real.al.knowles.ratpack.learning.database.TransactionWrapper;

import java.time.LocalDateTime;

public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TransactionWrapper transactionWrapper;

    @Inject
    public ProjectService(ProjectRepository projectRepository, TransactionWrapper transactionWrapper) {
        this.projectRepository = projectRepository;
        this.transactionWrapper = transactionWrapper;
    }

    public Promise<ProjectRepresentation> createProject(ProjectRepresentation projectRepresentation) {
        LocalDateTime now = LocalDateTime.now();
        projectRepresentation.setCreatedOn(now);
        projectRepresentation.setUpdatedOn(now);

        return transactionWrapper.execute(
                () -> projectRepository.createProject(projectRepresentation))
                .map(id -> {
                    projectRepresentation.setId(id);
                    return projectRepresentation;
                });
    }

}
