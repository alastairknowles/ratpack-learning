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
        when: 'home page is requested'
        String response = applicationUnderTest.httpClient.get().getBody().getText(UTF_8)

        then: 'Home page message is returned'
        response == 'homepage'
    }

    @Test
    def 'blocking'() {
        when: 'blocking page is requested'
        String response = applicationUnderTest.httpClient.get('blocking').getBody().getText(UTF_8)

        then: 'blocking message is returned'
        response == 'blocking'
    }

    @Test
    def 'non-blocking'() {
        when: 'non blocking page is requested'
        String response = applicationUnderTest.httpClient.get('non-blocking').getBody().getText(UTF_8)

        then: 'non blocking message is returned'
        response == 'non blocking'
    }

    @Test
    def 'hello world'() {
        when: 'hello world page is requested'
        String response = applicationUnderTest.httpClient.get('hello-world').getBody().getText(UTF_8)

        then: 'hello world message is returned'
        response == 'hello world'
    }

}
