package real.al.knowles.ratpack.learning.nonblocking;

import com.google.inject.Inject;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class NonBlockingChain implements Action<Chain> {

    private final NonBlockingService nonBlockingService;

    @Inject
    public NonBlockingChain(NonBlockingService nonBlockingService) {
        this.nonBlockingService = nonBlockingService;
    }

    @Override
    public void execute(Chain chain) {
        chain.get(nonBlockingService.render());
    }

}
