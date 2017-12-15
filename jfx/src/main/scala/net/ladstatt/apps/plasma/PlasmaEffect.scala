package net.ladstatt.apps.plasma

import java.text.DecimalFormat
import java.util.Random

import scala.collection.immutable.ListMap

case class PlasmaEffect(width: Int, height: Int) {

  /**
    * ranges for x and y direction
    */
  val (ixs, iys) = (0 until width, 0 until height)

  val (xs: Array[Int], ys: Array[Int]) = (ixs.toArray, iys.map(_ * width).toArray)

  val xPiFac: Double = 2 * Math.PI / width.toDouble
  val yPiFac: Double = 2 * Math.PI / height.toDouble

  val colXs: Array[Double] = ixs.map(x => xPiFac * x - Math.PI).toArray
  val colYs: Array[Double] = iys.map(y => yPiFac * y - Math.PI).toArray

  val colorDepth = 254


  def draw(a: Array[Int], time: Double): (Array[Int], Double) = {

    var yIdx = 0
    while (yIdx < height) {
      var xIdx = 0
      val y = ys(yIdx)
      val colY = colYs(yIdx)
      // println(s"yIdx: $yIdx, \t\tcolY : ${colYs(yIdx)}")
      while (xIdx < width) {
        val x = xs(xIdx)
        val colX = colXs(xIdx)
        paintPixel(a, time, x, colX, y, colY)
        xIdx = xIdx + 1
      }
      yIdx = yIdx + 1
    }

    (a, time + yPiFac)
  }

  private def paintPixel(backingArray: Array[Int]
                         , time: Double
                         , x: Int
                         , colX: Double
                         , y: Int
                         , colY: Double): Unit = {

    // wandering bars from right to left
    val wandering = wanderingBars(time, colX, colY)

    // wobbling bars round each other
    val wobbling = wobblingBars(time, colX, colY)

    // circles floating around
    val floating = floatingCircles(time, colX, colY)

    // adding all together
    val v: Double = wandering + wobbling + floating


    val r = 0
    val g = (Math.abs(sin(v * Math.PI + 2 * Math.PI / 3)) * colorDepth).toInt
    val b = (Math.abs(sin(v * Math.PI)) * colorDepth).toInt
    val alpha = 255

    backingArray(x + 0 + y) = (alpha << 24) + (r << 16) + (g << 8) + b

  }

  private def floatingCircles(time: Double, colX: Double, colY: Double) = {
    val cx = colX + 0.5 * sin(time / 5)
    val cy = colY + 0.5 * cos(time / 3)
    sin(Math.sqrt(9 * (cx * cx + cy * cy) + 1) + 5 * time)
  }

  private def wobblingBars(time: Double, colX: Double, colY: Double): Double = {
    sin(1 * (colX * sin(5 * time / 2) + colY * cos(5 * time / 3)) + 5 * time)
  }

  private def wanderingBars(time: Double, colX: Double, colY: Double): Double = {
    val barCount = 2
    val speedLeft = 15
    sin(colY * barCount + time * speedLeft)
  }

  val m2pi = r(2 * Math.PI)

  def r(d: Double): Double = Math.round(d * 1000d) / 1000d

  /**
    * precomputing sinus table
    */
  val sinTable: Array[Double] = (for (i <- -2 * Math.PI to 2 * Math.PI by 0.001) yield Math.sin(i)).toArray

  /**
    * precomputing cosinus table
    */
  val cosTable: Array[Double] = (for (i <- -2 * Math.PI to 2 * Math.PI by 0.001) yield Math.cos(i)).toArray

  /**
    * reduces sin calculation to a lookup, possibly inlined by compiler
    * @param angle
    * @return
    */
  @inline
  def sin(angle: Double): Double = sinTable(((r(angle % m2pi) + m2pi) * 1000).toInt)
  @inline
  def cos(angle: Double): Double = cosTable(((r(angle % m2pi) + m2pi) * 1000).toInt)

  //def cos(angle: Double): Double = Math.cos(angle)



}
