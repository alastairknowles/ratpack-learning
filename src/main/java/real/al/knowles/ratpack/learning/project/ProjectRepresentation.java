package real.al.knowles.ratpack.learning.project;

import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import real.al.knowles.ratpack.learning.AbstractRepresentation;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProjectRepresentation extends AbstractRepresentation<ProjectRepresentation> {

    private Long id;

    private String externalId;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @Override
    public ProjectRepresentation fromRow(Tuple row) {
        Long id = row.get(0, Long.class);
        String externalId = row.get(1, String.class);
        LocalDateTime createdOn = row.get(2, LocalDateTime.class);
        LocalDateTime updatedOn = row.get(3, LocalDateTime.class);
        return new ProjectRepresentation(id, externalId, createdOn, updatedOn);
    }

}
