package real.al.knowles.ratpack.learning.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRepresentation {

    private Long id;

    private String externalId;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @SuppressWarnings("unused")
    public ProjectRepresentation(Long id, String externalId, Timestamp createdOn, Timestamp updatedOn) {
        this.id = id;
        this.externalId = externalId;
        this.createdOn = createdOn.toLocalDateTime();
        this.updatedOn = updatedOn.toLocalDateTime();
    }

}
