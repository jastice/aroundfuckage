import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbt.BasicCommandStrings.{Shell, ShellDetailed}

object CommonPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {}

  override lazy val projectSettings = Seq(
    organization := "haha!"
  )

  override lazy val buildSettings = Seq(
  )

  override def globalSettings = Seq(

  )
}
