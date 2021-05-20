package com.example.gwynbleidd.verstkagame;

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


public class AlignmentField extends View {

    Integer playerOneGold = 1000;
    Integer playerTwoGold = 1000;


    Resources resources;
    Context context;
    Integer cellWidth = 1;
    Integer cellHeight = 1;
    Integer indexOfCellY = 0;
    Integer indexOfCellX = 0;
    Integer deltaScreenX = 0;
    Integer deltaScreenY = 0;
    Integer width;
    Integer height;
    Integer[][] matrix1 = new Integer[7][9];
    Integer[][] matrix2 = new Integer[7][9];
    Unit[][] unitMatrix1 = new Unit[7][9];
    Unit[][] unitMatrix2 = new Unit[7][9];
    Bitmap redImage;
    Bitmap blueImage;
    Bitmap archImage;
    Bitmap btnClassNonePressed ;
    Bitmap btnClassPressed;
    OurButton btnDeleteUnit;
    OurButton btnNextPlayer;
    OurButton btnStartGame;
    OurButton btnPreviousPlayer;
    OurButton btnMan;
    OurButton btnDwarf;
    OurButton btnElf;
    OurButton btnOrc;
    OurButton btnRanged;
    OurButton btnSword;
    OurButton btnHorse;
    OurButton btnMage;

    public long startTime1 = 0;
    Float dx;
    Float dy;
    Boolean fieldIsPressed = false;
    Boolean flag = false;

    NewGame activity;



    Integer playerNumber;



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

    Paint p;
    Unit replaceableUnit;
    Unit replaceableUnit2;

    Timer t;

    Bitmap groundImage;

    Bitmap groundCellImage;
    Bitmap orcImage;
    Bitmap orcRangedImage;


    Bitmap board1;


    Paint pRed;
    Paint pBlack;
    Paint pYellow;
    Paint pText;
    Paint pPlace;
    Paint pGreen;
    Bitmap groundFieldImage;


