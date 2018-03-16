package com.gusain.arogya;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FoodItemEntryActivity extends AppCompatActivity {

    private EditText entryName;
    private  EditText entryCarbs;
    private EditText entryProteins;
    private EditText entryFats;
    private Button mSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_entry);

        final SQLiteDatabase FoodDB = openOrCreateDatabase("FoodDB",MODE_APPEND,null);
        FoodDB.execSQL("create table if not exists FoodItems(name varchar, carbs varchar, proteins varchar, fats varchar)");

        entryName=(EditText)findViewById(R.id.entryName);
        entryCarbs=(EditText)findViewById(R.id.entryCarbs);
        entryProteins=(EditText)findViewById(R.id.entryProteins);
        entryFats=(EditText)findViewById(R.id.entryFats);

        mSubmitBtn= (Button)findViewById(R.id.entryBtn);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=entryName.getText().toString().trim();
                int carbs=Integer.parseInt(entryCarbs.getText().toString().trim());
                int proteins=Integer.parseInt(entryProteins.getText().toString().trim());
                int fats=Integer.parseInt(entryFats.getText().toString().trim());

                String q="select * from FoodItems";
                Cursor cursor=FoodDB.rawQuery(q,null);

                if(cursor.moveToNext()) {
                    FoodDB.execSQL("insert into FoodItems('name','carbs','proteins','fats')values('"+name+"','"+carbs+"','"+proteins+"','"+fats+"')");
                    Toast.makeText(FoodItemEntryActivity.this, "FoodItem Have been saved", Toast.LENGTH_SHORT).show();
                    while (cursor.moveToNext()){

                        Toast.makeText(FoodItemEntryActivity.this, cursor.getString(0), Toast.LENGTH_SHORT).show();
                       // Toast.makeText(FoodItemEntryActivity.this, String.valueOf(cursor.getInt(1)), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(FoodItemEntryActivity.this, String.valueOf(cursor.getInt(2)), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(FoodItemEntryActivity.this, String.valueOf(cursor.getInt(3)), Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                       FoodDB.execSQL("insert into FoodItems('name','carbs','proteins','fats')values('PanCakes',28,6,10)");
                       FoodDB.execSQL("insert into FoodItems('name','carbs','proteins','fats')values('Egg',1,6,11)");
                       FoodDB.execSQL("insert into FoodItems('name','carbs','proteins','fats')values('Omelette',0,11,12)");
                       FoodDB.execSQL("insert into FoodItems('name','carbs','proteins','fats')values('Bread',12,2,1)");
                       FoodDB.execSQL("insert into FoodItems('name','carbs','proteins','fats')values('Oats',66,17,7)");
                       /*Toast.makeText(FoodItemEntryActivity.this, cursor.getString(0), Toast.LENGTH_SHORT).show();
                       Toast.makeText(FoodItemEntryActivity.this, cursor.getInt(1), Toast.LENGTH_SHORT).show();
                       Toast.makeText(FoodItemEntryActivity.this, cursor.getInt(2), Toast.LENGTH_SHORT).show();
                       Toast.makeText(FoodItemEntryActivity.this, cursor.getInt(3), Toast.LENGTH_SHORT).show();*/
                    Toast.makeText(FoodItemEntryActivity.this, "Execute all sql", Toast.LENGTH_SHORT).show();

                    String q1="select * from FoodItems";
                    Cursor cursor2=FoodDB.rawQuery(q1,null);
                    while(cursor2.moveToNext()){
                        //Toast.makeText(FoodItemEntryActivity.this, "inside while", Toast.LENGTH_SHORT).show();
                        String name4=cursor2.getString(0);
                        Toast.makeText(FoodItemEntryActivity.this,name4, Toast.LENGTH_SHORT).show();
                    }


                    Toast.makeText(FoodItemEntryActivity.this, "Default Inserted", Toast.LENGTH_SHORT).show();

                }





               // Toast.makeText(FoodItemEntryActivity.this, "Food Item Has Been Saved....", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
