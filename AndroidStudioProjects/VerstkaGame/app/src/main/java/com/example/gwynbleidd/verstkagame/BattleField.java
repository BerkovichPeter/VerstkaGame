package com.example.gwynbleidd.verstkagame;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.gwynbleidd.verstkagame.UnitClasses.Humans.Archer;
import com.example.gwynbleidd.verstkagame.UnitClasses.Humans.Mage;
import com.example.gwynbleidd.verstkagame.UnitClasses.Orcs.OrcRanged;
import com.example.gwynbleidd.verstkagame.UnitClasses.Unit;


public class BattleField extends View {
    Resources resources;
    Context context;
    Integer cellWidth = 1;
    Integer cellHeight = 1;
    Integer indexOfCellY = 8;
    Integer indexOfCellX = 6;
    Integer deltaScreenX = 0;
    Integer deltaScreenY = 0;
    Integer indexOfCellOnClickY;
    Integer indexOfCellOnClickX;
    Integer width;
    Integer height;
    Integer previousIndexOfCellY = 8;
    Integer previousIndexOfCellX = 6;
    Integer befpreviousIndexOfCellX = 6;
    Integer befpreviousIndexOfCellY = 8;
    Integer[][] matrix = new Integer[7][9];
    Unit[][] unitMatrix = new Unit[7][9];
    Bitmap frame;
    Bitmap board1;
    Bitmap redImage;
    Bitmap blueImage;
    Bitmap red2Image;
    Bitmap armor;
    Bitmap turn;
    Bitmap turn2;
    Bitmap iconattack;
    Bitmap btnMenuImage;
    OurButton btnTurn;
    OurButton btnMenu;
    Boolean TurnOfSide;
    Canvas canvas;
    public long startTime1 = 0;
    Boolean DoubleClickCheck;
    Boolean DrawPermissed = false;



    Bitmap groundImage;

    Bitmap blue2Image;
    Bitmap groundCellImage;
    Bitmap orcImage;
    Bitmap orcRangedImage;
    Bitmap orc2Image;
    Bitmap orcRanged2Image;


