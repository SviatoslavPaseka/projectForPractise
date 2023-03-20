package rozkladkpi

import io.gatling.core.Predef._

import scala.concurrent.duration.DurationInt

object Scenarios {
  def default(testDuration: Int) = scenario("Default load test")
    .during(testDuration) {
      exec(UserJourneys.browseCrossSelection)
    }

  def crossGroupLecturer(testDuration: Int) = scenario("Cross group - lecturer")
    .during(testDuration.seconds){
      randomSwitch(
        75d ->exec(UserJourneys.browseGroupSelection),
        25d -> exec(UserJourneys.browseLecturerSelection)
      )
    }

  def rightWrongGroupInput(testDuration: Int) = scenario("Right - Wrong input group")
    .during(testDuration.seconds) {
      randomSwitch(
        50d -> exec(UserJourneys.browseGroupSelection),
        50d -> exec(UserJourneys.wrongGroupSelection)
      )
    }

  def wrongLecturerInput(testDuration: Int) = scenario("Wrong lecturer input")
    .during(testDuration.seconds) {
      exec(UserJourneys.wrongGroupSelection)
    }
}