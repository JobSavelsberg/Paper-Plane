package com.jobsavelsberg.paperplane;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jobsavelsberg.paperplane.Objects.Plane;
import com.jobsavelsberg.paperplane.Screens.PlayScreen;

/**
 * Created by s153640 on 26-12-2016.
 */
public class MyCamera extends OrthographicCamera {
    private Plane followPlane;
    private Vector2 target = new Vector2(0,0);
    private float smoothness = 5f;
    private boolean hasBottomBound;
    private float bottomBound = 0;
    private float horizontalOffset = 0;

    private float minZoom = 1f;
    private float maxZoom = 1.5f;

    public MyCamera(Vector2 viewSize){
        super(viewSize.x,viewSize.y);
    }
    public void update(float bottom, float deltaTime){
        if(followPlane!=null){
            target.set(followPlane.getPosition().x+horizontalOffset,followPlane.getPosition().y);
            if(hasBottomBound){
                target.y = Math.max(target.y,bottomBound);
            }
            translate((target.x-position.x)*deltaTime*smoothness,(target.y-position.y)*deltaTime*smoothness);

            this.zoom = MathUtils.lerp(zoom,MathUtils.clamp(0.6f+(followPlane.getPosition().y-bottom)/1000f,minZoom,maxZoom),0.03f);
        }

        super.update();
        MainGame.viewSize.x = viewportWidth*zoom;
        MainGame.viewSize.y = viewportHeight*zoom;
    }
    public void follow(Plane plane,float horizontalOffset) {
        this.followPlane = plane;
        position.set(plane.getPosition(),0);
        this.hasBottomBound = false;
        this.horizontalOffset = horizontalOffset;
    }
    public void follow(Plane plane,float horizontalOffset,float bottomBound) {
        this.followPlane = plane;
        position.set(plane.getPosition(),0);
        this.hasBottomBound =true;
        this.bottomBound = bottomBound;
        this.horizontalOffset = horizontalOffset;
    }
}
