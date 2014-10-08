/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaBaseball;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.Random;
import java.util.*;
import java.lang.Object;

/**
 *
 * @author manley
 */
public class JavaBaseball6 extends JApplet {

    public enum BatterResult {

        HOME_RUN, TRIPLE, DOUBLE, SINGLE, WALK, OUT, DOUBLE_PLAY
    };
    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;
    private Scoreboard scoreboard;

    public static final class Batter {

        public double homeRunNumber;
        public double tripleNumber;
        public double doubleNumber;
        public double singleNumber;
        public double walkNumber;
        public String batterName;

        Batter(String name, int atBats, int walks, int hits, int doubles, int triples, int homeRuns) {
            batterName = name;
            double pa = atBats + walks;
            homeRunNumber = homeRuns / pa;
            tripleNumber = triples / pa + homeRunNumber;
            doubleNumber = doubles / pa + tripleNumber;
            singleNumber = hits / pa;
            walkNumber = (hits + walks) / pa;

        }

        BatterResult Hits(double result) {
            if (result < homeRunNumber) {
                return BatterResult.HOME_RUN;
            }
            if (result < tripleNumber) {
                return BatterResult.TRIPLE;
            }
            if (result < singleNumber) {
                return BatterResult.SINGLE;
            }

            if (result > 900) {
                return BatterResult.DOUBLE_PLAY;
            }
            return BatterResult.OUT;

        }

        void PrintStats() {
            System.out.print(". " + batterName);
            System.out.print(" HR:" + homeRunNumber + " 3B:" + tripleNumber);
            System.out.print(" 2B:" + doubleNumber + " 1B:" + singleNumber);
            System.out.println(" BB:" + walkNumber);

        }

    }

    public static final class Lineup {

        private String teamName;
        private Batter[] batters;

        Lineup() {
            System.out.println("  Making a lineup  ");

            batters = new Batter[9];
        }
        
        public String GetTeamName()
        {
            return teamName;
        }
        
        public void SetBatter (Batter newBatter, int positionInLineup)
        {
            batters[positionInLineup] = newBatter;
        }
        
        public Batter GetBatter(int positionInLineup)
        {
            return batters[positionInLineup];
        }
    }

    public static final class Scoreboard {

        public enum WHO_IS_UP {

            AWAY, HOME
        };

        public int numberOfOuts = 0;
        public int inning = 1;
        public int[] nowAtBat;
        public int[] homeAndAwayScore;
        public Boolean isRunnerOnFirst = false;
        public Boolean isRunnerOnSecond = false;
        public Boolean isRunnerOnThird = false;
        public Lineup[] homeAndAwayBatters;
        public int isHomeTeamAtBat = 0;

        public void DrawScoreboard() {
            if (isHomeTeamAtBat == 1)
                System.out.print("Home half of");
            else
                System.out.print("Away half of");
            
            System.out.println(" inning number " + inning + "is coming up.  Home:" + homeAndAwayScore[1] + " Away:" + homeAndAwayScore[0]);
        }

        public Lineup Make82Angels(String name) {
            Lineup lineup = new Lineup();
            lineup.teamName = name;
            lineup.batters[0] = new Batter("Rodney The Kangaroo", 616, 69, 239, 38, 16, 14);
            lineup.batters[1] = new Batter("Dewey", 541, 106, 165, 37, 2, 34);
            lineup.batters[2] = new Batter("Lynn", 531, 82, 177, 42, 1, 39);
            lineup.batters[3] = new Batter("Mr October", 541, 114, 151, 36, 3, 47);
            lineup.batters[4] = new Batter("Pops", 522, 80, 156, 43, 3, 44);
            lineup.batters[5] = new Batter("Grich", 352, 40, 107, 14, 2, 22);
            lineup.batters[6] = new Batter("Baylor", 628, 71, 186, 33, 3, 36);
            lineup.batters[7] = new Batter("Dougie D", 575, 66, 173, 42, 5, 30);
            lineup.batters[8] = new Batter("Downing", 509, 77, 166, 27, 3, 12);
            return lineup;

        }

        public Lineup Make82RedSox(String name) {
            Lineup lineup = new Lineup();
            lineup.teamName = name;
            lineup.batters[0] = new Batter("Brett", 449, 58, 175, 33, 9, 24);
            lineup.batters[1] = new Batter("big dog", 587, 83, 186, 28, 6, 40);
            lineup.batters[2] = new Batter("Scmidt", 354, 73, 112, 19, 2, 31);
            lineup.batters[3] = new Batter("Yaz", 566, 128, 186, 29, 0, 40);
            lineup.batters[4] = new Batter("Rice", 677, 58, 213, 25, 15, 46);
            lineup.batters[5] = new Batter("Miller", 427, 50, 125, 15, 5, 2);
            lineup.batters[6] = new Batter("Remy", 410, 36, 110, 9, 1, 0);
            lineup.batters[7] = new Batter("Hoffman", 279, 25, 77, 17, 2, 6);
            lineup.batters[8] = new Batter("G Allenson", 714, 94, 159, 35, 2, 14);
            return lineup;

        }

