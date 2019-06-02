package real.al.knowles.ratpack.learning.helloworld;

import ratpack.exec.Promise;

public class WorldService {

    Promise<String> sayWorld() {
        return Promise.async(upstream -> upstream.success("world"));
    }

}
