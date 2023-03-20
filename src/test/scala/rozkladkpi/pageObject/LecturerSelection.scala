package rozkladkpi.pageObject

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object LecturerSelection {

  val scheduleLecturerSelectionFeeder = jsonFile("rozkladkpi/bodyData/postData/ScheduleLecturerSelection.json").circular
  val lecturerDetailsFeeder = jsonFile("rozkladkpi/bodyData/correctDetails/LecturerDetails.json").circular
  val wrongLecturerDetailsFeeder = jsonFile("rozkladkpi/bodyData/wrongDetails/WrongLecturerDetails.json").circular


  def lecturerSelectionPage = {
    exec(http("Lector Selection")
      .get("/Schedules/LecturerSelection.aspx")
      .check(substring("Викладач"))
      .check(status is 200))
  }

  def findScheduleByLecturer = {
    feed(scheduleLecturerSelectionFeeder).
      feed(lecturerDetailsFeeder)
      .exec(
        http("Lecturer selection: '#{lecturer}'")
          .post("/Schedules/LecturerSelection.aspx")
          .formParam("ctl00_ToolkitScriptManager_HiddenField", "")
          .formParam("__VIEWSTATE", "#{__VIEWSTATE}")
          .formParam("__EVENTTARGET", "")
          .formParam("__EVENTARGUMENT", "")
          .formParam("ctl00$MainContent$txtboxLecturer", "#{lecturer}")
          .formParam("ctl00$MainContent$btnSchedule", "#{ctl00$MainContent$btnSchedule}")
          .formParam("__EVENTVALIDATION", "#{__EVENTVALIDATION}")
          .formParam("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1")
          .check(status is 200)
          .check(substring("Розклад занять, викладач: #{lecturer}"))
      )
  }

  def findWrongScheduleByLecturer = {
    feed(scheduleLecturerSelectionFeeder).
      feed(wrongLecturerDetailsFeeder)
      .exec(
        http("Lecturer selection: '#{lecturer}'")
          .post("/Schedules/LecturerSelection.aspx")
          .formParam("ctl00_ToolkitScriptManager_HiddenField", "")
          .formParam("__VIEWSTATE", "#{__VIEWSTATE}")
          .formParam("__EVENTTARGET", "")
          .formParam("__EVENTARGUMENT", "")
          .formParam("ctl00$MainContent$txtboxLecturer", "#{lecturer}")
          .formParam("ctl00$MainContent$btnSchedule", "#{ctl00$MainContent$btnSchedule}")
          .formParam("__EVENTVALIDATION", "#{__EVENTVALIDATION}")
          .formParam("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1")
          .check(status is 200)
          .check(substring("Викладача з такими даними не знайдено!"))
      )
  }
}
