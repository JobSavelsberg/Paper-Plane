package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jobsavelsberg.paperplane.MainGame;
import com.jobsavelsberg.paperplane.Score;
import sun.applet.Main;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by s153640 on 28-12-2016.
 */
public class PickupHandler {
    private Vector2 position;
    private float bounds = 50;
    private float backBuffer = 400;

    private Sprite sprite;
    private Sprite coinSprite;


    public LinkedBlockingQueue<Pickup> pickupList = new LinkedBlockingQueue<Pickup>();
    private Pickup last;

    public boolean magnet;
    private float magnetRadius = 200;



    public PickupHandler(){
        coinSprite = new Sprite(new Texture("coin.png"));
        coinSprite.setScale(0.3f,0.3f);
        sprite = coinSprite; //default


        addToList(0,1000,500);

        magnet = false;

    }

    public void render(SpriteBatch batch){
        for(Pickup p: pickupList){

            switch(p.type){
                case 0:sprite=coinSprite;
            }
            sprite.setPosition(p.position.x-sprite.getWidth()/2,p.position.y-sprite.getHeight()/2);
            sprite.setScale((1-p.progress/2)*0.3f);
            sprite.draw(batch);
        }

    }

    public void update(float delta,float x,Terrain terrain,Plane plane){
        while(last.position.x<x+MainGame.viewSize.x/2){
            float random = x+ MainGame.viewSize.x/2+100+(float)Math.random()*600;
            addToList(0,random,terrain.getTerrainHeight(random)+100+(float)Math.random()*250);
        }
        try{
            while(pickupList.peek().position.x+ MainGame.viewSize.x/2+backBuffer<x){
                pickupList.remove();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for(Pickup p: pickupList) {
            if(magnet){
                if(plane.getPosition().x +magnetRadius> p.position.x-bounds && plane.getPosition().x -magnetRadius< p.position.x+bounds) {
                    if (plane.getPosition().y +magnetRadius> p.position.y - bounds && plane.getPosition().y -magnetRadius< p.position.y + bounds) {
                        pickUp(p,1.0f);
                    }

                }
            }else{
                if(plane.getPosition().x > p.position.x-bounds && plane.getPosition().x< p.position.x+bounds) {
                    if (plane.getPosition().y > p.position.y - bounds && plane.getPosition().y < p.position.y + bounds) {
                        pickUp(p,0.1f);
                    }

                }
            }
            p.update(delta,plane.getPosition());
            if(p.collected){
                collect(p);
            }

        }
        if(pickupList.isEmpty()){
            addToList(0,x+1000,terrain.getTerrainHeight(x+1000)+150);

        }

    }
    public void pickUp(Pickup p,float lifeTime){
        p.pickUp(lifeTime);
    }
    public void collect(Pickup p){
        p.collect();
        MainGame.score.collectCoin();
        pickupList.remove(p);
    }

    public void addToList(int type, float x, float y){
        last = new Pickup(type,x,y);
        pickupList.add(last);
    }
    public void removeFromList(){
        pickupList.remove();
    }
}
