name := "week-1-scalatra-rest-api"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += Classpaths.typesafeReleases

val ScalatraVersion = "2.6.2"

libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.4.8.v20171121",
        //"org.eclipse.jetty" % "jetty-server" % "9.4.8.v20171121",
        //"org.json4s" %% "json4s-native" % "{latestVersion}",
        "org.json4s" %% "json4s-jackson" % "3.6.0-M2",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

enablePlugins(ScalatraPlugin)