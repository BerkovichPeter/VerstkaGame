package com.example.gwynbleidd.verstkagame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;



public class OurButton {
    public Bitmap btnImageNonePressed;
    public Bitmap btnImagePressed;
    public Boolean isPressed;


    public OurButton(Bitmap imageNonepressed, Bitmap imagePressed, Resources resources, Integer btnWidth, Integer btnHeight) {
        this.isPressed = false;
        btnImageNonePressed = Bitmap.createScaledBitmap(imageNonepressed, btnWidth, btnHeight, false);
        btnImagePressed = Bitmap.createScaledBitmap(imagePressed, btnWidth, btnHeight, false);


    }


    public void draw(Canvas canvas, Float x, Float y) {
        Paint p = new Paint();
        if (!isPressed)
            canvas.drawBitmap(btnImageNonePressed, x , y, p);
        else
            canvas.drawBitmap(btnImagePressed, x , y, p);
    }


}
