package wfos.bgrxassembly

//import akka.actor.Status.Success
import csw.command.client.CommandServiceFactory
import csw.location.api.models.Connection.AkkaConnection
import csw.prefix.models.Prefix
import csw.location.api.models.{ComponentId, ComponentType}

import csw.params.commands.Setup
//import csw.prefix.models.Subsystem.WFOS
import csw.testkit.scaladsl.CSWService.{LocationServer, EventServer}
import csw.testkit.scaladsl.ScalaTestFrameworkTestKit
import org.scalatest.funsuite.AnyFunSuiteLike

import scala.concurrent.Await
import scala.concurrent.duration._

class BgrxassemblyTest extends ScalaTestFrameworkTestKit(LocationServer, EventServer) with AnyFunSuiteLike {

  import frameworkTestKit._

  override def beforeAll(): Unit = {
    super.beforeAll()
    // uncomment if you want one Assembly run for all tests
    spawnStandalone(com.typesafe.config.ConfigFactory.load("bgrxassemblyStandalone.conf"))
  }

  test("Assembly should be locatable using Location Service") {
    val connection   = AkkaConnection(ComponentId(Prefix("wfos.bgrxAssembly"), ComponentType.Assembly))
    val akkaLocation = Await.result(locationService.resolve(connection, 10.seconds), 10.seconds).get

    akkaLocation.connection shouldBe connection
  }
}
