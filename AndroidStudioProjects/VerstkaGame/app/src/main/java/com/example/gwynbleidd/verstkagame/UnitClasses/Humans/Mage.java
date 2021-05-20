package com.example.gwynbleidd.verstkagame.UnitClasses.Humans;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.gwynbleidd.verstkagame.R;
import com.example.gwynbleidd.verstkagame.UnitClasses.Unit;


public class Mage extends Unit {
    Paint p = new Paint();



    public Mage(String name, Resources resources, Integer cellWidth, Integer cellHeight, Bitmap image,  Bitmap groundCellImage, boolean isground, Integer HP, Integer DMG, Integer SHIELD, Integer RANGE, Integer MOVE) {
        super(name, resources, cellWidth, cellHeight, image,  groundCellImage, isground, HP, DMG, SHIELD, RANGE, MOVE, 100);
    }

    @Override
    public void attack(Integer indexOnClickX, Integer indexOnClickY, Integer indexX, Integer indexY, Unit[][] unitMatrix, boolean ground, Canvas canvas) {

        if (!ground) {
            unitMatrix[indexOnClickX][indexOnClickY].HP -= (this.DMG - this.SHIELD);

            if (this.HP > 0 && unitMatrix[indexOnClickX][indexOnClickY].HP <= 0) {

                unitMatrix[indexOnClickX][indexOnClickY] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);

            }


        }

    }
}
