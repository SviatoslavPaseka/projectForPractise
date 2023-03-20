package rozkladkpi

import io.gatling.core.Predef._
import rozkladkpi.pageObject.{GroupSelection, LecturerSelection, SessionSelection}

object UserJourneys {
  def browseSessionSelection = {
    exec(SessionSelection.getSeesionSelectionPage)
      .pause(2)
      .exec(SessionSelection.findSessionByGroup)
      .pause(2)
      .exec(SessionSelection.getSeesionSelectionPage)
      .pause(2)
  }

  def browseGroupSelection = {
    exec(GroupSelection.getGroupSelectionPage)
      .pause(2)
      .exec(GroupSelection.findScheduleByGroup)
      .pause(2)
      .exec(GroupSelection.getGroupSelectionPage)
      .pause(2)
  }

  def browseLecturerSelection = {
    exec(LecturerSelection.lecturerSelectionPage)
      .pause(2)
      .exec(LecturerSelection.findScheduleByLecturer)
      .pause(2)
      .exec(LecturerSelection.lecturerSelectionPage)
      .pause(2)
  }

  def browseCrossSelection = {
    exec(GroupSelection.getGroupSelectionPage)
      .pause(2)
      .exec(GroupSelection.findScheduleByGroup)
      .pause(2)
      .exec(LecturerSelection.lecturerSelectionPage)
      .pause(2)
      .exec(LecturerSelection.findScheduleByLecturer)
      .pause(2)
      .exec(SessionSelection.getSeesionSelectionPage)
      .pause(2)
      .exec(SessionSelection.findSessionByGroup)
      .pause(2)
  }

  def wrongGroupSelection ={
    exec(GroupSelection.getGroupSelectionPage)
      .pause(2)
    .exec(GroupSelection.wrongGroupInput)
      .pause(2)
  }

  def wrongLecturerSelection = {
    exec(LecturerSelection.lecturerSelectionPage)
      .pause(2)
      .exec(LecturerSelection.findWrongScheduleByLecturer)
      .pause(2)
  }
}