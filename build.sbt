import sbt.{Def, _}
import BuildConstants._

lazy val commonSettings: Seq[Def.SettingsDefinition] = Seq(
  organization := org,
  scalaVersion := scalaVer,
  version := buildVer,
  fork := true
)

lazy val model = (project in file("model/")).
  settings(commonSettings: _*).
  settings(name := "model")

lazy val jfx = (project in file("jfx/")).
  settings(commonSettings: _*).
  settings(name := "jfx").dependsOn(model)

lazy val js = (project in file("js/")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "js",
    libraryDependencies ++= Seq("org.scala-js" %%% "scalajs-dom" % "0.9.4"),
    fork := false
  ).dependsOn(model)

lazy val plasma = (project in file(".")).
  settings(commonSettings: _*).
  settings(name := "plasma").aggregate(model,js,jfx)