package com.gusain.arogya;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class CalorieMainActivity extends AppCompatActivity {


    TextView target;
    TextView ach;
    TextView carbs;
    TextView proteins;
    TextView fats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_main);

        target = (TextView)findViewById(R.id.textView5);
        ach = (TextView)findViewById(R.id.textView8);
        carbs= (TextView)findViewById(R.id.editText14);
        proteins = (TextView)findViewById(R.id.editText15);
        fats = (TextView)findViewById(R.id.editText16);

        SQLiteDatabase FoodDB = openOrCreateDatabase("FoodDB",MODE_APPEND,null);

        FoodDB.execSQL("create table if not exists FoodItems(name varchar, carbs varchar, proteins varchar, fats varchar)");
        FoodDB.execSQL("create table if not exists TargetMacros(carbs varchar, proteins varchar, fats varchar)");
        FoodDB.execSQL("create table if not exists DayMacros(carbs varchar, proteins varchar, fats varchar)");

        String q1="select * from TargetMacros";

        Cursor cursor1=FoodDB.rawQuery(q1,null);

        if(cursor1.moveToNext()){
            int cTemp = cursor1.getInt(0);

            int rTemp = cursor1.getInt(1);
            int lTemp = cursor1.getInt(2);

            /*Toast.makeText(CalorieMainActivity.this,String.valueOf(cTemp), Toast.LENGTH_LONG).show();
            Toast.makeText(CalorieMainActivity.this,String.valueOf(rTemp), Toast.LENGTH_SHORT).show();
            Toast.makeText(CalorieMainActivity.this,String.valueOf(lTemp), Toast.LENGTH_SHORT).show();*/
            int targetCals=((cTemp*4) + (rTemp*4) + (lTemp*9));
            target.setText(String.valueOf(targetCals));

        }

        String q2="select * from DayMacros";
        Cursor cursor2 = FoodDB.rawQuery(q2,null);
            if(cursor2.moveToNext()){
                int c2Temp=cursor2.getInt(0);
                int p2Temp=cursor2.getInt(1);
                int f2Temp=cursor2.getInt(2);

                carbs.setText(String.valueOf(c2Temp)+"  gm Carbohydrates");
                proteins.setText(String.valueOf(p2Temp)+"  gm Proteins");
                fats.setText(String.valueOf(f2Temp)+"  gm Fats");
                int achCals=((c2Temp*4) + (p2Temp*4) + (f2Temp*9));
                ach.setText(String.valueOf(achCals));


            }





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calorie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addFood) {
            Intent itementryIntent = new Intent(CalorieMainActivity.this,FoodItemEntryActivity.class);
            startActivity(itementryIntent);

            return true;
        }
        else if (id == R.id.action_diary)
        {
            Intent diaryIntent = new Intent(CalorieMainActivity.this,DiaryActivity.class);
            startActivity(diaryIntent);

        }
        else if (id == R.id.action_targets){
            Intent targetsIntent = new Intent(CalorieMainActivity.this,TargetsActivity.class);
            startActivity(targetsIntent);

        }
        else if(id == R.id.action_add){

            Intent enteryIntent = new Intent(CalorieMainActivity.this,CalEntryActivity.class);
            startActivity(enteryIntent);

        }
        else if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
