package rozkladkpi.pageObject

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object SessionSelection {
  val sessionGroupSelectionFeeder = jsonFile("rozkladkpi/bodyData/postData/SessionGroupSelection.json").circular
  val groupDetailsFeeder = jsonFile("rozkladkpi/bodyData/correctDetails/groupDetails.json").circular


  def getSeesionSelectionPage = {
    exec(http("Group selection")
      .get("/Schedules/SessionScheduleGroupSelection.aspx")
      .check(substring("Група"))
      .check(status is 200))
  }

  def findSessionByGroup = {
    feed(sessionGroupSelectionFeeder).
      feed(groupDetailsFeeder)
      .exec(
        http("Group selection: '#{groupName}'")
          .post("/Schedules/SessionScheduleGroupSelection.aspx")
          .formParam("ctl00_ToolkitScriptManager_HiddenField", "")
          .formParam("__VIEWSTATE", "#{__VIEWSTATE}")
          .formParam("__EVENTTARGET", "")
          .formParam("__EVENTARGUMENT", "")
          .formParam("ctl00$MainContent$ctl00$txtboxGroup", "#{groupName}")
          .formParam("ctl00$MainContent$ctl00$btnShowSchedule", "#{ctl00$MainContent$ctl00$btnShowSchedule}")
          .formParam("__EVENTVALIDATION", "#{__EVENTVALIDATION}")
          .formParam("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1")
          .check(status is 200)
          .check(substring("Розклад сесії")) // must be check(substring("Розклад сесії: #{groupName}"))
      )
  }
}
