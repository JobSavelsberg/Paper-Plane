package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jobsavelsberg.paperplane.MainGame;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by s153640 on 7-1-2017.
 */
public class BirdHandler {
    private Sprite sprite = new Sprite(new Texture("birdorigami.png"));

    LinkedBlockingQueue<Bird> birdList = new LinkedBlockingQueue<Bird>();

    private float backBuffer = 400;

    public BirdHandler(){
        birdList.add(new Bird(2000,500,sprite));
    }

    public void render(SpriteBatch batch){
        for(Bird b: birdList){
            b.render(batch);
        }
    }
    public void update(float delta,float x,Plane plane,Terrain terrain){
        for(Bird b: birdList){
            b.update(delta,plane,terrain);
            if(b.position.x<x- MainGame.viewSize.x/2-backBuffer){
                removeBird(b);
            }
        }
    }

    public void removeBird(Bird b){
        birdList.remove(b);
    }
}
