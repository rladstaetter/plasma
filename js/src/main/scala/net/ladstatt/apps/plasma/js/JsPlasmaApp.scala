package net.ladstatt.apps.plasma.js

import net.ladstatt.apps.plasma.{MathUtil, Timeline}
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("JsPlasmaApp")
object JsPlasmaApp {

  // timeline
  var current = 0.0
  var direction = 1
  val nextValue = Timeline.calcNext(0.01, 0.0, MathUtil.m2pi) _


  @JSExport
  def render(canvas: html.Canvas): Unit = {
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val pEffect = ScalaJsArrayBackedPlasmaEffect(ctx.createImageData(canvas.width, canvas.height))
    val fn: scala.scalajs.js.Function0[Any] =
      () => {
        pEffect.render(ctx, current)
        val (ndirection, ncurrent) = nextValue(current, direction)
        current = ncurrent
        direction = ndirection
      }
    val handler = dom.window.setInterval(fn, 0)
  }

}




