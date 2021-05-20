package com.example.gwynbleidd.verstkagame.UnitClasses.Humans;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.gwynbleidd.verstkagame.UnitClasses.Unit;


public class Archer extends Unit {


    public Archer(String name, Resources resources, Integer cellWidth, Integer cellHeight, Bitmap image1, Bitmap image2, Bitmap image3, Bitmap groundCellImage, boolean isground, Integer HP, Integer DMG, Integer SHIELD, Integer RANGE,Integer MOVE) {
        super(name, resources, cellWidth, cellHeight, image1,image2,image3,  groundCellImage, isground, HP, DMG, SHIELD,RANGE, MOVE, 150);
    }


    @Override
    public void drawInCell(Canvas canvas, Float x, Float y) {
        Paint p = new Paint();
        if ((modelInCellChangeCounter >= 0) && (modelInCellChangeCounter < 4))
            cellImage = cellImageModel1;
        if ((modelInCellChangeCounter >= 4) && (modelInCellChangeCounter < 8))
            cellImage = cellImageModel2;
        if ((modelInCellChangeCounter >= 8) && (modelInCellChangeCounter < 12))
            cellImage = cellImageModel3;
        if (modelInCellChangeCounter >= 12)
            modelInCellChangeCounter = 0;
        modelInCellChangeCounter++;
        canvas.drawBitmap(cellImage, x, y, p);


    }


    @Override
    public void attack(Integer indexOnClickX, Integer indexOnClickY, Integer indexX, Integer indexY, Unit[][] unitMatrix, boolean ground, Canvas canvas) {
        if (!ground) {
            unitMatrix[indexOnClickX][indexOnClickY].HP -= (this.DMG - this.SHIELD);




            if (this.HP > 0 && unitMatrix[indexOnClickX][indexOnClickY].HP <= 0) {

                unitMatrix[indexOnClickX][indexOnClickY] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage, true);

            }


        }
    }
}
