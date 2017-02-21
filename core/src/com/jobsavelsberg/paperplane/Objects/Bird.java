package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by s153640 on 2-1-2017.
 */

public class Bird {
    private Sprite sprite;

    public Vector2 position;
    public Vector2 velocity;
    public boolean following;


    public Bird(float x, float y,Sprite sprite){
        this.position = new Vector2(x,y);
        this.velocity = new Vector2(0,0);
        this.sprite = sprite;
        sprite.setScale(0.1f,0.1f);
        following=false;
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void update(float delta, Plane plane,Terrain terrain){
        if(terrain.getTerrainHeight(position.x)>position.y-20){
            following=false;
        }else if(!following){
            velocity.add(0, -2*delta);
            position.add(velocity);
        }else if(following){
            position.lerp(plane.getPosition(),0.1f);
        }
        sprite.setPosition(position.x-sprite.getWidth()/2,position.y-sprite.getHeight()/2);

    }
}
