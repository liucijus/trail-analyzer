package controllers

import lt.overdrive.trackparser.parsing.{ParserException, Parser}
import play.api.mvc._
import play.api.libs.json._
import lt.overdrive.trackparser.domain.{TrackPoint, Track}
import scala.collection.JavaConversions._
import lt.overdrive.trackparser.processing.{TrackRectangle, TrackProcessor}
import play.api.libs.json.Json.JsValueWrapper

object Trails extends Controller {

  def index = Action {
    Ok(views.html.map())
  }

  def upload = Action(parse.multipartFormData) {
    request =>
      request.body.file("file").map {
        trailFile =>
          try {
            val trail = Parser.parseFile(trailFile.ref.file)
            val jsonTracks = trail.getTracks.toList.map(convertToJson(_))
            val box = TrackProcessor.calculateRectangle(trail.getTracks)

            val trailJson = Json.toJson(jsonTracks)

            val json = Json.obj(
              "box" -> createBoxJson(box),
              "trail" -> trailJson)
            Ok(json)
          } catch {
            case _: ParserException => BadRequest(Json.toJson("Unrecognized file!"))
          }
      }.getOrElse {
        BadRequest(Json.toJson("Missing file!"))
      }
  }

  private def convertToJson(track: Track) = {
    val points = track.getPoints.toList
    Json.toJson(points.map(p => Json.obj(
      "lat" -> p.getLatitude.doubleValue,
      "lng" -> p.getLongitude.doubleValue,
      "alt" -> p.getAltitude.doubleValue
    )))
  }

  private def createBoxJson(box: TrackRectangle) = {
    def createJsonPoint(point: TrackPoint): Json.JsValueWrapper = {
      Json.obj(
        "lat" -> point.getLatitude.doubleValue,
        "lon" -> point.getLongitude.doubleValue
      )
    }
    Json.obj(
      "top" -> createJsonPoint(box.getTopRightPoint),
      "bottom" -> createJsonPoint(box.getBottomLeftPoint),
      "center" -> createJsonPoint(box.getCenterPoint)
    )
  }
}