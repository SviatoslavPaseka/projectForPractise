package rozkladkpi

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class rozkladTest2 extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://epi.kpi.ua")

  def userCount = getProperty("USERS", "5").toInt

  def rampDuration = getProperty("RAMP_DURATION", "10").toInt

  def testDuration = getProperty("DURATION", "20").toInt

  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  before {
    println(s"Test start with ${userCount} users")
    println(s"Test start with ${rampDuration} ramp duration")
    println(s"Test start with ${testDuration} test duration")
  }

  after {
    println("test end")
  }

  setUp(
    Scenarios.wrongLecturerInput(testDuration).inject(
      nothingFor(3),
     atOnceUsers(1),
      rampUsers(userCount).during(rampDuration.seconds)
    )
  ).protocols(httpProtocol)
}