package real.al.knowles.ratpack.learning.project;

import com.querydsl.core.Tuple;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import real.al.knowles.ratpack.learning.AbstractRepresentation;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ProjectRepresentation extends AbstractRepresentation<ProjectRepresentation> {

    private final Long id;

    private final String externalId;

    private final LocalDateTime createdOn;

    private final LocalDateTime updatedOn;

    @Override
    public ProjectRepresentation fromRow(Tuple row) {
        Long id = row.get(0, Long.class);
        String externalId = row.get(1, String.class);
        LocalDateTime createdOn = row.get(2, LocalDateTime.class);
        LocalDateTime updatedOn = row.get(3, LocalDateTime.class);
        return new ProjectRepresentation(id, externalId, createdOn, updatedOn);
    }

}
