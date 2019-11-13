package net.ladstatt.apps.plasma.js

import net.ladstatt.apps.plasma.Timeline
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("JsPlasmaApp")
object JsPlasmaApp {

  // timeline
  var tl = Timeline.Default

  @JSExport
  def renderPos(canvas: html.Canvas, pos: Double): Unit = {
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val pEffect = ScalaJsArrayBackedPlasmaEffect(ctx.createImageData(canvas.width, canvas.height))
    pEffect.render(ctx, pos)
  }

  @JSExport
  def render(canvas: html.Canvas): Unit = {
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val pEffect = ScalaJsArrayBackedPlasmaEffect(ctx.createImageData(canvas.width, canvas.height))
    val fn: scala.scalajs.js.Function0[Any] =
      () => {
        pEffect.render(ctx, tl.current / 100.0)
        tl = tl.next
      }
    val handler = dom.window.setInterval(fn, 0)
  }

}




