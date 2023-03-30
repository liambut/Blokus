package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.BlokusDuo;

import java.io.IOException;

import static com.mygdx.game.BlokusDuo.toLogic;
import static com.mygdx.game.BlokusDuo.toUI;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

    public static Boolean isUI = false;
    static Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();


    public static void main(String[] args) throws IOException {

        String starter = "-X";
        for (String arg : args) {
            switch (arg) {

                case "-gui":
                    isUI = true;

                case "-X":
                    starter = "-X";
                    break;

                case "-O":
                    starter = "-O";
                    break;

            }
        }
        if(isUI){
            toLogic.println(isUI);
            if(starter.equals("-O")){
                toLogic.println("O");
                toUI.println("O");
            }else{
                toLogic.println("X");
                toUI.println("X");
            }
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setWindowedMode(960, 540);
            config.useVsync(true);
            config.setForegroundFPS(60);
            new Lwjgl3Application(new BlokusDuo(), config);
        }else{
            if(starter.equals("-O")){
                toLogic.println("O");
            }else{
                toLogic.println("X");
            }
            nonGui.noGui(starter);
        }
    }
}
