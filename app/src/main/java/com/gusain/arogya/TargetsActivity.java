package com.gusain.arogya;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TargetsActivity extends AppCompatActivity {

    private EditText targetCarbs;
    private EditText targetProteins;
    private EditText targetFats;
    private Button mSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_targets);

        final SQLiteDatabase FoodDB = openOrCreateDatabase("FoodDB",MODE_APPEND,null);
        FoodDB.execSQL("create table if not exists TargetMacros(carbs varchar, proteins varchar, fats varchar)");

        targetCarbs=(EditText)findViewById(R.id.tagCarbs);
        targetProteins=(EditText)findViewById(R.id.tagPro);
        targetFats=(EditText)findViewById(R.id.tagfats);
        mSubmitBtn=(Button)findViewById(R.id.targetSubmitBtn);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int c= Integer.parseInt(targetCarbs.getText().toString());
                int p=Integer.parseInt(targetProteins.getText().toString());
                int f = Integer.parseInt(targetFats.getText().toString());



                String q="select * from TargetMacros";
                Cursor cursor=FoodDB.rawQuery(q,null);


                if(cursor.moveToNext()) {
                    FoodDB.execSQL("UPDATE TargetMacros SET carbs = '" + c + "', proteins = '" + p + "', fats ='" + f + "'");

                    Toast.makeText(TargetsActivity.this, "Targets Have been saved", Toast.LENGTH_SHORT).show();
                }
                    else{
                        FoodDB.execSQL("insert into TargetMacros('carbs','proteins','fats')values(0,0,0)");
                        Toast.makeText(TargetsActivity.this, "ZERO Insert", Toast.LENGTH_SHORT).show();
                }


                ///To validate the entry mech.
             String q1="select * from TargetMacros";

               Cursor cursor1=FoodDB.rawQuery(q,null);

               if(cursor1.moveToNext())
                {
                    int cTemp = cursor.getInt(0);

                   int rTemp = cursor.getInt(1);
                    int lTemp = cursor.getInt(2);

                   /* Toast.makeText(TargetsActivity.this,String.valueOf(cTemp), Toast.LENGTH_LONG).show();

                    Toast.makeText(TargetsActivity.this,String.valueOf(rTemp), Toast.LENGTH_SHORT).show();

                    Toast.makeText(TargetsActivity.this,String.valueOf(lTemp), Toast.LENGTH_SHORT).show();*/

                }

                Toast.makeText(TargetsActivity.this, "Target Submitted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TargetsActivity.this, CalorieMainActivity.class));
            }
        });
    }

}
