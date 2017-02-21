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
        addToList(2000,500);
    }

    public void render(SpriteBatch batch){
        for(Boost b: boostList){
            b.render(batch);
        }
    }
    public void update(float delta,float x,Plane plane,Terrain terrain){
        /*while(last.position.x<x+MainGame.viewSize.x/2){
            float random = x+ MainGame.viewSize.x/2+1000+(float)Math.random()*600;
            addToList(random,terrain.getTerrainHeight(random)+600+(float)Math.random()*250);
            System.out.println(random);
        }
        try{
            while(boostList.peek().position.x+ MainGame.viewSize.x/2+backBuffer<x){
                boostList.remove();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        for(Boost b: boostList){
            b.update(delta,plane,terrain);
            if(b.position.x<x- MainGame.viewSize.x/2-backBuffer){
                removeBoost(b);
            }
        }
        if(boostList.isEmpty()){
            addToList(x+1000,terrain.getTerrainHeight(x+1000)+150);

        }*/
    }

    public void removeBoost(Boost b){
        boostList.remove(b);
    }
    public void addToList(float x, float y){
        last = new Boost(x,y);
        boostList.add(last);
    }

}
