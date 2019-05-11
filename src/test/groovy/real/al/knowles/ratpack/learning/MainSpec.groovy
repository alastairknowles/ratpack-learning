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
    MainClassApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(Main.class)

    @Test
    def 'homepage'() {
        when: 'Home page is requested'
        String response = applicationUnderTest.httpClient.get().getBody().getText(UTF_8)

        then: 'Home page message is returned'
        response == 'homepage'
    }

    @Test
    def 'blocking'() {
        when: 'Blocking page is requested'
        String response = applicationUnderTest.httpClient.get('blocking').getBody().getText(UTF_8)

        then: 'Blocking page message is returned'
        response == 'blocking page'
    }

    @Test
    def 'non-blocking'() {
        when: 'Non blocking page is requested'
        String response = applicationUnderTest.httpClient.get('non-blocking').getBody().getText(UTF_8)

        then: 'Non blocking page message is returned'
        response == 'non blocking page'
    }

}
