name := "GoodsStoreETL"
version := "0.1"
scalaVersion := "2.11.12"
val sparkVersion = "2.4.6"

resolvers ++= Seq(
  "Artima Maven Repository" at "https://repo.artima.com/releases",
  "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven",
  "Typesafe Simple Repository" at "https://repo.typesafe.com/typesafe/simple/maven-releases",
  "MavenRepository" at "https://mvnrepository.com"
)
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion %"compile",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "compile",
  "org.scalatest" %% "scalatest" % "3.3.0-SNAP2" % Test,
  "org.mockito" %% "mockito-scala" % "1.16.23" % Test,
  "com.typesafe" % "config" % "1.4.1" % "compile",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "compile",
  "org.scalaj" % "scalaj-http_2.11" % "2.3.0"
)
unmanagedJars in Compile += file("lib\\postgresql-42.3.3.jar")
