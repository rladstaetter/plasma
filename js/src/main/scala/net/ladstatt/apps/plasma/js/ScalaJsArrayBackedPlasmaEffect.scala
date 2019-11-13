package net.ladstatt.apps.plasma.js

import net.ladstatt.apps.plasma.{Color, PlasmaEffect}
import org.scalajs.dom.ImageData
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js


/**
  * We have to provide a js.Array[Int] as backing array and also provide a method which translates our calculated
  * colors for each pixel to the appropriate form for the javascript canvas.
  */
case class ScalaJsArrayBackedPlasmaEffect(imageData: ImageData) {


  private val backingArray: js.Array[Int] = imageData.data

  private def updateColorAt(x: Int, y: Int, color: Color): Unit = {
    val idx = x + y
    backingArray(idx) = color.red
    backingArray(idx + 1) = color.green
    backingArray(idx + 2) = color.blue
    backingArray(idx + 3) = color.alpha
  }

  private val e = PlasmaEffect[scala.scalajs.js.Array[Int]](
    backingArray
    , imageData.width
    , imageData.height
    , 4
    , updateColorAt)

  private def draw(currentT: Double): Unit = e.draw(currentT)

  def render(ctx: CanvasRenderingContext2D, currentT: Double): Unit = {
    draw(currentT)
    ctx.putImageData(imageData, 0, 0)
  }

}