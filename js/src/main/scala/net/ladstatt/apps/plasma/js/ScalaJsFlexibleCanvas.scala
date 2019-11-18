package net.ladstatt.apps.plasma.js

import net.ladstatt.apps.plasma.PlasmaTypes.CalculateColorFn
import net.ladstatt.apps.plasma.{Color, Effects, FlexibleCanvas}
import org.scalajs.dom.ImageData
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js


/**
  * We have to provide a js.Array[Int] as backing array and also provide a method which translates our calculated
  * colors for each pixel to the appropriate form for the javascript canvas.
  */
case class ScalaJsFlexibleCanvas(imageData: ImageData
                                , calculateColor: CalculateColorFn) {

  private val backingArray: js.Array[Int] = imageData.data

  private def updateColorAt(x: Int, y: Int, color: Color): Unit = {
    val idx = x + y
    backingArray(idx) = color.redInt
    backingArray(idx + 1) = color.greenInt
    backingArray(idx + 2) = color.blueInt
    backingArray(idx + 3) = color.alphaInt
  }

  private val e = FlexibleCanvas[scala.scalajs.js.Array[Int]](
    backingArray
    , imageData.width
    , imageData.height
    , blockSize = 4
    , updateColorAt
    , calculateColor)

  private def draw(currentT: Double): ScalaJsFlexibleCanvas = {
    e.draw(currentT)
    this
  }

  def render(ctx: CanvasRenderingContext2D, currentT: Double): ScalaJsFlexibleCanvas = {
    draw(currentT)
    ctx.putImageData(imageData, 0, 0)
    this
  }

}