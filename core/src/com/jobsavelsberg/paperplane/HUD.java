package com.jobsavelsberg.paperplane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by s153640 on 18-2-2017.
 */
public class HUD {
    public Stage stage;
    private Viewport viewport;

    private int scoreDisp;

    Label scoreLabel;

    public HUD(SpriteBatch batch){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BebasNeue.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        BitmapFont fontBig = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


        scoreDisp = 0;

        viewport = new FitViewport(MainGame.viewSize.x,MainGame.viewSize.y, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label("score: "+scoreDisp,new Label.LabelStyle(fontBig, Color.WHITE));

        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void render (SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }


    public void update(float delta, Score score) {
        this.scoreDisp = score.getCoins();
        scoreLabel.setText("score: "+scoreDisp);
    }
}
