package real.al.knowles.ratpack.learning.blocking;

import com.google.inject.Inject;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class BlockingChain implements Action<Chain> {

    private final BlockingService blockingService;

    @Inject
    public BlockingChain(BlockingService blockingService) {
        this.blockingService = blockingService;
    }

    @Override
    public void execute(Chain chain) {
        chain.get(blockingService.render());
    }

}
