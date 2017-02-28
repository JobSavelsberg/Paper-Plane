package com.jobsavelsberg.paperplane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jobsavelsberg.paperplane.Screens.PlayScreen;

/**
 * Created by s153640 on 26-12-2016.
 */
public enum Background {
    fireWatch("firewatch.png");

    Texture img;
    float imgWidth;
    private Vector2 position;
    private float speed = 0.1f;


    Background(String path){
        position = new Vector2(0,0);
        img = new Texture(path);
        imgWidth = img.getWidth();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(img,position.x,position.y,MainGame.viewSize.x,MainGame.viewSize.y);
        batch.draw(img,position.x+MainGame.viewSize.x,position.y,MainGame.viewSize.x,MainGame.viewSize.y);

    }

    public void dispose() {
        img.dispose();
    }

    public void update(float cameraX, float cameraY) {
        position.x=imgWidth*(float)Math.floor((cameraX-imgWidth/2)/imgWidth);
        position.x=cameraX- MainGame.viewSize.x/2;
        position.y=cameraY- MainGame.viewSize.y/2;
    }

    public Texture getTexture(){
        return img;
    }
}
