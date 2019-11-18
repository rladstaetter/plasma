package net.ladstatt.apps.plasma

import net.ladstatt.apps.plasma.MathUtil.{cos, sin}
import net.ladstatt.apps.plasma.PlasmaTypes.{CalculateColorFn, SetColorAtFn}

/**
  * Some helper functions to create funky graphix
  */
object PaintUtil {

  def floatingCircles(time: Double, colX: Double, colY: Double): Double = {
    val cx = colX + 0.5 * sin(time / 5)
    val cy = colY + 0.5 * cos(time / 3)
    sin(Math.sqrt(9 * (cx * cx + cy * cy) + 1) + 5 * time)
  }

  def wobblingBars(time: Double, colX: Double, colY: Double): Double = {
    sin(1 * (colX * sin(5 * time / 2) + colY * cos(5 * time / 3)) + 5 * time)
  }

  def wanderingBars(time: Double, colX: Double, colY: Double): Double = {
    val barCount = 2
    val speedLeft = 15
    sin(colY * barCount + time * speedLeft)
  }

  def dancingCircle(time: Double, colX: Double, colY: Double, timeEffect: Double, blueCenterX: Double, blueCenterY: Double) = {
    sin(Math.sqrt((blueCenterX - colX) * (blueCenterX - colX) + (blueCenterY - colY) * (blueCenterY - colY)) + timeEffect * time)
  }

  def sigmoid(v: Double): Double = 1 / (1 + Math.exp(-v))


}

object Effects {

  /**
    * returns an intersting red/violet melange
    *
    * @param x
    * @param y
    * @param time
    * @param colX
    * @param colY
    * @param maxColX
    * @param maxColY
    * @return
    */
  def e1(x: Double
         , y: Double
         , time: Double
         , colX: Double
         , colY: Double
         , maxColX: Double
         , maxColY: Double
        ): Color = {

    // wobbling bars round each other
    lazy val wobbling = PaintUtil.wobblingBars(time, colX, colY)

    val sinTime = sin(time)
    val cosTime = cos(time)
    val timeEffect = sinTime * 5
    val blueCenterX = sinTime * maxColX
    val blueCenterY = cosTime * maxColY
    val alpha: Double = wobbling + PaintUtil.dancingCircle(time, colX, colY, timeEffect, blueCenterX, blueCenterY)
    val green = sin(alpha * Math.PI)
    val blue = (1 - sinTime * alpha)
    val red = (1 - cosTime * alpha)
    Color(red, green, blue, 1.0)
  }

  def e2(x: Double
         , y: Double
         , time: Double
         , colX: Double
         , colY: Double
         , maxColX: Double
         , maxColY: Double
        ): Color = {

    val sinTime = sin(time)
    val cosTime = cos(time)
    val timeEffect = sinTime * 5
    val blueCenterX = sinTime * maxColX
    val blueCenterY = cosTime * maxColY
    val alpha: Double = PaintUtil.dancingCircle(time, colX, colY, timeEffect, blueCenterX, blueCenterY)
    val green = -1.0
    val blue = -1.0
    val red = 1.0
    Color(red, green, blue, alpha)
  }

}

/**
  * Given an array as memory to paint on, along with dimensions in x and y direction, a blocksize which is dependend
  * on the array implementation, as well
  *
  * @param width     width of painting area (array)
  * @param height    height of painting area (
  * @param blockSize how many array elements do we need for one pixel (important for writing bytes or int's)
  * @tparam A the array implementation
  */
case class FlexibleCanvas[A](a: A
                             , width: Int
                             , height: Int
                             , blockSize: Int
                             , setColorValueAt: SetColorAtFn
                             , calculateColor: CalculateColorFn) {
  /**
    * paint something at a given time (t), on certain coordinates (x,y,colX/colY)
    */
  def paintPixel(time: Double
                 , x: Int
                 , y: Int
                 , colX: Double
                 , colY: Double
                 , maxColX: Double
                 , maxColY: Double): Unit = {

    setColorValueAt(x, y, calculateColor(x, y, time, colX, colY, maxColX, maxColY))

  }

  val canvas = CustomCanvas(width, height, blockSize, paintPixel)

  def draw(t: Double): A = {
    canvas.draw(t)
    a
  }


}