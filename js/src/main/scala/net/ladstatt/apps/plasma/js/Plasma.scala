package net.ladstatt.apps.plasma.js

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.{CanvasRenderingContext2D, ImageData}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("Plasma")
object Plasma {

  var currentT: Double = 0

  @JSExport
  def paint(canvas: html.Canvas, width: Int, height: Int, timeout: Int = 50): Unit = {
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val a = ctx.getImageData(0, 0, width, height).data
    val pEffect = ScalaJsArrayBackedPlasmaEffect(width, height, 4)
    val fn: scala.scalajs.js.Function0[Any] = () => run(ctx, pEffect, width, height)
    val handler = dom.window.setInterval(fn, timeout)
  }

  def run(ctx: CanvasRenderingContext2D, pEffect: ScalaJsArrayBackedPlasmaEffect, width: Int, height: Int): Unit = {
    val imgData: ImageData = ctx.getImageData(0, 0, width, height)
    val a: scala.scalajs.js.Array[Int] = imgData.data
    val iData = ctx.createImageData(width, height)
    val nextT: Double = pEffect.draw(iData.data, currentT)
    //   val (newA : scala.scalajs.js.Array[Int], nextT : Double) = pEffect.draw(currentT)
    ctx.putImageData(iData, 0, 0)
    currentT = nextT
  }

}




