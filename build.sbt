name := "sipmi"

version := "1.0-SNAPSHOT"

javacOptions ++= Seq("-encoding", "UTF-8")

resolvers += "Maven2 repository with HTTPS" at "https://repo1.maven.org/maven2/"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.31"
)

play.Project.playJavaSettings
