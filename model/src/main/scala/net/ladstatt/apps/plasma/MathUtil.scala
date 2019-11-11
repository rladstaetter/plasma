package net.ladstatt.apps.plasma

object MathUtil {

  val m2pi = r(2 * Math.PI)

  def r(d: Double): Double = Math.round(d * 1000d) / 1000d

  val numericRange = Range.BigDecimal.inclusive(BigDecimal(-m2pi), BigDecimal(m2pi), BigDecimal(0.001))

  /**
    * precomputing sinus table
    */
  val sinTable: Array[Double] = (for (i <- numericRange) yield Math.sin(i.doubleValue)).toArray

  /**
    * precomputing cosinus table
    */
  val cosTable: Array[Double] = (for (i <- numericRange) yield Math.cos(i.doubleValue)).toArray

  /**
    * reduces sin calculation to a lookup, possibly inlined by compiler
    *
    * @param angle
    * @return
    */
  @inline def sin(angle: Double): Double = sinTable(((r(angle % m2pi) + m2pi) * 1000).toInt)

  @inline def cos(angle: Double): Double = cosTable(((r(angle % m2pi) + m2pi) * 1000).toInt)


}