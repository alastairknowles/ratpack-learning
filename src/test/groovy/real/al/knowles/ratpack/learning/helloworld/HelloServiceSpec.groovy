package real.al.knowles.ratpack.learning.helloworld

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import ratpack.test.exec.ExecHarness

import static org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner)
class HelloServiceSpec {

    private HelloService helloService

    @Before
    void setUp() {
        helloService = new HelloService()
    }

    @Test
    void sayHello_success() {
        def result = ExecHarness.yieldSingle({
            helloService.sayHello()
        })

        assertEquals('hello', result.value)
    }

}
