package net.ladstatt.apps.plasma.jfx

import net.ladstatt.apps.plasma.PlasmaBase

/**
  * Parameterize PlasmaBase with an array and provide a method to set each element of the backing array with appropriate values.
  */
case class JfxPlasmaEffect(width: Int, height: Int, blockSize: Int) extends PlasmaBase[Array[Int]](width, height, blockSize) {

  def writeBackingArray(backingArray: Array[Int],
                        x: Int, y: Int,
                        red: Int, green: Int, blue: Int, alpha: Int): Unit = {
    backingArray(x + y) = (alpha << 24) + (red << 16) + (green << 8) + blue
  }

}
