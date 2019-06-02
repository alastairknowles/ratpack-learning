package real.al.knowles.ratpack.learning.metric

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ratpack.test.exec.ExecHarness
import real.al.knowles.ratpack.learning.helloworld.HelloWorldService

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner.class)
class MetricServiceSpec {

    @Mock
    private LoggerService loggerService

    private MetricService metricService

    @Before
    void setUp() {
        metricService = new MetricService(loggerService)
    }

    @After
    void tearDown() {
        verifyNoMoreInteractions(loggerService)
    }

    @Test
    void record_success() {
        ExecHarness.yieldSingle({
            def result = metricService.record(
                    HelloWorldService.getDeclaredMethod('sayHelloWorld'), 'hello world')
            assertEquals('hello world', result)

        })

        verify(loggerService, times(1)).info('class real.al.knowles.ratpack.learning.helloworld.HelloWorldService - ratpack.handling.Handler real.al.knowles.ratpack.learning.helloworld.HelloWorldService.sayHelloWorld(): hello world')
    }

}
