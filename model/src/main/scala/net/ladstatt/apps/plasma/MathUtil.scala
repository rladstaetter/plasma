package net.ladstatt.apps.plasma

object MathUtil {

  final val m2pi = r(2 * Math.PI)

  final private def r(d: Double): Double = Math.round(d * 1000d) / 1000d

  final val numericRange = Range.BigDecimal.inclusive(BigDecimal(-m2pi), BigDecimal(m2pi), BigDecimal(0.001))

  /**
    * precomputing sinus table
    */
  final private val sinTable: Array[Double] = (for (i <- numericRange) yield Math.sin(i.doubleValue)).toArray

  /**
    * precomputing cosinus table
    */
  final private val cosTable: Array[Double] = (for (i <- numericRange) yield Math.cos(i.doubleValue)).toArray

  /**
    * reduces sin calculation to a lookup, possibly inlined by compiler
    *
    * @param angle
    * @return
    */
  @inline def sinWithLookup(angle: Double): Double = sinTable(((r(angle % m2pi) + m2pi) * 1000).toInt)

  @inline def nativeSin(rangle: Double): Double = Math.sin(rangle)

  @inline def cosWithLookup(angle: Double): Double = cosTable(((r(angle % m2pi) + m2pi) * 1000).toInt)

  @inline def nativeCos(angle: Double): Double = Math.cos(angle)

  val sin = sinWithLookup _
  val cos = cosWithLookup _

}