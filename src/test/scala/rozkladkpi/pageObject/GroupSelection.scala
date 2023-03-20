package rozkladkpi.pageObject

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object GroupSelection {
  val scheduleGroupSelectionFeeder = jsonFile("rozkladkpi/bodyData/postData/ScheduleGroupSelection.json").circular
  val groupDetailsFeeder = jsonFile("rozkladkpi/bodyData/correctDetails/groupDetails.json").circular
  val wrongGroupDetailsFeeder = jsonFile("rozkladkpi/bodyData/wrongDetails/wrongGroupDetails.json").circular

  def getGroupSelectionPage = {
    exec(http("Group selection")
      .get("/Schedules/ScheduleGroupSelection.aspx")
      .check(substring("Група"))
      .check(status is 200))
  }

  def findScheduleByGroup = {
    feed(scheduleGroupSelectionFeeder).
      feed(groupDetailsFeeder)
      .exec(
        http("Group selection: '#{groupName}'")
          .post("/Schedules/ScheduleGroupSelection.aspx")
          .formParam("ctl00_ToolkitScriptManager_HiddenField", "")
          .formParam("__VIEWSTATE", "#{__VIEWSTATE}")
          .formParam("__EVENTTARGET", "")
          .formParam("__EVENTARGUMENT", "")
          .formParam("ctl00$MainContent$ctl00$txtboxGroup", "#{groupName}")
          .formParam("ctl00$MainContent$ctl00$btnShowSchedule", "#{ctl00$MainContent$ctl00$btnShowSchedule}")
          .formParam("__EVENTVALIDATION", "#{__EVENTVALIDATION}")
          .formParam("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1")
          .check(status is 200)
          .check(substring("Розклад занять для #{groupName}"))
      )
  }

  def wrongGroupInput = {
    feed(scheduleGroupSelectionFeeder).
      feed(wrongGroupDetailsFeeder)
      .exec(
        http("Group selection: '#{groupName}'")
          .post("/Schedules/ScheduleGroupSelection.aspx")
          .formParam("ctl00_ToolkitScriptManager_HiddenField", "")
          .formParam("__VIEWSTATE", "#{__VIEWSTATE}")
          .formParam("__EVENTTARGET", "")
          .formParam("__EVENTARGUMENT", "")
          .formParam("ctl00$MainContent$ctl00$txtboxGroup", "#{groupName}")
          .formParam("ctl00$MainContent$ctl00$btnShowSchedule", "#{ctl00$MainContent$ctl00$btnShowSchedule}")
          .formParam("__EVENTVALIDATION", "#{__EVENTVALIDATION}")
          .formParam("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1")
          .check(status is 200)
          .checkIf("#{groupName}a".length != 0)(substring("Групи з такою назвою не знайдено!"))
          .checkIf("#{groupName}".isEmpty)(substring("Введіть назву групи для відображення!"))
      )
  }

  def defaultPostGroupSelection = {
    feed(scheduleGroupSelectionFeeder)
      //.exec { session => println("___" + session + "___"); session }
      .exec(
        http("Group selection")
          .post("/Schedules/ScheduleGroupSelection.aspx")
          .formParam("ctl00_ToolkitScriptManager_HiddenField", "")
          .formParam("__VIEWSTATE", "#{__VIEWSTATE}")
          .formParam("__EVENTTARGET", "#{__EVENTTARGET}")
          .formParam("__EVENTARGUMENT", "")
          .formParam("ctl00$MainContent$ctl00$txtboxGroup", "")
          .formParam("__EVENTVALIDATION", "#{__EVENTVALIDATION}")
          .formParam("hiddenInputToUpdateATBuffer_CommonToolkitScripts", "1")
          .check(substring("Департамент навчальної роботи"))
          .check(status is 200)
      )
  }
}