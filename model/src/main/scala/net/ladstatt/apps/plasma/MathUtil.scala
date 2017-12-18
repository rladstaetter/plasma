package net.ladstatt.apps.plasma

import scala.scalajs.js.annotation.JSExportTopLevel

object MathUtil {

  val m2pi = r(2 * Math.PI)

  def r(d: Double): Double = Math.round(d * 1000d) / 1000d

  /**
    * precomputing sinus table
    */
  val sinTable: Array[Double] = (for (i <- -m2pi to m2pi by 0.001) yield Math.sin(i)).toArray

  /**
    * precomputing cosinus table
    */
  val cosTable: Array[Double] = (for (i <- -m2pi to m2pi by 0.001) yield Math.cos(i)).toArray

  /**
    * reduces sin calculation to a lookup, possibly inlined by compiler
    *
    * @param angle
    * @return
    */
  @inline def sin(angle: Double): Double = sinTable(((r(angle % m2pi) + m2pi) * 1000).toInt)

  @inline def cos(angle: Double): Double = cosTable(((r(angle % m2pi) + m2pi) * 1000).toInt)


}