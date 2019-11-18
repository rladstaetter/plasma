package net.ladstatt.apps.plasma.js

import net.ladstatt.apps.plasma.PlasmaTypes.CalculateColorFn
import net.ladstatt.apps.plasma.{Color, Effects, Timeline}
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.CanvasRenderingContext2D

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("JsPlasmaApp")
object JsPlasmaApp {

  // timeline
  var tl = Timeline.Default

  @JSExport
  def renderUsingFn(canvas: html.Canvas
                    , label: html.Div
                    , jsEffect: scala.scalajs.js.Function7[Double,Double,Double,Double,Double,Double,Double,Color]): Unit = {
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    val pEffect = ScalaJsFlexibleCanvas(ctx.createImageData(canvas.width, canvas.height), jsEffect)

    val fn: scala.scalajs.js.Function0[Any] =
      () => {
        val before = System.currentTimeMillis()
        pEffect.render(ctx, tl.current / 100.0)
        val duration = System.currentTimeMillis() - before
        label.innerHTML = "duration: " + duration + " ms"
        tl = tl.next
      }

    val handler = dom.window.setInterval(fn, 0)
  }

  @JSExport
  def render(canvas: html.Canvas, label: html.Div): Unit = {
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    val pEffect = ScalaJsFlexibleCanvas(ctx.createImageData(canvas.width, canvas.height), Effects.e1)
    val fn: scala.scalajs.js.Function0[Any] =
      () => {
        val before = System.currentTimeMillis()
        pEffect.render(ctx, tl.current / 100.0)
        val duration = System.currentTimeMillis() - before
        label.innerHTML = "duration: " + duration + " ms"
        tl = tl.next
      }

    val handler = dom.window.setInterval(fn, 0)
  }


  @JSExport
  def renderPos(canvas: html.Canvas, pos: Double): Unit = {
    val ctx: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val pEffect = ScalaJsFlexibleCanvas(ctx.createImageData(canvas.width, canvas.height), Effects.e2)
    pEffect.render(ctx, pos)
  }
}




