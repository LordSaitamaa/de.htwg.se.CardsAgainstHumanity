import org.scalatest.WordSpec
import org.scalatest.matchers.should.Matchers

class CaHSpec extends WordSpec with Matchers  {
  "The CaH main class" should {
    "accept text input as argument without readline loop, to test it from command line " in {
      CaHMain.main(Array[String]("q"))
    }
  }

}
