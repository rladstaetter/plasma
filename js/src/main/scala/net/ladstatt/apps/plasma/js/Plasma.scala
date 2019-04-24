package net.ladstatt.apps.plasma.js

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("Plasma")
object Plasma {

  var currentT: Double = 0

  @JSExport
  def paint(canvas: html.Canvas, width: Int, height: Int, timeout: Int = 50): Unit = {
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val pEffect = JsPlasmaEffect(width, height, 4)
    val fn: scala.scalajs.js.Function0[Any] = () => run(ctx, pEffect, width, height)
    val handler = dom.window.setInterval(fn, timeout)
  }

  def run(ctx: CanvasRenderingContext2D, pEffect: JsPlasmaEffect, width: Int, height: Int): Unit = {
    val imgData = ctx.getImageData(0, 0, width, height)
    val a: scala.scalajs.js.Array[Int] = imgData.data
    val (newA, nextT) = pEffect.draw(a, currentT)
    ctx.putImageData(imgData, 0, 0)
    currentT = nextT
  }

}




