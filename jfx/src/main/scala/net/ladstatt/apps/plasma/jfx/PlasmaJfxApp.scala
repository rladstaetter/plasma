package net.ladstatt.apps.plasma.jfx

import javafx.animation.AnimationTimer

import net.ladstatt.apps.plasma.PlasmaEffect
import javafx.application.Application
import javafx.scene.{Group, Scene}
import javafx.scene.canvas.Canvas
import javafx.scene.image.{PixelFormat, PixelWriter}
import javafx.stage.Stage

class PlasmaJfxApp extends Application {

  private val BigScreen: (Int, Int) = (1024, 768)
  private val MediumScreen: (Int, Int) = (800, 600)
  private val SmallScreen: (Int, Int) = (640, 480)
  private val Banner: (Int, Int) = (450, 150)
  private val Tiny: (Int, Int) = (100, 100)

  /**
    * the width and height of our visual area
    */
  val (width, height) = SmallScreen

  val effect = PlasmaEffect(width, height)


  def time[A](a: => A, display: Long => Unit = s => (), divisor: Int = 1000 * 1000): A = {
    val now = System.nanoTime
    val result = a
    val millis = (System.nanoTime - now) / divisor
    display(millis)
    result
  }


  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Plasma Effect")

    val root = new Group()
    val canvas = new Canvas(width, height)

    root.getChildren.add(canvas)
    primaryStage.setScene(new Scene(root))
    primaryStage.show()


    new AnimationTimer() {

      var cnt = 0
      var t = 0.0

      override def handle(now: Long): Unit = {
        cnt = cnt + 1
        if (cnt % 2 == 0) {

          val (a, nextT) = effect.draw(t)
          drawByteArray(canvas, a)
          cnt = 0
          t = nextT

        }
      }
    }.start()


  }


  private def drawByteArray(canvas: Canvas, bytes: Array[Byte]) = {
    val pxw: PixelWriter = canvas.getGraphicsContext2D.getPixelWriter
    pxw.setPixels(0, 0, width, height, PixelFormat.getByteRgbInstance, bytes, 0, width * 3)
  }

}
