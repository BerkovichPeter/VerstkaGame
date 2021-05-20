package com.example.gwynbleidd.verstkagame.UnitClasses;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.gwynbleidd.verstkagame.R;

public class Unit {
    public String name;
    public Bitmap cellImage;
    public Bitmap outImage;
    public boolean ground;
    public Integer HP;
    public Integer MAXHP;
    public Integer getHP() {
        return HP;
    }

    public void setHP(Integer HP) {
        this.HP = HP;
    }

    public Integer getDMG() {
        return DMG;
    }

    public void setDMG(Integer DMG) {
        this.DMG = DMG;
    }

    public Integer getSHIELD() {
        return SHIELD;
    }

    public void setSHIELD(Integer SHIELD) {
        this.SHIELD = SHIELD;
    }

    public Bitmap groundCellImage;
    public Integer DMG;
    public Integer SHIELD;
    public Integer MOVE;
    public Integer RANGE;
    public Resources resources;
    public Integer cellWidth;
    public Integer cellHeight;
    public Boolean Owner;
    public Integer VALUABLEMOVE;
    public Integer COST;
    public Boolean canAttack;

    public Integer modelInCellChangeCounter = 0;

    public Bitmap cellImageModel1;
    public Bitmap cellImageModel2;
    public Bitmap cellImageModel3;


    public Unit(String name, Resources resources, Integer cellWidth, Integer cellHeight, Bitmap image, Bitmap groundCellImage, boolean isground, Integer HP, Integer DMG, Integer SHIELD, Integer RANGE, Integer MOVE, Integer COST) {
        this.name = name;
        outImage = Bitmap.createScaledBitmap(image, cellWidth * 2, cellHeight * 2, false);
        cellImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
        this.ground = isground;
        this.MAXHP = HP;
        this.HP = MAXHP;
        this.DMG = DMG;
        this.MOVE = MOVE;
        this.SHIELD = SHIELD;
        this.RANGE = RANGE;
        this.resources = resources;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.VALUABLEMOVE = MOVE;
        this.COST = COST;
        this.groundCellImage = groundCellImage;
        this.canAttack = true;

    }

    public Unit(String name, Resources resources, Integer cellWidth, Integer cellHeight, Bitmap image, Bitmap image2, Bitmap image3, Bitmap groundCellImage, boolean isground, Integer HP, Integer DMG, Integer SHIELD, Integer RANGE, Integer MOVE, Integer COST) {
        this.name = name;
        cellImageModel1 = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
        outImage = Bitmap.createScaledBitmap(image, cellWidth * 2, cellHeight * 2, false);
        cellImage = cellImageModel1;
        this.ground = isground;
        this.MAXHP = HP;
        this.HP = MAXHP;
        this.DMG = DMG;
        this.MOVE = MOVE;
        this.SHIELD = SHIELD;
        this.RANGE = RANGE;
        this.resources = resources;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.VALUABLEMOVE = MOVE;
        this.COST = COST;
        this.groundCellImage = groundCellImage;
        this.canAttack = true;
        cellImageModel2 = Bitmap.createScaledBitmap(image2, cellWidth, cellHeight, false);
        cellImageModel3 = Bitmap.createScaledBitmap(image3, cellWidth, cellHeight, false);

    }


    public Unit(String name, Resources resources, Integer cellWidth, Integer cellHeight, Bitmap cellImage, boolean isground) {
        this.name = name;
        this.cellImage = cellImage;
        this.ground = true;
    }

    public Resources getResources() {
        return resources;
    }

    public void drawInCell(Canvas canvas, Float x, Float y) {
        Paint p = new Paint();
        canvas.drawBitmap(cellImage, x, y, p);
        if (modelInCellChangeCounter >= 12)
            modelInCellChangeCounter = 0;

    }

    public void drawOutCell(Canvas canvas, Integer x, Integer y) {
        Paint p = new Paint();
        canvas.drawBitmap(outImage, x, y, p);

    }

    public boolean isGround() {
        return ground;
    }

