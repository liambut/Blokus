package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

import static com.mygdx.game.BlokusDuo.scanFromLogic;
import static com.mygdx.game.BlokusDuo.toUI;

public class menu2 implements Screen{
    BlokusDuo game;
    Boolean clicked;
    public menu2(final BlokusDuo game) {
        this.game = game;
        clicked=false;
        Gdx.gl.glClearColor( 1, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        Gdx.input.setInputProcessor(game.stage);
        Table table = new Table();
        table.setBackground(game.skin.getDrawable("color"));
        table.setColor(game.skin.getColor("black"));
        table.setFillParent(true);
        table.add();
        final String firstPlayer = scanFromLogic.nextLine();
        final String player1 = scanFromLogic.nextLine();
        final String player2 = scanFromLogic.nextLine();
        Label label;
        if(firstPlayer.equals("X")){
            label = new Label("Player " + player1 +" goes first!", game.skin);
        }else{
            label = new Label("Player " + player2 +" goes first!", game.skin);
        }
        label.setName("playerAnnounce");
        table.add(label);
        table.row();
        table.add();
        TextButton textButton = new TextButton("Okie Dokie", game.skin);
        textButton.setName("ackPlayer");
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for(Actor actor : game.stage.getActors())
                {
                    actor.remove();
                }
                toUI.println(firstPlayer);
                game.toUI.println(player1);
                game.toUI.println(player2);
                game.setScreen(new menu3(game));
            };
        });
        table.add(textButton);
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