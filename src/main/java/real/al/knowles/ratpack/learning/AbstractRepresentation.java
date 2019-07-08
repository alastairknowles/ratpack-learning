package real.al.knowles.ratpack.learning;

import com.querydsl.core.Tuple;

public abstract class AbstractRepresentation<T extends AbstractRepresentation> {

    public abstract T fromRow(Tuple row);

}
