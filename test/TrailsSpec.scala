import controllers.routes
import java.io.File
import play.api.libs.Files
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData
import play.api.mvc.MultipartFormData.FilePart

import play.api.test._
import play.api.test.FakeHeaders
import scala.Some

class TrailsSpec extends PlaySpecification {
  "Trails" should {
    "return OK and JSON object if correct GPX/TCX file uploaded" in new WithApplication() {
      val data = createMultipartFormData("sample.gpx")

      val result = controllers.Trails.upload(fakeRequest(data))

      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
    }

    "return BAD_REQUEST and JSON object if invalid file uploaded" in new WithApplication() {
      val data = createMultipartFormData("test/non-gps.file")

      val result = controllers.Trails.upload(fakeRequest(data))

      status(result) must equalTo(BAD_REQUEST)
      contentType(result) must beSome("application/json")
    }
  }


  def fakeRequest(data: MultipartFormData[Files.TemporaryFile]): FakeRequest[MultipartFormData[Files.TemporaryFile]] = {
    FakeRequest(POST, routes.Trails.upload.url, FakeHeaders(), data)
  }

  def createMultipartFormData(fileName: String) = {
    val tempFile = File.createTempFile("uploadTestFile", null)
    Files.copyFile(from = new File(fileName), to = tempFile)
    val part = FilePart("file", fileName, Some("application/octet-stream"), TemporaryFile(tempFile))
    MultipartFormData(dataParts = Map(), files = Seq(part), badParts = Seq(), missingFileParts = Seq())
  }
}
