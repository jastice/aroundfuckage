logLevel := Level.Warn

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0-M8")

addSbtPlugin("com.eed3si9n" % "sbt-doge" % "0.1.5")


// resolvers += Resolver.url("jb-structure-extractor-0.13", url(s"http://dl.bintray.com/jetbrains/sbt-plugins"))(sbt.Patterns(false,"[organisation]/[module]/scala_2.10/sbt_0.13/[revision]/[type]s/[artifact](-[classifier]).[ext]"))
// addSbtPlugin("org.jetbrains" % "sbt-structure-extractor-0-13" % "7.0.0-11-gb59538c")
// addSbtPlugin("org.jetbrains" % "sbt-structure-extractor-0-13" % "7.0.0-10-ga707ca1")