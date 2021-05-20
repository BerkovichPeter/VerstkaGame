package com.example.gwynbleidd.verstkagame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button newGame;
    Button loadGame;

    Integer[][] HPMatrix = new Integer[7][9];
    Integer[][] VALUABLEMOVEMatrix = new Integer[7][9];
    Boolean[][] OWNERMatrix = new Boolean[7][9];
    Boolean[][] canAttackMatrix = new Boolean[7][9];
    String[][] nameMatrix = new String[7][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        newGame = (Button) findViewById(R.id.newGame);
        loadGame = (Button) findViewById(R.id.loadGame);
        newGame.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), NewGame.class);
            startActivity(intent);
        }
        });
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 9; j++) {
                        HPMatrix[i][j] = 1;
                        VALUABLEMOVEMatrix[i][j] = 0;
                        OWNERMatrix[i][j] = false;
                        canAttackMatrix[i][j] = false;
                        nameMatrix[i][j] = "";
                    }
                }

                SQLiteDatabase myDB = openOrCreateDatabase("save1.db", MODE_PRIVATE, null);
                Cursor myCursor = myDB.rawQuery("select name, indexX, indexY, HP, canAttack, VALUABLEMOVE, OWNER from save1", null);

                while(myCursor.moveToNext()) {
                    String name = myCursor.getString(0);
                    Integer indexX = myCursor.getInt(1);
                    Integer indexY = myCursor.getInt(2);
                    Integer HP = myCursor.getInt(3);
                    boolean canAttack = (myCursor.getInt(4) == 1);
                    Integer VALUABLEMOVE = myCursor.getInt(5);
                    boolean OWNER = (myCursor.getInt(6) == 1);

                    nameMatrix[indexX][indexY] = name;
                    HPMatrix[indexX][indexY] = HP;
                    canAttackMatrix[indexX][indexY] = canAttack;
                    VALUABLEMOVEMatrix[indexX][indexY] = VALUABLEMOVE;
                    OWNERMatrix[indexX][indexY] = OWNER;

                }

                myCursor.close();
                myDB.close();
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("HPMatrix", HPMatrix);
                intent.putExtra("VALUABLEMOVEMatrix", VALUABLEMOVEMatrix);
                intent.putExtra("canAttackMatrix", canAttackMatrix);
                intent.putExtra("OWNERMatrix", OWNERMatrix);
                intent.putExtra("nameMatrix", nameMatrix);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
