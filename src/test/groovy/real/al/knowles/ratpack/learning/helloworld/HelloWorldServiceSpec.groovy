package real.al.knowles.ratpack.learning.helloworld

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ratpack.exec.Promise
import ratpack.test.handling.RequestFixture
import real.al.knowles.ratpack.learning.metric.MetricService

import java.lang.reflect.Method

import static org.junit.Assert.assertEquals
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner.class)
class HelloWorldServiceSpec {

    @Mock
    private HelloService helloService

    @Mock
    private WorldService worldService

    @Mock
    private MetricService metricService

    private HelloWorldService helloWorldService

    @Before
    void setUp() {
        when(helloService.sayHello()).thenReturn(Promise.value('hello'))
        when(worldService.sayWorld()).thenReturn(Promise.value('world'))

        when(metricService.record(any(Method), any(String.class))).thenAnswer({
            invocation -> return invocation.arguments[1]
        })

        helloWorldService = new HelloWorldService(helloService, worldService, metricService)
    }

    @After
    void tearDown() {
        verifyNoMoreInteractions(helloService, worldService, metricService)
    }

    @Test
    void sayHelloWorld_success() {
        def result = RequestFixture.handle(helloWorldService.sayHelloWorld(), {})
        assertEquals('hello world', result.rendered(String))

        verify(helloService, times(1)).sayHello()
        verify(worldService, times(1)).sayWorld()

        verify(metricService, times(1)).record(
                HelloWorldService.class.getDeclaredMethod('sayHelloWorld'), 'hello world')
    }

}
