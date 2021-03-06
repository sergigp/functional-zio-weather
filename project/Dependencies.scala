import sbt._

object Dependencies {

  object Version {
    val zio       = "1.0.12"
    val zioConfig = "1.0.6"
    val circe     = "0.14.1"
  }

  object Production {
    val zio               = "dev.zio"                       %% "zio"                            % Version.zio
    val zioStreams        = "dev.zio"                       %% "zio-streams"                    % Version.zio
    val zioConfig         = "dev.zio"                       %% "zio-config"                     % Version.zioConfig
    val zioConfigMagnolia = "dev.zio"                       %% "zio-config-magnolia"            % Version.zioConfig
    val zioConfigTypesafe = "dev.zio"                       %% "zio-config-typesafe"            % Version.zioConfig
    val zioHttp           = "io.d11"                        %% "zhttp"                          % "1.0.0.0-RC24"
    val zioMagic          = "io.github.kitlangton"          %% "zio-magic"                      % "0.3.11"
    val sttp              = "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio1" % "3.5.1"
    val sttpCirce         = "com.softwaremill.sttp.client3" %% "circe"                          % "3.5.1"
    val circeCore         = "io.circe"                      %% "circe-core"                     % Version.circe
    val circeGeneric      = "io.circe"                      %% "circe-generic"                  % Version.circe
    val circeParser       = "io.circe"                      %% "circe-parser"                   % Version.circe
  }

  object Test {}
}
