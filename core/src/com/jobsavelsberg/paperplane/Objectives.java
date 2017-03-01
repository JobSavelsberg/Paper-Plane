package com.jobsavelsberg.paperplane;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


/**
 * Created by s153640 on 1-3-2017.
 */
public class Objectives {
    public Objective[] objectives = new Objective[3];

    private BitmapFont font;

    public Objectives(){
        font = Font.small.get();

        objectives[0] = new Objective("coins", 20);
        objectives[1] = new Objective("coins", 50);
        objectives[2] = new Objective("coins", 100);
    }

    public void render(Batch batch, float x, float y) {
        for(int i = 0; i < objectives.length; i++){
            font.draw(batch,objectives[i].text,x-120,y-70-70*i);
        }
    }

    public void update() {
        for(int i = 0; i < objectives.length; i++){
            Objective o = objectives[i];
            if(o.type == "coins"){
                if(MainGame.score.getCoins() >= o.amount){
                    o.clear();
                }
            }
        }
    }

    public void regenerate() {
        for(int i = 0; i < objectives.length; i++){
            Objective o = objectives[i];
            if(o.isCleared){
                o.generate("coins",o.amount+10,o.amount*2);
            }
        }
    }


    public class Objective{
        private String text;
        private String type;
        private int amount;
        private boolean isCleared = false;

        public Objective(String type, int amount){
            this.type = type;
            this.amount = amount;
            this.text = "Collect " + amount + " " + type;
        }

        public void clear(){
            isCleared = true;
            text = "Collected " + amount + " " + type +"!";
        }

        public void generate(String type, int min, int max) {
            this.type = type;
            this.amount = 5*(Math.round((float)(Math.random()*(max-min)+min)/5));
            this.text = "Collect " + amount + " " + type;
        }
    }
}

