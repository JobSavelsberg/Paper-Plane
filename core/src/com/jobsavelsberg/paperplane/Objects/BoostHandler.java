package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jobsavelsberg.paperplane.MainGame;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by s153640 on 21-2-2017.
 */
public class BoostHandler {

    LinkedBlockingQueue<Boost> boostList = new LinkedBlockingQueue<Boost>();

    private float backBuffer = 900;
    private Boost last = null;

    public BoostHandler(){
        addToList(3000,700);
        addToList(7600,500);
        addToList(15800,400);

    }

    public void render(SpriteBatch batch){
        for(Boost b: boostList){
            b.render(batch);
        }
    }
    public void update(float delta,float x,Plane plane,Terrain terrain){
        for(Boost b: boostList){
            b.update(delta,plane,terrain);
            if(b.position.x<x- MainGame.viewSize.x/2-backBuffer){
                removeBoost(b);
            }
        }
    }

    public void removeBoost(Boost b){
        boostList.remove(b);
    }
    public void addToList(float x, float y){
        last = new Boost(x,y);
        boostList.add(last);
    }

}
