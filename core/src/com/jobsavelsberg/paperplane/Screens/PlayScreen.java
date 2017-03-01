package com.jobsavelsberg.paperplane.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jobsavelsberg.paperplane.*;
import com.jobsavelsberg.paperplane.Objects.*;

/**
 * Created by s153640 on 27-12-2016.
 */
public class PlayScreen implements Screen{
    public boolean isFrozen;

    //Objects
    private MyCamera camera;
    public Background background;
    public Terrain terrain;
    private World world;
    private Plane plane;
    private PickupHandler pickups;
    private BirdHandler birds;
    private BoostHandler boosts;


    //Graphics
    private SpriteBatch batch;
    private PolygonSpriteBatch polygonBatch;
    public MainGame game;
    private ShapeRenderer shapeRenderer;
    private HUD hud;

    public PlayScreen(MainGame game){
        this.game = game;
        init();
    }

    //TODO: clean up init() to work with restart,
    // so only objects contributing to state in init (also holds for other classes)
    public void init(){
        Gdx.input.setInputProcessor(game);

        batch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();
        shapeRenderer = new ShapeRenderer();
        hud = new HUD(batch);
        MainGame.score.reset();

        camera = new MyCamera(game.viewSize);
        game.viewport = new FitViewport(game.viewSize.x,game.viewSize.y,camera);

        world = new World(new Vector2(0, Constants.GRAVITY), true);
        if(background==null){
            background = background.fireWatch;
        }
        terrain = new Terrain();
        plane = new Plane(this, game.viewSize.x/2, game.viewSize.y*0.8f,800f,0f);
        pickups = new PickupHandler();
        birds = new BirdHandler();
        boosts = new BoostHandler();

        camera.follow(plane, game.viewSize.x/3);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game);
    }

    @Override
    public void render(float delta) {
        if(!isFrozen){
            update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        background.draw(batch);
        //batch.draw(background.getTexture(),0,0,MainGame.viewSize.x,MainGame.viewSize.y);
        batch.end();

        polygonBatch.setProjectionMatrix(camera.combined);
        polygonBatch.begin();
        terrain.render(polygonBatch);
        polygonBatch.end();


        batch.begin();

        pickups.render(batch);
        boosts.render(batch);

        plane.render(batch);
        birds.render(batch);


        batch.end();
        hud.render(batch);  //changes projection matrix




        /*shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(plane.getPosition().x,terrain.getTerrainHeight(plane.getPosition().x),10);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(plane.sprite.getX(),plane.sprite.getY(),12);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle(plane.getPosition().x,plane.getPosition().y,14);

        shapeRenderer.setColor(0.9f,0.2f,0.7f,0.5f);
        for(Pickup p: pickups.pickupList){
            shapeRenderer.circle(p.position.x,p.position.y,40);
        }

        shapeRenderer.end();*/
    }

    private void update(float delta) {
        camera.update(terrain.getTerrainHeight(plane.getPosition().x),delta);
        background.update(camera.position.x,camera.position.y);
        terrain.update(delta,camera.position.x);
        plane.update(delta);
        pickups.update(delta,camera.position.x,terrain,plane);
        boosts.update(delta,camera.position.x,plane,terrain);

        birds.update(delta,camera.position.x,plane,terrain);
        hud.update(delta);
    }

    public void restart(){
        dispose();
        MainGame.objectives.regenerate();
        init();
    }
    @Override
    public void resize(int width, int height) {
        game.resize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(game);

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //background.dispose();
        plane.dispose();
        batch.dispose();
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < MainGame.windowSize.x/2){
            plane.dive(true);
            plane.lift(false);
        }else{
            plane.lift(true);
            plane.dive(false);
        }

        return false;
    }
    public boolean touchUp() {
        plane.lift(false);
        plane.dive(false);
        return false;
    }
}
