package real.al.knowles.ratpack.learning.hello;

import ratpack.exec.Promise;

public class HelloService {

    Promise<String> sayHello() {
        return Promise.async(upstream -> upstream.success("hello"));
    }

}
