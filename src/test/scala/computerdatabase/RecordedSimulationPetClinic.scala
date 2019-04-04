package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulationPetClinic extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:9966")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico"""), WhiteList())
		.acceptHeader("image/webp,*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:65.0) Gecko/20100101 Firefox/65.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Accept" -> "text/css,*/*;q=0.1")

	val headers_2 = Map("Accept" -> "*/*")

	val headers_14 = Map(
		"Accept" -> "*/*",
		"Pragma" -> "no-cache")

    val uri2 = "http://detectportal.firefox.com/success.txt"

	val scn = scenario("RecordedSimulationPetClinic")
		.exec(http("request_0")
			.get("/petclinic/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/petclinic/resources/css/petclinic.css;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040")
			.headers(headers_1),
            http("request_2")
			.get("/petclinic/webjars/jquery-ui/1.10.3/ui/jquery.ui.core.js;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040")
			.headers(headers_2),
            http("request_3")
			.get("/petclinic/webjars/jquery-ui/1.10.3/ui/jquery.ui.datepicker.js;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040")
			.headers(headers_2),
            http("request_4")
			.get("/petclinic/webjars/jquery/2.0.3/jquery.js;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040")
			.headers(headers_2),
            http("request_5")
			.get("/petclinic/webjars/jquery-ui/1.10.3/themes/base/jquery-ui.css;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040")
			.headers(headers_1),
            http("request_6")
			.get("/petclinic/webjars/bootstrap/2.3.0/css/bootstrap.min.css;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040")
			.headers(headers_1),
            http("request_7")
			.get("/petclinic/resources/images/spring-pivotal-logo.png;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040"),
            http("request_8")
			.get("/petclinic/resources/images/pets.png;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040"),
            http("request_9")
			.get("/petclinic/resources/images/banner-graphic.png;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040"),
            http("request_10")
			.get("/petclinic/webjars/bootstrap/2.3.0/img/glyphicons-halflings.png")))
		.pause(5)
		.exec(http("request_11")
			.get("/petclinic/owners/find.html;jsessionid=A4F9C7096A42EDEB6A0DFC2B60E25040")
			.headers(headers_0)
			.resources(http("request_12")
			.get("/petclinic/resources/images/banner-graphic.png"),
            http("request_13")
			.get("/petclinic/resources/images/spring-pivotal-logo.png")))
		.pause(10)
		.exec(http("request_14")
			.get(uri2 + "")
			.headers(headers_14))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}