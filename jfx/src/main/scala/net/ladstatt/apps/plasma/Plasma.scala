package net.ladstatt.apps.plasma

import net.ladstatt.apps.plasma.jfx.PlasmaJfxApp
import _root_.javafx.application.Application

/**
  * Old school graphic effect ('plasma') which is diplayed via java fx.
  */
object Plasma {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[PlasmaJfxApp], args: _*)
  }

}




