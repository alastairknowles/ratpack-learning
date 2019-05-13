package real.al.knowles.ratpack.learning.nonblocking;

import com.google.inject.Inject;
import ratpack.handling.Chain;

public class NonBlockingChainImpl implements NonBlockingChain {

    private final NonBlockingService nonBlockingService;

    @Inject
    public NonBlockingChainImpl(NonBlockingService nonBlockingService) {
        this.nonBlockingService = nonBlockingService;
    }

    @Override
    public void execute(Chain chain) {
        chain.get(context -> context.render(nonBlockingService.render()));
    }

}
