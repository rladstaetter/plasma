package net.ladstatt.apps.plasma.js

import net.ladstatt.apps.plasma.PlasmaEffect

import scala.scalajs.js


/**
  * We have to provide a js.Array[Int] as backing array and also provide a method which translates our calculated
  * colors for each pixel to the appropriate form for the javascript canvas.
  */
case class ScalaJsArrayBackedPlasmaEffect(width: Int
                                          , height: Int
                                          , blockSize: Int)
  extends PlasmaEffect[scala.scalajs.js.Array[Int]](
    width
    , height
    , blockSize) {

  override def updateArray(backingArray: js.Array[Int], x: Int, y: Int, red: Int, green: Int, blue: Int, alpha: Int): Unit = {
    val idx = x + y
    backingArray(idx) = red
    backingArray(idx + 1) = green
    backingArray(idx + 2) = blue
    backingArray(idx + 3) = alpha
  }
}