import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class update {

  public static void main(String[] args) throws IOException {
    File currentDirectory = new File(".");
    File pom = new File(currentDirectory, "pom.xml");
    List<String> lines = Files.readAllLines(pom.toPath());
    Pattern versionPattern = Pattern.compile("(.*<version>)(.*)(</version>.*)");
    String version = lines.stream().filter(versionPattern.asMatchPredicate()).map(line -> {
      Matcher matcher = versionPattern.matcher(line);
      matcher.find();
      return matcher.group(2);
    }).toList().get(1);
    String[] jars = currentDirectory.list((dir, name) -> name.endsWith(".jar"));
    Pattern bundleVersionPattern = Pattern.compile("(Bundle-Version: )(.*)(.qualifier)\"");
    File manifest = new File(currentDirectory, "../META-INF/MANIFEST.MF");
    lines = Files.readAllLines(manifest.toPath());
    List<String> newLines = new ArrayList<>();
    boolean added = false;
    for (String string : lines) {
      if (!string.contains("lib/")) {
        newLines.add(string);
      } else if (bundleVersionPattern.asMatchPredicate().test(string)) {
        Matcher matcher = bundleVersionPattern.matcher(string);
        matcher.find();
        newLines.add(matcher.group(1) + version + matcher.group(3));
      } else {
        if (!added) {
          added = true;
          int i = 0;
          for (String jar : jars) {
            i++;
            String comma = ",";
            if (i == jars.length) {
              comma = "";
            }
            newLines.add(string.substring(0, string.indexOf("/") + 1) + jar + comma);
          }
        }
      }
    }
    Files.write(manifest.toPath(), newLines);
    File buildProperties = new File(currentDirectory, "../build.properties");
    lines = Files.readAllLines(buildProperties.toPath());
    newLines = new ArrayList<>();
    added = false;
    for (String string : lines) {
      if (!string.contains("lib/")) {
        newLines.add(string);
      } else {
        if (!added) {
          added = true;
          for (String jar : jars) {
            newLines.add(string.substring(0, string.indexOf("/") + 1) + jar + ",\\");
          }
        }
      }
    }
    Files.write(buildProperties.toPath(), newLines);
    File parentPom = new File(currentDirectory, "../pom.xml");
    lines = Files.readAllLines(parentPom.toPath());
    newLines = new ArrayList<>();
    int nb = 0;
    for (String string : lines) {
      if (!versionPattern.asMatchPredicate().test(string)) {
        newLines.add(string);
      } else {
        nb++;
        Matcher matcher = versionPattern.matcher(string);
        matcher.find();
        if (nb == 2) {
          newLines.add(matcher.group(1) + version + "-SNAPSHOT" + matcher.group(3));
        } else if (nb == 3) {
          newLines.add(matcher.group(1) + version + matcher.group(3));
        } else {
          newLines.add(string);
        }
      }
    }
    Files.write(parentPom.toPath(), newLines);
  }
}
