package controllers

import javax.inject.Inject
import java.net.URLDecoder

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

/**
  * Created by pierre on 10/10/16.
  */
class Application @Inject() (configuration: Configuration) extends Controller {

  val decoder = new URLDecoder()

  val ime = (new models.IME(configuration)).ime


  def index = Action {
    Ok("hello")
  }

  def lookup(query: String, usePOJ: Boolean=false) = Action.async {
      scala.concurrent.Future {
        val candidates =
          ime.computeCandidateList(query, "", true, usePOJ)
            .filter(_.consumedLength == query.length)
            .map(_.label)
            .mkString("|")
        Ok(candidates)
      }
  }

  def ime(query:String, context: String, usePOJ: Boolean=false): Action[AnyContent] = Action.async {
    val decodedContext = URLDecoder.decode(context,"UTF8")
    scala.concurrent.Future {
      val strippedContext = decodedContext.substring(math.max(0,decodedContext.length - 9), decodedContext.length)
      val strippedQuery = query.substring(0, math.min(100,query.length))
      val normalized = ime.normalizeRomanization(strippedQuery, usePOJ, usePOJ)
      Logger.info(s"$normalized : P($strippedQuery|$strippedContext)")
      Ok(s"${normalized.getOrElse("???")}\n${ime.computeCandidateList(strippedQuery, strippedContext, true, usePOJ).map(_.label).mkString("\n").toString}")
    }
  }

}
