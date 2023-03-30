package com.mygdx.game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.mygdx.game.BlokusDuo.*;
import static com.mygdx.game.NewThread.GUI;
import static java.lang.Math.abs;

public class Player {
    static Scanner myObj = new Scanner(System.in);
    String name;
    static String lastPiecePlaced;
    int score;
    boolean move;
    char Icon;
    int moveCount;
    public static int chosenx;
    public static int choseny;

    public Player(String pName, int pScore, boolean pMove, char pIcon){
        name = pName;
        score = pScore;
        move = pMove;
        Icon = pIcon;
        moveCount = 0;

    }
    //getter for name
    public static String getPlayerName(Player player) {
        return player.name;
    }
    //getter for score
    public static int getPlayerScore(Player player) {
        return player.score;
    }
    //Show the user inputs name
    public static int getMoveCount(Player player){return player.moveCount; }


    public static String inputName() {

        System.out.println("Enter the name of Player: ");
        String name;

        do {
            name = myObj.nextLine();
        } while (name == null);
        System.out.println(name);
        return name;
    }



    //Standard code for asking for the player to input a block
    public int inputBlock(Blocks blocks, Board b1) {
        String block = null;
        String choice = null;
        boolean hasBlockNum = false;
        int blockNum = -1;
        int x=-1;
        int UIX;
        int UIY;
        String xy;
        String[]  xyint;
        int y=-1;
        int pNum = -1;
        int repeatCounter = 0;
        this.moveCount = this.moveCount + 1;
        if(this.Icon == 'X'){
            pNum = 1;
        } else {
            //this.Icon == 'O'
            pNum = 2;
        }

        while (!hasBlockNum) {
            while(blockNum == -1) {
                if (repeatCounter == 0) {

                    if(GUI){
                        System.out.println(getValidBlocks(blocks));
                        System.out.println("Please enter which block you would like to place");
                        block = scanFromUI.nextLine();
                        if (block.contains("player1")) {
                            block = block.replace("player1", "");
                        } else {
                            block = block.replace("player2", "");
                        }
                        lastPiecePlaced = block;
                        System.out.println(block);
                    } else {
                        System.out.println(getValidBlocks(blocks));
                        System.out.println("Please enter which block you would like to place");
                        block = myObj.next();
                        lastPiecePlaced = block;
                    }

                } else if (repeatCounter != 0) {

                    if(GUI) {
                        System.out.println("That is not a valid block");
                        System.out.println("Please enter which block you would like to place");
                        block = scanFromUI.nextLine();
                        if (block.contains("player1")) {
                            block = block.replace("player1", "");
                        } else {
                            block = block.replace("player2", "");
                        }
                        lastPiecePlaced = block;
                    } else {
                        System.out.println("That is not a valid block");
                        System.out.println(getValidBlocks(blocks));
                        System.out.println("Please enter which block you would like to place");
                        block = myObj.next();
                        lastPiecePlaced = block;

                    }
                }

                if (Blocks.checkPieceName(blocks, block) == -1) {

                } else {
                    blockNum = Blocks.checkPieceName(blocks, block);
                }
                repeatCounter++;
            }

            if (pNum == 2) {
                blockNum += 21;
            }

            //print your choice of block
            Blocks.printBlock(blockNum);

            while (choice != "p") {
                if(GUI) {
                    System.out.println("Would you like to place the block like this or rotate it or flip it?");
                    System.out.println("Press p to place, Press r to rotate and Press f to flip");
                    choice = scanFromUI.nextLine();
                } else {
                    System.out.println("Would you like to place the block like this or rotate it or flip it?");
                    System.out.println("Press p to place, Press r to rotate and Press f to flip");
                    choice = myObj.next();
                }


                switch (choice) {

                    case "change":

                        block = scanFromUI.nextLine();
                        if(block.contains("player1")){
                            block = block.replace("player1", "");
                        }else{
                            block = block.replace("player2", "");
                        }
                        if (Blocks.checkPieceName(blocks, block) == -1) {

                        } else {
                            blockNum = Blocks.checkPieceName(blocks, block);
                        }
                        repeatCounter = 0;
                        choice = null;
                        x=-1;
                        y=-1;
                        break;

                    case "test":
                        xy = scanFromUI.nextLine();
                        xyint = xy.split(":");
                        this.chosenx = Math.abs(Integer.parseInt(xyint[1])-13);
                        this.choseny = Integer.parseInt(xyint[0]);
                        boolean minused = false;
                        if (blockNum > 20) {
                            blockNum -= 21;
                            minused = true;
                        }
                        boolean checker = blocks.checkMove(blocks, blockNum, b1, this.chosenx, this.choseny,this);
                        if(minused){
                            blockNum +=21;
                        }
                        System.out.println("done");
                        toUI.println(checker);
                        choice = "null";
                        break;

                    case "p":

                        choice = "p";
                        break;

                    case "r":

                        blocks.rotateMatrix(blocks.pieces.get(blockNum));
                        blocks.printBlock(blockNum);
                        choice = "null";
                        break;


                    case "f":

                        blocks.flipMatrix(blocks.pieces.get(blockNum));
                        blocks.printBlock(blockNum);
                        choice = "null";
                        break;

                    default:
                        System.out.println("Invalid choice please try again");
                        choice = null;
                }
            }
            if(!GUI) {
                while (x < 0 || x > 14) {
                    if (x != -1) {
                        System.out.println("Please enter a valid X.");
                    }
                    System.out.println("Which X cordinate would you like to place at");
                    x = myObj.nextInt();
                }

                while (y < 0 || y > 14) {
                    if (y != -1) {
                        System.out.println("Please enter a valid Y.");
                    }
                    System.out.println("Which Y cordinate would you like to place at");
                    y = myObj.nextInt();
                }
                this.chosenx = Math.abs(y - 13);
                this.choseny = x;
            }
            if (blockNum > 20) {
                blockNum -= 21;
            }
            if (!blocks.checkMove(blocks, blockNum, b1, this.chosenx, this.choseny,this)) {
                hasBlockNum = true;
                return blockNum;
            } else {
                blockNum = -1;
                repeatCounter = 0;
                choice = null;
                x=-1;
                y=-1;
                System.out.println("Please choose a valid combination");
                System.out.println();
                System.out.println();
            }
        }
        return blockNum;
    }

    public int getPlayerMoveCount() {
        return this.moveCount;
    }



    public String getValidBlocks(Blocks playerblocks) {
        String available = "Blocks available:";
        for(int i = 0; i < playerblocks.placed.size(); i++){
            if(!playerblocks.placed.get(i)){
                available = available.concat(" " + playerblocks.piecename.get(i));
            }
        }
        return available;
    }
    private static boolean allBlocksPlaced(Blocks playerBlocks) {
        for (int x = 0; x < 21; x++) {
            if (playerBlocks.placed.get(x)) {

            } else {
                return false;
            }
        }
        return true;
    }
    public static int calculatePlayerScore(Blocks playerBlocks){
        int TotalScore = 0;
        int temp = 0;

        for(int x = 0; x < 21; x++){
            if(!playerBlocks.placed.get(x)){
                temp = playerBlocks.value.get(x);
                TotalScore += temp;
            }
        }
        if(lastPiecePlaced == "I1" && allBlocksPlaced(playerBlocks)){
            TotalScore -= 5;
        }
        if(allBlocksPlaced(playerBlocks)) {
            TotalScore -= 15;
        }

        return TotalScore * -1;
    }
}