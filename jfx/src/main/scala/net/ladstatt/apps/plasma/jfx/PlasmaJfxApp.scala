package net.ladstatt.apps.plasma.jfx

import java.util.concurrent.ThreadPoolExecutor

import _root_.javafx.application.Application
import javafx.animation.AnimationTimer
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import net.ladstatt.apps.plasma.{MathUtil, Timeline}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Old school graphic effect ('plasma') which is displayed via java fx.
  */
object PlasmaJfxApp {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[PlasmaJfxApp], args: _*)
  }

}

class PlasmaJfxApp extends Application {

  private val FullHdScreen: (Int, Int) = (1920, 1080)
  private val BigScreen: (Int, Int) = (1200, 800)
  private val MediumScreen: (Int, Int) = (800, 600)
  private val RectangleScreen: (Int, Int) = (640, 480)
  private val SmallScreen: (Int, Int) = (320, 240)
  private val Banner: (Int, Int) = (450, 150)
  private val Tiny: (Int, Int) = (100, 100)

  /**
    * the width and height of our visual area
    */
  val (width, height) = RectangleScreen

  // timeline
  var timeLine = Timeline.Default
  //private val upperBound: Int = (MathUtil.m2pi * 1000).toInt

  implicit val ec = scala.concurrent.ExecutionContext.global

  val cachedEffects: Map[Int, IntArrayBackedPlasmaEffect] =
    Await.result(
      Future.sequence(for (i <- timeLine.range) yield {
        Future {
          i -> computeEffect(i)
        }
      }), Duration.Inf).toMap

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
        cachedEffects(timeLine.current).render(canvas)
        timeLine = timeLine.next
        val duration = System.nanoTime() - now
       // println(s"${timeLine.current} t, fps: " + 1000d / (duration / (1000 * 1000)))
      }
    }.start()

  }

  private def computeEffect(index: Int) = {
    val i = IntArrayBackedPlasmaEffect(width, height, Array.tabulate(width * height)(_ => 0)).draw(index / 100.0)
    println("precomputed " + index)
    i
  }
}


