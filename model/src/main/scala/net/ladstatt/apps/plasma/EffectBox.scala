package net.ladstatt.apps.plasma
import java.lang.Math.{cos, sin}

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("net.ladstatt.apps.plasma.EffectBox")
object EffectBox {

  type SfxFn = (Double, Double, Double, Double) => Double

  def floatingCircles(v: Double, time: Double, colX: Double, colY: Double): Double = {
    val cx = colX + 0.5 * sin(time / 5)
    val cy = colY + 0.5 * cos(time / 3)
    sin(Math.sqrt(9 * (cx * cx + cy * cy) + 1) + 5 * time)
  }

  def wobblingBars(v: Double, time: Double, colX: Double, colY: Double): Double = {
    sin(1 * (colX * sin(5 * time / 2) + colY * cos(5 * time / 3)) + 5 * time)
  }

  def wanderingBars(v: Double, time: Double, colX: Double, colY: Double): Double = {
    val barCount = 2
    val speedLeft = 15
    sin(colY * barCount + time * speedLeft)
  }

}