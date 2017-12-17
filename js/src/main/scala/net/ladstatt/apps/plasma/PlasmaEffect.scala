package net.ladstatt.apps.plasma

import scala.scalajs.js
import Math.sin
import Math.cos

/**
  *
  * @param width
  * @param height
  * @param blockSize how many array elements do we need for one pixel
  */
case class PlasmaEffect(width: Int, height: Int, blockSize: Int) {

  import EffectBox._

  /**
    * ranges for x and y direction
    */
  val (ixs, iys) = (0 until width, 0 until height)

  val (xs: Array[Int], ys: Array[Int]) = (ixs.map(_ * blockSize).toArray, iys.map(_ * width * blockSize).toArray)

  private val pi2: Double = 2 * Math.PI

  val xPiFac: Double = pi2 / width.toDouble
  val yPiFac: Double = pi2 / height.toDouble

  val colXs: Array[Double] = ixs.map(x => xPiFac * x - Math.PI).toArray
  val colYs: Array[Double] = iys.map(y => yPiFac * y - Math.PI).toArray

  val colorDepth = 254


  def draw(a: js.Array[Int], time: Double): (js.Array[Int], Double) = {

    var yIdx = 0
    while (yIdx < height) {
      var xIdx = 0
      val y = ys(yIdx)
      val colY = colYs(yIdx)

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


  //val fns: Seq[SfxFn] = Seq(wanderingBars, floatingCircles)
  //val fns2: Seq[SfxFn] = Seq(wobblingBars)
  //val fns1: Seq[SfxFn] = Seq(floatingCircles)
  val fns: Seq[SfxFn] = Seq(wanderingBars, wobblingBars, floatingCircles)

  private def paintPixel(backingArray: js.Array[Int]
                         , time: Double
                         , x: Int
                         , colX: Double
                         , y: Int
                         , colY: Double): Unit = {

    //val v = Random.shuffle(fns).foldLeft(0.0)((acc, fn) => acc + fn(time, colX, colY))

    // wandering bars from right to left
    val wandering = wanderingBars(0, time, colX, colY)

    // wobbling bars round each other
    val wobbling = wobblingBars(wandering, time, colX, colY)

    // circles floating around
    val floating = floatingCircles(wobbling, time, colX, colY)

    // adding all together
    val v: Double = wandering + wobbling + floating
    import Math._

    val rb = x + y
    val gb = x + y + 1
    val bb = x + y + 2
    val ab = x + y + 3

    backingArray(rb) = 0
    backingArray(gb) = (Math.abs(sin(v * Math.PI + sin(v))) * colorDepth).toInt
    backingArray(bb) = (Math.abs(sin(v * Math.PI)) * colorDepth).toInt
    backingArray(ab) = (Math.sin(abs(floating * v)) * colorDepth).toInt

  }


}
