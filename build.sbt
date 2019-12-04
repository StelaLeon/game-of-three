import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile
import com.typesafe.sbt.packager.docker._

name := "game-of-three"

version := "1.0"

scalaVersion := "2.13.1"

sbtVersion := "0.13.1"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerSpotifyClientPlugin)



libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-parent" % "2.2.1.RELEASE",
  "org.springframework.boot" % "spring-boot-starter-web" % "2.2.1.RELEASE",
  "org.springframework.boot" % "spring-boot-starter-test" % "2.2.1.RELEASE",
  "com.novocode" % "junit-interface" % "0.11" % Test,
  "org.junit.platform" % "junit-platform-runner" % "1.6.0-M1" % Test,
  "org.junit.jupiter" % "junit-jupiter-engine" % "5.6.0-M1" % Test,
  "org.springframework.kafka" % "spring-kafka" % "2.3.3.RELEASE",
  "org.apache.kafka" % "kafka-streams" % "2.3.1" ,
  "org.apache.kafka" % "kafka-clients" % "2.3.1"

)

//@todo fix/clean this
// Include only src/main/java in the compile configuration
//unmanagedSourceDirectories in Compile := (javaSource in Compile).value :: Nil

// Include only src/test/java in the test configuration
//unmanagedSourceDirectories in Test := (javaSource in Test).value :: Nil


val buildContainer = taskKey[Unit]("creates the container in order to pass it to the container at startup")
val startPlayer1 = taskKey[String]("Creates the player one and returns the IP")
val startGame = taskKey[String]("Start the player's containers...")
val stopGame = taskKey[Unit]("Stop the player's containers...")
val cleanUp = taskKey[Unit]("Clean after the game has ended...")

buildContainer := {
  publishLocal.value
}

startPlayer1 := {
  buildContainer.value

  ""
}