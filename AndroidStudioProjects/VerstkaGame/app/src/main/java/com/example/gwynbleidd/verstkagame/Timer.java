package com.example.gwynbleidd.verstkagame;

import android.os.CountDownTimer;
import android.view.View;



public class Timer extends CountDownTimer {
    View view;

    public Timer(View view, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.view = view;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.view.invalidate();

    }

    @Override
    public void onFinish() {

    }
}
