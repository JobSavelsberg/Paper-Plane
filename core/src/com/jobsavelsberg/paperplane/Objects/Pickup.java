package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by s153640 on 2-1-2017.
 */
public class Pickup {
    public int type;
    public Vector2 position = new Vector2(0,0);

    public boolean collected = false;
    public boolean pickingUp = false;
    public float timeElapsed = 0f;
    public float lifeTime;
    private Interpolation interp = Interpolation.pow2In;
    public  float progress = 0;


    public Pickup(int type, float x, float y){
        this.type = type;
        position.set(x,y);

    }

    public void update(float delta,Vector2 plane) {
        if(pickingUp){
            if(timeElapsed>lifeTime){
                collect();
            }else{
                timeElapsed+=delta;
            }
            progress = Math.min(1f, timeElapsed/lifeTime);
            position.lerp(plane,interp.apply(progress));

        }
    }

    public void pickUp(float lifetime) {
        this.lifeTime = lifetime;
        pickingUp = true;
    }
    public void collect(){
        pickingUp = false;
        collected = true;
    }


}
