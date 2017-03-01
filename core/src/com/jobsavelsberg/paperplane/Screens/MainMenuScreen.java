package com.jobsavelsberg.paperplane.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jobsavelsberg.paperplane.Background;
import com.jobsavelsberg.paperplane.Font;
import com.jobsavelsberg.paperplane.MainGame;
import com.jobsavelsberg.paperplane.Objectives;
import sun.swing.BakedArrayList;

/**
 * Created by s153640 on 28-2-2017.
 */
public class MainMenuScreen implements Screen {
    public MainGame game;

    private FitViewport viewport;
    public Stage stage;


    private SpriteBatch batch;
    private BitmapFont font;
    private Skin skin;
    private Background background;


    public MainMenuScreen(MainGame game){
        this.game = game;

        init();
    }

    private void init() {
        batch = new SpriteBatch();
        viewport = new FitViewport(MainGame.viewSize.x,MainGame.viewSize.y, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        font = Font.big.get();

        skin = new Skin();
        skin.add("defaultFont", font);


        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));



        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = skin.getFont("defaultFont");
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.overFontColor = Color.LIGHT_GRAY;
        textButtonStyle.downFontColor = Color.DARK_GRAY;
        textButtonStyle.checkedFontColor = Color.RED;

        skin.add("defaultButton", textButtonStyle);

        final TextButton textButton= createButton("Play", textButtonStyle, -1,500);
        textButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen( game.playScreen);
            }
        });

        background = Background.fireWatch;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(stage.getCamera().combined);

        //game.playScreen.render(delta);
        stage.getBatch().begin();
        //background.draw(stage.getBatch());
        stage.getBatch().draw(background.getTexture(), 0, 0, MainGame.viewSize.x, MainGame.viewSize.y);
        MainGame.objectives.update();
        MainGame.objectives.render(stage.getBatch(), MainGame.viewSize.x/2, MainGame.viewSize.y/2);
        stage.getBatch().end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public TextButton createButton(String text, TextButtonStyle textButtonStyle, float x, float y){
        TextButton textButton= new TextButton("PLAY",textButtonStyle);
        textButton.setPosition(x, y);
        if(x<0){
            textButton.setX(MainGame.viewSize.x/2-textButton.getWidth()/2);
        }
        if(y<0){
            textButton.setY(MainGame.viewSize.y/2-textButton.getHeight()/2);
        }
        stage.addActor(textButton);
        return textButton;
    }
}
