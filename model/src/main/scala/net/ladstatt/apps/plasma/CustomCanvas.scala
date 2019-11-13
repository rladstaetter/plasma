package net.ladstatt.apps.plasma

import net.ladstatt.apps.plasma.PlasmaTypes.AeraPainter

/**
  * Painting canvas for setting each pixel and painting frame after frame.
  */
case class CustomCanvas(width: Int
                        , height: Int
                        , blockSize: Int
                        , aeraPainter: AeraPainter) {

  /**
    * ranges for x and y direction
    */
  private val (ixs, iys) = (0 until width, 0 until height)

  private val (xs: Array[Int], ys: Array[Int]) = (ixs.map(_ * blockSize).toArray, iys.map(_ * width * blockSize).toArray)

  private val xPiFac: Double = MathUtil.m2pi / width.toDouble
  private val yPiFac: Double = MathUtil.m2pi / height.toDouble

  private val colXs: Array[Double] = ixs.map(x => xPiFac * x - Math.PI).toArray
  private val colYs: Array[Double] = iys.map(y => yPiFac * y - Math.PI).toArray

  def draw(time: Double): Unit = {
    var yIdx = 0
    while (yIdx < height) {
      var xIdx = 0
      val y = ys(yIdx)
      val colY = colYs(yIdx)
      while (xIdx < width) {
        val x = xs(xIdx)
        val colX = colXs(xIdx)
        aeraPainter(time, x, y, colX, colY)
        xIdx = xIdx + 1
      }
      yIdx = yIdx + 1
    }
  }



}
