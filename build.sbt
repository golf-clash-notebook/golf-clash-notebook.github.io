import microsites._

name := "golf-clash-notebook"

ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "golf-clash-notebook"
ThisBuild / organizationName := "Golf Clash Notebook"
ThisBuild / scalafmtOnCompile := true

////////////////////////////////////////////////////////////////////////////////
val CatsV            = "2.6.1"
val CirceV           = "0.14.1"
val MonixV           = "3.4.0"
val ScalaCheckV      = "1.15.4"
val ScalaTagsV       = "0.9.4"
val ScalaTestV       = "3.2.10"
val ScalaJSJqueryV   = "1.0.0"
val ScalaJavaTimeV   = "2.3.0"
val ScalaJavaTimeTzV = s"${ScalaJavaTimeV}"
////////////////////////////////////////////////////////////////////////////////

lazy val `golf-clash-notebook` = (project in file("."))
  .aggregate(site, siteJS, firebase, jspdf, markedjs)
  .dependsOn(site, siteJS, firebase, jspdf, markedjs)

lazy val site = (project in file("modules/site"))
  .settings(moduleSettings("site"): _*)
  .settings(siteSettings)
  .dependsOn(siteJS)
  .enablePlugins(MicrositesPlugin, MdocPlugin)

lazy val siteJS = (project in file("modules/siteJS"))
  .settings(moduleSettings("sitejs"): _*)
  .dependsOn(firebase, markedjs, jspdf)
  .settings(
    libraryDependencies ++= Seq(
      "be.doeraene"       %%% "scalajs-jquery"       % ScalaJSJqueryV,
      "com.lihaoyi"       %%% "scalatags"            % ScalaTagsV,
      "io.circe"          %%% "circe-core"           % CirceV,
      "io.circe"          %%% "circe-generic"        % CirceV,
      "io.circe"          %%% "circe-parser"         % CirceV,
      "org.typelevel"     %%% "cats-core"            % CatsV,
      "io.monix"          %%% "monix"                % MonixV,
      "io.github.cquiroz" %%% "scala-java-time"      % ScalaJavaTimeV,
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % ScalaJavaTimeTzV
    ),
    Compile / fastOptJS / artifactPath := ((baseDirectory).value / "src" / "main" / "resources" / "microsite" / "js" / "golf-clash-notebook-site.js"),
    Compile / fullOptJS / artifactPath := ((baseDirectory).value / "src" / "main" / "resources" / "microsite" / "js" / "golf-clash-notebook-site.js"),
    scalaJSUseMainModuleInitializer := true
  )
  .enablePlugins(ScalaJSPlugin)

lazy val firebase = (project in file("modules/firebase"))
  .settings(moduleSettings("firebase"): _*)
  .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin)

lazy val markedjs = (project in file("modules/markedjs"))
  .settings(moduleSettings("markedjs"): _*)
  .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin)

lazy val jspdf = (project in file("modules/jspdf"))
  .settings(moduleSettings("jspdf"): _*)
  .enablePlugins(AutomateHeaderPlugin, ScalaJSPlugin)

/////////////////////////////////////////

def moduleSettings(module: String, prefix: String = "golf-clash-notebook-") = {
  Seq(name := s"$prefix$module") ++ commonSettings
}

lazy val commonSettings = Seq(
  Global / cancelable := true,
  libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % ScalaCheckV % Test,
    "org.scalatest"  %% "scalatest"  % ScalaTestV  % Test
  ),
  Compile / scalacOptions ~= (_ filterNot Set(
    "-Wunused:explicits",
    "-Wunused:params",
    "-Ywarn-unused:params"
  )),
  run / fork := true,
  autoAPIMappings := true,
  headerLicense := Some(HeaderLicense.Custom(headerComment)),
  dependencyUpdatesFilter -= moduleFilter(organization = "org.eclipse.jetty")
)

/////////////////////////////////////////

val copySiteJS   = taskKey[Unit]("Copy siteJS artifacts to site.")
val compressJson = taskKey[Unit]("Compress JSON assets.")

lazy val siteSettings = Seq(
  micrositeName := "Golf Clash Notebook",
  micrositeDescription := "An Open Source Guide to Mastering Golf Clash",
  micrositeAuthor := "Golf Clash Notebook Contributors",
  micrositeGithubOwner := "golf-clash-notebook",
  micrositeGithubRepo := "golf-clash-notebook.github.io",
  micrositeGitterChannel := false,
  micrositeTheme := "pattern",
  micrositeHomepage := "https://www.github.com/golf-clash-notebook/golf-clash-notebook.github.io",
  micrositeConfigYaml := ConfigYml(
    yamlPath = Some((Compile / resourceDirectory).value / "microsite" / "custom-config.yml")
  ),
  micrositePalette := Map(
    "brand-primary"   -> "#4caf50",
    "brand-secondary" -> "#39833C",
    "brand-tertiary"  -> "#4caf50",
    "gray-dark"       -> "#49494b",
    "gray"            -> "#7b7b7e",
    "gray-light"      -> "#e5e5e6",
    "gray-lighter"    -> "#f4f3f4",
    "white-color"     -> "#ffffff"
  ),
  micrositeFooterText := None,
  makeSite / includeFilter := "CNAME" | "*.css" | "*.gif" | "*.html" | "*.jpg" | "*.js" | "*.js.map" | "*.json" | "*.json.gz" | "*.liquid" | "*.md" | "*.otf" | "*.pdf" | "*.png" | "*.svg" | "*.swf" | "*.ttf" | "*.txt" | "*.webmanifest" | "*.woff" | "*.woff2" | "*.xml" | "*.yml",
  makeMicrosite := (compressJson
    .dependsOn((makeMicrosite.dependsOn(copySiteJS.dependsOn(siteJS / Compile / fullOptJS)))))
    .value,
  copySiteJS := {
    val jsFileSrc = (siteJS / Compile / fullOptJS / artifactPath).value
    val jsFileDst =
      ((baseDirectory).value / "src" / "main" / "resources" / "microsite" / "js" / "golf-clash-notebook-site.js")

    val sourceMapFileSrc =
      new File((siteJS / Compile / fullOptJS / artifactPath).value.getAbsolutePath() + ".map")
    val sourceMapFileDst =
      ((baseDirectory).value / "src" / "main" / "resources" / "microsite" / "js" / "golf-clash-notebook-site.js.map")

    IO.copyFile(jsFileSrc, jsFileDst)
    IO.copyFile(sourceMapFileSrc, sourceMapFileDst)
  },
  compressJson := {
    import java.io._
    import java.util.zip._
    import scala.io._

    streams.value.log.info("Compressing JSON assets...")

    def compressFile(f: File): Unit = {
      if (f.isDirectory) {
        f.listFiles.foreach(compressFile)
      } else if (f.getName().endsWith(".json")) {
        val os =
          new PrintWriter(new GZIPOutputStream(new FileOutputStream(s"${f.getAbsolutePath()}.gz")))
        Source.fromFile(f).getLines.foreach(os.write)
        os.close()
      }
    }

    compressFile(target.value / "site")
  }
)

lazy val headerComment = IO.readLines(file("LICENSE")).mkString("\n")
