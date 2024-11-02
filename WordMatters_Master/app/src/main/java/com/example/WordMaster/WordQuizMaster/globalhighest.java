package com.example.WordMaster.WordQuizMaster;

import android.app.Application;

public class globalhighest extends Application {
    private int highestmarks, currenthighestmarks;


    //set marks
    public void setHighestmarks(int highestmarks ){
        this.highestmarks = highestmarks;
    }

    public void setCurrentHighestmarks(int currentHighestmarks ){
        this.currenthighestmarks = currenthighestmarks;
    }

    //get marks
    public int getHighestmarks(){
        return highestmarks;
    }

    public int getCurrentHighestmarks(){
        return currenthighestmarks;
    }
}