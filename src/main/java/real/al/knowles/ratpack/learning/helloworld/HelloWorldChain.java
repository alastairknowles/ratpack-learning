package real.al.knowles.ratpack.learning.helloworld;

import com.google.inject.Inject;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class HelloWorldChain implements Action<Chain> {

    private final HelloWorldService helloWorldService;

    @Inject
    public HelloWorldChain(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @Override
    public void execute(Chain chain) {
        chain.get("hello-world", helloWorldService.sayHelloWorld());
    }

}
