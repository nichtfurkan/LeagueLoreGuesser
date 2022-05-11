package de.furkan.leagueloreguesser.interfaces;

import com.merakianalytics.orianna.Orianna;
import de.furkan.componentutil.ButtonClick;
import de.furkan.componentutil.components.ButtonComponent;
import de.furkan.componentutil.components.ImageComponent;
import de.furkan.componentutil.components.TextComponent;
import de.furkan.leagueloreguesser.LoreGuesser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainInterface extends JFrame {

  public static boolean limitMode = false;
  public static boolean randomizeMode = false;
  JLabel background;

  public MainInterface() {
    try {
      background = new JLabel(new ImageIcon(getClass().getResource("/background.gif")));
      setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);
    setTitle("League of Legends Lore guesser - 1.2 | By Furkan.#4554");
    setBounds(600, 300, 600, 300);

    // Title

    TextComponent title =
        new TextComponent(
            "Lore Guesser", 0, 10, Font.SANS_SERIF, 40, Font.BOLD, Color.WHITE, getSize(), this);
    title.create(true, false);
    // Subtitle
      TextComponent subTitle;
      if(LoreGuesser.getInstance().downloadNew) {

    subTitle =
        new TextComponent(
            "First start (Downloading for Aatrox)",
            0,
            55,
            Font.SANS_SERIF,
            15,
            Font.ITALIC,
            Color.WHITE,
            getSize(),
            this);
    subTitle.create(true, false);
          add(background);
          repaint();

          setLocationRelativeTo(null);
          setVisible(true);
          Orianna.getChampions().forEach(champion -> {
              subTitle.getComponent().setText("First start (Downloading for "+champion.getName()+")");
              repaint();
              LoreGuesser.getInstance().championLoreList.put(champion.getName(),champion.getLore());
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
          });
    LoreGuesser.getInstance().downloadNew = false;
   dispose();
  new MainInterface();
    } else {
      Orianna.getChampions()
          .forEach(
              champion -> {
                LoreGuesser.getInstance()
                    .championLoreList
                    .put(champion.getName(), champion.getLore());
              });
          subTitle =
                  new TextComponent(
                          "Created by Furko on 09.05.2022 with <3",
                          0,
                          55,
                          Font.SANS_SERIF,
                          15,
                          Font.ITALIC,
                          Color.WHITE,
                          getSize(),
                          this);
          subTitle.create(true, false);
      TextComponent highScore =
          new TextComponent(
              "Current Highscore: " + LoreGuesser.getInstance().highScore,
              10,
              45,
              Font.SANS_SERIF,
              15,
              Font.ITALIC,
              Color.WHITE,
              getSize(),
              this);
      highScore.create(false, false);


      // Play Button
      initPlayButton();

      // Hardmode
      initLimitButton();
      // Randomize
      initRandomizeButton();
      // Donation button
      initDonationButton();

      // Background
      add(background);
      repaint();

      setLocationRelativeTo(null);
      setVisible(true);
    }
  }

  private void initDonationButton() {
    ButtonComponent donationButton =
        new ButtonComponent(
            "Donate",
            5,
            230,
            20,
            100,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.BLACK,
            Color.DARK_GRAY,
            getSize(),
            this);
    donationButton.create(false, false);
    donationButton.onClick(
        new ButtonClick() {
          @Override
          public void onClick() {
            try {
              Desktop.getDesktop().browse(new URI("https://ko-fi.com/Gurkan"));
            } catch (IOException e) {
              e.printStackTrace();
            } catch (URISyntaxException e) {
              e.printStackTrace();
            }
          }
        });
  }

  private void initLimitButton() {
    JCheckBox jCheckBox = new JCheckBox();
    jCheckBox.setBounds(5, 5, 80, 20);
    jCheckBox.setOpaque(false);
    jCheckBox.setText(" Limit mode");
    jCheckBox.setForeground(Color.WHITE);
    jCheckBox.setSelected(limitMode);
    jCheckBox.addMouseListener(
        new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (jCheckBox.isSelected()) {
              JOptionPane.showMessageDialog(
                  null,
                  "Limit mode limits the lore length.",
                  "Limit mode activated",
                  JOptionPane.INFORMATION_MESSAGE);
            }
            limitMode = jCheckBox.isSelected();
          }

          @Override
          public void mousePressed(MouseEvent e) {}

          @Override
          public void mouseReleased(MouseEvent e) {}

          @Override
          public void mouseEntered(MouseEvent e) {}

          @Override
          public void mouseExited(MouseEvent e) {}
        });

    add(jCheckBox);
  }

  private void initRandomizeButton() {
    JCheckBox jCheckBox = new JCheckBox();
    jCheckBox.setBounds(5, 25, 160, 20);
    jCheckBox.setOpaque(false);
    jCheckBox.setText(" Randomize lore");
    jCheckBox.setForeground(Color.WHITE);
    jCheckBox.setSelected(randomizeMode);
    jCheckBox.addMouseListener(
        new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (jCheckBox.isSelected()) {
              JOptionPane.showMessageDialog(
                  null,
                  "Randomize mode begins the lore at a random point.",
                  "Randomize mode activated",
                  JOptionPane.INFORMATION_MESSAGE);
            }
            randomizeMode = jCheckBox.isSelected();
          }

          @Override
          public void mousePressed(MouseEvent e) {}

          @Override
          public void mouseReleased(MouseEvent e) {}

          @Override
          public void mouseEntered(MouseEvent e) {}

          @Override
          public void mouseExited(MouseEvent e) {}
        });

    add(jCheckBox);
  }

  private void initPlayButton() {

    ImageComponent playButton;
    try {
      playButton =
          new ImageComponent(
              getClass().getResource("/PlayButton.png"),
              0,
              80,
              282,
              90,
              Image.SCALE_SMOOTH,
              getSize(),
              this);
      playButton.setX(((this.getSize().width - 280) / 2) - 8);
      playButton.create(false, false, false, false);
      playButton
          .getComponent()
          .addMouseListener(
              new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                  new GameInterface();
                  dispose();
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {
                  playButton.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
                  try {
                    playButton
                        .getComponent()
                        .setIcon(
                            new ImageIcon(
                                ImageIO.read(getClass().getResource("/PlayButtonHover.png"))
                                    .getScaledInstance(
                                        playButton.getWidth(),
                                        playButton.getHeight(),
                                        Image.SCALE_DEFAULT)));
                  } catch (Exception ex) {
                    ex.printStackTrace();
                  }
                  repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                  playButton.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                  try {
                    playButton
                        .getComponent()
                        .setIcon(
                            new ImageIcon(
                                ImageIO.read(getClass().getResource("/PlayButton.png"))
                                    .getScaledInstance(
                                        playButton.getWidth(),
                                        playButton.getHeight(),
                                        Image.SCALE_DEFAULT)));
                  } catch (Exception ex) {
                    ex.printStackTrace();
                  }
                  repaint();
                }
              });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
