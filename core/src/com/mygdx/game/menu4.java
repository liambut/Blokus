package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import static com.mygdx.game.BlokusDuo.scanFromLogic;
import static com.mygdx.game.BlokusDuo.scanFromUI;

public class menu4 implements Screen {
    private Skin skin;
    BlokusDuo game;

    private Stage stage;

    public menu4(final BlokusDuo game) {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("GAME OVER", skin);
        label.setFontScale(2);
        table.add(label);

        table.row();
        label = new Label("Player X Wins with a score of", skin);
        label.setText(scanFromLogic.nextLine());
        table.add(label);

        table.row();
        label = new Label("Player X had a score of", skin);
        label.setText(scanFromLogic.nextLine());
        table.add(label);

        table.row();
        TextButton textButton = new TextButton("Play Again", skin);
        table.add(textButton);

        table.row();
        textButton = new TextButton("Exit", skin);
        table.add(textButton);
        stage.addActor(table);
    }

    public void render() {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.stage.act();
        game.stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        skin.dispose();
    }
}
