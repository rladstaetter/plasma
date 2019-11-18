package net.ladstatt.apps.plasma

object PlasmaTypes {

  type AeraPainter = (Double, Int, Int, Double, Double, Double, Double) => Unit

  type SetColorAtFn = (Int, Int, Color) => Unit

  type CalculateColorFn =
    (Double // x
      , Double // y
      , Double // time
      , Double // colX
      , Double // colY
      , Double // maxColX
      , Double // maxColY
      ) => Color

}
