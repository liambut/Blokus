package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static com.mygdx.game.BlokusDuo.*;
import static com.mygdx.game.NewThread.firstPlayer;

public class menu1 implements Screen {
    BlokusDuo game;
    boolean clicked;
    public menu1(final BlokusDuo game) {
        this.game = game;
        clicked = false;
        Table table = new Table();
        table.setBackground(game.skin.getDrawable("color"));
        table.setColor(game.skin.getColor("black"));
        table.setFillParent(true);
        table.add();
        Label label = new Label("Blokus Duo", game.skin);
        table.add(label);
        table.add();
        table.row();
        label = new Label("Enter the name of player 1:", game.skin);
        label.setColor(game.skin.getColor("text"));
        table.add(label);
        Image image = new Image(game.skin, "redsquare");
        image.setName("menuPlayer1Square");
        table.add(image).size(16.0f);
        final TextField textField = new TextField(null, game.skin);
        textField.setName("menuPlayer1Name");
        table.add(textField);
        table.row();
        label = new Label("Enter the name of player 2:", game.skin);
        label.setColor(game.skin.getColor("text"));
        table.add(label);
        image = new Image(game.skin, "bluesquare");
        image.setName("menuPlayer2Square");
        table.add(image).size(16.0f);
        final TextField player2Text = new TextField(null, game.skin);
        player2Text.setName("menuPlayer2Name");
        table.add(player2Text);
        table.row();
        table.add();
        TextButton textButton = new TextButton("Start Game", game.skin);
        textButton.setName("startGameButton");
        table.add(textButton);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String player1Name = textField.getText();
                String player2Name = player2Text.getText();
                toLogic.println(player1Name);
                toLogic.println(player2Name);
                toUI.println(player1Name);
                toUI.println(player2Name);
                NewThread t1 = new NewThread();
                t1.setName("GAME");
                t1.start();
                NewThread t2 = new NewThread();
                t2.setName("Music");
                t2.start();
                toEgg.println(player1Name);
                toEgg.println(player2Name);
                for(Actor actor : game.stage.getActors())
                {
                    actor.remove();
                }
                game.setScreen(new menu2(game));
            };
        });
        table.add();
        game.stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.stage.act();
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
