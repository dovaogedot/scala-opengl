scalaVersion := "3.7.2"

name := "game"
organization := "dovaogedot"
version := "1.0"

resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"

val lwjglVersion = "3.3.6"
val jomlVersion = "1.10.8"
val jomlPrimitivesVersion = "1.10.0"
val lwjglNatives = "natives-linux"

libraryDependencies ++= Seq(
  "org.lwjgl" % "lwjgl-bom" % lwjglVersion pomOnly (),
  "org.lwjgl" % "lwjgl" % lwjglVersion,
  "org.lwjgl" % "lwjgl-fmod" % lwjglVersion,
  "org.lwjgl" % "lwjgl-freetype" % lwjglVersion,
  "org.lwjgl" % "lwjgl-glfw" % lwjglVersion,
  "org.lwjgl" % "lwjgl-openal" % lwjglVersion,
  "org.lwjgl" % "lwjgl-opengl" % lwjglVersion,
  "org.lwjgl" % "lwjgl" % lwjglVersion classifier lwjglNatives,
  "org.lwjgl" % "lwjgl-freetype" % lwjglVersion classifier lwjglNatives,
  "org.lwjgl" % "lwjgl-glfw" % lwjglVersion classifier lwjglNatives,
  "org.lwjgl" % "lwjgl-openal" % lwjglVersion classifier lwjglNatives,
  "org.lwjgl" % "lwjgl-opengl" % lwjglVersion classifier lwjglNatives,
  "org.joml" % "joml" % jomlVersion,
  "org.joml" % "joml-primitives" % jomlPrimitivesVersion
)
