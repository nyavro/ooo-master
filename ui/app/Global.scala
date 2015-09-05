import com.eny.ooo.manager.person.{PersonRepositoryImpl, PersonRepository}
import com.google.inject.{Guice, AbstractModule}
import play.api.GlobalSettings
import services.{SimpleUUIDGenerator, UUIDGenerator}

object Global extends GlobalSettings {

  val injector = Guice.createInjector(
    new AbstractModule {
      protected def configure() = {
        bind(classOf[UUIDGenerator]).to(classOf[SimpleUUIDGenerator])
        bind(classOf[PersonRepository]).to(classOf[PersonRepositoryImpl])
      }
    }
  )

  override def getControllerInstance[A](controllerClass: Class[A]) = injector.getInstance(controllerClass)
}
