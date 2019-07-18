package real.al.knowles.ratpack.learning.project;

import com.google.inject.Inject;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;

import static real.al.knowles.ratpack.learning.chain.ChainUtils.pathTokenAsLong;

public class ProjectChain implements Action<Chain> {

    private final ProjectService projectService;

    @Inject
    public ProjectChain(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void execute(Chain chain) {
        chain.post(context ->
                context.parse(ProjectRepresentation.class)
                        .flatMap(projectService::createProject)
                        .map(Jackson::json)
                        .then(context::render))
                .get(":id", context -> {
                    Long id = pathTokenAsLong(context, "id");
                    projectService.getProject(id)
                            .map(Jackson::json)
                            .then(context::render);
                });
    }

}