        public Scoreboard() {
            nowAtBat = new int[]{1, 1};
            homeAndAwayScore = new int[]{0, 0};
            homeAndAwayBatters = new Lineup[2];
            homeAndAwayBatters[1] = Make82Angels("Wordsworth Wonders");
            homeAndAwayBatters[0] = Make82RedSox("Liam's Poo Team");

        }

        public void HomeRun() {
            homeAndAwayScore[isHomeTeamAtBat]++;
            if (isRunnerOnFirst) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            if (isRunnerOnSecond) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            if (isRunnerOnThird) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            isRunnerOnFirst = false;
            isRunnerOnSecond = false;
            isRunnerOnThird = false;
            System.out.println("It's a dinger!");
        }

        public void Triple() {
            if (isRunnerOnFirst) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            if (isRunnerOnSecond) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            if (isRunnerOnThird) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            isRunnerOnFirst = false;
            isRunnerOnSecond = false;
            isRunnerOnThird = true;

            System.out.println("It's a triple!");
        }

        public void Double() {
            if (isRunnerOnSecond) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            if (isRunnerOnThird) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            isRunnerOnSecond = true;
            isRunnerOnThird = isRunnerOnFirst;
            isRunnerOnFirst = false;

            System.out.println("It's a double!");
        }

        public void Single() {
            if (isRunnerOnThird) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            isRunnerOnSecond = isRunnerOnFirst;
            isRunnerOnThird = isRunnerOnSecond;
            isRunnerOnFirst = true;

            System.out.println("It's a single!");
        }

        public void BaseOnBalls() {
            if (isRunnerOnThird && isRunnerOnSecond && isRunnerOnFirst) {
                homeAndAwayScore[isHomeTeamAtBat]++;
            }
            isRunnerOnThird = (isRunnerOnSecond && isRunnerOnFirst || (isRunnerOnThird && !isRunnerOnSecond) || (isRunnerOnThird && !isRunnerOnFirst));
            isRunnerOnSecond = isRunnerOnFirst || (isRunnerOnSecond && !isRunnerOnFirst);
            isRunnerOnFirst = true;
            System.out.println("It's a walk!");
        }

        public void handleBatter(double randomNumber) {
            int placeInLineup = nowAtBat[isHomeTeamAtBat];
            Lineup lineup = homeAndAwayBatters[isHomeTeamAtBat];
            Batter batterAtBat = lineup.batters[placeInLineup - 1];
            System.out.print("Now batting  ");
            System.out.print("Batter #" + placeInLineup);

            batterAtBat.PrintStats();

            System.out.println(" rolls a random " + randomNumber);

            if (randomNumber < batterAtBat.homeRunNumber) {
                HomeRun();

            } else if (randomNumber < batterAtBat.tripleNumber) {
                Triple();

            } else if (randomNumber < batterAtBat.doubleNumber) {
                Double();
            } else if (randomNumber < batterAtBat.singleNumber) {
                Single();
            } else if (randomNumber < batterAtBat.walkNumber) {
                BaseOnBalls();
            } else if (randomNumber < 900) {
                System.out.println("It's a regular out...");
                numberOfOuts++;
            } else {
                System.out.print("Double Play");
                if (isRunnerOnFirst) {
                    System.out.println("with the runner at first being retired!");
                    numberOfOuts = numberOfOuts + 2;
                    isRunnerOnFirst = false;
                } else {
                    System.out.println("with no one at first.  Lucky dog!");
                    numberOfOuts = numberOfOuts + 1;
                }
            }

            /* Increase batter position to next batter */
            nowAtBat[isHomeTeamAtBat]++;
            if (nowAtBat[isHomeTeamAtBat] == 10) {
                nowAtBat[isHomeTeamAtBat] = 1;
            }

            if (numberOfOuts >= 3) {
                System.out.println("Inning is over");

                isRunnerOnFirst = false;
                isRunnerOnSecond = false;
                isRunnerOnThird = false;
                numberOfOuts = 0;

                if (isHomeTeamAtBat == 1) {
                    if (inning == 9) {
                        System.out.println("Regulation 9 innings is over, computers don't get sleepy though so if you keep clicking I'll keep calculating!");
                    }
                    inning++;
                    isHomeTeamAtBat = 0;
                } else {
                    isHomeTeamAtBat = 1;
                }
                DrawScoreboard();
            }

        }

    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }

                JFrame frame = new JFrame("Java Baseball Scorebaord");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JApplet applet = new JavaBaseball6();
                applet.init();

                frame.setContentPane(applet.getContentPane());

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                applet.start();
            }
        });
    }

    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                createScene();
            }
        });
    }

    private void createScene() {
        Scoreboard scoreboard = new Scoreboard();
        Random rand = new Random();
        Button batterUp = new Button();

        batterUp.setText("Next Batter!");
        batterUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                         for (int i =0; i < 100000; i++)
                scoreboard.handleBatter(rand.nextDouble());

            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(batterUp);

        fxContainer.setScene(new Scene(root));
    }

}
