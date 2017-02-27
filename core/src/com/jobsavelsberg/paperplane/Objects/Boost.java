package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by s153640 on 21-2-2017.
 */
public class Boost {
    private Sprite boostSprite = new Sprite(new Texture("boost.png"));

    public Vector2 position = new Vector2();

    private float bounds = 100;

    //public Boost(int x, int y) {this.Boost(x,y);}
    public Boost(float x, float y){
        this.position.set(x,y);
        //boostSprite.setColor(Color.BLACK);
        boostSprite.setScale(0.5f,0.5f);
        boostSprite.setRotation(10f);
    }

    public void update(float delta, Plane plane, Terrain terrain){
        this.position.y=terrain.getTerrainHeight(position.x)+boostSprite.getHeight()/2;
        boostSprite.setPosition(position.x-boostSprite.getWidth()/2,terrain.getTerrainHeight(boostSprite.getX()));

        if(plane.getPosition().x > position.x-bounds && plane.getPosition().x< position.x+bounds) {
            if (plane.getPosition().y > position.y - bounds && plane.getPosition().y < position.y + bounds) {
                boost(plane);
            }

        }
    }

    private void boost(Plane plane) {
        plane.boost();
    }

    public void render(SpriteBatch batch){
        boostSprite.draw(batch);
    }
}
