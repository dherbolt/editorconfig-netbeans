package org.editorconfig.netbeans.test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.editorconfig.core.EditorConfig;
import org.editorconfig.core.EditorConfigException;
import org.editorconfig.core.OutPair;
import org.openide.util.Exceptions;

public class MainClass {

  private static final Logger LOG = Logger.getLogger(MainClass.class.getName());

  public static void main(String[] args) throws URISyntaxException {
    Pattern pattern = Pattern.compile(".*py$");
    Matcher matcher = pattern.matcher("myfile.py");
    boolean isMatched = matcher.matches();
    System.out.println("Is matched: " + isMatched);

    /*
     EditorConfigParser parser = new EditorConfigParser();
     String result = parser.parseResource("editorconfig-test.ini");
     System.out.println(result);
     */
    List<String> filePaths = new ArrayList<>();

    // Get test file
    String testFilePath = "org/editorconfig/example/.editorconfig";
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    // Absolute Path with forward slashes
    URL resource = classLoader.getResource(testFilePath);
    File testFile = new File(resource.getFile());
    filePaths.add(resource.getPath());

    for (String filePath : filePaths) {
      EditorConfig editorConfig = new EditorConfig(testFile.getName(), EditorConfig.VERSION);
      try {
        List<OutPair> properties = editorConfig.getProperties(filePath);

        for (int i = 0; i < properties.size(); ++i) {
          OutPair property = properties.get(i);
          System.out.println("Key: " + property.getKey());
          System.out.println("Value: " + property.getVal());
        }
      } catch (EditorConfigException ex) {
        Exceptions.printStackTrace(ex);
      }
    }

  }
}