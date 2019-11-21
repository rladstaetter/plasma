import BuildConstants._
import pl.project13.scala.sbt.JmhPlugin
import sbt.{Def, _}


val bootstrapMinJs: NameFilter = "**/bootstrap.min.js"
val bootstrapMinCss: NameFilter = "**/bootstrap.min.css"
val bootstrapFilters: NameFilter = bootstrapMinCss | bootstrapMinJs

def unpackjar(jar: File, to: File, filter: NameFilter): File = {
  val files: Set[File] = IO.unzip(jar, to, filter)
  println(s"Processing $jar and unzipping to $to")
  files foreach println
  jar
}

lazy val commonSettings: Seq[Def.SettingsDefinition] = Seq(
  organization := org,
  scalaVersion := scalaVer,
  version := buildVer,
  fork := true,
  // add local maven repository to lookup libs (custom javafx)
  resolvers ++=
    Seq(Resolver.mavenLocal)
)
lazy val bench = (project in file("bench"))
  .enablePlugins(JmhPlugin)
  .settings(commonSettings: _*)
  .settings(name := "bench",
    fork := true)
  .dependsOn(model)

lazy val model = (project in file("model/")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(name := "model"
    , fork := false
  )

lazy val jfx = (project in file("jfx/")).
  settings(commonSettings: _*).
  settings(name := "jfx",
    libraryDependencies ++=
      Seq(
        "org.openjfx" % "javafx-graphics" % jfxVer classifier "mac"
        , "org.openjfx" % "javafx-base" % jfxVer
        , "org.openjfx" % "javafx-base" % jfxVer classifier "mac"
        , "org.scalactic" %% "scalactic" % "3.0.8"
        , "org.scalatest" %% "scalatest" % "3.0.8" % "test")
  ).dependsOn(model)

lazy val js = (project in file("js/")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings: _*).
  settings(
    name := "js"
    , libraryDependencies ++=
      Seq("org.scala-js" %%% "scalajs-dom" % "0.9.7"
        , "org.webjars" % "bootstrap" % "4.3.1")
    , fork := false
    , resourceGenerators in Compile += Def.task {
      val jar = (update in Compile).value
        .select(configurationFilter("compile"))
        .filter(_.name.contains("bootstrap"))
        .head
      val to = (target in Compile).value
      unpackjar(jar, to, bootstrapFilters)
      Seq.empty[File]
    }.taskValue
  ).dependsOn(model)

lazy val plasma = (project in file(".")).
  settings(commonSettings: _*).
  settings(name := "plasma").aggregate(model, js, jfx, bench)