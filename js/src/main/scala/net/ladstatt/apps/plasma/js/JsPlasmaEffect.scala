package net.ladstatt.apps.plasma.js

import net.ladstatt.apps.plasma.PlasmaBase

/**
  * We have to provide a js.Array[Int] as backing array and also provide a method which translates our calculated
  * colors for each pixel to the appropriate form for the javascript canvas.
  */
case class JsPlasmaEffect(width: Int, height: Int, blockSize: Int) extends PlasmaBase[scala.scalajs.js.Array[Int]](width, height, blockSize) {

  def writeBackingArray(backingArray: scala.scalajs.js.Array[Int],
                        x: Int, y: Int,
                        red: Int, green: Int, blue: Int, alpha: Int): Unit = {
    val rb = x + y
    val gb = x + y + 1
    val bb = x + y + 2
    val ab = x + y + 3

    backingArray(rb) = red
    backingArray(gb) = green
    backingArray(bb) = blue
    backingArray(ab) = alpha

  }

}
