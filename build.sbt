// intentionally messy build :D

name := "aroundfuckage"

version := "1.0"

scalaVersion := "2.11.8"


enablePlugins(BuildInfoPlugin)
val projectDep = ProjectRef(uri("git://github.com/jastice/lattice"),"lattice")
val localDep = ProjectRef(file("/Users/jast/playspace/foo0"), "foo0")

dependsOn(projectDep, localDep)

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "hello"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

val sharedSettings = Seq(
  scalaVersion := "2.11.8",
  organization := "silly",
  version := "42"
)

val fx = taskKey[Unit]("an effect!")
val fxs = taskKey[String]("more effects!")

lazy val sub0 = project.settings(sharedSettings: _*)
lazy val sub1 = project.settings(sharedSettings: _*)

// do not aggregate indie, so we see what happens with an independent submodule
lazy val indie = (project in file("indie"))
  .enablePlugins(JvmPlugin)

lazy val root = (project in file("."))
  .aggregate(sub0, sub1)
  .dependsOn(sub0)
  .settings(sharedSettings: _*)
  .settings(
    fx := println("An Effect!"),
    fxs := Def.taskDyn {
      val v = fx.taskValue
      Def.task {
        v.value
        "I dun effex"
      }
    }.value
  )

libraryDependencies ++= Seq(
//  "com.github.julien-truffaut" %%  "monocle-core"    % "1.2.0",
//  "com.github.julien-truffaut" %%  "monocle-generic" % "1.2.0",
//  "com.github.julien-truffaut" %%  "monocle-macro"   % "1.2.0",
//  "io.spray" % "spray-routing_2.11" % "1.3.1",
//  "com.typesafe.slick" %% "slick" % "3.1.0",
//  "org.scala-lang.modules" % "scala-async_2.11" % "0.9.5",
  "org.typelevel" %% "cats" % "0.4.0"
)

