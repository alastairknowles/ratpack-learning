package real.al.knowles.ratpack.learning.project;

import com.google.inject.Inject;
import ratpack.func.Action;
import ratpack.handling.Chain;

import static ratpack.jackson.Jackson.json;

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
                        .then(projectRepresentation -> context.render(json(projectRepresentation))));
    }

}
