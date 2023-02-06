import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IDEPathHelper {

  static final Path gradleSourcesDirectory;
  static final Path gradleResourcesDirectory;
  static final Path gradleBinariesDirectory;
  static final Path resultsDirectory;
  static final Path recorderConfigFile;

  static {
    try {
      Path projectRootDir = Paths.get(IDEPathHelper.class.getClassLoader().getResource("gatling.conf").toURI()).getParent().getParent().getParent().getParent();
      Path gradleBuildDirectory = projectRootDir.resolve("build");
      Path gradleSrcDirectory = projectRootDir.resolve("src").resolve("gatling");

      gradleSourcesDirectory = gradleSrcDirectory.resolve("java");
      gradleResourcesDirectory = gradleSrcDirectory.resolve("resources");
      gradleBinariesDirectory = gradleBuildDirectory.resolve("classes").resolve("java").resolve("gatling");
      resultsDirectory = gradleBuildDirectory.resolve("reports").resolve("gatling");
      recorderConfigFile = gradleResourcesDirectory.resolve("recorder.conf");
    } catch (URISyntaxException e) {
      throw new ExceptionInInitializerError(e);
    }
  }
}