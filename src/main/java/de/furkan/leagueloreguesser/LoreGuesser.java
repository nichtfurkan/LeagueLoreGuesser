package de.furkan.leagueloreguesser;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import de.furkan.leagueloreguesser.interfaces.MainInterface;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class LoreGuesser {

  private static final LoreGuesser instance = new LoreGuesser();
  public ArrayList<String> alreadyShown = new ArrayList<>();
  public int highScore = 0;
  public int currentScore = 0;
  public boolean downloadNew = false;
  public HashMap<String,String> championLoreList = new HashMap<>();
  public File saveFile =
      new File(
          "C:\\Users\\"
              + System.getProperty("user.name")
              + "\\Documents\\LoreGuesser\\LoreGuesserHighScore.yml");

  public static LoreGuesser getInstance() {
    return instance;
  }

  public static void main(String[] args) {
    try {
      if (!getInstance().saveFile.exists()) {
        new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\LoreGuesser")
            .mkdir();
        getInstance().saveFile.createNewFile();
        FileWriter fileWriter = new FileWriter(getInstance().saveFile);
        fileWriter.write("0");
        fileWriter.flush();
        fileWriter.close();
        getInstance().downloadNew = true;
      }
      String line = new BufferedReader(new FileReader(getInstance().saveFile)).readLine();
      if (line != null) {
        getInstance().highScore = Integer.parseInt(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    Orianna.setRiotAPIKey("RGAPI-b2848e68-27a5-4b42-898d-c2a3ed2cac84");
    Orianna.setDefaultRegion(Region.EUROPE_WEST);
    try {
      UIManager.setLookAndFeel(new WindowsLookAndFeel());
      new MainInterface();
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
  }
}
