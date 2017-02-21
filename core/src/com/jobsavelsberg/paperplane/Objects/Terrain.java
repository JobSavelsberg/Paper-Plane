package com.jobsavelsberg.paperplane.Objects;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.jobsavelsberg.paperplane.MainGame;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by s153640 on 27-12-2016.
 */
public class Terrain {
    TextureRegion color;
    TextureRegion colorShade;
    short[] triangles = new short[]{0, 1, 2,1, 2, 3};

    LinkedBlockingQueue<PolygonRegion> polyList = new LinkedBlockingQueue<PolygonRegion>();


    private float distance = 400;
    private float heightDif = 400;
    private float backBuffer = 800;
    private float frontBuffer = 800;
    private float depth = 800;
    private PolygonRegion lastPoly;

    public Terrain(){
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xFFFFFFFF); // DE is red, AD is green and BE is blue.
        pix.fill();
        color = new TextureRegion(new Texture(pix));
        pix.setColor(0xBBBBBBFF); // DE is red, AD is green and BE is blue.
        pix.fill();
        colorShade = new TextureRegion(new Texture(pix));

        float[] vertices = {
                0,300,
                100,300,
                0,0,
                100,0
        };

        PolygonRegion polyReg = new PolygonRegion(color, vertices, triangles);
        addPolyToList(polyReg);
    }


    public void render(PolygonSpriteBatch polyBatch) {
        for(PolygonRegion p: polyList){
            polyBatch.draw(p,0,0);
        }
    }

    public void update(float delta, float x) {
            try{
                while(polyList.peek().getVertices()[2]+ MainGame.viewSize.x/2+backBuffer<x){
                    polyList.remove();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            while(lastPoly.getVertices()[2]<x+MainGame.viewSize.x/2+frontBuffer){
                generatePoly();
            }


    }

    public void generatePoly(){
        float[] last = lastPoly.getVertices();
        float lastx = last[2];
        float lasty = last[3];
        float newy  =(float) (lasty-60+Math.random()*heightDif-heightDif/2);
        float bottomy = Math.min(newy,lasty)-depth*1.5f;
        float[] vertices = {
                lastx,                  lasty ,                                         //topleft
                lastx+distance,         newy,    //topright
                lastx,                  bottomy,                                              //bottomleft
                lastx+distance,         bottomy                                               //bottomright
        };
        //lasty-newy>=0?color:colorShade
        PolygonRegion newpoly = new PolygonRegion(color, vertices, triangles);



        addPolyToList(newpoly);
    }

    private void addPolyToList(PolygonRegion newpoly) {
        lastPoly = newpoly;
        polyList.add(newpoly);

    }

    public boolean isUnderGround(Vector2 position){
        return isUnderGround(position.x,position.y);
    }
    public boolean isUnderGround(float x, float y){
        if(y<=getTerrainHeight(x)){
            return true;
        }
        return false;
    }

    public float getTerrainHeight(float x){
        for(PolygonRegion p: polyList){
            if(p.getVertices()[0]<=x && p.getVertices()[2]>=x){                  //is the x coordinate in the range as the polygon
                float xleft = p.getVertices()[0];
                float xright = p.getVertices()[2];
                float yleft = p.getVertices()[1];
                float yright = p.getVertices()[3];
                return ((x-xleft)/(xleft-xright))*(yleft-yright)+yleft;         //formula to calculate if y is below the line at point x

            }
        }
        return 0;
    }

}
