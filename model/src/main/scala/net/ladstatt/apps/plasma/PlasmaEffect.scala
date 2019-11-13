package net.ladstatt.apps.plasma

import java.lang.Math.abs

import net.ladstatt.apps.plasma.MathUtil.{cos, sin}
import net.ladstatt.apps.plasma.PlasmaTypes.UpdateBackingMemoryFn

case class Color(red: Int, green: Int, blue: Int, alpha: Int)

object PlasmaTypes {

  type AeraPainter = (Double, Int, Int, Double, Double) => Unit

  type UpdateBackingMemoryFn = (Int, Int, Color) => Unit
}

object Timeline {

  val Default = Timeline(1, 1, 1, 0, 1000)

}

case class Timeline(current: Int
                    , direction: Int
                    , increment: Int
                    , lowerBound: Int
                    , upperBound: Int) {

  lazy val range = lowerBound to upperBound by increment

  def next: Timeline = {
    val next = current + increment * direction
    direction match {
      case 1 =>
        if (next < upperBound) {
          copy(direction = direction, current = next)
        } else {
          copy(direction = -direction, current = next)
        }
      case -1 =>
        if (next > lowerBound) {
          copy(direction = direction, current = next)
        } else {
          copy(direction = -direction, current = next)
        }
    }
  }
}

/**
  * A plasma effect.
  *
  * @param width     width of painting area (array)
  * @param height    height of painting area (
  * @param blockSize how many array elements do we need for one pixel (important for writing bytes or int's)
  * @tparam A the array implementation
  */
case class PlasmaEffect[A](a: A
                           , width: Int
                           , height: Int
                           , blockSize: Int
                           , updateBackingMemoryFn: UpdateBackingMemoryFn) {


  /**
    * paint something at a given time (t), on certain coordinates (x,y,colX/colY)
    */
  def paintPixel(time: Double
                 , x: Int
                 , y: Int
                 , colX: Double
                 , colY: Double): Unit = {

    updateBackingMemoryFn(x, y, plasmaEffect(time, colX, colY))

  }

  val canvas = CustomCanvas(width, height, blockSize, paintPixel)

  def draw(t: Double): Unit = canvas.draw(t)

  private val colorDepth = 254

  private def floatingCircles(v: Double, time: Double, colX: Double, colY: Double): Double = {
    val cx = colX + 0.5 * sin(time / 5)
    val cy = colY + 0.5 * cos(time / 3)
    sin(Math.sqrt(9 * (cx * cx + cy * cy) + 1) + 5 * time)
  }

  private def wobblingBars(v: Double, time: Double, colX: Double, colY: Double): Double = {
    sin(1 * (colX * sin(5 * time / 2) + colY * cos(5 * time / 3)) + 5 * time)
  }

  private def wanderingBars(v: Double, time: Double, colX: Double, colY: Double): Double = {
    val barCount = 2
    val speedLeft = 15
    sin(colY * barCount + time * speedLeft)
  }

  def plasmaEffect(time: Double
                   , colX: Double
                   , colY: Double): Color = {
    // wandering bars from right to left
    val wandering = wanderingBars(0, time, colX, colY)

    // wobbling bars round each other
    val wobbling = wobblingBars(wandering, time, colX, colY)

    // circles floating around
    val floating = floatingCircles(wobbling, time, colX, colY)

    // adding all together
    val v: Double = wandering + wobbling + floating


    val red: Int = 0
    val green: Int = (abs(sin(v * Math.PI + sin(v))) * colorDepth).toInt
    val blue: Int = (abs(sin(v * Math.PI)) * colorDepth).toInt
    val alpha: Int = (sin(Math.abs(floating * v)) * colorDepth).toInt
    Color(red, green, blue, alpha)
  }


}