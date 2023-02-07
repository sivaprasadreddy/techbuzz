import io.gatling.recorder.GatlingRecorder;
import io.gatling.recorder.config.RecorderPropertiesBuilder;
import java.nio.file.Path;
import scala.Option;

public class Recorder {
    public static void main(String[] args) {
        RecorderPropertiesBuilder props =
                new RecorderPropertiesBuilder()
                        .simulationsFolder(IDEPathHelper.gradleSourcesDirectory.toString())
                        .resourcesFolder(IDEPathHelper.gradleResourcesDirectory.toString())
                        .simulationPackage("techbuzz")
                        .simulationFormatJava();

        GatlingRecorder.fromMap(
                props.build(), Option.<Path>apply(IDEPathHelper.recorderConfigFile));
    }
}
