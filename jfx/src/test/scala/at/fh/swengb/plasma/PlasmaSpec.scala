package at.fh.swengb.plasma

import org.scalatest.WordSpecLike

class PlasmaSpec extends WordSpecLike {

  "Basic Stuff" when {
    "sinus calculations" should {
      "yield for 0 and 2*pi the same" in {
        for (a <- 0 to 360) {
          println(a + " :"  + Math.sin(a * 2 * Math.PI / 360))
        }
      }
    }
  }
}
