package real.al.knowles.ratpack.learning.nonblocking;

import com.google.inject.Inject;

public class NonBlockingControllerImpl implements NonBlockingController {

    private final NonBlockingService nonBlockingService;

    @Inject
    public NonBlockingControllerImpl(NonBlockingService nonBlockingService) {
        this.nonBlockingService = nonBlockingService;
    }

    @Override
    public String get() {
        return nonBlockingService.render();
    }

}
