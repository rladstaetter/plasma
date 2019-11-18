package net.ladstatt.apps.plasma.jfx

import javafx.scene.canvas.Canvas
import javafx.scene.image.{PixelFormat, PixelWriter}


object JfxRenderer {

  def render(canvas: Canvas, backingMemory: Array[Int], width: Int, height: Int): Unit = {
    val pxw: PixelWriter = canvas.getGraphicsContext2D.getPixelWriter
    pxw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance, backingMemory, 0, width)
  }

}
