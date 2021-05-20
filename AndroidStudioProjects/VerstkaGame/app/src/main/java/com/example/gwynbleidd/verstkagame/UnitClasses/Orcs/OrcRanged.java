package com.example.gwynbleidd.verstkagame.UnitClasses.Orcs;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.gwynbleidd.verstkagame.UnitClasses.Unit;


public class OrcRanged extends Unit {
    public OrcRanged(String name, Resources resources, Integer cellWidth, Integer cellHeight, Bitmap image, Bitmap groundCellImage, boolean isground, Integer HP, Integer DMG, Integer SHIELD, Integer RANGE, Integer MOVE, Integer COST) {
        super(name, resources, cellWidth, cellHeight, image, groundCellImage, isground, HP, DMG, SHIELD, RANGE, MOVE, COST);
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
