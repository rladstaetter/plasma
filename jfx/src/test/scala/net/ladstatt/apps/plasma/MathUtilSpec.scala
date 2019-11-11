package net.ladstatt.apps.plasma

import org.scalatest.WordSpecLike

class MathUtilSpec extends WordSpecLike {

  "Numeric Range" in {
    assert(MathUtil.numericRange.length == 12567)
    //assert(MathUtil.numericRange1.length == 12567)
  }
}
