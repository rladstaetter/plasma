package net.ladstatt.apps.plasma.jfx

import _root_.javafx.application.Application
import javafx.animation.AnimationTimer
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import net.ladstatt.apps.plasma.{MathUtil, Timeline}

/**
  * Old school graphic effect ('plasma') which is displayed via java fx.
  */
object PlasmaJfxApp {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[PlasmaJfxApp], args: _*)
  }

}

class PlasmaJfxApp extends Application {

  private val BigScreen: (Int, Int) = (1200, 800)
  private val MediumScreen: (Int, Int) = (800, 600)
  private val RectangleScreen: (Int, Int) = (640, 480)
  private val SmallScreen: (Int, Int) = (320, 240)
  private val Banner: (Int, Int) = (450, 150)
  private val Tiny: (Int, Int) = (100, 100)

  /**
    * the width and height of our visual area
    */
  val (width, height) = BigScreen

  // timeline
  var current = 0.0
  var direction = 1
  val nextValue = Timeline.calcNext(0.01, 0.0, MathUtil.m2pi) _

  val effect: IntArrayBackedPlasmaEffect = IntArrayBackedPlasmaEffect(width, height)


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
        effect.render(canvas, current);
        val (ndirection, ncurrent) = nextValue(current, direction)
        current = ncurrent
        direction = ndirection
        val duration = System.nanoTime() - now
        println(s"$current t, fps: " + 1000d / (duration / (1000 * 1000)))
      }
    }.start()

  }

}


