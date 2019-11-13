package net.ladstatt.apps.plasma.jfx


import javafx.scene.canvas.Canvas
import javafx.scene.image.{PixelFormat, PixelWriter}
import net.ladstatt.apps.plasma.{Color, PlasmaEffect}


/**
  * PlasmaEffect backed up by an Array[Int]
  */
case class IntArrayBackedPlasmaEffect(width: Int, height: Int) {

  private val backingMemory: Array[Int] = Array.tabulate(width * height)(_ => 0)

  private def updateColorAt(x: Int, y: Int, color: Color): Unit = {
    backingMemory(x + y) = (color.alpha << 24) + (color.red << 16) + (color.green << 8) + color.blue
  }

  private val e = PlasmaEffect(backingMemory, width, height, 1, updateColorAt)

  private def draw(currentT: Double): Unit = e.draw(currentT)

  def render(canvas: Canvas, t: Double): Unit = {
    draw(t)
    val pxw: PixelWriter = canvas.getGraphicsContext2D.getPixelWriter
    pxw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance, backingMemory, 0, width)
  }
}

