package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by s153640 on 2-1-2017.
 */

public class Bird {
    private Sprite sprite;

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 offset;
    private Vector2 gravity = new Vector2(0,-20f);
    public boolean following;
    float animation = 0;
    private float wakeRadius = 200;
    float floatingOffsetY = 0;
    float floatingOffsetX = 0;


    public Bird(float x, float y,Sprite sprite){
        this.position = new Vector2(x,y);
        this.velocity = new Vector2(0,0);
        this.offset = new Vector2(5f,5f);
        this.sprite = sprite;
        following=false;
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void update(float delta, Plane plane, Terrain terrain, float orderOffset){
        animation+=delta;
        if(animation>=Float.MAX_VALUE){
            animation=0;
        }

        if(!following){
            velocity.add(10*delta, -6*delta);
            position.add(velocity);
            if(terrain.getTerrainHeight(position.x)>position.y-20){
                position.set(position.x,terrain.getTerrainHeight(position.x));
                velocity.set(0,0);
            }
            if(position.cpy().sub(plane.getPosition()).len()<wakeRadius){
                following = true;
            }
        }
        

        if(following){
            Vector2 target = plane.getPosition().cpy();
            float angle = target.cpy().sub(position).angle();

            position.lerp(target.cpy().sub(offset.cpy().scl(orderOffset*10)),4*delta);
            position.lerp(position.cpy().add(gravity),2*delta);
            sprite.setRotation(angle-20);

            floatingOffsetY = 50*(float)Math.sin(animation*2+orderOffset*30);
            floatingOffsetX = 30*(float)Math.cos(animation*3+orderOffset*10);
        }



        sprite.setPosition(position.x-sprite.getWidth()/2+floatingOffsetX,position.y-sprite.getHeight()/2+floatingOffsetY);

    }
}
