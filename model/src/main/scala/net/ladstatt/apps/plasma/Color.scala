package net.ladstatt.apps.plasma

import scala.scalajs.js.annotation.JSExportTopLevel


object Color {

  def c(value: Double): Int = (value / (1 / 127.0)).toInt + 127

  /*
    def apply(red: Double, green: Double, blue: Double, alpha: Double): Color = {
      new Color(c(red), c(green), c(blue), c(alpha))
    }
    */
}

@JSExportTopLevel("Color")
case class Color(val red: Double
                 , val green: Double
                 , val blue: Double
                 , val alpha: Double) {

  lazy val redInt: Int = Color.c(red)
  lazy val greenInt: Int = Color.c(green)
  lazy val blueInt: Int = Color.c(blue)
  lazy val alphaInt: Int = Color.c(alpha)
}
