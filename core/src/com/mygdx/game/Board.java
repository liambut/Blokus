package com.mygdx.game;

public class Board {

    int [][] board = new int[14][14];
    public static int WIDTH = 14;
    public static int HEIGHT = 14;

    public static int startX = 4;
    public static int startY = 4;
    /*
    public static final String cyan = "\u001B[36m";
    public static final String black = "\u001B[30m";
    public static final String purple = "\u001B[35m";
    public static final String red = "\u001B[31m";
    public static final String reset = "\u001B[0m";
    */
    public Board(){
        board = new int[WIDTH][HEIGHT];
    }

    public static int getWidth(){
        return WIDTH;
    }

    public static int getHeight(){
        return HEIGHT;
    }

    public static int getStartX(){
        return startX;
    }

    public static int getStartY(){
        return startY;
    }

    //Setup the board for a game
    public static void boardSetup(Board setupBoard){
        //fill in the array
        for (int x = 0; x < 14; x++) {
            for (int y = 0; y < 14; y++) {
                setupBoard.board[x][y] = 0;
            }
        }

        //set the starting points
        //left side starting point
        setupBoard.board[4][4] = 4;
        //right side starting point
        setupBoard.board[9][9] = 5;
    }
    //Print the board
    public static void printBoard(Board printedBoard){
        //Board Print
        //Switch Statement
        //key
        //0 = empty
        //1 = X(player 1)
        //2 = O(player 2)
        //3 = Option
        //4 = * Player 1 Start
        //5 = * Player 2 Start
        /* on placement of pice there will be 2 checkers to check both diagonally of first piece and
        vertically/horisontally of first piece, after this there will be 1 constant checker that checks
        horisontally for each potential block to be placed "yes yes that's good" - luke rickard
         */

        //make for loop dynamic
        //Two loops For the entire array
        for(int x = 0; x < 14; x++){
            for (int y = 0; y < 14; y++){
                //Numbers on the side
                if(y == 0) {
                    System.out.printf("");
                    if(x < 5){
                        System.out.print(13 - (x) + "  ");
                    }
                    else {
                        System.out.print(13 - (x) + "  ");
                    }
                }
                //The board empty
                if(printedBoard.board[x][y] == 0) {
                    System.out.print(".");
                    System.out.print("   ");
                }
                //Player 1 Square
                if(printedBoard.board[x][y] == 1){
                    System.out.print("X");
                    System.out.print("   ");
                }
                //Player 2 Square
                if(printedBoard.board[x][y] == 2){
                    System.out.print("O");
                    System.out.print("    ");
                }
                //option doesnt matter just for processing power
                if(printedBoard.board[x][y] == 3){
                    System.out.print("+");
                    System.out.print("   ");
                }
                //player 1 starting point
                if(printedBoard.board[x][y] == 4){
                    System.out.print("*");
                    System.out.print("   ");
                }
                //player 1 starting point
                if(printedBoard.board[x][y] == 5){
                    System.out.print("*");
                    System.out.print("   ");
                }
                //New line after each row is printed
                if(y == 13) {
                    System.out.println();
                }
            }
        }
        //last line numbers printed (cant think of a dynamic way)
        System.out.print("  ");
        for(int i = 0; i <= 13; i++) {
            System.out.print(i);
            System.out.print("   ");
        }
        System.out.println("");
    }
    public static void printScoreboard(Player p1, Player p2){
        System.out.println("--------------------------------------");
        System.out.print("|");
        System.out.print("Player 1: " + p1.name + " ");
        System.out.print("|");
        System.out.print("Player 2: " + p2.name + " ");
        System.out.println("|");
        System.out.println("--------------------------------------");
        System.out.print("|");
        System.out.print("Player 1 Score : " + p1.score);
        System.out.print("|");
        System.out.print("Player 2 Score : " + p2.score);
        System.out.println("|");
        System.out.println("--------------------------------------");
    }
}

