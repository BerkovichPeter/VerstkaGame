package com.example.gwynbleidd.verstkagame;


import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;



public class GameActivity extends AppCompatActivity {

    Integer[][] matrix1;
    Integer[][] matrix2;
    BattleField battleField;
    Dialog menu;
    Dialog winMessage;

    AppCompatActivity game;
    Integer[][] HPMatrix = new Integer[7][9];
    Integer[][] VALUABLEMOVEMatrix = new Integer[7][9];
    Boolean[][] OWNERMatrix = new Boolean[7][9];
    Boolean[][] canAttackMatrix = new Boolean[7][9];
    String[][] nameMatrix = new String[7][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.game = this;
        getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        menu = new Dialog(GameActivity.this);
        menu.setContentView(R.layout.menu_layout);
        menu.setTitle("Меню");
        menu.getWindow().setLayout(width / 2, height / 2);



        winMessage= new Dialog(GameActivity.this);




        Button btnSave = (Button) menu.findViewById(R.id.saveGame);
        btnSave.setText("Сохранить игру");
        Button btnMainMenu = (Button) menu.findViewById(R.id.mainMenu);
        btnMainMenu.setText("В главное меню");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase myDB = openOrCreateDatabase("save1.db", MODE_PRIVATE, null);
                myDB.execSQL("DROP TABLE IF EXISTS save1");
                myDB.execSQL(
                        "CREATE TABLE IF NOT EXISTS save1 (name TEXT, indexX INT, indexY INT, HP INT, canAttack INT, VALUABLEMOVE INT, OWNER INT)"
                );

                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (!battleField.unitMatrix[i][j].isGround()) {
                            ContentValues tmpRow = new ContentValues();
                            tmpRow.put("name", battleField.unitMatrix[i][j].name);
                            tmpRow.put("indexX", i);
                            tmpRow.put("indexY", j);
                            tmpRow.put("HP", battleField.unitMatrix[i][j].HP);
                            tmpRow.put("canAttack", (battleField.unitMatrix[i][j].canAttack == true ? 1 : 0));
                            tmpRow.put("VALUABLEMOVE", battleField.unitMatrix[i][j].VALUABLEMOVE);
                            tmpRow.put("OWNER", (battleField.unitMatrix[i][j].Owner == true ? 1 : 0));
                            myDB.insert("save1", null, tmpRow);
                        }
                    }
                }

                myDB.close();
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.cancel();
                game.finish();
            }
        });




        matrix1 = (Integer[][]) getIntent().getExtras().getSerializable("UnitMatrixPlayerOne");
        matrix2 = (Integer[][]) getIntent().getExtras().getSerializable("UnitMatrixPlayerTwo");
        HPMatrix = (Integer[][]) getIntent().getExtras().getSerializable("HPMatrix");
        VALUABLEMOVEMatrix = (Integer[][]) getIntent().getExtras().getSerializable("VALUABLEMOVEMatrix");
        nameMatrix = (String[][]) getIntent().getExtras().getSerializable("nameMatrix");
        canAttackMatrix = (Boolean[][]) getIntent().getExtras().getSerializable("canAttackMatrix");
        OWNERMatrix = (Boolean[][]) getIntent().getExtras().getSerializable("OWNERMatrix");
        battleField = new BattleField(this, width, height, matrix1, matrix2, nameMatrix, HPMatrix, canAttackMatrix, OWNERMatrix, VALUABLEMOVEMatrix, menu, winMessage);
        setContentView(battleField);




        winMessage.setContentView(R.layout.win_message);
        winMessage.getWindow().setLayout(width / 2, height / 8);
        Button btnToMainMenu = (Button) winMessage.findViewById(R.id.toMainMenu);
        Button btnWait = (Button) winMessage.findViewById(R.id.btnWait);

        btnToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.finish();
            }
        });

        btnWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winMessage.cancel();
            }
        });



    }


    @Override
    protected void onDestroy() {
        battleField.iconattack.recycle();
        battleField.armor.recycle();
        battleField.blue2Image.recycle();
        battleField.blueImage.recycle();
        battleField.red2Image.recycle();
        battleField.redImage.recycle();
        battleField.turn.recycle();
        battleField.turn2.recycle();
        battleField.frame.recycle();
        battleField.board1.recycle();
        battleField.groundCellImage.recycle();
        battleField.groundImage.recycle();
        battleField.btnMenuImage.recycle();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                battleField.unitMatrix[i][j].cellImage.recycle();
                if (battleField.unitMatrix[i][j].outImage != null)
                    battleField.unitMatrix[i][j].outImage.recycle();
                if (battleField.unitMatrix[i][j].cellImageModel1 != null)
                    battleField.unitMatrix[i][j].cellImageModel1.recycle();
                if (battleField.unitMatrix[i][j].cellImageModel2 != null)
                    battleField.unitMatrix[i][j].cellImageModel2.recycle();
                if (battleField.unitMatrix[i][j].cellImageModel3 != null)
                    battleField.unitMatrix[i][j].cellImageModel3.recycle();
            }
        }
        super.onDestroy();
    }
}

