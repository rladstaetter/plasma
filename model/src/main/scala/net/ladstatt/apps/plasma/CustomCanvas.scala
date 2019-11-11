package net.ladstatt.apps.plasma

/**
  * Painting canvas for setting each pixel and painting frame after frame.
  *
  * @tparam A array to fill with values
  */
abstract class CustomCanvas[A](width: Int, height: Int, blockSize: Int) {

  import MathUtil._

  /**
    * ranges for x and y direction
    */
  val (ixs, iys) = (0 until width, 0 until height)

  val (xs: Array[Int], ys: Array[Int]) = (ixs.map(_ * blockSize).toArray, iys.map(_ * width * blockSize).toArray)

  val xPiFac: Double = m2pi / width.toDouble
  val yPiFac: Double = m2pi / height.toDouble

  val colXs: Array[Double] = ixs.map(x => xPiFac * x - Math.PI).toArray
  val colYs: Array[Double] = iys.map(y => yPiFac * y - Math.PI).toArray

  val colorDepth = 254

  def draw(a: A, time: Double): (A, Double) = {

    var yIdx = 0
    while (yIdx < height) {
      var xIdx = 0
      val y = ys(yIdx)
      val colY = colYs(yIdx)

      while (xIdx < width) {
        val x = xs(xIdx)
        val colX = colXs(xIdx)
        paintPixel(a, time, x, y, colX, colY)
        xIdx = xIdx + 1
      }
      yIdx = yIdx + 1
    }

    (a, time + yPiFac)
  }

  def paintPixel(backingArray: A
                 , time: Double
                 , x: Int, y: Int
                 , colX: Double, colY: Double): Unit


  def writeBackingArray(backingArray: A,
                        x: Int, y: Int,
                        red: Int, green: Int, blue: Int, alpha: Int): Unit
}
