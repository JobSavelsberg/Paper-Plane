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
import com.jobsavelsberg.paperplane.Objects.BirdHandler;
import com.jobsavelsberg.paperplane.Objects.PickupHandler;
import com.jobsavelsberg.paperplane.Objects.Plane;
import com.jobsavelsberg.paperplane.Objects.Terrain;

/**
 * Created by s153640 on 27-12-2016.
 */
public class PlayScreen implements Screen{
    //Objects
    private MyCamera camera;
    private Background background;
    public Terrain terrain;
    private World world;
    private Plane plane;
    private PickupHandler pickups;
    private BirdHandler birds;
    private Score score = new Score();

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
    public void init(){
        batch = new SpriteBatch();
        polygonBatch = new PolygonSpriteBatch();
        shapeRenderer = new ShapeRenderer();
        hud = new HUD(batch);

        camera = new MyCamera(game.viewSize);
        game.viewport = new FitViewport(game.viewSize.x,game.viewSize.y,camera);

        world = new World(new Vector2(0, Constants.GRAVITY), true);
        background = new Background("firewatchnight.png");
        terrain = new Terrain();
        plane = new Plane(this, game.viewSize.x/2, game.viewSize.y*0.8f,800f,0f);
        pickups = new PickupHandler(score);
        birds = new BirdHandler();

        camera.follow(plane, game.viewSize.x/3);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        background.draw(batch);

        batch.end();

        polygonBatch.setProjectionMatrix(camera.combined);
        polygonBatch.begin();
        terrain.render(polygonBatch);
        polygonBatch.end();


        batch.begin();

        pickups.render(batch);
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
        birds.update(delta,camera.position.x,plane,terrain);
        hud.update(delta,score);
    }

    public void restart(){
        dispose();
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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        plane.dispose();
        batch.dispose();
    }
    public boolean touchDown() {
        plane.lift(true);
        return false;
    }
    public boolean touchUp() {
        plane.lift(false);
        return false;
    }
}
