package de.furkan.leagueloreguesser.threads;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import de.furkan.leagueloreguesser.LoreGuesser;
import de.furkan.leagueloreguesser.interfaces.MainInterface;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class GameThread {

  private final String lore;
  private final String champion;

  public GameThread() {
    Champion champion = getRandomChamp();
    if (!new File(
            "C:\\Users\\"
                + System.getProperty("user.name")
                + "\\Documents\\LoreGuesser\\ChampionImage"
                + champion.getName()
                + ".png")
        .exists()) {
      try (InputStream in = new URL(champion.getImage().getURL()).openStream()) {
        Files.copy(
            in,
            Paths.get(
                "C:\\Users\\"
                    + System.getProperty("user.name")
                    + "\\Documents\\LoreGuesser\\ChampionImage"
                    + champion.getName()
                    + ".png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    String[] name;
    if (champion.getName().split(" ").length > 0) {
      name = champion.getName().split(" ");
    } else {
      name = new String[] {champion.getName()};
    }
    String lore1 = champion.getLore();
    String rawLore = champion.getLore();
    if (MainInterface.randomizeMode && rawLore.contains(".") && rawLore.split("\\.").length > 0) {
      lore1 = lore1.split("\\.")[new Random().nextInt(lore1.split("\\.").length)];
    }
    if (MainInterface.limitMode && rawLore.contains(".")) {
      lore1 = lore1.split("\\.")[0];
    }

    this.champion = champion.getName();

    StringBuilder builder = new StringBuilder();
    for (String s : lore1.split(" ")) {
      String check = s.toLowerCase();
      check = check.replaceAll("\\.", "");
      check = check.replaceAll(",", "");
      check = check.replaceAll(":", "");
      for (String s1 : name) {
        if (check.equals(s1.toLowerCase())) {
          s = "***";
        }
      }
      if (check.startsWith("noxus")
          || check.equals("noxian")
          || check.startsWith("zaun")
          || check.startsWith("piltover")
          || check.startsWith("demacia")
          || check.startsWith("targon")
          || check.startsWith("shurima")
          || check.startsWith("bilgewater")
              || check.startsWith("ionia")
              || check.startsWith("freljord")
              || check.startsWith("ixtal")
              || check.startsWith("void")
              || check.startsWith("bandle")
          || check.startsWith("shadow isles")) s = "***";
      if (check.equals("her")
          || check.equals("his")
          || check.equals("him")
          || check.equals("himself")
          || check.equals("herself")
          || check.equals("she")
          || check.equals("he")
          || check.equals("he's")
          || check.equals("she's")
          || check.equals("her's")
          || check.equals("man")
          || check.equals("woman")
          || check.equals("girl")
          || check.equals("boy")) s = "*";
      builder.append(s).append(" ");
    }
    lore = builder.toString();
  }

  private Champion getRandomChamp() {
    Champion champion =
        Orianna.getChampions().get(new Random().nextInt(Orianna.getChampions().size()));
    if (LoreGuesser.getInstance().alreadyShown.contains(champion)) {
      champion = getRandomChamp();
    }
    if (LoreGuesser.getInstance().alreadyShown.size() >= 30) {
      LoreGuesser.getInstance().alreadyShown.clear();
    }
    LoreGuesser.getInstance().alreadyShown.add(champion);
    return champion;
  }

  public String getChampion() {
    return champion;
  }

  public String getLore() {
    return lore;
  }
}
