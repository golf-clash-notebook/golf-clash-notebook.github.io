import microsites._

name := "golf-clash-notebook"

scalaVersion in ThisBuild := "2.12.4"
organization in ThisBuild := "golf-clash-notebook"
organizationName in ThisBuild := "Golf Clash Notebook"

////////////////////////////////////////////////////////////////////////////////
val CatsV          = "1.0.1"
val CatsEffectV    = "0.8"
val CirceV         = "0.9.1"
val MonixV         = "3.0.0-M3"
val ScalaCheckV    = "1.13.5"
val ScalaTagsV     = "0.6.7"
val ScalaTestV     = "3.0.4"
val ScalaJSJqueryV = "0.9.2"
////////////////////////////////////////////////////////////////////////////////

scalafmtOnCompile in ThisBuild := true

lazy val `golf-clash-notebook` = (project in file("."))
  .aggregate(firebase, markedjs, site)
  .dependsOn(firebase, markedjs, site)

lazy val site = (project in file("modules/site"))
  .settings(moduleSettings("site"): _*)
  .settings(siteSettings)
  .dependsOn(firebase, markedjs)
  .enablePlugins(AutomateHeaderPlugin, MicrositesPlugin, ScalaJSPlugin, ScalaUnidocPlugin)

lazy val firebase = (project in file("modules/firebase"))
  .settings(moduleSettings("firebase"): _*)
  .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin, ScalaUnidocPlugin)

lazy val markedjs = (project in file("modules/markedjs"))
  .settings(moduleSettings("markedjs"): _*)
  .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin, ScalaUnidocPlugin)

/////////////////////////////////////////

def moduleSettings(module: String, prefix: String = "golf-clash-notebook-") = {
  Seq(name := s"$prefix$module") ++ commonSettings
}

lazy val commonSettings = Seq(
  cancelable in Global := true,
  libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % ScalaCheckV % Test,
    "org.scalatest"  %% "scalatest"  % ScalaTestV  % Test
  ),
  scalacOptions ++= Seq(
    // format: off
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    // "-Ywarn-unused:params",           // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",            // Warn if a private member is unused.
    "-Ywarn-value-discard",              // Warn when non-Unit expression results are unused.
    "-P:scalajs:sjsDefinedByDefault"     // https://www.scala-js.org/doc/interoperability/sjs-defined-js-classes.html
    // format: on
  ),
  scalacOptions in (Compile, console) ~= (_ filterNot Set( "-Xfatal-warnings", "-Ywarn-dead-code", "-Ywarn-unused:imports")),
  scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value,
  fork in run := true,
  autoAPIMappings := true,
  headerLicense := Some(HeaderLicense.Custom(headerComment)),
  dependencyUpdatesFilter -= moduleFilter(organization = "org.eclipse.jetty")
)

lazy val siteSettings = Seq(
  libraryDependencies ++= Seq(
    "be.doeraene"   %%% "scalajs-jquery" % ScalaJSJqueryV,
    "com.lihaoyi"   %%% "scalatags"      % ScalaTagsV,
    "io.circe"      %%% "circe-core"     % CirceV,
    "io.circe"      %%% "circe-generic"  % CirceV,
    "io.circe"      %%% "circe-parser"   % CirceV,
    "org.typelevel" %%% "cats-core"      % CatsV,
    "org.typelevel" %%% "cats-effect"    % CatsEffectV,
    "io.monix"      %%% "monix"          % MonixV
  ),
  micrositeName := "Golf Clash Notebook",
  micrositeDescription := "An Open Source Guide to Mastering Golf Clash",
  micrositeAuthor := "Golf Clash Notebook Contributors",
  micrositeGithubOwner := "golf-clash-notebook",
  micrositeGithubRepo := "golf-clash-notebook.github.io",
  micrositeGitterChannel := false,
  micrositeHighlightTheme := "atom-one-light",
  micrositeHomepage := "https://www.github.com/golf-clash-notebook/golf-clash-notebook.github.io",
  micrositePalette := Map(
    "brand-primary"   -> "#192a37",
    "brand-secondary" -> "#192a37",
    "brand-tertiary"  -> "#0c171f",
    "gray-dark"       -> "#49494b",
    "gray"            -> "#7b7b7e",
    "gray-light"      -> "#e5e5e6",
    "gray-lighter"    -> "#f4f3f4",
    "white-color"     -> "#ffffff"
  ),
  micrositeFooterText := None,
  siteSubdirName in ScalaUnidoc := "api",
  addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc),
  includeFilter in makeSite := "*.html" | "*.css" | "*.png" | "*.svg" | "*.jpg" | "*.gif" | "*.js" | "*.js.map" | "*.swf" | "*.yml" | "*.md" | "*.liquid" | "*.json" | "*.ttf" | "*.otf" | "*.woff" | "*.woff2" | "*.webmanifest" | "*.xml" | "*.pdf" | "CNAME",
  fork in tut := true,
  scalacOptions in Tut ~= (_.filterNot(Set("-Ywarn-unused-import", "-Ywarn-dead-code"))),
  fork in (ScalaUnidoc, unidoc) := true,
  scalaJSUseMainModuleInitializer := true,
  makeMicrosite := (makeMicrosite dependsOn (fullOptJS in Compile)).value,
  artifactPath in(Compile, fastOptJS) := ((baseDirectory).value / "src" / "main" / "resources" / "microsite" / "js" / ((moduleName in (Compile, fastOptJS)).value + ".js")),
  artifactPath in(Compile, fullOptJS) := ((baseDirectory).value / "src" / "main" / "resources" / "microsite" / "js" / ((moduleName in (Compile, fastOptJS)).value + ".js"))
)

lazy val headerComment = IO.readLines(file("LICENSE")).mkString("\n")
