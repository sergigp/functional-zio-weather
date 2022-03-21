name := "weather"

version := "0.1"

scalaVersion := "2.13.8"

scalaSource in Compile := baseDirectory.value / "src/main/"
scalaSource in Test := baseDirectory.value / "src/test/"
resourceDirectory in Compile := baseDirectory.value / "resources"

resolvers += "central" at "https://repo.maven.apache.org/maven2/"

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature")
javaOptions += "-Duser.timezone=UTC"

libraryDependencies ++= Seq(
  Dependencies.Production.zio,
  Dependencies.Production.zioStreams,
  Dependencies.Production.zioConfig,
  Dependencies.Production.zioConfigMagnolia,
  Dependencies.Production.zioConfigTypesafe,
  Dependencies.Production.zioHttp,
  Dependencies.Production.zioMagic,
)

Test / fork := true
cancelable in Global := true
parallelExecution in Test := false
testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

/** *****************************************/
/** ************** ALIASES ******************/
/** *****************************************/
addCommandAlias("c", "compile")
addCommandAlias("s", "scalastyle")
addCommandAlias("tc", "test:compile")
addCommandAlias("ts", "test:scalastyle")
addCommandAlias("t", "test")
addCommandAlias("to", "testOnly")
addCommandAlias("ta", "testOnly com.cassette.acceptance.**")
addCommandAlias("tb", "testOnly com.cassette.behaviour.**")
addCommandAlias("ti", "testOnly com.cassette.integration.**")
addCommandAlias("prep", ";scalastyle;test:scalastyle;scalafmtCheck;test:scalafmtCheck")
