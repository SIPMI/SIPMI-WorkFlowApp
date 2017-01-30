name := "sipmi"

version := "1.0-SNAPSHOT"

javacOptions ++= Seq("-encoding", "UTF-8")

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.31"
)

play.Project.playJavaSettings
