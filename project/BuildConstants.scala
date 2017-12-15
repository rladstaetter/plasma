import sbt._

object Dependencies {
  lazy val jgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "4.9.0.201710071750-r"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.3" % Test
  lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.5" % Test
}

object BuildConstants {
  val org: String = "at.fh-joanneum.ima"
  val buildVer = "2017.0.6-SNAPSHOT"
  val scalaVer = "2.12.4"
}
