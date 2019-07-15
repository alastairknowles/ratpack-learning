package real.al.knowles.ratpack.learning.project;

import com.google.inject.Inject;
import ratpack.exec.Promise;
import real.al.knowles.ratpack.learning.database.TransactionWrapper;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TransactionWrapper transactionWrapper;

    @Inject
    public ProjectService(ProjectRepository projectRepository, TransactionWrapper transactionWrapper) {
        this.projectRepository = projectRepository;
        this.transactionWrapper = transactionWrapper;
    }

    public Promise<ProjectRepresentation> createProject(ProjectRepresentation projectRepresentation) {
        LocalDateTime createdOn = now();
        return Promise.value(
                new ProjectRepresentation(1L, "externalId", createdOn, createdOn));
    }

}
