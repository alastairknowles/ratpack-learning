package real.al.knowles.ratpack.learning.blocking;

import ratpack.exec.Blocking;
import ratpack.handling.Context;

public class BlockingServiceImpl implements BlockingService {

    @Override
    public void render(Context context) {
        Blocking.get(() -> "blocking page")
                .then(context::render);
    }

}
