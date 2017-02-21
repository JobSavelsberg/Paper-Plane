package com.jobsavelsberg.paperplane;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jobsavelsberg.paperplane.Screens.PlayScreen;

/**
 * Created by s153640 on 27-12-2016.
 */
public class MainGame extends Game implements InputProcessor{
    public Viewport viewport;
    public static Vector2 viewSize;
    public static Vector2 windowSize;

    public PlayScreen playScreen;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);

        windowSize = new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewSize = new Vector2(1920,1080);

        playScreen = new PlayScreen(this);
        setScreen(playScreen);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        getScreen().dispose();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(getScreen().equals(playScreen)){
            playScreen.touchDown();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(getScreen().equals(playScreen)){
            playScreen.touchUp();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
