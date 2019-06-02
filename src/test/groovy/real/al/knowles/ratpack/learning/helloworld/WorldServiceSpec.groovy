package real.al.knowles.ratpack.learning.helloworld

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import ratpack.test.exec.ExecHarness

import static org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner)
class WorldServiceSpec {

    private WorldService worldService

    @Before
    void setUp() {
        worldService = new WorldService()
    }

    @Test
    void sayWorld_success() {
        def result = ExecHarness.yieldSingle({
            worldService.sayWorld()
        })

        assertEquals('world', result.value)
    }

}
