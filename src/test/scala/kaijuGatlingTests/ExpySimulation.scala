package kaijuGatlingTests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ExpySimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("https://expansiagroup.com/")
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

	val scn = scenario("Expy")
		.exec(http("request_0")
			.get("/about/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/services/#")
			.headers(headers_1),
            http("request_2")
			.get("/careers/")
			.headers(headers_2),
            http("request_3")
			.get("/services/#Program-TechnologyStrategies")
			.headers(headers_2),
            http("request_4")
			.get("")
			.headers(headers_2),
            http("request_5")
			.get("")
			.headers(headers_1),
            http("request_6")
			.get("")
			.headers(headers_1),
            http("request_7")
			.get(""),
            http("request_8")
			.get(""),
            http("request_9")
			.get(""),
            http("request_10")
			.get("")))
		.pause(5)
		.exec(http("request_11")
			.get("")
			.headers(headers_0)
			.resources(http("request_12")
			.get(""),
            http("request_13")
			.get("")))
		.pause(10)
		.exec(http("request_14")
			.get(uri2 + "")
			.headers(headers_14))

	setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)
  //mvn gatling:test -Dgatling.simulationClass=computerdatabase.ExpySimulation
}