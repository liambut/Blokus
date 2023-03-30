package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.cell;
import static com.mygdx.game.BlokusDuo.scanFromLogic;
import static java.lang.Integer.parseInt;

public class menu3 implements Screen {
    int timer = 0;
    BlokusDuo game;
    Boolean clicked;
    Image image;
    Image placeHolder;
    Image piece;
    Image mouse;
    int p1Score = -89, p2Score = -89;
    public String chosenPiece = "NULL";
    String currentPlayer = "P1";
    int movecount = 1;
    ArrayList<String> pieces = new ArrayList<>();
    private ShapeRenderer shapeRenderer;
    public menu3(final BlokusDuo game) {
        final String firstPlayer = scanFromLogic.nextLine();
        if(firstPlayer.equals("O")){
            currentPlayer = "P2";
        }
        final String player1 = scanFromLogic.nextLine();
        final String player2 = scanFromLogic.nextLine();
        this.game = game;
        final Label messageLabel = new Label("Select a piece, then choose where to place it", game.skin);
        final Label player = new Label(player1, game.skin);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        clicked=false;
        Gdx.input.setInputProcessor(game.stage);
        Gdx.gl.glClearColor( 1, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        Table table = new Table();
        table.setName("boardTable");
        table.setColor(game.skin.getColor("black"));
        table.setBackground(game.skin.getDrawable("color"));
        table.align(Align.bottom);
        table.setFillParent(true);
        for(int y = 0; y< 14; y++){
            for(int x = 0; x< 14; x++){
                if((x==4&&y==4)||(x==9&&y==9)){
                    image = new Image(game.skin, "greenblock");
                    image.setName("greenblock");
                } else if (y % 2 == 0) {
                    if(x%2==0){
                        image = new Image(game.skin, "whiteblock");
                    }else{
                        image = new Image(game.skin, "blackblock");
                    }
                }else{
                    if(x%2==0){
                        image = new Image(game.skin, "blackblock");
                    }else{
                        image = new Image(game.skin, "whiteblock");
                    }
                }
                image.setName(Integer.toString(x) + ":" + Integer.toString(13-y));
                table.add(image).size(15);
                image.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        image = (Image) event.getListenerActor();
                        piece = game.stage.getRoot().findActor(chosenPiece);
                        System.out.println(image.getName());
                        boolean invalid;
                        if (chosenPiece == "NULL") {
                            if (currentPlayer.equals("P1")) {
                                messageLabel.setText("Please select a red piece");
                            } else {
                                messageLabel.setText("Please select a blue piece");
                            }
                        } else {
                            game.toLogic.println("test");
                            game.toLogic.println(image.getName());
                            while(!scanFromLogic.hasNextBoolean()){
                                scanFromLogic.nextLine();
                            }
                            invalid = scanFromLogic.nextBoolean();
                            if(invalid){
                                if(movecount < 3){
                                    messageLabel.setText("First piece must be placed on a green square");
                                }else{
                                    messageLabel.setText("Invalid move: Please try again");
                                }
                            }else {
                                int rot = (int) piece.getRotation();
                                System.out.println(rot);
                                piece.setSize(166,166);
                                piece.setPosition(image.getX() + 8, image.getY()  + 8, image.getAlign());
                                /*
                                Old code to fix position when rotated and flipped
                                if (rot == 90 || rot == -270) {
                                    piece.setPosition(image.getX() + (piece.getHeight() / 2) + 8, image.getY() + 12, image.getAlign());
                                } else if (rot == 180 || rot == -180) {
                                    piece.setPosition(image.getX() + (piece.getHeight() / 2) + 3, image.getY() + (piece.getWidth() / 2) + 12, image.getAlign());
                                } else if (rot == -90 || rot == 270) {
                                    piece.setPosition(image.getX() + 3, image.getY() + (piece.getWidth() / 2) + 8, image.getAlign());
                                } else {
                                    piece.setPosition(image.getX() + 7, image.getY() + 7, image.getAlign());
                                }
                                 */
                                piece.setTouchable(Touchable.disabled);
                                pieces.remove(chosenPiece);
                                chosenPiece = "NULL";
                                game.toLogic.println("p");
                                currentPlayer = "NULL";
                                do{
                                    currentPlayer = scanFromLogic.nextLine();
                                }while(!currentPlayer.equals("P1") && !currentPlayer.equals("P2"));
                                while(!scanFromLogic.hasNextInt()){
                                    scanFromLogic.nextLine();
                                }
                                if(currentPlayer.equals("P1")){
                                    p1Score = scanFromLogic.nextInt();
                                    player.setText(player2);
                                    messageLabel.setText("Turn: " + player2 + " with a score of: " + p2Score);
                                }else {
                                    p2Score = scanFromLogic.nextInt();
                                    player.setText(player1);
                                    messageLabel.setText("Turn: " + player1 + " with a score of: " + p1Score);
                                }
                                if(scanFromLogic.hasNextLine()){
                                    if(scanFromLogic.nextLine().equals("over")) {
                                        for (Actor actor : game.stage.getActors()) {
                                            actor.remove();
                                        }
                                        game.setScreen(new menu4(game));
                                    }
                                }
                                movecount++;
                            }
                        }
                    };
                });
            }
            table.row();
        }
        //Image image = new Image(game.skin, "board");
        //table.add(image).pad(3.0f).size(250);
        //table.row();
        table.padBottom(30);
        game.stage.addActor(table);
        table = new Table();
        table.padLeft(0.0f);
        table.padRight(0.0f);
        table.padTop(0.0f);
        table.padBottom(-100.0f);
        table.align(Align.bottom);
        table.setFillParent(true);
        placeHolder = new Image(game.skin, "blackblock");
        placeHolder.setName("placeHolder");
        placeHolder.setOrigin(placeHolder.getWidth()/2, placeHolder.getHeight()/2);
        placeHolder.setVisible(false);
        table = new Table();
        table.padLeft(0.0f);
        table.padRight(0.0f);
        table.padTop(7.0f);
        table.padBottom(0.0f);
        table.align(Align.top);
        table.setFillParent(true);
        Label label = new Label("Blokus Duo", game.skin);
        table.add(label);
        table.row();
        label = new Label("by Salami", game.skin);
        table.add(label);
        table.row();
        TextButton clockwise = new TextButton("Rotate Clockwise", game.skin);
        clockwise.setName("rotateClockwise");
        clockwise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (chosenPiece == "NULL") {
                    if(currentPlayer.equals("P1")){
                        messageLabel.setText("Please select a red piece");
                    }else{
                        messageLabel.setText("Please select a blue piece");
                    }
                }else {
                    game.toLogic.println("r");
                    image = game.stage.getRoot().findActor(chosenPiece);
                    image.rotateBy(-90);
                    image.setOrigin(image.getWidth()/2, image.getHeight()/2);
                }
            };
        });
        table.add(clockwise);
        table.row();
        TextButton antiClockwise = new TextButton("Rotate Anti-Clockwise", game.skin);
        antiClockwise.setName("rotateAntiClockwise");
        antiClockwise.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(timer > 60) {
                    if (chosenPiece == "NULL") {
                        if (currentPlayer.equals("P1")) {
                            messageLabel.setText("Please select a red piece");
                        } else {
                            messageLabel.setText("Please select a blue piece");
                        }
                    } else {
                        game.toLogic.println("r");
                        game.toLogic.println("r");
                        game.toLogic.println("r");
                        image = game.stage.getRoot().findActor(chosenPiece);
                        image.rotateBy(90);
                        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                    }
                    timer = 0;
                }
            };
        });
        table.add(antiClockwise);
        table.row();
        TextButton flipHor = new TextButton("Flip Horisontally", game.skin);
        flipHor.setName("flipHor");
        flipHor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(timer>60) {
                    if (chosenPiece == "NULL") {
                        if (currentPlayer.equals("P1")) {
                            messageLabel.setText("Please select a red piece");
                        } else {
                            messageLabel.setText("Please select a blue piece");
                        }
                    } else {
                        game.toLogic.println("r");
                        game.toLogic.println("f");
                        game.toLogic.println("r");
                        game.toLogic.println("r");
                        game.toLogic.println("r");
                        image = game.stage.getRoot().findActor(chosenPiece);
                        if (image.getDrawable().toString().contains("f")) {
                            image.setDrawable(game.skin, image.getDrawable().toString().replace("f", ""));
                        } else {
                            image.setDrawable(game.skin, image.getDrawable().toString() + "f");
                        }
                        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                    }
                    timer = 0;
                }
            };
        });
        table.add(flipHor);
        table.row();
        TextButton flipVert = new TextButton("Flip Vertically", game.skin);
        flipVert.setName("flipVert");
        flipVert.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(timer>60) {
                    if (chosenPiece == "NULL") {
                        if (currentPlayer.equals("P1")) {
                            messageLabel.setText("Please select a red piece");
                        } else {
                            messageLabel.setText("Please select a blue piece");
                        }
                    } else {
                        game.toLogic.println("f");
                        image = game.stage.getRoot().findActor(chosenPiece);
                        if (image.getDrawable().toString().contains("f")) {
                            image.setDrawable(game.skin, image.getDrawable().toString().replace("f", ""));
                        } else {
                            image.setDrawable(game.skin, image.getDrawable().toString() + "f");
                        }
                        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                        image.rotateBy(180);
                    }
                    timer = 0;
                }
            };
        });
        table.add(flipVert);
        table.row();
        table.add(player);
        table.row();
        table.add(messageLabel);
        game.stage.addActor(table);

        table = new Table();
        table.setName("player1");
        table.align(Align.left);
        table.setFillParent(true);

        image = new Image(game.skin, "player1I1");
        image.setName("player1I1");
        pieces.add("player1I1");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1I2");
        image.setName("player1I2");
        pieces.add("player1I2");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1I3");
        image.setName("player1I3");
        pieces.add("player1I3");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player1I4");
        image.setName("player1I4");
        pieces.add("player1I4");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1I5");
        image.setName("player1I5");
        pieces.add("player1I5");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1L4");
        image.setName("player1L4");
        pieces.add("player1L4");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player1L5");
        image.setName("player1L5");
        pieces.add("player1L5");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1N");
        image.setName("player1N");
        pieces.add("player1N");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1O4");
        image.setName("player1O4");
        pieces.add("player1O4");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player1P");
        image.setName("player1P");
        pieces.add("player1P");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1T4");
        image.setName("player1T4");
        pieces.add("player1T4");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1T5");
        image.setName("player1T5");
        pieces.add("player1T5");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player1U");
        image.setName("player1U");
        pieces.add("player1U");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1V3");
        image.setName("player1V3");
        pieces.add("player1V3");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1V5");
        image.setName("player1V5");
        pieces.add("player1V5");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player1W");
        image.setName("player1W");
        pieces.add("player1W");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1X");
        image.setName("player1X");
        pieces.add("player1X");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1Y");
        image.setName("player1Y");
        pieces.add("player1Y");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player1Z4");
        image.setName("player1Z4");
        pieces.add("player1Z4");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1Z5");
        image.setName("player1Z5");
        pieces.add("player1Z5");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player1F");
        image.setName("player1F");
        pieces.add("player1F");
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P1")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a blue piece");
                }
            };
        });
        table.add(image);
        game.stage.addActor(table);

        table = new Table();
        table.align(Align.right);
        table.setFillParent(true);

        image = new Image(game.skin, "player2I1");
        image.setName("player2I1");
        pieces.add("player2I1");
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2I2");
        image.setName("player2I2");
        pieces.add("player2I2");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2I3");
        image.setName("player2I3");
        pieces.add("player2I3");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player2I4");
        image.setName("player2I4");
        pieces.add("player2I4");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2I5");
        image.setName("player2I5");
        pieces.add("player2I5");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2L4");
        image.setName("player2L4");
        pieces.add("player2L4");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player2L5");
        image.setName("player2L5");
        pieces.add("player2L5");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2N");
        image.setName("player2N");
        pieces.add("player2N");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2O4");
        image.setName("player2O4");
        pieces.add("player2O4");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player2P");
        image.setName("player2P");
        pieces.add("player2P");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2T4");
        image.setName("player2T4");
        pieces.add("player2T4");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2T5");
        image.setName("player2T5");
        pieces.add("player2T5");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player2U");
        image.setName("player2U");
        pieces.add("player2U");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2V3");
        image.setName("player2V3");
        pieces.add("player2V3");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2V5");
        image.setName("player2V5");
        pieces.add("player2V5");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player2W");
        image.setName("player2W");
        pieces.add("player2W");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2X");
        image.setName("player2X");
        pieces.add("player2X");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2Y");
        image.setName("player2Y");
        pieces.add("player2Y");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        table.row();
        image = new Image(game.skin, "player2Z4");
        image.setName("player2Z4");
        pieces.add("player2Z4");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2Z5");
        image.setName("player2Z5");
        pieces.add("player2Z5");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);

        image = new Image(game.skin, "player2F");
        image.setName("player2F");
        pieces.add("player2F");
        pieces.add("player2I1");image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPlayer.equals("P2")) {
                    if(chosenPiece != "NULL"){
                        game.stage.getRoot().findActor(chosenPiece).setSize(88,88);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setPosition(placeHolder.getX(), placeHolder.getY());
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.enabled);
                        game.toLogic.println("change");
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }else{
                        chosenPiece = event.getListenerActor().getName();
                        game.stage.getRoot().findActor(chosenPiece).setSize(166,166);
                        game.stage.getRoot().findActor(chosenPiece).setOrigin(game.stage.getRoot().findActor(chosenPiece).getWidth()/2, game.stage.getRoot().findActor(chosenPiece).getHeight()/2);
                        game.stage.getRoot().findActor(chosenPiece).setTouchable(Touchable.disabled);
                        placeHolder.setPosition(game.stage.getRoot().findActor(chosenPiece).getX(), game.stage.getRoot().findActor(chosenPiece).getY());
                        game.toLogic.println(chosenPiece);
                    }
                }else{
                    messageLabel.setText("Please select a red piece");
                }
            };
        });
        table.add(image);
        game.stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(timer<300){timer++;}
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!chosenPiece.equals("NULL")){
            mouse = game.stage.getRoot().findActor(chosenPiece);
            mouse.setPosition(Gdx.input.getX() - 82, 540-Gdx.input.getY() - 84);
        }
        game.stage.act();
        if(!chosenPiece.equals("NULL")){
            game.stage.addActor(mouse);
        }
        game.stage.draw();
    }

    public void resize(int width, int height) {
        game.stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        game.stage.dispose();
        game.skin.dispose();
    }
}