package real.al.knowles.ratpack.learning

import org.junit.Test
import ratpack.test.MainClassApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import static com.google.common.base.Charsets.UTF_8

class MainSpec extends Specification {

    @Shared
    @AutoCleanup
    private MainClassApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(Main.class)

    @Test
    def 'homepage'() {
        when: 'home page is requested'
        String response =
                applicationUnderTest
                        .httpClient
                        .get()
                        .getBody()
                        .getText(UTF_8)

        then: 'Home page message is returned'
        response == 'homepage'
    }

}
