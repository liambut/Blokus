package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.mygdx.game.BlokusDuo.*;
import static com.mygdx.game.BlokusDuo.toUI;
import static com.mygdx.game.Player.calculatePlayerScore;
import static com.mygdx.game.Player.getPlayerScore;

public class NewThread extends Thread{
    public static boolean GUI;
    public static String firstPlayer;
    public void run() {
        PrintStream toLogic = BlokusDuo.toLogic;
        Scanner scanfromUI = BlokusDuo.scanFromUI;
        PrintStream toUI = BlokusDuo.toUI;
        Scanner scanfromLogic = BlokusDuo.scanFromLogic;
        if(Thread.currentThread().getName().equals("GAME")) {
            GUI = false;
            if(scanFromUI.hasNextBoolean() && scanFromUI.nextBoolean()){
                GUI = true;
            }
            do {
                firstPlayer = scanFromUI.nextLine();
            }while(!firstPlayer.equals("X") && !firstPlayer.equals("O"));
            final String player1 = scanfromUI.nextLine();
            final String player2 = scanfromUI.nextLine();
            // BlokusDuo.myOut.println(player1);
            // BlokusDuo.myOut.println(player2);
            Player p1 = new Player(player1, 0, true, 'X');
            Player p2 = new Player(player2, 0, true, 'O');
            Board b1 = new Board();
            Board.printScoreboard(p1, p2);
            Board.boardSetup(b1);
            Board.printBoard(b1);
            Blocks p1blocks = new Blocks(p1);
            Blocks p2blocks = new Blocks(p2);
            p1blocks.blocksSetup();
            p2blocks.blocksSetup();
            if(firstPlayer.equals("X")){
                p1blocks.pushBlock(p1.inputBlock(p1blocks, b1), b1, Player.chosenx, Player.choseny);
                Board.printBoard(b1);
                toUI.println("P2");
                toUI.println(calculatePlayerScore(p1blocks));
                p2blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                Board.printBoard(b1);
                toUI.println(calculatePlayerScore(p2blocks));
                toUI.println("P1");
                while (Blocks.moveExists(p1blocks, b1, p1) || Blocks.moveExists(p2blocks, b1, p2)) {
                    if (Blocks.moveExists(p1blocks, b1, p1)) {
                        toUI.println("P1");
                        toUI.println(calculatePlayerScore(p2blocks));
                        p1blocks.pushBlock(p1.inputBlock(p1blocks, b1), b1, Player.chosenx, Player.choseny);
                        Board.printBoard(b1);
                    }
                    if (Blocks.moveExists(p2blocks, b1, p2)) {
                        toUI.println("P2");
                        toUI.println(calculatePlayerScore(p1blocks));
                        p2blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                        Board.printBoard(b1);
                    }
                }
            }else {
                p2blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                Board.printBoard(b1);
                toUI.println("P1");
                toUI.println(calculatePlayerScore(p2blocks));
                p1blocks.pushBlock(p1.inputBlock(p1blocks, b1), b1, Player.chosenx, Player.choseny);
                Board.printBoard(b1);
                toUI.println(calculatePlayerScore(p1blocks));
                while (Blocks.moveExists(p1blocks, b1, p1) || Blocks.moveExists(p2blocks, b1, p2)) {
                    if (Blocks.moveExists(p2blocks, b1, p2)) {
                        toUI.println("P2");
                        toUI.println(calculatePlayerScore(p1blocks));
                        p2blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                        Board.printBoard(b1);
                    }
                    if (Blocks.moveExists(p1blocks, b1, p1)) {
                        toUI.println("P1");
                        toUI.println(calculatePlayerScore(p2blocks));
                        p1blocks.pushBlock(p1.inputBlock(p1blocks, b1), b1, Player.chosenx, Player.choseny);
                        Board.printBoard(b1);
                    }
                }
            }
            toUI.println("P1");
            toUI.println("over");
            if (p1.score > p2.score) {
                toUI.println("Player " + p1.name + "wins with a score of: ");
                toUI.println("Player " + p2.name + "had a score of: ");
            } else {
                toUI.println("Player " + p2.name + "wins with a score of: ");
                toUI.println("Player " + p1.name + "had a score of: ");
            }
            System.out.println("Game Over");
        }else{
            try {
                Music music_background;
                final String player1 = scanEgg.nextLine();
                final String player2 = scanEgg.nextLine();
                AudioInputStream audioInputStream = null;
                Clip clip = null;
                if (player1.equals("Rick") && player2.equals("Astley")) {
                    audioInputStream = AudioSystem.getAudioInputStream(new File(Gdx.files.internal("RickRoll.wav").path()).getAbsoluteFile());
                } else if (player1.equals("Nyan") && player2.equals("Cat")) {
                    audioInputStream = AudioSystem.getAudioInputStream(new File(Gdx.files.internal("easter.wav").path()).getAbsoluteFile());
                } else if (player1.equals("Sans") && player2.equals("Undertale")) {
                    audioInputStream = AudioSystem.getAudioInputStream(new File(Gdx.files.internal("brr.wav").path()).getAbsoluteFile());
                } else{
                    audioInputStream = AudioSystem.getAudioInputStream(new File(Gdx.files.internal("blokus.wav").path()).getAbsoluteFile());
                }
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                if (player1.equals("") && player2.equals("")) {
                 clip.stop();
                }
            }catch(LineUnavailableException | IOException | UnsupportedAudioFileException e){
                e.printStackTrace();
            }
        }
    }
}