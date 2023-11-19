lazy val root = (project in file("."))
  .settings(projectSettings: _*)
  .settings(libraryDependencies ++= projectDependencies)
  .settings(scalacOptions ++= advanceScalacOptions)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .enablePlugins(AshScriptPlugin)
  .settings(dockerBaseImage := "openjdk:jre-alpine")

lazy val projectSettings = Seq(
  name := "mpasa",
  version := "0.1.0",
  organization := "me.mpasa",
  scalaVersion := "2.13.12"
)

val COMMONMARK_VERSION = "0.17.0"
val MACWIRE_VERSION = "2.5.9"

lazy val projectDependencies = Seq(
  // Resume
  "me.mpasa" %% "resume" % "0.1-SNAPSHOT",
  // Dependency injection
  "com.softwaremill.macwire" %% "macros" % MACWIRE_VERSION % "provided",
  "com.softwaremill.macwire" %% "macrosakka" % MACWIRE_VERSION % "provided",
  "com.softwaremill.macwire" %% "util" % MACWIRE_VERSION,
  "com.softwaremill.macwire" %% "proxy" % MACWIRE_VERSION,
  // Server
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  // Templates
  "com.lihaoyi" %% "scalatags" % "0.12.0",
  // Markdown rendering
  "com.atlassian.commonmark" % "commonmark" % COMMONMARK_VERSION,
  "com.atlassian.commonmark" % "commonmark-ext-gfm-tables" % COMMONMARK_VERSION,
  "com.atlassian.commonmark" % "commonmark-ext-yaml-front-matter" % COMMONMARK_VERSION,
  // Utils
  "org.typelevel" %% "mouse" % "1.2.2",
  // Testing
  "org.scalatest" %% "scalatest" % "3.2.17" % "test"
)

// https://tpolecat.github.io/2017/04/25/scalac-flags.html
lazy val advanceScalacOptions = Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)
