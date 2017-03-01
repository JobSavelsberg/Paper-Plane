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

        BitmapFont fontBig = Font.big.get();

        scoreDisp = 0;

        viewport = new FitViewport(MainGame.viewSize.x,MainGame.viewSize.y, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(""+scoreDisp,new Label.LabelStyle(fontBig, Color.WHITE));

        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void render (SpriteBatch batch) {
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getBatch().begin();
        MainGame.objectives.render(stage.getBatch(),200,1000);
        stage.getBatch().end();
        stage.draw();
    }


    public void update(float delta) {
        this.scoreDisp = MainGame.score.getCoins();
        scoreLabel.setText(""+scoreDisp);
        MainGame.objectives.update();
    }
}