    public AlignmentField(Context context, Integer width, Integer height, NewGame activity) {
        super(context);
        this.context = context;
        this.cellWidth = width / 8;
        this.cellHeight = height / 12;
        this.deltaScreenX = width / 8;
        this.deltaScreenY = height / 6;
        this.width = width;
        this.height = height;
        this.startTime1 = System.currentTimeMillis();
        this.activity = activity;
        this.playerNumber = 1;
        this.resources = getResources();
        btnClassNonePressed = BitmapFactory.decodeResource(resources, R.drawable.boxoff);
        btnClassPressed    = BitmapFactory.decodeResource(resources, R.drawable.boxon);
        btnNextPlayer = new OurButton(btnClassNonePressed, btnClassPressed ,resources,cellWidth,cellHeight* 2);
        btnPreviousPlayer = new OurButton(btnClassNonePressed, btnClassPressed ,resources,cellWidth,cellHeight * 2);
        btnStartGame = new OurButton(btnClassNonePressed, btnClassPressed ,resources,cellWidth,cellHeight * 2);
        redImage = BitmapFactory.decodeResource(resources, R.drawable.mage);
        blueImage = BitmapFactory.decodeResource(resources, R.drawable.swordman);
        archImage = BitmapFactory.decodeResource(resources, R.drawable.archer);
        orcImage = BitmapFactory.decodeResource(resources, R.drawable.orc_melee);
        orcRangedImage = BitmapFactory.decodeResource(resources, R.drawable.orc_ranged);


        groundImage = BitmapFactory.decodeResource(resources, R.drawable.ground_test);
        groundFieldImage = Bitmap.createScaledBitmap(groundImage, cellWidth * 6, cellHeight * 8, false);
        groundCellImage = Bitmap.createScaledBitmap(groundImage, cellWidth, cellHeight, false);

        replaceableUnit = new Unit("SwordMan", resources, cellWidth, cellHeight, blueImage, groundCellImage, false, 20, 5, 0, 2 ,1,100);





        btnMan = new OurButton(btnClassNonePressed, btnClassPressed, resources, cellWidth* 2, cellHeight/ 2);
        btnDwarf = new OurButton(btnClassNonePressed, btnClassPressed, resources, cellWidth* 2, cellHeight/ 2);
        btnElf = new OurButton(btnClassNonePressed, btnClassPressed, resources, cellWidth* 2, cellHeight/ 2);
        btnOrc = new OurButton(btnClassNonePressed, btnClassPressed, resources, cellWidth* 2, cellHeight/ 2);

        btnMage = new OurButton(btnClassNonePressed, btnClassPressed ,resources,cellWidth* 2,cellHeight/ 2);
        btnSword = new OurButton(btnClassNonePressed, btnClassPressed ,resources,cellWidth* 2,cellHeight/ 2);
        btnHorse = new OurButton(btnClassNonePressed, btnClassPressed ,resources,cellWidth* 2,cellHeight/ 2);
        btnRanged = new OurButton(btnClassNonePressed, btnClassPressed ,resources,cellWidth* 2,cellHeight/ 2);


        btnDeleteUnit = new OurButton(btnClassNonePressed, btnClassPressed, resources, cellWidth * 2, cellHeight / 2);

        btnMan.isPressed = true;
        btnSword.isPressed = true;



        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                this.matrix1[i][j] = 0;
                this.matrix2[i][j] = 0;
                this.unitMatrix1[i][j] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);
                this.unitMatrix2[i][j] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);

            }
        }



        p = new Paint();
        t = new Timer(this, Integer.MAX_VALUE, 100);
        t.start();

        board1 = BitmapFactory.decodeResource(resources, R.drawable.board1);
        board1 = Bitmap.createScaledBitmap(board1, width, height, false);





        pBlack = new Paint();
        pBlack.setColor(Color.BLACK);
        pBlack.setStrokeWidth(width/150);
        pYellow = new Paint();
        pYellow.setColor(Color.YELLOW);
        pYellow.setStrokeWidth(width/150);
        pYellow.setStyle(Paint.Style.STROKE);
        pGreen = new Paint();
        pGreen.setColor(Color.GREEN);
        pGreen.setStrokeWidth(width/150);
        pGreen.setStyle(Paint.Style.STROKE);
        pRed = new Paint();
        pRed.setColor(Color.RED);
        pRed.setStrokeWidth(width/150);
        pRed.setStyle(Paint.Style.STROKE);


        pText = new Paint();
        pText.setTextSize(cellHeight / 2.1f);



        pPlace = new Paint();
        pPlace.setStyle(Paint.Style.STROKE);
        pPlace.setColor(Color.BLUE);
        pPlace.setAlpha(127);

    }



    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(board1, 0f, 0f, p);
        canvas.drawBitmap(groundFieldImage, deltaScreenX, deltaScreenY, p);

        for (int i = 0; i < 9; i++) {
            canvas.drawLine(deltaScreenX, i*cellHeight+deltaScreenY, canvas.getWidth() - deltaScreenX, i*cellHeight + deltaScreenY, pBlack);
        }

        for (int i = 0; i < 7; i++) {
            canvas.drawLine(i*cellWidth + deltaScreenX, deltaScreenY, i*cellWidth + deltaScreenX, canvas.getHeight() - deltaScreenY, pBlack);
        }



        if (playerNumber == 1) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    unitMatrix1[i][j].drawInCell(canvas, deltaScreenX + cellWidth * i * 1f, deltaScreenY + cellHeight * j * 1f);
                }

            }
        } else {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    unitMatrix2[i][j].drawInCell(canvas, deltaScreenX + cellWidth * i * 1f, deltaScreenY + cellHeight * j * 1f);
                }

            }
        }


        replaceableUnit.drawOutCell(canvas,cellWidth * 6,0);


        btnMan.draw(canvas, deltaScreenX * 1f, 0f);
        canvas.drawText("Man", deltaScreenX * 1.3f, deltaScreenY * 0.2f, pText);
        btnDwarf.draw(canvas, deltaScreenX * 1f, deltaScreenY * 0.25f);
        canvas.drawText("Dwarf", deltaScreenX * 1.3f, deltaScreenY * 0.45f, pText);
        btnElf.draw(canvas, deltaScreenX * 1f, deltaScreenY * 0.5f);
        canvas.drawText("Elf", deltaScreenX * 1.3f, deltaScreenY * 0.7f, pText);
        btnOrc.draw(canvas, deltaScreenX * 1f, deltaScreenY * 0.75f);
        canvas.drawText("Orc", deltaScreenX * 1.3f, deltaScreenY * 0.95f, pText);
        btnSword.draw(canvas, deltaScreenX + cellWidth * 3f, 0f);
        canvas.drawText("Sword", deltaScreenX + cellWidth * 3.25f, deltaScreenY * 0.2f, pText);
        btnMage.draw(canvas, deltaScreenX + cellWidth * 3f, deltaScreenY * 0.25f);
        canvas.drawText("Mage", deltaScreenX + cellWidth * 3.25f, deltaScreenY * 0.45f, pText);
        btnHorse.draw(canvas, deltaScreenX + cellWidth * 3f, deltaScreenY * 0.5f);
        canvas.drawText("Horse", deltaScreenX + cellWidth * 3.25f, deltaScreenY * 0.7f, pText);
        btnRanged.draw(canvas, deltaScreenX + cellWidth * 3f, deltaScreenY * 0.75f);
        canvas.drawText("Ranged", deltaScreenX + cellWidth * 3.25f, deltaScreenY * 0.95f, pText);


        if (playerNumber == 1) {
            btnNextPlayer.draw(canvas, deltaScreenX + cellWidth * 6f, deltaScreenY + cellHeight * 3f);
            canvas.drawText(playerOneGold.toString(),cellWidth * 5f, deltaScreenY + cellHeight * 9f, pText);
            canvas.drawText("Amount of gold:", cellWidth * 1f, deltaScreenY + cellHeight * 9f, pText);
        }
        else {
            btnStartGame.draw(canvas, deltaScreenX + cellWidth * 6f, deltaScreenY + cellHeight * 3f);
            btnPreviousPlayer.draw(canvas, 0f, deltaScreenY + cellHeight * 3f);
            canvas.drawText(playerTwoGold.toString(), cellWidth * 5f, deltaScreenY + cellHeight * 9f, pText);
            canvas.drawText("Amount of gold:", cellWidth * 1f, deltaScreenY + cellHeight * 9f, pText);
        }





        if (playerNumber == 1) {
            if (fieldIsPressed) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (!unitMatrix1[indexOfCellX][indexOfCellY].isGround())
                            if (unitMatrix1[i][j].isGround()) {
                                if (Math.abs(indexOfCellX - i) + Math.abs(indexOfCellY - j) <= unitMatrix1[indexOfCellX][indexOfCellY].MOVE) {
                                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pGreen);
                                }
                            } else {
                                if (Math.abs(indexOfCellX - i) + Math.abs(indexOfCellY - j) <= unitMatrix1[indexOfCellX][indexOfCellY].RANGE) {
                                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pRed);
                                }
                            }
                    }
                }


                if (!unitMatrix1[indexOfCellX][indexOfCellY].isGround())
                    unitMatrix1[indexOfCellX][indexOfCellY].drawOutCell(canvas, +cellWidth * 6, height - deltaScreenY);
                canvas.drawRect(indexOfCellX * cellWidth + deltaScreenX, indexOfCellY * cellHeight + deltaScreenY, (indexOfCellX + 1) * cellWidth + deltaScreenX, (indexOfCellY + 1) * cellHeight + deltaScreenY, pYellow);

            }
        } else {
            if (fieldIsPressed) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (!unitMatrix2[indexOfCellX][indexOfCellY].isGround())
                            if (unitMatrix2[i][j].isGround()) {
                                if (Math.abs(indexOfCellX - i) + Math.abs(indexOfCellY - j) <= unitMatrix2[indexOfCellX][indexOfCellY].MOVE) {
                                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pGreen);
                                }
                            } else {
                                if (Math.abs(indexOfCellX - i) + Math.abs(indexOfCellY - j) <= unitMatrix2[indexOfCellX][indexOfCellY].RANGE) {
                                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pRed);
                                }
                            }
                    }
                }

                if (!unitMatrix2[indexOfCellX][indexOfCellY].isGround())
                    unitMatrix2[indexOfCellX][indexOfCellY].drawOutCell(canvas, +cellWidth * 6, height - deltaScreenY);
                canvas.drawRect(indexOfCellX * cellWidth + deltaScreenX, indexOfCellY * cellHeight + deltaScreenY, (indexOfCellX + 1) * cellWidth + deltaScreenX, (indexOfCellY + 1) * cellHeight + deltaScreenY, pYellow);

            }
        }


        if (replaceableUnit2 != null)
            replaceableUnit2.drawInCell(canvas, dx, dy);



        if (playerNumber == 1) {
            if (!unitMatrix1[indexOfCellX][indexOfCellY].isGround()) {
                btnDeleteUnit.draw(canvas, deltaScreenX + cellWidth * 2f, deltaScreenY + cellHeight * 9.25f);
            }
        } else {
            if (!unitMatrix2[indexOfCellX][indexOfCellY].isGround()) {
                btnDeleteUnit.draw(canvas, deltaScreenX + cellWidth * 2f, deltaScreenY + cellHeight * 9.25f);
            }
        }


        if (playerNumber == 1) {
            for (int i = 0; i < 6; i++) {
                for (int j = 5; j < 8; j++) {
                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pPlace);
                }
            }
        } else {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 3; j++) {
                    canvas.drawRect(i * cellWidth + deltaScreenX, j * cellHeight + deltaScreenY, (i + 1) * cellWidth + deltaScreenX, (j + 1) * cellHeight + deltaScreenY, pPlace);
                }
            }
        }

    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {


        //При нажатии на кнопку удаления, юнит заменяется на землю
        if (playerNumber == 1) {
            if (!unitMatrix1[indexOfCellX][indexOfCellY].isGround()) {
                if ((event.getX() >= deltaScreenX + cellWidth * 2f) && (event.getX() <= deltaScreenX + cellWidth * 4f) && (event.getY() >= deltaScreenY + cellHeight * 9.25f) && (event.getY() <= deltaScreenY + cellHeight * 9.75f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
                    matrix1[indexOfCellX][indexOfCellY] = 0;
                    playerOneGold += unitMatrix1[indexOfCellX][indexOfCellY].COST;
                    unitMatrix1[indexOfCellX][indexOfCellY] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);
                }
            }
        } else {
            if (!unitMatrix2[indexOfCellX][indexOfCellY].isGround()) {
                if ((event.getX() >= deltaScreenX + cellWidth * 2f) && (event.getX() <= deltaScreenX + cellWidth * 4f) && (event.getY() >= deltaScreenY + cellHeight * 9.25f) && (event.getY() <= deltaScreenY + cellHeight * 9.75f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
                    matrix2[indexOfCellX][indexOfCellY] = 0;
                    playerTwoGold += unitMatrix2[indexOfCellX][indexOfCellY].COST;
                    unitMatrix2[indexOfCellX][indexOfCellY] = new Unit("ground", resources, cellWidth, cellHeight, groundCellImage,true);
                }
            }
        }



        if ((event.getX() >= deltaScreenX) && (event.getX() <= cellWidth * 6 + deltaScreenX) && (event.getY() >= deltaScreenY) && (event.getY() <= cellHeight * 8 + deltaScreenY)) {
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
            fieldIsPressed = true;
        } else {
            fieldIsPressed = false;
            indexOfCellX = 6;
            indexOfCellY = 8;
        }




        //Кнопки, отвечающие за расу юнита (тут ничего менять не надо)
        if ((event.getX() >= deltaScreenX * 1f) && (event.getX() <= deltaScreenX + cellWidth * 2f) && (event.getY() >= 0f) && (event.getY() <= deltaScreenY * 0.25f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnMan.isPressed = true;
            btnDwarf.isPressed = false;
            btnElf.isPressed = false;
            btnOrc.isPressed = false;
        } else if ((event.getX() >= deltaScreenX * 1f) && (event.getX() <= deltaScreenX + cellWidth * 2f) && (event.getY() >= deltaScreenY * 0.25f) && (event.getY() <= deltaScreenY * 0.5f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnMan.isPressed = false;
            btnDwarf.isPressed = true;
            btnElf.isPressed = false;
            btnOrc.isPressed = false;
        } else if ((event.getX() >= deltaScreenX * 1f) && (event.getX() <= deltaScreenX + cellWidth * 2f) && (event.getY() >= deltaScreenY * 0.5f) && (event.getY() <= deltaScreenY * 0.75f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnMan.isPressed = false;
            btnDwarf.isPressed = false;
            btnElf.isPressed = true;
            btnOrc.isPressed = false;
        } else if ((event.getX() >= deltaScreenX * 1f) && (event.getX() <= deltaScreenX + cellWidth * 2f) && (event.getY() >= deltaScreenY * 0.75f) && (event.getY() <= deltaScreenY * 1f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnMan.isPressed = false;
            btnDwarf.isPressed = false;
            btnElf.isPressed = false;
            btnOrc.isPressed = true;
        }


        //Кнопки, отвечающие за класс юнита (тут ничего менять не надо)
        if ((event.getX() >= deltaScreenX + cellWidth * 3f) && (event.getX() <= deltaScreenX + cellWidth * 5f) && (event.getY() >= 0f) && (event.getY() <= deltaScreenY * 0.25f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnSword.isPressed = true;
            btnMage.isPressed = false;
            btnHorse.isPressed = false;
            btnRanged.isPressed = false;
        } else if ((event.getX() >= deltaScreenX + cellWidth * 3f) && (event.getX() <= deltaScreenX + cellWidth * 5f) && (event.getY() >= deltaScreenY * 0.25f) && (event.getY() <= deltaScreenY * 0.5f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnSword.isPressed = false;
            btnMage.isPressed = true;
            btnHorse.isPressed = false;
            btnRanged.isPressed = false;
        } else if ((event.getX() >= deltaScreenX + cellWidth * 3f) && (event.getX() <= deltaScreenX + cellWidth * 5f) && (event.getY() >= deltaScreenY * 0.5f) && (event.getY() <= deltaScreenY * 0.75f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnSword.isPressed = false;
            btnMage.isPressed = false;
            btnHorse.isPressed = true;
            btnRanged.isPressed = false;
        } else if ((event.getX() >= deltaScreenX + cellWidth * 3f) && (event.getX() <= deltaScreenX + cellWidth * 5f) && (event.getY() >= deltaScreenY * 0.75f) && (event.getY() <= deltaScreenY * 1f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            btnSword.isPressed = false;
            btnMage.isPressed = false;
            btnHorse.isPressed = false;
            btnRanged.isPressed = true;
        }


        // Сюда надо вставлять юнитов в зависимости от того, какая комбинация кнопок нажата
        if ((btnMan.isPressed) && (btnSword.isPressed) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            replaceableUnit = new Unit("Swordman", resources, cellWidth, cellHeight, blueImage, groundCellImage,false,20,5,0,2,1, 100);
        } else if ((btnMan.isPressed) && (btnRanged.isPressed) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            replaceableUnit = new Archer("Archer", resources, cellWidth, cellHeight, BitmapFactory.decodeResource(resources, R.drawable.archer), BitmapFactory.decodeResource(resources, R.drawable.archer_model2), BitmapFactory.decodeResource(resources, R.drawable.archer_model3), groundCellImage,false,10,10,0,2,1);
        } else if ((btnMan.isPressed) && (btnMage.isPressed) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            replaceableUnit = new Mage("Mage", resources, cellWidth, cellHeight, redImage,groundCellImage, false,15,10,0,2,2);
        } else if ((btnOrc.isPressed) && (btnSword.isPressed) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            replaceableUnit = new Unit("Orc", resources, cellWidth, cellHeight, orcImage, groundCellImage, false, 15, 10, 0, 1, 2, 150);
        } else if ((btnOrc.isPressed) && (btnRanged.isPressed) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            replaceableUnit = new OrcRanged("OrcRanged", resources, cellWidth, cellHeight, orcRangedImage, groundCellImage, false, 20, 8, 0 , 2 , 1, 200);
        }



        //Часть кода, отвечающая за перенос юнита на поле
        if (playerNumber == 1) {
            if ((((event.getY() < deltaScreenY) && (event.getX() > deltaScreenX + cellWidth * 5)) || (flag)) && (playerOneGold - replaceableUnit.COST >= 0)) {
                replaceableUnit2 = replaceableUnit;
                dx = event.getX();
                dy = event.getY();
                flag = true;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    flag = false;
                    if ((fieldIsPressed) && (indexOfCellY >= 5)) {
                        if (!unitMatrix1[indexOfCellX][indexOfCellY].isGround())
                            playerOneGold += unitMatrix1[indexOfCellX][indexOfCellY].COST;
                        unitMatrix1[indexOfCellX][indexOfCellY] = replaceableUnit2;
                        playerOneGold -= replaceableUnit.COST;

                        //В этой части заполняется массив, который потом передастся в BattleField, так что при добавлении новых юнитов, надо прописывать сюда их заполнение в массив
                        if ((btnMan.isPressed) && (btnMage.isPressed))
                            matrix1[indexOfCellX][indexOfCellY] = 1;
                        else if ((btnMan.isPressed) && (btnSword.isPressed))
                            matrix1[indexOfCellX][indexOfCellY] = 2;
                        else if ((btnMan.isPressed) && (btnRanged.isPressed))
                            matrix1[indexOfCellX][indexOfCellY] = 3;
                        else if ((btnOrc.isPressed) && (btnSword.isPressed))
                            matrix1[indexOfCellX][indexOfCellY] = 4;
                        else if ((btnOrc.isPressed) && (btnRanged.isPressed))
                            matrix1[indexOfCellX][indexOfCellY] = 5;
                    }
                    replaceableUnit2 = null;
                }
            }
        } else {
            if ((((event.getY() < deltaScreenY) && (event.getX() > deltaScreenX + cellWidth * 5)) || (flag)) && (playerTwoGold - replaceableUnit.COST >= 0)) {
                replaceableUnit2 = replaceableUnit;
                dx = event.getX();
                dy = event.getY();
                flag = true;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    flag = false;
                    if ((fieldIsPressed) && (indexOfCellY <= 2) && (playerTwoGold >= 0)) {
                        if (!unitMatrix2[indexOfCellX][indexOfCellY].isGround())
                            playerTwoGold += unitMatrix2[indexOfCellX][indexOfCellY].COST;
                        unitMatrix2[indexOfCellX][indexOfCellY] = replaceableUnit2;
                        playerTwoGold -= replaceableUnit.COST;

                        //В этой части заполняется массив, который потом передастся в BattleField, так что при добавлении новых юнитов, надо прописывать сюда их заполнение в массив
                        if ((btnMan.isPressed) && (btnMage.isPressed))
                            matrix2[indexOfCellX][indexOfCellY] = 1;
                        else if ((btnMan.isPressed) && (btnSword.isPressed))
                            matrix2[indexOfCellX][indexOfCellY] = 2;
                        else if ((btnMan.isPressed) && (btnRanged.isPressed))
                            matrix2[indexOfCellX][indexOfCellY] = 3;
                        else if ((btnOrc.isPressed) && (btnSword.isPressed))
                            matrix2[indexOfCellX][indexOfCellY] = 4;
                        else if ((btnOrc.isPressed) && (btnRanged.isPressed))
                            matrix2[indexOfCellX][indexOfCellY] = 5;
                    }
                    replaceableUnit2 = null;
                }
            }
        }








        if ((event.getX() >= deltaScreenX + cellWidth * 6f) && (event.getX() <= deltaScreenX + cellWidth * 7f) && (event.getY() >= deltaScreenY + cellHeight * 3f) && (event.getY() <= deltaScreenY + cellHeight * 5f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {


            if (playerNumber == 1) {
                btnNextPlayer.isPressed = true;
                btnStartGame.isPressed = false;
                btnPreviousPlayer.isPressed = false;
                playerNumber = 2;
            } else {
                btnStartGame.isPressed = true;
                activity.finish();
                t.onFinish();
            }

        }

        if ((event.getX() >= 0f) && (event.getX() <= deltaScreenX) && (event.getY() >= deltaScreenY + cellHeight * 3f) && (event.getY() <= deltaScreenY + cellHeight * 5f) && (event.getAction() == MotionEvent.ACTION_DOWN)) {


            if (playerNumber == 1) {

            } else {
                btnPreviousPlayer.isPressed = true;
                btnNextPlayer.isPressed = false;
                playerNumber = 1;
            }

        }



        return true;
    }


}

