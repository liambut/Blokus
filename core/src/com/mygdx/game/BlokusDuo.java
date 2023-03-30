package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
public class BlokusDuo extends Game {
	Skin skin;
	public Boolean start = false;
	Stage stage;
	public static PipedOutputStream pipedOutToLogic = new PipedOutputStream();
	public static PipedInputStream pipedInToLogic;
	public static PipedOutputStream pipedOutToUI = new PipedOutputStream();
	public static PipedInputStream pipedInToUI;
	public static PipedOutputStream pipedOutToEgg = new PipedOutputStream();
	public static PipedInputStream pipedInToEgg;

	static {
		try {
			pipedInToLogic = new PipedInputStream(pipedOutToLogic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static {
		try {
			pipedInToEgg = new PipedInputStream(pipedOutToEgg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static {
		try {
			pipedInToUI = new PipedInputStream(pipedOutToUI);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PrintStream toLogic = new PrintStream(pipedOutToLogic);
	public static Scanner scanFromUI = new Scanner(pipedInToLogic);
	public static PrintStream toUI = new PrintStream(pipedOutToUI);
	public static Scanner scanFromLogic = new Scanner(pipedInToUI);
	public static PrintStream toEgg = new PrintStream(pipedOutToEgg);
	public static Scanner scanEgg = new Scanner(pipedInToEgg);

	public BlokusDuo() throws IOException {
	}

	public void create() {
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("skin.json"));
		Gdx.input.setInputProcessor(stage);
		setScreen(new menu1(this));
	}
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void render(){
		super.render();
	}

	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	public static void PostRunnable(Runnable r){
		Gdx.app.postRunnable(r);
	}
	public void startGame(){
		start = true;
	}
}
