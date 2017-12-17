package net.ladstatt.apps.plasma

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("net.ladstatt.apps.plasma.Plasma")
object Plasma {
  /*
    private val BigScreen: (Int, Int) = (1024, 768)
    private val MediumScreen: (Int, Int) = (800, 600)
    private val SmallScreen: (Int, Int) = (640, 480)
    private val Banner: (Int, Int) = (450, 150)
    private val Tiny: (Int, Int) = (100, 100)

    val (width, height) = BigScreen
  */
  var currentT: Double = 0

  @JSExport
  def paint(canvas: html.Canvas, width: Int, height: Int, timeout: Int = 50): Unit = {
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val pEffect = PlasmaEffect(width, height, 4)
    val fn: js.Function0[Any] = () => run(ctx, pEffect, width, height)
    val handler = dom.window.setInterval(fn, timeout)
  }

  def run(ctx: CanvasRenderingContext2D, pEffect: PlasmaEffect, width: Int, height: Int): Unit = {
    val imgData = ctx.getImageData(0, 0, width, height)
    val a: js.Array[Int] = imgData.data
    val (newA, nextT) = pEffect.draw(a, currentT)
    ctx.putImageData(imgData, 0, 0)
    currentT = nextT
  }

}




