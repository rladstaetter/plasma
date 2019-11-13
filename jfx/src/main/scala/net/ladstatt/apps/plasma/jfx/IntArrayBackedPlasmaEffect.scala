package net.ladstatt.apps.plasma.jfx


import java.util

import javafx.scene.canvas.Canvas
import javafx.scene.image.{PixelFormat, PixelWriter}
import net.ladstatt.apps.plasma.{Color, PlasmaEffect}


object RenderMan {


  def render(canvas: Canvas, backingMemory: Array[Int], width: Int, height: Int): Unit = {
    val pxw: PixelWriter = canvas.getGraphicsContext2D.getPixelWriter
    pxw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance, backingMemory, 0, width)
  }

}

/**
  * PlasmaEffect backed up by an Array[Int]
  */
case class IntArrayBackedPlasmaEffect(width: Int
                                      , height: Int
                                     , backingMemory : Array[Int]) {

  private def updateColorAt(x: Int, y: Int, color: Color): Unit = {
    backingMemory(x + y) = (color.alpha << 24) + (color.red << 16) + (color.green << 8) + color.blue
  }

  private val e = PlasmaEffect(backingMemory, width, height, 1, updateColorAt)

  /**
    * when called, writes plasma effect to backing memory
    *
    * @param currentT
    */
  def draw(currentT: Double): IntArrayBackedPlasmaEffect = {
    e.draw(currentT)
    this
  }

  def render(canvas: Canvas, t: Double): IntArrayBackedPlasmaEffect = {
    draw(t)
    render(canvas)
    this
  }

  def render(canvas: Canvas): IntArrayBackedPlasmaEffect = {
    RenderMan.render(canvas, backingMemory, width, height)
    this
  }
}

