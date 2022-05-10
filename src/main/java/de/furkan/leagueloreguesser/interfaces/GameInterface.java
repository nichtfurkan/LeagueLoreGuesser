package de.furkan.leagueloreguesser.interfaces;

import de.furkan.componentutil.components.ButtonComponent;
import de.furkan.componentutil.components.TextComponent;
import de.furkan.componentutil.components.TextboxComponent;
import de.furkan.leagueloreguesser.LoreGuesser;
import de.furkan.leagueloreguesser.threads.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameInterface extends JFrame {

  JLabel background;

  public GameInterface() {
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

    TextComponent subTitle =
        new TextComponent(
            "Fetching champion information...",
            0,
            100,
            Font.SANS_SERIF,
            15,
            Font.ITALIC,
            Color.WHITE,
            getSize(),
            this);
    subTitle.create(true, false);

    new Timer()
        .schedule(
            new TimerTask() {
              @Override
              public void run() {
                GameThread gameThread = new GameThread();
                subTitle.setVisible(false);
                createGameInterface(gameThread.getLore(), gameThread.getChampion());
              }
            },
            1000 * 3);

    // Background
    add(background);

    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void createGameInterface(String lore, String champion) {
    remove(background);

    JTextArea textArea = new JTextArea(lore);
    textArea.setLineWrap(true);
    textArea.setForeground(Color.WHITE);
    textArea.setBackground(Color.DARK_GRAY);
    textArea.setWrapStyleWord(true);
    textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    textArea.setEditable(false);

    JScrollPane scroll = new JScrollPane(textArea);
    scroll.setBounds(20, 80, 540, 170);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    TextComponent guessingPreText =
        new TextComponent(
            "Ex. Mordekaiser",
            30,
            43,
            Font.MONOSPACED,
            19,
            Font.PLAIN,
            Color.LIGHT_GRAY,
            getSize(),
            this);
    guessingPreText.create(false, false);
    guessingPreText
        .getComponent()
        .addMouseListener(
            new MouseListener() {
              @Override
              public void mouseClicked(MouseEvent e) {}

              @Override
              public void mousePressed(MouseEvent e) {}

              @Override
              public void mouseReleased(MouseEvent e) {}

              @Override
              public void mouseEntered(MouseEvent e) {
                guessingPreText.getComponent().setCursor(new Cursor(Cursor.TEXT_CURSOR));
              }

              @Override
              public void mouseExited(MouseEvent e) {
                guessingPreText.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
              }
            });
    TextComponent currentScore =
        new TextComponent(
            "Current Score: " + LoreGuesser.getInstance().currentScore,
            415,
            18,
            Font.SANS_SERIF,
            15,
            Font.ITALIC,
            Color.WHITE,
            getSize(),
            this);
    currentScore.create(false, false);
    TextComponent highScore =
        new TextComponent(
            "Current Highscore: " + LoreGuesser.getInstance().highScore,
            415,
            3,
            Font.SANS_SERIF,
            15,
            Font.ITALIC,
            Color.WHITE,
            getSize(),
            this);
    highScore.create(false, false);

    TextboxComponent guessing =
        new TextboxComponent(
            "",
            20,
            40,
            30,
            300,
            Font.MONOSPACED,
            20,
            Font.PLAIN,
            Color.WHITE,
            Color.DARK_GRAY,
            getSize(),
            this);
    guessing
        .getComponent()
        .addKeyListener(
            new KeyListener() {
              @Override
              public void keyTyped(KeyEvent e) {}

              @Override
              public void keyPressed(KeyEvent e) {
                if (guessingPreText.isVisible()) {
                  guessingPreText.setVisible(false);
                }
              }

              @Override
              public void keyReleased(KeyEvent e) {}
            });
    guessing.create(false, false);

    ButtonComponent guess =
        new ButtonComponent(
            "Guess",
            30 + guessing.getWidth(),
            40,
            30,
            100,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.DARK_GRAY,
            Color.WHITE,
            getSize(),
            this);
    guess.setButtonColor(Color.DARK_GRAY);
    guess.setTextColor(Color.black);
    guess.create(false, false);
    ButtonComponent back =
        new ButtonComponent(
            "Back to menu",
            140 + guessing.getWidth(),
            40,
            30,
            100,
            Font.SANS_SERIF,
            15,
            Font.PLAIN,
            Color.DARK_GRAY,
            Color.WHITE,
            getSize(),
            this);
    TextComponent title =
        new TextComponent(
            "Who is this Champion?",
            0,
            5,
            Font.SANS_SERIF,
            20,
            Font.BOLD,
            Color.WHITE,
            getSize(),
            this);
    TextComponent legend =
        new TextComponent(
            "*** = Champion name, Region",
            5,
            1,
            Font.SANS_SERIF,
            13,
            Font.ITALIC,
            Color.WHITE,
            getSize(),
            this);
    legend.create(false, false);
    TextComponent legend2 =
        new TextComponent(
            "* = Gender", 5, 18, Font.SANS_SERIF, 13, Font.ITALIC, Color.WHITE, getSize(), this);
    legend2.create(false, false);
    back.getComponent()
        .addMouseListener(
            new MouseListener() {
              @Override
              public void mouseClicked(MouseEvent e) {
                  notfiy("The Champion was " + champion,"Skipped",champion);
                dispose();
                LoreGuesser.getInstance().currentScore = 0;
                new MainInterface();
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
    guess
        .getComponent()
        .addMouseListener(
            new MouseListener() {
              @Override
              public void mouseClicked(MouseEvent e) {
                if (guessing.getTextField().getText().equalsIgnoreCase(champion)) {
                  LoreGuesser.getInstance().currentScore += 1;
                  if (LoreGuesser.getInstance().currentScore
                      >= LoreGuesser.getInstance().highScore) {
                    LoreGuesser.getInstance().highScore = LoreGuesser.getInstance().currentScore;
                    LoreGuesser.getInstance().saveFile.delete();
                    new Timer()
                        .schedule(
                            new TimerTask() {
                              @Override
                              public void run() {
                                try {
                                  LoreGuesser.getInstance().saveFile.createNewFile();
                                  FileWriter fileWriter =
                                      new FileWriter(LoreGuesser.getInstance().saveFile);
                                  fileWriter.write(
                                      String.valueOf(LoreGuesser.getInstance().highScore));
                                  fileWriter.flush();
                                  fileWriter.close();
                                } catch (IOException ex) {
                                  ex.printStackTrace();
                                }
                              }
                            },
                            1000 * 2);
                  }
                 notfiy("The Champion was " + champion +" well done!","Successfully guessed",champion);
                  dispose();
                  new GameInterface();
                } else {
                  int reply =
                      JOptionPane.showConfirmDialog(
                          null,
                          "Wrong champion guess your score was reset\nWould you like to skip this champion?",
                          "Wrong guess",
                          JOptionPane.YES_NO_OPTION);
                  LoreGuesser.getInstance().currentScore = 0;
                  currentScore.getComponent().setText("Current Score: " + LoreGuesser.getInstance().currentScore);
                  repaint();

                  if (reply == JOptionPane.YES_OPTION) {
                      notfiy("The Champion was " + champion,"Skipped",champion);
                    dispose();
                    new GameInterface();
                  }
                }
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

    back.setButtonColor(Color.DARK_GRAY);
    back.setTextColor(Color.black);
    back.create(false, false);

    title.create(true, false);

    add(scroll);
    new Timer()
        .schedule(
            new TimerTask() {
              @Override
              public void run() {
                scroll.setVisible(false);
                scroll.setVisible(true);
                repaint();
              }
            },
            1);
    add(background);
  }

  private void notfiy(String message,String title,String champion) {
      if (new File(
              "C:\\Users\\"
                      + System.getProperty("user.name")
                      + "\\Documents\\LoreGuesser\\ChampionImage"
                      + champion
                      + ".png")
              .exists()) {
          JOptionPane.showMessageDialog(
                  null,
                  message,
                  title,
                  JOptionPane.INFORMATION_MESSAGE,
                  new ImageIcon(
                          "C:\\Users\\"
                                  + System.getProperty("user.name")
                                  + "\\Documents\\LoreGuesser\\ChampionImage"
                                  + champion
                                  + ".png"));
      }
  }
}
