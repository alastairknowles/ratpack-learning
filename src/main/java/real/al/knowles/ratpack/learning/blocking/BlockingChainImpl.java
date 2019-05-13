package real.al.knowles.ratpack.learning.blocking;

import com.google.inject.Inject;
import ratpack.handling.Chain;

public class BlockingChainImpl implements BlockingChain {

    private final BlockingService blockingService;

    @Inject
    public BlockingChainImpl(BlockingService blockingService) {
        this.blockingService = blockingService;
    }

    @Override
    public void execute(Chain chain) {
        chain.get(blockingService::render);
    }

}
