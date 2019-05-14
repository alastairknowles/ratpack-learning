package real.al.knowles.ratpack.learning.blocking;

import ratpack.handling.Handler;

import static ratpack.exec.Blocking.get;

public class BlockingService {

    Handler render() {
        return (context ->
                get(() -> "blocking page")
                        .then(context::render));
    }

}
