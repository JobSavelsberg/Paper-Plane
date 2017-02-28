package com.jobsavelsberg.paperplane;

/**
 * Created by s153640 on 21-2-2017.
 */
public class Score {
    private int coins;

    public Score(){
        coins = 0;
    }

    public int getCoins() {return coins;}

    public void collectCoin(){coins++;}
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void reset() {
        setCoins(0);
    }
}
