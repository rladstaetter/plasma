package net.ladstatt.apps.plasma.jfx

import _root_.javafx.application.Application
import javafx.animation.AnimationTimer
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.{PixelFormat, PixelWriter}
import javafx.scene.layout.StackPane
import javafx.stage.Stage

/**
  * Old school graphic effect ('plasma') which is displayed via java fx.
  */
object Plasma {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[PlasmaJfxApp], args: _*)
  }

}

class PlasmaJfxApp extends Application {

  private val BigScreen: (Int, Int) = (1200, 800)
  private val MediumScreen: (Int, Int) = (800, 600)
  private val RectangleScreen: (Int, Int) = (500, 500)
  private val SmallScreen: (Int, Int) = (640, 480)
  private val Banner: (Int, Int) = (450, 150)
  private val Tiny: (Int, Int) = (100, 100)

  /**
    * the width and height of our visual area
    */
  val (width, height) = RectangleScreen

  val effect = JfxPlasmaEffect(width, height, 1)

  val canvasArray: Array[Int] = Array.tabulate(width * height)(i => 0)

  var t = 0.0

  def time[A](a: => A, display: Long => Unit = s => (), divisor: Int = 1000 * 1000): A = {
    val now = System.nanoTime
    val result = a
    val millis = (System.nanoTime - now) / divisor
    display(millis)
    result
  }


  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Plasma Effect")

    val root = new StackPane()
    val canvas = new Canvas(width, height)
    root.setStyle("-fx-background-color: black")
    root.getChildren.add(canvas)

    val scene = new Scene(root)

    primaryStage.setScene(scene)
    primaryStage.show()

    new AnimationTimer() {
      override def handle(now: Long): Unit = {
        val (a, nextT) = effect.draw(canvasArray, t)
        drawArray(canvas, a)
        t = nextT
      }
    }.start()

  }


  private def drawArray(canvas: Canvas, argbEncodedPixels: Array[Int]) = {
    val pxw: PixelWriter = canvas.getGraphicsContext2D.getPixelWriter
    pxw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance, argbEncodedPixels, 0, width)
  }

}