    public void move(Integer indexOnClickX, Integer indexOnClickY, Integer indexX, Integer indexY, Unit[][] unitMatrix, boolean ground) {
        if (ground) {
            Unit tmp = unitMatrix[indexOnClickX][indexOnClickY];
            unitMatrix[indexOnClickX][indexOnClickY] = this;
            unitMatrix[indexX][indexY] = tmp;


        }

    }
    private void die(){



    }





    public void attack(Integer indexOnClickX, Integer indexOnClickY, Integer indexX, Integer indexY, Unit[][] unitMatrix, boolean ground, Canvas canvas) {

        Bitmap tmpImage = BitmapFactory.decodeResource(resources, R.drawable.ground);
        if (!ground) {
            unitMatrix[indexOnClickX][indexOnClickY].HP -= (this.DMG - this.SHIELD);
            this.HP -= (unitMatrix[indexOnClickX][indexOnClickY].DMG - unitMatrix[indexOnClickX][indexOnClickY].SHIELD);


            if (this.HP <= 0 && unitMatrix[indexOnClickX][indexOnClickY].HP > 0) {
                if (unitMatrix[indexX][indexY].cellImage != null)
                    unitMatrix[indexX][indexY].cellImage.recycle();
                if (unitMatrix[indexX][indexY].cellImageModel1 != null)
                    unitMatrix[indexX][indexY].cellImageModel1.recycle();
                if (unitMatrix[indexX][indexY].cellImageModel2 != null)
                    unitMatrix[indexX][indexY].cellImageModel2.recycle();
                if (unitMatrix[indexX][indexY].cellImageModel3 != null)
                    unitMatrix[indexX][indexY].cellImageModel3.recycle();
                unitMatrix[indexX][indexY]  = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);
            }

            if (this.HP > 0 && unitMatrix[indexOnClickX][indexOnClickY].HP <= 0) {


                if (unitMatrix[indexOnClickX][indexOnClickY].cellImage != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImage.recycle();
                if (unitMatrix[indexOnClickX][indexOnClickY].cellImageModel1 != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImageModel1.recycle();
                if (unitMatrix[indexOnClickX][indexOnClickY].cellImageModel2 != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImageModel2.recycle();
                if (unitMatrix[indexOnClickX][indexOnClickY].cellImageModel3 != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImageModel3.recycle();
                unitMatrix[indexOnClickX][indexOnClickY] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);

                this.move(indexOnClickX,indexOnClickY, indexX, indexY,unitMatrix,unitMatrix[indexOnClickX][indexOnClickY].isGround());
            }

            if (this.HP <= 0 && unitMatrix[indexOnClickX][indexOnClickY].HP <= 0) {
                if (unitMatrix[indexOnClickX][indexOnClickY].cellImage != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImage.recycle();
                if (unitMatrix[indexOnClickX][indexOnClickY].cellImageModel1 != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImageModel1.recycle();
                if (unitMatrix[indexOnClickX][indexOnClickY].cellImageModel2 != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImageModel2.recycle();
                if (unitMatrix[indexOnClickX][indexOnClickY].cellImageModel3 != null)
                    unitMatrix[indexOnClickX][indexOnClickY].cellImageModel3.recycle();
                unitMatrix[indexOnClickX][indexOnClickY] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);


                if (unitMatrix[indexX][indexY].cellImage != null)
                    unitMatrix[indexX][indexY].cellImage.recycle();
                if (unitMatrix[indexX][indexY].cellImageModel1 != null)
                    unitMatrix[indexX][indexY].cellImageModel1.recycle();
                if (unitMatrix[indexX][indexY].cellImageModel2 != null)
                    unitMatrix[indexX][indexY].cellImageModel2.recycle();
                if (unitMatrix[indexX][indexY].cellImageModel3 != null)
                    unitMatrix[indexX][indexY].cellImageModel3.recycle();
                unitMatrix[indexX][indexY] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);

                this.cellImage = Bitmap.createScaledBitmap(tmpImage, cellWidth, cellHeight, false);
            }


        }





    }



}