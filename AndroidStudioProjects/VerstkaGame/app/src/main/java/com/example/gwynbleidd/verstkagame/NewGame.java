package com.example.gwynbleidd.verstkagame;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;


public class NewGame extends AppCompatActivity {

    AlignmentField alignmentField;
    Bundle mBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        alignmentField = new AlignmentField(getApplicationContext(), width, height, this);
        setContentView(alignmentField);


        mBundle = new Bundle();


    }


    @Override
    protected void onStop() {
        if (alignmentField.btnStartGame.isPressed) {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            mBundle.putSerializable("UnitMatrixPlayerOne", alignmentField.matrix1);
            mBundle.putSerializable("UnitMatrixPlayerTwo", alignmentField.matrix2);
            intent.putExtras(mBundle);
            alignmentField.resources = null;
            alignmentField.context = null;
            alignmentField.activity = null;
            alignmentField.board1.recycle();
            alignmentField.groundImage.recycle();
            alignmentField.groundCellImage.recycle();
            alignmentField.btnClassNonePressed.recycle();
            alignmentField.btnClassPressed.recycle();
            alignmentField.redImage.recycle();
            alignmentField.blueImage.recycle();
            alignmentField.archImage.recycle();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    alignmentField.unitMatrix1[i][j].cellImage.recycle();
                    if (alignmentField.unitMatrix1[i][j].cellImageModel1 != null)
                        alignmentField.unitMatrix1[i][j].cellImageModel1.recycle();
                    if (alignmentField.unitMatrix1[i][j].cellImageModel2 != null)
                        alignmentField.unitMatrix1[i][j].cellImageModel2.recycle();
                    if (alignmentField.unitMatrix1[i][j].cellImageModel3 != null)
                        alignmentField.unitMatrix1[i][j].cellImageModel3.recycle();
                    if (alignmentField.unitMatrix1[i][j].outImage != null)
                        alignmentField.unitMatrix1[i][j].outImage.recycle();

                    alignmentField.unitMatrix2[i][j].cellImage.recycle();
                    if (alignmentField.unitMatrix2[i][j].cellImageModel1 != null)
                        alignmentField.unitMatrix2[i][j].cellImageModel1.recycle();
                    if (alignmentField.unitMatrix2[i][j].cellImageModel2 != null)
                        alignmentField.unitMatrix2[i][j].cellImageModel2.recycle();
                    if (alignmentField.unitMatrix2[i][j].cellImageModel3 != null)
                        alignmentField.unitMatrix2[i][j].cellImageModel3.recycle();
                    if (alignmentField.unitMatrix2[i][j].outImage != null)
                        alignmentField.unitMatrix2[i][j].outImage.recycle();
                }
            }

            alignmentField = null;
            startActivity(intent);
        }
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




}
