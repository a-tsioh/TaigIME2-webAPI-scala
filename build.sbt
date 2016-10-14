import com.typesafe.sbt.packager.archetypes.ServerLoader

name := "IMEWebService"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala, DebianPlugin)

maintainer in Linux := "Pierre Magistry <pierre@magistry.fr>"

packageSummary in Linux := "Web REST API for Taiwanese Input Method"

packageDescription := "Refer to (Magistry, 2016) presented at Rocling 2016"

serverLoading in Debian := ServerLoader.Systemd

libraryDependencies ++= Seq(
  filters,
  //"org.slf4j" % "slf4j-api" % "1.7.21",
  //"org.slf4j" % "slf4j-nop" % "1.7.21",
  "fr.magistry" %% "taigiutils" % "0.1-SNAPSHOT",
  "fr.magistry" %% "nlplib" % "1.0-SNAPSHOT"
)