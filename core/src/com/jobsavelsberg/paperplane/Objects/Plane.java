package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.jobsavelsberg.paperplane.Constants;
import com.jobsavelsberg.paperplane.Screens.PlayScreen;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Job Savelsberg on 25-12-2016.
 */
public class Plane {
    private PlayScreen screen;

    private Vector2 position;
    private Vector2 velocity;

    private Vector2 scale;
    private Texture texture;
    public Sprite sprite;

    private boolean lifting;
    private boolean diving;
    private float drag = 0.999f;
    private float maxTurnSpeed = 20f;
    private float stallSpeed = 300f;

    private boolean active;
    private boolean boosting;


    LinkedBlockingQueue<Vector2> prevPositions = new LinkedBlockingQueue<Vector2>(120);

    /** Constructor for the Plane object
     * @param screen    The screen/scene on which the plane is put
     * @param x         The starting position x in world space
     * @param y         The starting position y in world space
     * @param velX      The starting velocity in x direction
     * @param velY      The starting velocity in y direction
     *
     */
    public Plane(PlayScreen screen, float x, float y, float velX, float velY){
        this.screen = screen;

        active = true;

        this.position = new Vector2(x,y);
        this.velocity = new Vector2(velX,velY);

        this.scale = new Vector2(0.5f,0.5f);

        this.texture = new Texture("plane.png");
        this.sprite = new Sprite(texture);
        sprite.setOriginCenter();
        //sprite.setCenter(sprite.getWidth()/2,sprite.getHeight()/2);

        sprite.setScale(scale.x,scale.y);

    }

    /**
     *
     * @param deltaTime  Time between updates, used for precise physics simulation
     *
     * @modifies only if the plane is active it modifies the position, velocity and rotation according
     *           to specified physics laws
     */
    public void update(float deltaTime){
        if(active) {
            if (lifting || velocity.x<-200f) {
                velocity.rotate(2.5f);
            }else if(diving){
                velocity.rotate(-1.5f);
            }

            velocity.add(0, Constants.GRAVITY * deltaTime + liftPhysics());
            velocity.scl(drag);
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);
            sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2 );
            sprite.setRotation(velocity.angle());

            if(screen.terrain.isUnderGround(position.x,position.y)){
                crash();
            }
        }

    }

    private void crash() {
        active=false;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                screen.restart();
            }
        }, 2);
    }

    private float liftPhysics() {
        return velocity.x*0.003f;
    }

    public void render(Batch batch){
        //sprite.setCenter(0,0);
        sprite.draw(batch);

    }

    public void dispose(){
        texture.dispose();
    }

    public void lift(boolean lift) {lifting=lift;
    }
    public void dive(boolean dive) { diving=dive;}

    public void boost(){
        if(velocity.len()<1200f){
            boosting=false;
        }
        if(!boosting){
            velocity.scl(1.5f);
            //velocity.nor().scl(1200f);
            boosting=true;
        }
    }

    public Vector2 getPosition() {
        return position;
    }


}
