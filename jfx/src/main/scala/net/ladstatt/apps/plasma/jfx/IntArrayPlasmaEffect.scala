package net.ladstatt.apps.plasma.jfx


import javafx.scene.canvas.Canvas
import net.ladstatt.apps.plasma.{Color, FlexibleCanvas, Effects}


/**
  * PlasmaEffect backed up by an Array[Int]
  */
case class IntArrayPlasmaEffect(width: Int
                                , height: Int
                                , backingMemory: Array[Int]) {

  private def updateColorAt(x: Int, y: Int, color: Color): Unit = {
    backingMemory(x + y) = (color.alphaInt << 24) + (color.redInt << 16) + (color.greenInt << 8) + color.blueInt
  }

  private val e = FlexibleCanvas(backingMemory, width, height, 1, updateColorAt, Effects.e1)

  /**
    * when called, writes plasma effect to backing memory
    *
    * @param time
    */
  def draw(time: Double): IntArrayPlasmaEffect = {
    copy(backingMemory = e.draw(time))
  }

  def render(canvas: Canvas): Unit = {
    JfxRenderer.render(canvas, backingMemory, width, height)
  }
}

