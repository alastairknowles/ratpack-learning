package real.al.knowles.ratpack.learning.blocking;

import com.google.inject.Inject;
import ratpack.handling.Context;

public class BlockingControllerImpl implements BlockingController {

    private final BlockingService blockingService;

    @Inject
    public BlockingControllerImpl(BlockingService blockingService) {
        this.blockingService = blockingService;
    }

    @Override
    public void get(Context context) {
        blockingService.render(context);
    }

}
