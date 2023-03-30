package com.mygdx.game;


public class nonGui {
        public static void noGui(String arg) {
            switch (arg) {

                case "-X":
                    String y = Player.inputName();
                    String x = Player.inputName();


                    Player p1 = new Player(y, 0, true, 'X');
                    Player p2 = new Player(x, 0, true, 'O');
                    Board b1 = new Board();
                    Board.printScoreboard(p1, p2);
                    Board.boardSetup(b1);
                    Board.printBoard(b1);
                    Blocks p1blocks = new Blocks(p1);
                    Blocks p2blocks = new Blocks(p2);
                    p1blocks.blocksSetup();
                    p2blocks.blocksSetup();

                    p1blocks.pushBlock(p1.inputBlock(p1blocks, b1), b1, Player.chosenx, Player.choseny);
                    Board.printBoard(b1);
                    p2blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                    Board.printBoard(b1);

                    while (Blocks.moveExists(p1blocks, b1, p1) || Blocks.moveExists(p2blocks, b1, p2)) {

                        if (Blocks.moveExists(p1blocks, b1, p1)) {
                            p1blocks.pushBlock(p1.inputBlock(p1blocks, b1), b1, Player.chosenx, Player.choseny);
                            Board.printBoard(b1);
                        }
                        if (Blocks.moveExists(p2blocks, b1, p2)) {
                            p2blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                            Board.printBoard(b1);

                        }
                    }
                    System.out.println("Game Over");
                    break;


                case "-O":
                    y = Player.inputName();
                    x = Player.inputName();


                    p2 = new Player(x, 0, true, '0');
                    p1 = new Player(y, 0, true, 'X');
                    b1 = new Board();
                    Board.printScoreboard(p1, p2);
                    Board.boardSetup(b1);
                    Board.printBoard(b1);
                    p1blocks = new Blocks(p1);
                    p2blocks = new Blocks(p2);
                    p1blocks.blocksSetup();
                    p2blocks.blocksSetup();


                    p2blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                    Board.printBoard(b1);
                    p1blocks.pushBlock(p1.inputBlock(p1blocks, b1), b1, Player.chosenx, Player.choseny);
                    Board.printBoard(b1);
                    while (Blocks.moveExists(p1blocks, b1, p1) || Blocks.moveExists(p2blocks, b1, p2)) {

                        if (Blocks.moveExists(p1blocks, b1, p1)) {
                            p2blocks.pushBlock(p1.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                            Board.printBoard(b1);
                        }

                        if (Blocks.moveExists(p2blocks, b1, p2)) {
                            p1blocks.pushBlock(p2.inputBlock(p2blocks, b1), b1, Player.chosenx, Player.choseny);
                            Board.printBoard(b1);

                        }

                    }
                    System.out.println("Game Over");
                    break;

            }
        }
    }

