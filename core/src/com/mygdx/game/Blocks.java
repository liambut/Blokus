package com.mygdx.game;

import java.util.ArrayList;

import static com.mygdx.game.BlokusDuo.toUI;
import static com.mygdx.game.Player.calculatePlayerScore;

public class Blocks {
    static ArrayList<int[][]> pieces = new ArrayList<int[][]>();
    ArrayList<Boolean> placed = new ArrayList<Boolean>();
    ArrayList<String> piecename = new ArrayList<>();
    ArrayList<Integer> value = new ArrayList<>();
    int piecenumber;

    Blocks(Player player) {
        if (player.Icon == 'X') {
            piecenumber = 1;
        } else {
            piecenumber = 2;
        }
    }

    public void blocksSetup() {
        pieces.add(setBlock(this.piecenumber, "I1"));
        placed.add(false);
        value.add(1);
        pieces.add(setBlock(this.piecenumber, "I2"));
        placed.add(false);
        value.add(2);
        pieces.add(setBlock(this.piecenumber, "I3"));
        placed.add(false);
        value.add(3);
        pieces.add(setBlock(this.piecenumber, "I4"));
        placed.add(false);
        value.add(4);
        pieces.add(setBlock(this.piecenumber, "I5"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "V3"));
        placed.add(false);
        value.add(3);
        pieces.add(setBlock(this.piecenumber, "L4"));
        placed.add(false);
        value.add(4);
        pieces.add(setBlock(this.piecenumber, "Z4"));
        placed.add(false);
        value.add(4);
        pieces.add(setBlock(this.piecenumber, "O4"));
        placed.add(false);
        value.add(4);
        pieces.add(setBlock(this.piecenumber, "L5"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "T5"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "V5"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "N"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "Z5"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "T4"));
        placed.add(false);
        value.add(4);
        pieces.add(setBlock(this.piecenumber, "P"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "W"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "U"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "F"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "X"));
        placed.add(false);
        value.add(5);
        pieces.add(setBlock(this.piecenumber, "Y"));
        placed.add(false);
        value.add(5);
    }