    public Integer getCellWidth() {
        return cellWidth;
    }
    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
    }
    public Integer getCellHeight() {
        return cellHeight;
    }
    public void setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
    }


    boolean gameStopped = false;
    Paint p;
    Paint pBlack;
    Paint pGreen;
    Paint pYellow;
    Paint pText;
    Paint pHealthBar;
    Paint pHealthBarBounds;
    Paint pRed;

    Integer countTeamOne = 0;
    Integer countTeamTwo = 0;

    Dialog menu;
    Dialog winMessage;
    Bitmap groundFieldImage;


    public BattleField(Context context, Integer width, Integer height, Integer[][] matrix1, Integer[][] matrix2, String[][] nameMatrix, Integer[][] HPMatrix, Boolean[][] canAttackMatrix, Boolean[][] OWNERmatrix, Integer[][] VALUABLEMOVEMatrix,  Dialog menu, Dialog winMessage) {
        super(context);
        this.context = context;
        this.menu = menu;
        this.winMessage = winMessage;
        this.cellWidth = width / 8;
        this.cellHeight = height / 12;
        this.deltaScreenX = width / 8;
        this.deltaScreenY = height / 6;
        this.width = width;
        this.height = height;
        this.resources = getResources();
        this.startTime1 = System.currentTimeMillis();
        turn = BitmapFactory.decodeResource(resources, R.drawable.turn);
        turn2 = BitmapFactory.decodeResource(resources, R.drawable.turn2);
        btnTurn = new OurButton(turn, turn2, resources, cellWidth * 2, cellHeight / 2);
        board1 = BitmapFactory.decodeResource(resources, R.drawable.board1);


        btnMenuImage = BitmapFactory.decodeResource(resources, R.drawable.gear);

        btnMenu = new OurButton(btnMenuImage, btnMenuImage, resources , width / 17, height / 33);


        frame = BitmapFactory.decodeResource(resources, R.drawable.cutramka);
        frame = Bitmap.createScaledBitmap(frame, cellWidth, cellHeight, false);
        redImage = BitmapFactory.decodeResource(resources, R.drawable.mage);
        blueImage = BitmapFactory.decodeResource(resources, R.drawable.swordman);
        blue2Image = BitmapFactory.decodeResource(resources, R.drawable.swordman2);

        orcImage = BitmapFactory.decodeResource(resources, R.drawable.orc_melee);
        orcRangedImage = BitmapFactory.decodeResource(resources, R.drawable.orc_ranged);
        orc2Image = BitmapFactory.decodeResource(resources, R.drawable.orc2_melee);
        orcRanged2Image = BitmapFactory.decodeResource(resources, R.drawable.orc2_ranged);


        groundImage = BitmapFactory.decodeResource(resources, R.drawable.ground_test);

        groundCellImage = Bitmap.createScaledBitmap(groundImage, cellWidth, cellHeight, false);


        red2Image = BitmapFactory.decodeResource(resources, R.drawable.mage2);


        armor = BitmapFactory.decodeResource(resources, R.drawable.armor);
        iconattack = BitmapFactory.decodeResource(resources, R.drawable.iconattack);
        DoubleClickCheck = false;
        TurnOfSide = true;
        groundFieldImage = Bitmap.createScaledBitmap(groundImage, cellWidth * 6, cellHeight * 8, false);





        if (VALUABLEMOVEMatrix == null || nameMatrix == null || OWNERmatrix == null || HPMatrix == null || canAttackMatrix == null) {

            //Расстановка второго игрока
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 4; j++) {
                    if (matrix2[i][j] == 0) {
                        this.unitMatrix[i][j] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage, true);
                    } else if (matrix2[i][j] == 1) {
                        this.unitMatrix[i][j] = new Mage("Mage", resources, cellWidth, cellHeight, red2Image, groundCellImage, false, 15, 10, 0, 2, 2);
                        this.unitMatrix[i][j].Owner = false;
                    } else if (matrix2[i][j] == 2) {
                        this.unitMatrix[i][j] = new Unit("Swordman", resources, cellWidth, cellHeight, blue2Image, groundCellImage, false, 25, 5, 0, 1, 2, 100);
                        this.unitMatrix[i][j].Owner = false;
                    } else if (matrix2[i][j] == 3) {
                        this.unitMatrix[i][j] = new Archer("Archer", resources, cellWidth, cellHeight, BitmapFactory.decodeResource(resources, R.drawable.archer2), BitmapFactory.decodeResource(resources, R.drawable.archer2_model2), BitmapFactory.decodeResource(resources, R.drawable.archer2_model3), groundCellImage, false, 20, 5, 0, 2, 2);
                        this.unitMatrix[i][j].Owner = false;
                    } else if (matrix2[i][j] == 4) {
                        this.unitMatrix[i][j] = new Unit("Orc", resources, cellWidth, cellHeight, orc2Image, groundCellImage, false, 20, 10, 0, 1, 2, 150);
                        this.unitMatrix[i][j].Owner = false;
                    } else if (matrix2[i][j] == 5) {
                        this.unitMatrix[i][j] = new OrcRanged("OrcRanged", resources, cellWidth, cellHeight, orcRanged2Image, groundCellImage, false, 20, 10, 0 , 2 , 1, 200);
                        this.unitMatrix[i][j].Owner = false;
                    }
                }
            }

            //Расстановка первого игрока
            for (int i = 0; i < 7; i++) {
                for (int j = 4; j < 9; j++) {
                    if (matrix1[i][j] == 0) {
                        this.unitMatrix[i][j] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage, true);
                    } else if (matrix1[i][j] == 1) {
                        this.unitMatrix[i][j] = new Mage("Mage", resources, cellWidth, cellHeight, redImage, groundCellImage, false, 15, 10, 0, 2, 2);
                        this.unitMatrix[i][j].Owner = true;
                    } else if (matrix1[i][j] == 2) {
                        this.unitMatrix[i][j] = new Unit("Swordman", resources, cellWidth, cellHeight, blueImage, groundCellImage, false, 25, 5, 0, 1, 2, 100);
                        this.unitMatrix[i][j].Owner = true;
                    } else if (matrix1[i][j] == 3) {
                        this.unitMatrix[i][j] = new Archer("Archer", resources, cellWidth, cellHeight, BitmapFactory.decodeResource(resources, R.drawable.archer), BitmapFactory.decodeResource(resources, R.drawable.archer_model2), BitmapFactory.decodeResource(resources, R.drawable.archer_model3), groundCellImage, false, 20, 5, 0, 2, 2);
                        this.unitMatrix[i][j].Owner = true;
                    } else if (matrix1[i][j] == 4) {
                        this.unitMatrix[i][j] = new Unit("Orc", resources, cellWidth, cellHeight, orcImage, groundCellImage, false, 20, 10, 0, 1, 2, 150);
                        this.unitMatrix[i][j].Owner = true;
                    } else if (matrix1[i][j] == 5) {
                        this.unitMatrix[i][j] = new OrcRanged("OrcRanged", resources, cellWidth, cellHeight, orcRangedImage, groundCellImage, false, 20, 10, 0 , 2 , 1, 200);
                        this.unitMatrix[i][j].Owner = true;
                    }
                }
            }

        } else {

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 9; j++) {
                    if (nameMatrix[i][j].equals("Archer")) {
                        if (OWNERmatrix[i][j])
                            this.unitMatrix[i][j] = new Archer("Archer", resources, cellWidth, cellHeight, BitmapFactory.decodeResource(resources, R.drawable.archer), BitmapFactory.decodeResource(resources, R.drawable.archer_model2), BitmapFactory.decodeResource(resources, R.drawable.archer_model3), groundCellImage, false, 20, 5, 0, 2, 2);
                        else
                            this.unitMatrix[i][j] =  new Archer("Archer", resources, cellWidth, cellHeight, BitmapFactory.decodeResource(resources, R.drawable.archer2), BitmapFactory.decodeResource(resources, R.drawable.archer2_model2), BitmapFactory.decodeResource(resources, R.drawable.archer2_model3), groundCellImage, false, 20, 5, 0, 2, 2);
                        this.unitMatrix[i][j].HP = HPMatrix[i][j];
                        this.unitMatrix[i][j].canAttack = canAttackMatrix[i][j];
                        this.unitMatrix[i][j].Owner = OWNERmatrix[i][j];
                        this.unitMatrix[i][j].VALUABLEMOVE = VALUABLEMOVEMatrix[i][j];
                    } else if (nameMatrix[i][j].equals("Swordman")) {
                        if (OWNERmatrix[i][j])
                            this.unitMatrix[i][j] = new Unit("Swordman", resources, cellWidth, cellHeight, blueImage, groundCellImage, false, 25, 5, 0, 1, 2, 100);
                        else
                            this.unitMatrix[i][j] = new Unit("Swordman", resources, cellWidth, cellHeight, blue2Image, groundCellImage, false, 25, 5, 0, 1, 2, 100);
                        this.unitMatrix[i][j].HP = HPMatrix[i][j];
                        this.unitMatrix[i][j].canAttack = canAttackMatrix[i][j];
                        this.unitMatrix[i][j].Owner = OWNERmatrix[i][j];
                        this.unitMatrix[i][j].VALUABLEMOVE = VALUABLEMOVEMatrix[i][j];
                    } else if (nameMatrix[i][j].equals("Mage")) {
                        if (OWNERmatrix[i][j])
                            this.unitMatrix[i][j] = new Mage("Mage", resources, cellWidth, cellHeight, redImage, groundCellImage, false, 15, 10, 0, 2, 2);
                        else
                            this.unitMatrix[i][j] =  new Mage("Mage", resources, cellWidth, cellHeight, red2Image, groundCellImage, false, 15, 10, 0, 2, 2);
                        this.unitMatrix[i][j].HP = HPMatrix[i][j];
                        this.unitMatrix[i][j].canAttack = canAttackMatrix[i][j];
                        this.unitMatrix[i][j].Owner = OWNERmatrix[i][j];
                        this.unitMatrix[i][j].VALUABLEMOVE = VALUABLEMOVEMatrix[i][j];
                    } else if (nameMatrix[i][j].equals("Orc")) {
                        if (OWNERmatrix[i][j])
                            this.unitMatrix[i][j] = new Unit("Orc", resources, cellWidth, cellHeight, orcImage, groundCellImage, false, 20, 10, 0, 1, 2, 150);
                        else
                            this.unitMatrix[i][j] =  new Unit("Orc", resources, cellWidth, cellHeight, orc2Image, groundCellImage, false, 20, 10, 0, 1, 2, 150);
                        this.unitMatrix[i][j].HP = HPMatrix[i][j];
                        this.unitMatrix[i][j].canAttack = canAttackMatrix[i][j];
                        this.unitMatrix[i][j].Owner = OWNERmatrix[i][j];
                        this.unitMatrix[i][j].VALUABLEMOVE = VALUABLEMOVEMatrix[i][j];
                    } else if (nameMatrix[i][j].equals("OrcRanged")) {
                        if (OWNERmatrix[i][j])
                            this.unitMatrix[i][j] = new OrcRanged("OrcRanged", resources, cellWidth, cellHeight, orcRangedImage, groundCellImage, false, 20, 10, 0 , 2 , 1, 200);
                        else
                            this.unitMatrix[i][j] =  new OrcRanged("OrcRanged", resources, cellWidth, cellHeight, orcRanged2Image, groundCellImage, false, 20, 10, 0 , 2 , 1, 200);
                        this.unitMatrix[i][j].HP = HPMatrix[i][j];
                        this.unitMatrix[i][j].canAttack = canAttackMatrix[i][j];
                        this.unitMatrix[i][j].Owner = OWNERmatrix[i][j];
                        this.unitMatrix[i][j].VALUABLEMOVE = VALUABLEMOVEMatrix[i][j];
                    } else
                        this.unitMatrix[i][j] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage, true);
                }
            }

        }


        p = new Paint();
        Timer t = new Timer(this, Integer.MAX_VALUE, 100);
        t.start();





        pBlack = new Paint();
        pBlack.setColor(Color.BLACK);
        pBlack.setStrokeWidth(width / 150);
        pYellow = new Paint();
        pYellow.setColor(Color.YELLOW);
        pYellow.setStrokeWidth(width / 150);
        pYellow.setStyle(Paint.Style.STROKE);
        pGreen = new Paint();
        pGreen.setColor(Color.GREEN);
        pGreen.setStrokeWidth(width / 150);
        pGreen.setStyle(Paint.Style.STROKE);
        pRed = new Paint();
        pRed.setColor(Color.RED);
        pRed.setStrokeWidth(width / 150);
        pRed.setStyle(Paint.Style.STROKE);
        pHealthBarBounds = new Paint();
        pHealthBarBounds.setColor(Color.BLACK);
        pHealthBarBounds.setStyle(Paint.Style.STROKE);
        pHealthBarBounds.setStrokeWidth(width / 170);
        pHealthBar = new Paint();
        pHealthBar.setColor(Color.RED);
        pHealthBar.setStyle(Paint.Style.FILL);
        pHealthBar.setStrokeWidth(width / 200);
        pText = new Paint();
        pText.setTextSize(cellHeight * 0.3f);

        board1 = Bitmap.createScaledBitmap(board1, width, height, false);
        armor = Bitmap.createScaledBitmap(armor, cellWidth * 2 / 3, cellHeight * 2 / 3, false);
        iconattack = Bitmap.createScaledBitmap(iconattack, cellWidth * 2 / 3, cellHeight / 3, false);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(board1, 0f, 0f, p);
        canvas.drawBitmap(groundFieldImage, deltaScreenX, deltaScreenY, p);
        btnMenu.draw(canvas, width * 16 / 17f, 0f);
        this.canvas = canvas;
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(deltaScreenX, i * cellHeight + deltaScreenY, canvas.getWidth() - deltaScreenX, i * cellHeight + deltaScreenY, pBlack);
        }

        for (int i = 0; i < 7; i++) {
            canvas.drawLine(i * cellWidth + deltaScreenX, deltaScreenY, i * cellWidth + deltaScreenX, canvas.getHeight() - deltaScreenY, pBlack);
        }


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                unitMatrix[i][j].drawInCell(canvas, deltaScreenX + cellWidth * i * 1f, deltaScreenY + cellHeight * j * 1f);
            }

        }




        if (!unitMatrix[indexOfCellX][indexOfCellY].isGround()) {
            unitMatrix[indexOfCellX][indexOfCellY].drawOutCell(canvas, +cellWidth * 6, height - deltaScreenY);
            canvas.drawRect(cellWidth * 4.25f, cellHeight * 10.1f, cellWidth * 5.75f, cellHeight * 10.4f, pHealthBarBounds);
            canvas.drawRect(cellWidth * 4.25f, cellHeight * 10.1f, cellWidth * 5.75f - (1 - (float) unitMatrix[indexOfCellX][indexOfCellY].HP / (float) unitMatrix[indexOfCellX][indexOfCellY].MAXHP) * 1.5f * cellWidth, cellHeight * 10.4f, pHealthBar);
            canvas.drawText("Health Points : ", cellWidth * 1.5f, cellHeight * 10.35f, pText);
            canvas.drawBitmap(armor, cellWidth * 4.6f, cellHeight * 10.65f, p);
            canvas.drawBitmap(iconattack, cellWidth * 4.6f, cellHeight * 11.6f, p);
            canvas.drawText(unitMatrix[indexOfCellX][indexOfCellY].SHIELD.toString(), cellWidth * 4.85f, cellHeight * 11.1f, pText);
            canvas.drawText(unitMatrix[indexOfCellX][indexOfCellY].DMG.toString(), cellWidth * 4.85f, cellHeight * 11.85f, pText);
            if (unitMatrix[indexOfCellX][indexOfCellY].HP / 10 > 0)
                canvas.drawText(unitMatrix[indexOfCellX][indexOfCellY].HP.toString() + " / " + unitMatrix[indexOfCellX][indexOfCellY].MAXHP.toString(), cellWidth * 4.5f, cellHeight * 10.35f, pText);
            else
                canvas.drawText(" " + unitMatrix[indexOfCellX][indexOfCellY].HP.toString() + " / " + unitMatrix[indexOfCellX][indexOfCellY].MAXHP.toString(), cellWidth * 4.5f, cellHeight * 10.35f, pText);



        }

        btnTurn.draw(canvas, cellWidth * 3f, cellHeight * 1f);


        if (DrawPermissed) {
            if (TurnOfSide.equals(unitMatrix[indexOfCellX][indexOfCellY].Owner)) {


                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (!unitMatrix[indexOfCellX][indexOfCellY].isGround())
                            if (unitMatrix[i][j].isGround()) {
                                if (Math.abs(indexOfCellX - i) + Math.abs(indexOfCellY - j) <= unitMatrix[indexOfCellX][indexOfCellY].VALUABLEMOVE) {
                                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pGreen);
                                }
                            } else {
                                if ((Math.abs(indexOfCellX - i) + Math.abs(indexOfCellY - j) <= unitMatrix[indexOfCellX][indexOfCellY].RANGE) && (unitMatrix[indexOfCellX][indexOfCellY].Owner != unitMatrix[i][j].Owner) && unitMatrix[indexOfCellX][indexOfCellY].canAttack) {
                                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pRed);
                                }
                            }
                    }
                }
            }
            canvas.drawRect(indexOfCellX * cellWidth + deltaScreenX, indexOfCellY * cellHeight + deltaScreenY, (indexOfCellX + 1) * cellWidth + deltaScreenX, (indexOfCellY + 1) * cellHeight + deltaScreenY, pYellow);
        }
    }


    boolean firstTouch = false;
    long time = 0;

    public boolean isDoubleClick(MotionEvent event) {

        if (event.getAction() == event.ACTION_DOWN) {
            if (firstTouch && (System.currentTimeMillis() - time) <= 300 && (System.currentTimeMillis() - time) > 50) {
                //do stuff here for double tap

                return true;

            } else {


                firstTouch = true;
                time = System.currentTimeMillis();
            }
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getX() >= (width * 16 / 17) && event.getY() <= height / 33) {
            btnMenu.isPressed = true;
            menu.show();
        }


        if (event.getX() > cellWidth * 3f && event.getY() >=cellHeight * 1f && event.getX() <= cellWidth * 3f + cellWidth*2 && event.getY() <=cellHeight * 1f + cellHeight/2  ){
            btnTurn.isPressed = !btnTurn.isPressed;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 8; j++) {

                    if (!unitMatrix[i][j].isGround()){
                        if (unitMatrix[i][j].Owner.equals(TurnOfSide)){
                            unitMatrix[i][j].VALUABLEMOVE = unitMatrix[i][j].MOVE;
                            unitMatrix[i][j].canAttack = true;
                        }

                    }
                }

            }
            TurnOfSide = !TurnOfSide;
        }


        if ((event.getX() >= deltaScreenX) && (event.getX() <= cellWidth * 6 + deltaScreenX) && (event.getY() >= deltaScreenY) && (event.getY() <= cellHeight * 8 + deltaScreenY)) {
            DrawPermissed = true;
            for (int i = 1; i < 9; i++) {
                if (event.getY() > cellHeight * (i - 1) + deltaScreenY && event.getY() < cellHeight * i + deltaScreenY) {
                    this.indexOfCellY = i - 1;
                }
            }

            for (int i = 1; i < 7; i++) {
                if (event.getX() > cellWidth * (i - 1) + deltaScreenX && event.getX() < cellWidth * i + deltaScreenX) {
                    this.indexOfCellX = i - 1;
                }
            }



            if (!unitMatrix[indexOfCellX][indexOfCellY].isGround() && !isDoubleClick(event)) {


            }
        } else {
            indexOfCellX = 6;
            indexOfCellY = 8;
            DrawPermissed = false;
        }


        if ((event.getX() >= deltaScreenX) && (event.getX() <= cellWidth * 6 + deltaScreenX) && (event.getY() >= deltaScreenY) && (event.getY() <= cellHeight * 8 + deltaScreenY) && isDoubleClick(event)) {
            DrawPermissed = true;

            for (int i = 1; i < 9; i++) {
                if (event.getY() > cellHeight * (i - 1) + deltaScreenY && event.getY() < cellHeight * i + deltaScreenY) {
                    this.indexOfCellOnClickY = i - 1;
                }
            }

            for (int i = 1; i < 7; i++) {
                if (event.getX() > cellWidth * (i - 1) + deltaScreenX && event.getX() < cellWidth * i + deltaScreenX) {
                    this.indexOfCellOnClickX = i - 1;
                }

            }


            //Передвижение и атака
            if(!unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].ground && unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].Owner.equals(TurnOfSide) ) {
                if ( unitMatrix[indexOfCellOnClickX][indexOfCellOnClickY].ground &&(Math.abs(indexOfCellOnClickX - befpreviousIndexOfCellX) + Math.abs(indexOfCellOnClickY - befpreviousIndexOfCellY)) <= unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].VALUABLEMOVE ){
                    unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].VALUABLEMOVE -= Math.abs(indexOfCellOnClickX - befpreviousIndexOfCellX) + Math.abs(indexOfCellOnClickY - befpreviousIndexOfCellY);
                    unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].move(indexOfCellOnClickX, indexOfCellOnClickY, befpreviousIndexOfCellX, befpreviousIndexOfCellY, unitMatrix, unitMatrix[indexOfCellOnClickX][indexOfCellOnClickY].isGround());
                    indexOfCellX = indexOfCellOnClickX;
                    indexOfCellY = indexOfCellOnClickY;

                } else if ((Math.abs(indexOfCellOnClickX - befpreviousIndexOfCellX) + Math.abs(indexOfCellOnClickY - befpreviousIndexOfCellY)) <= unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].RANGE && unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].canAttack) {
                    unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].canAttack = false;
                    unitMatrix[befpreviousIndexOfCellX][befpreviousIndexOfCellY].attack(indexOfCellOnClickX, indexOfCellOnClickY, befpreviousIndexOfCellX, befpreviousIndexOfCellY, unitMatrix, unitMatrix[indexOfCellOnClickX][indexOfCellOnClickY].isGround(), canvas);

                }

            }
        }


        befpreviousIndexOfCellX = previousIndexOfCellX;
        befpreviousIndexOfCellY = previousIndexOfCellY;
        previousIndexOfCellY = indexOfCellY;
        previousIndexOfCellX = indexOfCellX;


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (unitMatrix[i][j].Owner != null) {
                    if (unitMatrix[i][j].Owner)
                        countTeamOne++;
                    else
                        countTeamTwo++;
                }
            }
        }

        if (((countTeamOne == 0) || (countTeamTwo == 0)) && !gameStopped) {
            winMessage.setTitle("Победа игрока: " + (countTeamTwo == 0 ? "Синий" : "Красный"));
            gameStopped = true;
            winMessage.show();
        }

        if (!gameStopped) {
            countTeamOne = 0;
            countTeamTwo = 0;
        }

        return false;
    }

}