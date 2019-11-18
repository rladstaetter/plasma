package net.ladstatt.apps.plasma

object Timeline {

  val Default = Timeline(1, 1, 1, 0, 1000)

}

case class Timeline(current: Int
                    , direction: Int
                    , increment: Int
                    , lowerBound: Int
                    , upperBound: Int) {

  lazy val range = lowerBound to upperBound by increment

  def next: Timeline = {
    val next = current + increment * direction
    direction match {
      case 1 =>
        if (next < upperBound) {
          copy(direction = direction, current = next)
        } else {
          copy(direction = -direction, current = next)
        }
      case -1 =>
        if (next > lowerBound) {
          copy(direction = direction, current = next)
        } else {
          copy(direction = -direction, current = next)
        }
    }
  }
}