import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import sbt.BasicCommandStrings.{Shell, ShellDetailed}

object IdeaShellPluginPrototype extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {}

  override lazy val projectSettings = Seq()

  override lazy val buildSettings = Seq(
//    commands += ideaShell
  )

  val IdeaShell: String = "idea-" + Shell

  // three invisible spaces ought to be enough for anyone
  val IDEA_PROMPT_MARKER = "\u200b\u200b\u200b"

  // copied and adapted from shell command in sbt.BasicCommands
  private def ideaShell = Command.command(IdeaShell, Help.more(IdeaShell, ShellDetailed)) { s =>
    val history = (s get BasicKeys.historyPath) getOrElse Some(new File(s.baseDir, ".history"))
    val prompt = s get BasicKeys.shellPrompt match {
      case Some(pf) => pf(s) + IDEA_PROMPT_MARKER
      case None => ">" + IDEA_PROMPT_MARKER
    }

    val reader = new FullReader(history, s.combinedParser)
    val line = reader.readLine(prompt)
    line match {
      case Some(cmd) =>
        val newState = s.copy(
          onFailure = Some(IdeaShell),
          remainingCommands = cmd +: IdeaShell +: s.remainingCommands)
          .setInteractive(true)
        if (cmd.trim.isEmpty) newState else newState.clearGlobalLog
      case None => s.setInteractive(false)
    }
  }

  override def globalSettings = Seq()
}
