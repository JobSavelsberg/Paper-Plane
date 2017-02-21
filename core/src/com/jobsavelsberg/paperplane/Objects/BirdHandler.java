package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Color;
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
        sprite.setScale(0.11f,0.11f);
        birdList.add(new Bird(4000,500,new Sprite(sprite)));
        birdList.add(new Bird(5000,500,new Sprite(sprite)));
        birdList.add(new Bird(6000,500,new Sprite(sprite)));
        birdList.add(new Bird(6400,1000,new Sprite(sprite)));
        birdList.add(new Bird(8400,1000,new Sprite(sprite)));
        birdList.add(new Bird(10800,1000,new Sprite(sprite)));
        birdList.add(new Bird(12800,1000,new Sprite(sprite)));
        birdList.add(new Bird(14800,1000,new Sprite(sprite)));



    }

    public void render(SpriteBatch batch){
        for(Bird b: birdList){
            b.render(batch);
        }
    }
    public void update(float delta,float x,Plane plane,Terrain terrain){
        int i = 0;
        for(Bird b: birdList){
            b.update(delta,plane,terrain,i);
            if(b.position.x<x- MainGame.viewSize.x/2-backBuffer){
                removeBird(b);
            }
            i++;
        }
    }

    public void removeBird(Bird b){
        birdList.remove(b);
    }
}
