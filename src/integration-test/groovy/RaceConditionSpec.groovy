import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.integration.Integration
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import spock.lang.Specification

import static grails.async.Promises.task
import static grails.async.Promises.waitAll

@Integration
class RaceConditionSpec extends Specification {

    String urlBase

    def setup() {
        if (!urlBase) {
            urlBase = "http://localhost:${serverPort}"
        }
    }

    def 'calls from two different threads with simulated latency succeed'() {

        given: 'two competing threads to call addToBooks'
        def thread1 = task this.&callAddToBooks
        def thread2 = task this.&callAddToBooks

        expect: 'both calls succeed'
        [HttpStatus.OK, HttpStatus.OK] == waitAll(thread1, thread2)
    }

    private HttpStatus callAddToBooks() {
        def bookBody = new LinkedMultiValueMap()
        bookBody.setAll([
                name: 'Dr. Seuss',
                title: 'Cat in the Hat'
        ])

        def response = (new RestBuilder()).post("${urlBase}/author/addToBooks") {
            contentType 'application/x-www-form-urlencoded'
            body bookBody
        }

        return response.statusCode
    }
}
