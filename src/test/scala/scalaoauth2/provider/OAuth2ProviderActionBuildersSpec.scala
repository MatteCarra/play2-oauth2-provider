package scalaoauth2.provider

import javax.inject.Inject

import org.scalatest._
import org.scalatest.Matchers._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.{ BaseController, ControllerComponents }
import play.api.test.Helpers._
import play.api.test.FakeRequest

class OAuth2ProviderActionBuildersSpec extends FlatSpec {

  class MyController @Inject() (val controllerComponents: ControllerComponents) extends BaseController with OAuth2ProviderActionBuilders {

    val action = AuthorizedAction(new MockDataHandler) { request =>
      Ok(request.authInfo.user.name)
    }

  }

  val injector = new GuiceApplicationBuilder().injector()
  val controllerComponents = injector.instanceOf[ControllerComponents]

  it should "return BadRequest" in {
    val controller = new MyController(controllerComponents)
    val result = controller.action(FakeRequest())
    status(result) should be(400)
    contentAsString(result) should be("")
  }

}
