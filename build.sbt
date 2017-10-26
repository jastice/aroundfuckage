
// intentionally messy build :D

name := "aroundfuckage"

version := "1.0"

//scalaVersion := "2.12.2"


enablePlugins(BuildInfoPlugin)
val projectDep = RootProject(uri("git://github.com/jastice/lattice"))
val localDep = ProjectRef(file("/Users/jast/playspace/foo0"), "foo0")

dependsOn(projectDep, localDep)

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "hello"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"


val sharedSettings = Seq(
  scalaVersion := "2.11.9",
  organization := "silly",
  version := "42"
)

val fx = taskKey[Unit]("an effect!")
val fxs = taskKey[String]("more effects!")

val allTheProjects = taskKey[Map[String,Seq[ProjectRef]]]("projects!")

allTheProjects := {
  val extracted = Project extract state.value
  val deps = for {
    (uri,build) <- extracted.structure.units
    (id,project) <- build.defined
  } yield {
    val projectDependencyRefs = project.dependencies.map(_.project)
    (id,projectDependencyRefs)
  }
  deps
}

lazy val subZero = project.settings(sharedSettings)
  .settings(libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.3")
lazy val sub1 = project.settings(sharedSettings)

// do not aggregate indie, so we see what happens with an independent submodule
lazy val indie = (project in file("indie"))
  .enablePlugins(JvmPlugin)

lazy val rootbeer = RootProject(file("rootbeer"))
sharedSettings.map { s => s.mapKey(Def.mapScope(_.in(rootbeer))) }

version in rootbeer := version.value + "-lol"

lazy val root = (project in file("."))
  .aggregate(subZero, sub1, rootbeer)
  .dependsOn(subZero)
  .settings(sharedSettings)
  .settings(
    fx := println("An Effect!"),
    fxs := Def.taskDyn {
      val v = fx.taskValue
      Def.task {
        val _ = v.value
        "I dun effex"
      }
    }.value
  )
  .settings(
    unmanagedSourceDirectories in Compile ++= (unmanagedSourceDirectories in (indie,Compile)).value,
    libraryDependencies ++= (libraryDependencies in indie).value
  )

autoAPIMappings := true
apiMappings += file(".") -> uri("http://google.com").toURL
apiMappings in doc += file(".") -> uri("http://google.com").toURL



val localBase: TaskKey[File] = taskKey[File]("the local base of project")
localBase in projectDep := {
  val extracted = Project.extract(state.value)
  extracted.currentUnit.localBase
}

//val allYourBase = taskKey[Map[URI,File]]("project bases")
//allYourBase := {
//  val extracted = Project extract staeh displayoptionen oder soate.value
//  extracted.structure.units.map { case (uri, unit) => (uri,unit.localBase)}
//}

val runFoo = inputKey[Unit]("run with foo")
runFoo in Compile := Def.taskDyn {
  val scope = resolvedScoped.value.scope
  println("resolved scoped: " + resolvedScoped.value)
  Def.task {
    println("le opts are " + (javaOptions in scope).value)
//    (run in (Compile, runFoo)).evaluated
  }
//  (run in Compile).evaluated
}.value

javaOptions.in (Compile) += "-Dfoo=bar"
fork in run := true

val s = settingKey[String]("s")
val t1 = taskKey[String]("t1")
val t2 = taskKey[String]("t2")
s := "default"
t1 := s.value
t2 := t1.value


val t0 = taskKey[String]("t0")
s in t1 := "override"
t0 := s.value

libraryDependencies ++= Seq(
//  "com.github.julien-truffaut" %%  "monocle-core"    % "1.2.0",
//  "com.github.julien-truffaut" %%  "monocle-generic" % "1.2.0",
//  "com.github.julien-truffaut" %%  "monocle-macro"   % "1.2.0",
//  "io.spray" % "spray-routing_2.11" % "1.3.1",
//  "com.typesafe.slick" %% "slick" % "3.1.0",
//  "org.scala-lang.modules" % "scala-async_2.11" % "0.9.5",
  "org.typelevel" %% "cats" % "0.4.0"
)

shellPrompt := (_ => ">:) ")

val dumps = inputKey[String]("yo")
dumps := {
  import sbt.complete.DefaultParsers._
  val input = spaceDelimited("<urgh>").parsed
  input.head
}


val parsedOptions = inputKey[Seq[String]]("")
parsedOptions := {
  sbt.complete.DefaultParsers.spaceDelimited("opts plz").parsed
}

val optionsWithDefault = inputKey[Seq[String]]("")
optionsWithDefault := {
  val manual = parsedOptions.evaluated
  if (manual.isEmpty) Seq("default")
  else manual
}

val otherOptionDependency = inputKey[Seq[String]]("")
otherOptionDependency := {
  "***" +: parsedOptions.evaluated :+ "***"
}

val jumbled = inputKey[Seq[String]]("")
jumbled := {
  val a = parsedOptions.evaluated
  val b = optionsWithDefault.evaluated
  val c = otherOptionDependency.evaluated
  println("a = " + a)
  println("b = " + b)
  println("c = " + c)
  a ++ b ++ c
}