    public static int[][] initArray() {
        int[][] mat = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, };
        return mat;
    }

    public int[][] setBlock(int piecenumber, String blockName) {
        int[][] tempArray = initArray();
        tempArray[5][5] = 6;
        if (blockName == "I1") {
        }
        if (blockName.equals("I2")) {
            tempArray[4][5] = piecenumber;
        }
        if (blockName.equals("I3")) {
            tempArray[4][5] = piecenumber;
            tempArray[3][5] = piecenumber;
            tempArray[6][8] = 3;
            tempArray[4][8] = 3;
            tempArray[6][4] = 3;
            tempArray[4][4] = 3;
        }

        if (blockName.equals("I4")) {
            tempArray[4][5] = piecenumber;
            tempArray[3][5] = piecenumber;
            tempArray[2][5] = piecenumber;
        }

        if (blockName.equals("I5")) {
            tempArray[4][5] = piecenumber;
            tempArray[3][5] = piecenumber;
            tempArray[2][5] = piecenumber;
            tempArray[1][5] = piecenumber;
        }

        if (blockName.equals("V3")) {
            tempArray[4][5] = piecenumber;
            tempArray[5][6] = piecenumber;
        }

        if (blockName.equals("L4")) {
            tempArray[4][5] = piecenumber;
            tempArray[3][5] = piecenumber;
            tempArray[5][6] = piecenumber;
        }

        if (blockName.equals("Z4")) {
            tempArray[4][5] = piecenumber;
            tempArray[5][4] = piecenumber;
            tempArray[4][6] = piecenumber;
        }

        if (blockName.equals("O4")) {
            tempArray[4][5] = piecenumber;
            tempArray[4][6] = piecenumber;
            tempArray[5][6] = piecenumber;
        }

        if (blockName.equals("L5")) {
            tempArray[4][5] = piecenumber;
            tempArray[5][6] = piecenumber;
            tempArray[5][7] = piecenumber;
            tempArray[5][8] = piecenumber;
        }

        if (blockName.equals("T5")) {
            tempArray[4][5] = piecenumber;
            tempArray[3][5] = piecenumber;
            tempArray[5][4] = piecenumber;
            tempArray[5][6] = piecenumber;
        }

        if (blockName.equals("V5")) {
            tempArray[4][5] = piecenumber;
            tempArray[3][5] = piecenumber;
            tempArray[5][6] = piecenumber;
            tempArray[5][7] = piecenumber;
        }

        if (blockName.equals("N")) {
            tempArray[5][6] = piecenumber;
            tempArray[5][7] = piecenumber;
            tempArray[6][5] = piecenumber;
            tempArray[6][4] = piecenumber;
        }

        if (blockName.equals("Z5")) {
            tempArray[5][6] = piecenumber;
            tempArray[5][4] = piecenumber;
            tempArray[4][6] = piecenumber;
            tempArray[6][4] = piecenumber;
        }

        if (blockName.equals("T4")) {
            tempArray[4][5] = piecenumber;
            tempArray[5][6] = piecenumber;
            tempArray[5][4] = piecenumber;
        }

        if (blockName.equals("P")) {
            tempArray[6][5] = piecenumber;
            tempArray[7][5] = piecenumber;
            tempArray[5][6] = piecenumber;
            tempArray[6][6] = piecenumber;
        }

        if (blockName.equals("W")) {
            tempArray[4][5] = piecenumber;
            tempArray[4][6] = piecenumber;
            tempArray[5][4] = piecenumber;
            tempArray[6][4] = piecenumber;
        }

        if (blockName.equals("U")) {
            tempArray[4][5] = piecenumber;
            tempArray[6][5] = piecenumber;
            tempArray[4][6] = piecenumber;
            tempArray[6][6] = piecenumber;
        }

        if (blockName.equals("F")) {
            tempArray[4][5] = piecenumber;
            tempArray[4][6] = piecenumber;
            tempArray[5][4] = piecenumber;
            tempArray[6][5] = piecenumber;
        }

        if (blockName.equals("X")) {
            tempArray[5][6] = piecenumber;
            tempArray[6][5] = piecenumber;
            tempArray[5][4] = piecenumber;
            tempArray[4][5] = piecenumber;
        }

        if (blockName.equals("Y")) {
            tempArray[4][5] = piecenumber;
            tempArray[5][4] = piecenumber;
            tempArray[5][6] = piecenumber;
            tempArray[5][7] = piecenumber;
        }
        this.piecename.add(blockName);
        return tempArray;
    }

    public static void rotateMatrix( int mat[][]) {
        int x = 0;
        int y = 0;
        for (x = 0; x < 11 / 2; x++) {
            for (y = x; y < 11 - x - 1; y++) {
                int temp = mat[x][y];
                mat[x][y] = mat[11 - 1 - y][x];
                mat[11 - 1 - y][x] = mat[11 - 1 -x][11 - 1 - y];
                mat[11 - 1 - x][11 - 1 - y] = mat[y][11 - 1 - x];
                mat[y][11 - 1 - x] = temp;
            }
        }
    }

    public static void flipMatrix(int mat[][]) {
        for(int y = 0;y < 11/2; y++){
            for(int x = 0;x < 11; x++){
                int temp = mat[x][y];
                mat[x][y] = mat[x][10 - y];
                mat[x][10 - y] = temp;
            }

        }
    }


    public static int checkPieceName(Blocks blocks, String pieceName) {
        for (int i = 0; i < 21; i++) {
            if (blocks.piecename.get(i).equals(pieceName) && !blocks.placed.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean coversStartPosition(Blocks blocks, int blockNumber, int x_axis, int y_axis) {
        boolean result = false;
        if(blocks.piecenumber == 2){
            blockNumber += 21;
        }
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (pieces.get(blockNumber)[x][y] == blocks.piecenumber || pieces.get(blockNumber)[x][y] == 6) {
                    if(NewThread.firstPlayer.equals("X")){
                        if(blocks.piecenumber == 1) {
                            if (x_axis + x - 5 == 4 && y_axis + y - 5 == 4) {
                                result = true;
                            }
                        } else if(blocks.piecenumber == 2) {
                            if (x_axis + x - 5 == 9 && y_axis + y - 5 == 9) {
                                result = true;
                            }
                        }
                    }else {
                        if (blocks.piecenumber == 1) {
                            if (x_axis + x - 5 == 4 && y_axis + y - 5 == 4) {
                                result = true;
                            }
                        } else if (blocks.piecenumber == 2) {
                            if (x_axis + x - 5 == 9 && y_axis + y - 5 == 9) {
                                result = true;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean boardSquareTouchesAtASide(int blockNumber,Board playBoard, int x_axis, int y_axis) {
        boolean result = false;
        if(this.piecenumber == 2){
            blockNumber += 21;
        }
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (pieces.get(blockNumber)[x][y] == this.piecenumber || pieces.get(blockNumber)[x][y] == 6) {
                    if(y_axis + y - 5 < 0 || y_axis + y - 5 > 13){
                        return false;
                    } if (x_axis + x - 4 < 0 || x_axis + x - 4 > 13) {
                        if(playBoard.board[x_axis + x - 6][y_axis + y - 5] == this.piecenumber){
                            return true;
                        }
                    }
                    if(x_axis + x - 6 > 13 || x_axis + x - 6 < 0 ){
                        if(playBoard.board[x_axis + x - 4][y_axis + y - 5] == this.piecenumber){
                            return true;
                        }
                    }
                    if(y_axis + y - 4 < 0 || y_axis + y - 4 > 13 ){
                        if(playBoard.board[x_axis + x - 5][y_axis + y - 6] == this.piecenumber){
                            return true;
                        }
                    }
                    if(y_axis + y - 6 < 0 || y_axis + y - 6 > 13){
                        if(playBoard.board[x_axis + x - 5][y_axis + y - 4] == this.piecenumber){
                            return true;
                        }
                    } if(y_axis + y - 4 < 0 || y_axis + y - 4 > 13 || y_axis + y - 6 < 0 || y_axis + y - 6 > 13 ||
                         x_axis + x - 6 > 13 || x_axis + x - 6 < 0 ||x_axis + x - 4 < 0 || x_axis + x - 4 > 13){
                        return false;
                    } else {
                        if ((playBoard.board[x_axis + x - 4][y_axis + y - 5] == this.piecenumber) ||
                            playBoard.board[x_axis + x - 6][y_axis + y - 5] == this.piecenumber ||
                            playBoard.board[x_axis + x - 5][y_axis + y - 4] == this.piecenumber ||
                            playBoard.board[x_axis + x - 5][y_axis + y - 6] == this.piecenumber) {
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean boardSquareTouchesAtACorner(int blockNumber ,Board playBoard, int x_axis, int y_axis) {
        boolean result = false;
        if(this.piecenumber == 2){
            blockNumber += 21;
            System.out.println(blockNumber);
        }
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (pieces.get(blockNumber)[x][y] == this.piecenumber || pieces.get(blockNumber)[x][y] == 6) {
                    if (x_axis + x - 4 == 14 &&  y_axis + y - 4 == 14){
                        if(playBoard.board[x_axis + x - 6][y_axis + y - 6] == this.piecenumber){
                            return true;
                        }
                    } if (x_axis + x - 6 == -1 &&  y_axis + y - 6 == -1){
                        if(playBoard.board[x_axis + x - 4][y_axis + y - 4] == this.piecenumber){
                            return true;
                        }
                    } if (x_axis + x - 4 == -1 &&  y_axis + y - 6 == 14){
                        if(playBoard.board[x_axis + x - 4][y_axis + y - 4] == this.piecenumber){
                            return true;
                        }
                    } if (x_axis + x - 6 == 14 &&  y_axis + y - 4 == -1){
                        if(playBoard.board[x_axis + x - 4][y_axis + y - 4] == this.piecenumber){
                            return true;
                        }
                    } else if (x_axis + x - 4 < 0 || x_axis + x - 4 > 13){
                        if(y_axis + y - 4 == -1 || y_axis + y - 4 == 14 || x_axis + x - 6 == -1 ||x_axis + x - 6 == 14 || y_axis + y - 6 ==-1 || y_axis + y - 6==14) {
                            break;
                        }else if(playBoard.board[x_axis + x - 6][y_axis + y - 4] == this.piecenumber ||
                                 playBoard.board[x_axis + x - 6][y_axis + y - 6] == this.piecenumber){
                            return true;
                        }
                    } else if(x_axis + x - 6 < 0|| x_axis + x - 6 > 13){
                        if(y_axis + y - 4 == -1 || y_axis + y - 4 == 14 || x_axis + x - 4 == -1 || x_axis + x - 4 == 14 || y_axis + y - 6 == -1 || y_axis + y - 6 == 14) {
                            break;
                        }else if(playBoard.board[x_axis + x - 4][y_axis + y - 4] == this.piecenumber ||
                                 playBoard.board[x_axis + x - 4][y_axis + y - 6] == this.piecenumber ){
                            return true;
                        }
                    } else if(y_axis + y - 6 < 0 || y_axis + y - 6 > 13){
                        if(y_axis + y - 4 == 0 || y_axis + y - 4 == 14) {
                            break;
                        }else if(playBoard.board[x_axis + x - 4][y_axis + y - 4] == this.piecenumber ||
                                 playBoard.board[x_axis + x - 6][y_axis + y - 4] == this.piecenumber ){
                            return true;
                        }
                    } else if (y_axis + y - 4 > 13 || y_axis + y - 4 < 0){
                        if(y_axis + y - 6 == 0 || y_axis + y - 6 == 14) {
                            break;
                        }else if(playBoard.board[x_axis + x - 4][y_axis + y - 6] == this.piecenumber ||
                                 playBoard.board[x_axis + x - 6][y_axis + y - 6] == this.piecenumber ){
                            return true;
                        }
                    } else {
                        if ((playBoard.board[x_axis + x - 4][y_axis + y - 4] == this.piecenumber) ||
                            playBoard.board[x_axis + x - 6][y_axis + y - 4] == this.piecenumber ||
                            playBoard.board[x_axis + x - 4][y_axis + y - 6] == this.piecenumber ||
                            playBoard.board[x_axis + x - 6][y_axis + y - 6] == this.piecenumber) {
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }
    private boolean touchesSamePlayerPiecesOnlyAtCorners(int blockNumber ,Board playBoard, int x_axis, int y_axis) {
        boolean atLeastOneSquareTouchesAtASide = false;
        boolean atLeastOneSquareTouchesAtCorner = false;
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (pieces.get(blockNumber)[x][y] == this.piecenumber || pieces.get(blockNumber)[x][y] == 6) {
                    if (boardSquareTouchesAtACorner(blockNumber,playBoard,x_axis, y_axis)) {
                        atLeastOneSquareTouchesAtCorner = true;
                    }
                    if (boardSquareTouchesAtASide(blockNumber,playBoard,x_axis, y_axis)) {
                        atLeastOneSquareTouchesAtASide = true;
                    }
                }
            }
        }
        return  atLeastOneSquareTouchesAtCorner && (! atLeastOneSquareTouchesAtASide);
    }


    public boolean checkMove(Blocks blocks, int blockNumber, Board playBoard, int x_axis, int y_axis, Player p) {
        boolean result = false;
        System.out.println(p.moveCount);
        if(p.moveCount == 1) {
            if (Blocks.coversStartPosition(blocks, blockNumber, x_axis, y_axis)){
            } else {
                System.out.println("1");
                result = true;
            }
        }
        if(this.piecenumber == 2){
            blockNumber +=21;
        }
        for (int x = 0; x < 11; x++) {
            //int x_offset = 0;
            for (int y = 0; y < 11; y++) {
                if (pieces.get(blockNumber)[x][y] == this.piecenumber || pieces.get(blockNumber)[x][y] == 6) {
                    // outside bounds
                    if (((x - 5 + x_axis) < -1) || ((x - 5 + x_axis) > 13) || ((y - 5 + y_axis) < -1) || ((y - 5 + y_axis) > 13)) {
                        return true;
                    }
                    //Another block placed
                    if (playBoard.board[x_axis + x - 5][y_axis + y - 5] == 1 || playBoard.board[x_axis + x - 5][y_axis + y - 5] == 2) {
                        return true;
                    }
                }
            }
        }
        if(this.piecenumber == 2){
            blockNumber -=21;
        }
        if(p.moveCount != 1){
            if(touchesSamePlayerPiecesOnlyAtCorners(blockNumber,playBoard,x_axis,y_axis)){

            } else {
                result = true;
            }
        }
        return result;
    }

    public void pushBlock(int blockNumber, Board playBoard, int x_axis, int y_axis){
        for(int x = 0; x < 11; x++){
            for(int y = 0; y < 11; y++){
                if(this.piecenumber == 1){
                    if(Blocks.pieces.get(blockNumber)[x][y] == 1 || Blocks.pieces.get(blockNumber)[x][y] == 6){
                        playBoard.board[x_axis + x - 5][y_axis + y - 5] = 1;
                    }
                } else {
                    if(Blocks.pieces.get(blockNumber +21)[x][y] == 2 || Blocks.pieces.get(blockNumber +21)[x][y] == 6){
                            playBoard.board[x_axis + x - 5][y_axis + y - 5] = 2;
                    }
                }
            }
        }
        placed.set(blockNumber, true);
    }

    public static boolean moveExists(Blocks blocks, Board playBoard,Player p) {
        String[] adjustments = {"", "r", "r", "r", "r", "f", "r", "r", "r"};
        for(int i = 0; i < 21; i++) {
            if (blocks.placed.get(i)) {
                break;
            }
            for(int x = 0; x < 14; x++) {
                for(int y = 0; y < 14; y++) {
                    for(int j = 0; j < adjustments.length; j++) {
                        String choice = adjustments[j];
                        switch (choice) {

                            case "r":

                                rotateMatrix(blocks.pieces.get(i));
                                adjustments[j] = "r";
                                break;

                            case "f":

                                flipMatrix(blocks.pieces.get(i));
                                adjustments[j] = "f";
                                break;

                            default:
                                break;
                        }
                        if (blocks.checkMove(blocks, i, playBoard, x, y, p)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void printBlock(int blockNum) {
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (pieces.get(blockNum)[x][y] == 1 || pieces.get(blockNum)[x][y] == 2 || pieces.get(blockNum)[x][y] == 6) {

                    if (pieces.get(blockNum)[x][y] == 1) {
                        System.out.print("X");
                    } else if(pieces.get(blockNum)[x][y] == 2){
                        System.out.print("O");
                    } else if(pieces.get(blockNum)[x][y] == 6){
                        System.out.print("+");
                    }
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }
}
