package com.example.gwynbleidd.verstkagame.UnitClasses.Humans;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.example.gwynbleidd.verstkagame.UnitClasses.Unit;



public class Horseman extends Unit {
    public Horseman(String name, Resources resources, Integer cellWidth, Integer cellHeight, Bitmap image, Bitmap groundCellImage, boolean isground, Integer HP, Integer DMG, Integer SHIELD, Integer RANGE, Integer MOVE, Integer COST) {
        super(name, resources, cellWidth, cellHeight, image, groundCellImage, isground, HP, DMG, SHIELD, RANGE, MOVE, COST);
    }
}