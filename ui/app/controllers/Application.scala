package controllers

import java.io.File
import javax.inject.{Inject, Singleton}

import org.slf4j.{Logger, LoggerFactory}
import play.api.Play
import play.api.Play.current
import play.api.mvc._
import services.UUIDGenerator
import play.api.i18n.Messages

import scala.concurrent.Future

@Singleton
class Application @Inject() (uuidGenerator: UUIDGenerator) extends Controller {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def partial_home = Action.async {
    Future.successful(Ok(views.html.partial_home()))
  }

  def partial_about = Action.async {
    Future.successful(Ok(views.html.partial_about()))
  }

  def index = Action {
    request =>
    logger.info("Serving index page...")
      logger.debug(Messages("entity.create.title"))
    Ok(
      views.html.index(
        if (Play.isDev)
          Option(Play.getFile("app/assets"))
            .filter(_.exists)
            .map(findScripts)
            .getOrElse(Nil)
        else
          List("concat.min.js")
      )
    )
  }

  def create = Action.async {
    Future.successful(Ok(views.html.entity.create()))
  }

  def view = Action.async {
    Future.successful(Ok(views.html.view()))
  }

  private def findScripts(base: File): Seq[String] =
    directoryFlatMap(base, scriptMapper).map(f => base.toURI.relativize(f.toURI).getPath)

  private def scriptMapper(file: File): Option[File] = {
    val name = file.getName
    if (name.endsWith(".js")) Some(file)
    else if (name.endsWith(".coffee")) Some(new File(file.getParent, name.dropRight(6) + "js"))
    else None
  }

  private def directoryFlatMap[A](in: File, fileFun: File => Option[A]): Seq[A] =
    in.listFiles.flatMap {
      case f if f.isDirectory => directoryFlatMap(f, fileFun)
      case f if f.isFile => fileFun(f)
    }

  def randomUUID = Action {
    logger.info("calling UUIDGenerator...")
    Ok(uuidGenerator.generate.toString)
  }

  def update() = Action.async {
    Future.successful(Ok(views.html.entity.update()))
  }
}
