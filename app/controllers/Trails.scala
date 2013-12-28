package controllers

import lt.overdrive.trackparser.parsing.{ParserException, Parser}
import play.api.mvc._
import play.api.libs.json._
import lt.overdrive.trackparser.domain.GpsTrack
import scala.collection.JavaConversions._

object Trails extends Controller {

  def index = Action {
    Ok(views.html.map())
  }

  def convertToJson(track: GpsTrack) = {
    val points = track.getPoints.toList
    Json.toJson(points.map(p => Json.obj(
      "lat" -> p.getLatitude.doubleValue(),
      "lng" -> p.getLongitude.doubleValue(),
      "alt" -> p.getAltitude.doubleValue()
    )))
  }

  def upload = Action(parse.multipartFormData) {
    request =>
      request.body.file("file").map {
        trailFile =>
          try {
            val trail = Parser.parseFile(trailFile.ref.file)
            val jsonTracks = trail.getTracks.toList.map(convertToJson(_))
            val json = Json.toJson(jsonTracks)
            Ok(json)
          } catch {
            case _ : ParserException => BadRequest(Json.toJson("Unrecognized file!"))
          }
      }.getOrElse {
        BadRequest(Json.toJson("Missing file!"))
      }
  }

}