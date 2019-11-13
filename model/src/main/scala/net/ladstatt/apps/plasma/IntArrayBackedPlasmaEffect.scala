package net.ladstatt.apps.plasma


/**
  * Parameterize PlasmaBase with an array and provide a method to set each element of the backing array with appropriate values.
  */
case class IntArrayBackedPlasmaEffect(width: Int
                                      , height: Int
                                      , blockSize: Int)
  extends PlasmaEffect[Array[Int]]( width, height, blockSize) {

  val a = Array.tabulate(width * height)(_ => 0)

  override def updateArray(backingArray: Array[Int], x: Int, y: Int, red: Int, green: Int, blue: Int, alpha: Int): Unit = {
    backingArray(x + y) = (alpha << 24) + (red << 16) + (green << 8) + blue
  }
}

