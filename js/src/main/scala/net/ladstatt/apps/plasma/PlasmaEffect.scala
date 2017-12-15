package net.ladstatt.apps.plasma

import scala.scalajs.js

case class PlasmaEffect(width: Int, height: Int) {

  /**
    * ranges for x and y direction
    */
  val (ixs, iys) = (0 until width, 0 until height)

  val (xs: Array[Int], ys: Array[Int]) = (ixs.map(_ * 4).toArray, iys.map(_ * width * 4).toArray)

  val xPiFac: Double = 2 * Math.PI / width.toDouble
  val yPiFac: Double = 2 * Math.PI / height.toDouble

  val colXs: Array[Double] = ixs.map(x => xPiFac * x - Math.PI).toArray
  val colYs: Array[Double] = iys.map(y => yPiFac * y - Math.PI).toArray


  val colorDepth = 254


  def draw(a: js.Array[Int], time: Double): (js.Array[Int], Double) = {

    //val a: Array[Int] = Array.tabulate(width * height * 3)(i => 0.toByte)

    var yIdx = 0
    while (yIdx < height) {
      var xIdx = 0
      while (xIdx < width) {
        val x = xs(xIdx)
        val colX = colXs(xIdx)
        val y = ys(yIdx)
        val colY = colYs(yIdx)
        paintPixel(a, time, x, colX, y, colY)
        xIdx = xIdx + 1
      }
      yIdx = yIdx + 1
    }

    (a,
      if (time > 2 * Math.PI) {
        0
      } else
        time + xPiFac)
  }

  private def opaintPixel(a: js.Array[Int]
                         , time: Double
                         , x: Int
                         , colX: Double
                         , y: Int
                         , colY: Double): Unit = {

    a(x + 0 + y) = 254
    a(x + 1 + y) = 0
    a(x + 2 + y) = 0
    a(x + 3 + y) = 254
  }


  private def paintPixel(backingArray: js.Array[Int]
                          , time: Double
                          , x: Int
                          , colX: Double
                          , y: Int
                          , colY: Double): Unit = {

    val v: Double = {

      // wandering bars from right to left
      val wandering = wanderingBars(time, colX, colY)

      // wobbling bars round each other
      val wobbling = wobblingBars(time, colX, colY)

      // circles floating around
      val floating = floatingCircles(time, colX, colY)

      // adding all together
      wandering  + wobbling + floating
    }


    backingArray(x + 0 + y) = 0
    backingArray(x + 1 + y) = (sin(v * Math.PI + 2 * Math.PI / 3) * colorDepth).toInt
    backingArray(x + 2 + y) = (sin(v * Math.PI) * colorDepth).toInt
    backingArray(x + 3 + y) = 254

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


  def sin(angle: Double): Double = Math.sin(angle)

  def cos(angle: Double): Double = Math.cos(angle)


}
