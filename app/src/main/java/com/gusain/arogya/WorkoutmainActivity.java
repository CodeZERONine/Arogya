package com.gusain.arogya;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WorkoutmainActivity extends AppCompatActivity {

    private ImageButton t1;
    private ImageButton t2;
    private ImageButton t3;

    private TextView val1;
    private TextView val2;
    private TextView val3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQLiteDatabase workoutDB = openOrCreateDatabase("workoutDB",MODE_APPEND,null);
        workoutDB.execSQL("create table if not exists workout(running varchar, cycling varchar, weights varchar)");

        t1=(ImageButton)findViewById(R.id.workoutTracker1);
        t2=(ImageButton)findViewById(R.id.workoutTracker2);
        t3=(ImageButton)findViewById(R.id.workoutTracker3);

        val1=(TextView) findViewById(R.id.tracker1val);
        val2=(TextView) findViewById(R.id.tracker2val);
        val3=(TextView) findViewById(R.id.tracker3val);

        //setting values to the Text Views
        String q="select * from workout";
        Cursor cursor=workoutDB.rawQuery(q,null);
        double cTemp ,rTemp,lTemp;
        Toast.makeText(this, cursor.toString(), Toast.LENGTH_LONG).show();

       /* if(cursor!=null){
            cursor.moveToNext();
            Toast.makeText(this,"cursor move next", Toast.LENGTH_SHORT).show();

        }*/

        if(cursor.moveToNext()) {
            cTemp = cursor.getInt(0);

            Toast.makeText(this, "Initials", Toast.LENGTH_SHORT).show();

            rTemp = cursor.getInt(1);
            lTemp = cursor.getInt(2);

            val1.setText(String.valueOf(rTemp));
            val2.setText(String.valueOf(cTemp));
            val3.setText(String.valueOf(lTemp));
        }




        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkoutmainActivity.this,WorkoutTimeActivity.class));

            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WorkoutmainActivity.this,WorkoutTimeActivity.class));

            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkoutmainActivity.this,WorkoutTimeActivity.class));

            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        else if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
