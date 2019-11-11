import BuildConstants._
import sbt.{Def, _}

lazy val commonSettings: Seq[Def.SettingsDefinition] = Seq(
  organization := org,
  scalaVersion := scalaVer,
  version := buildVer,
  fork := true,
  // add local maven repository to lookup libs (custom javafx)
  resolvers += Resolver.mavenLocal
)

lazy val model = (project in file("model/")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(name := "model", fork := false)

lazy val jfx = (project in file("jfx/")).
  settings(commonSettings: _*).
  settings(name := "jfx",
    libraryDependencies ++=
      Seq(
        "org.openjfx" % "javafx-graphics" % jfxVer classifier "mac"
        , "org.openjfx" % "javafx-base" % jfxVer
        , "org.openjfx" % "javafx-base" % jfxVer classifier "mac")
  ).dependsOn(model)

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
  settings(name := "plasma").aggregate(model, js, jfx)