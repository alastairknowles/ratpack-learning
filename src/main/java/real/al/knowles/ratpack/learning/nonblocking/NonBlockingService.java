package real.al.knowles.ratpack.learning.nonblocking;

import ratpack.handling.Handler;

public class NonBlockingService {

    Handler render() {
        return (context -> context.render("non blocking"));
    }

}
