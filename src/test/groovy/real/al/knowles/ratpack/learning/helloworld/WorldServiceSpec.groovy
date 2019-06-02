package real.al.knowles.ratpack.learning.helloworld

import org.junit.Test
import ratpack.test.exec.ExecHarness
import spock.lang.Shared
import spock.lang.Specification

class HelloServiceSpec extends Specification {

    @Shared
    private HelloService helloService

    void setupSpec() {
        helloService = new HelloService()
    }

    @Test
    def "hello world - success"() {
        when: "calling the method"
        def result = ExecHarness.yieldSingle({
            helloService.sayHello()
        })

        then: "the method yields 'hello'"
        result.value == 'hello'
    }

}
