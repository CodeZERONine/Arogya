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

public class WorkoutTimeActivity extends AppCompatActivity {

    private EditText cTime;
    private EditText rTime;
    private EditText lTime;

    private Button Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_time);

        final SQLiteDatabase workoutDB = openOrCreateDatabase("workoutDB",MODE_APPEND,null);
        workoutDB.execSQL("create table if not exists workout(running varchar, cycling varchar, weights varchar)");


        cTime=(EditText)findViewById(R.id.cyclingtime);
        rTime=(EditText)findViewById(R.id.runningtime);
        lTime=(EditText)findViewById(R.id.liftingtime);

        Btn=(Button)findViewById(R.id.addBtn);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int c= (int) Double.parseDouble(cTime.getText().toString());
                int r= (int) Double.parseDouble(rTime.getText().toString());
                int l= (int) Double.parseDouble(lTime.getText().toString());

                String q="select * from workout";
                Cursor cursor=workoutDB.rawQuery(q,null);
                int cTemp = 0,rTemp=0,lTemp=0;
                if(cursor.moveToNext())

                //while(cursor.moveToNext())
                {
                    rTemp = cursor.getInt(0);
                    cTemp = cursor.getInt(1);
                    lTemp = cursor.getInt(2);


                    c = c + cTemp;
                    r = r + rTemp;
                    l = l + lTemp;
                    // workoutDB.execSQL("update workout(running,cycling,weights) values('"+c+"','"+r+"','"+l+"',')");
                    workoutDB.execSQL("UPDATE workout SET running = '" + r + "', cycling = '" + c + "', weights ='" + l + "'");
                    Toast.makeText(WorkoutTimeActivity.this, "Update", Toast.LENGTH_SHORT).show();



                }
                else{
                    workoutDB.execSQL("insert into workout('running','cycling','weights')values(0,0,0)");
                    Toast.makeText(WorkoutTimeActivity.this, "ZERO Insert", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(WorkoutTimeActivity.this, "Entry Added", Toast.LENGTH_SHORT).show();

               /*///To validate the entry mech.
               String q1="select * from workout";

                Cursor cursor1=workoutDB.rawQuery(q,null);

                if(cursor1.moveToNext()){
                    cTemp = cursor.getInt(0);

                    rTemp = cursor.getInt(1);
                    lTemp = cursor.getInt(2);

                    Toast.makeText(WorkoutTimeActivity.this,String.valueOf(cTemp), Toast.LENGTH_LONG).show();

                    Toast.makeText(WorkoutTimeActivity.this,String.valueOf(rTemp), Toast.LENGTH_SHORT).show();

                    Toast.makeText(WorkoutTimeActivity.this,String.valueOf(lTemp), Toast.LENGTH_SHORT).show();

                }
*/

                startActivity(new Intent(WorkoutTimeActivity.this,WorkoutmainActivity.class));
            }
        });

    }
}
