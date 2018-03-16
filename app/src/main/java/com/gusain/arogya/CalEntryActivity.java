package com.gusain.arogya;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CalEntryActivity extends AppCompatActivity {

    private Button msubmitBtn;

    private Spinner s1;
    private Spinner s2;

    String food[]={"PanCakes","Egg","Omelette","Bread","Oats"};
    String quant[]={"1","2","3","4","5","6","7","8","9","10"};
    private int c,p,f,n,sum;
    private String nam=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_entry);

        final SQLiteDatabase FoodDB = openOrCreateDatabase("FoodDB",MODE_APPEND,null);
        FoodDB.execSQL("create table if not exists DayMacros(carbs varchar, proteins varchar, fats varchar)");
        FoodDB.execSQL("create table if not exists FoodItems(name varchar, carbs varchar, proteins varchar, fats varchar)");

        msubmitBtn=(Button)findViewById(R.id.foodentrySubmitBtn);
        s1=(Spinner)findViewById(R.id.spinner1);
        s2=(Spinner)findViewById(R.id.spinner2);

        ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,food);
        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,quant);
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s1.setAdapter(ad1);
        s2.setAdapter(ad2);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              // Toast.makeText(CalEntryActivity.this,food[i],Toast.LENGTH_LONG).show();
                String item=food[i];
                //Toast.makeText(CalEntryActivity.this,item, Toast.LENGTH_SHORT).show();
                String q1="select * from FoodItems where name ='"+item+"'";
                Cursor cursor1=FoodDB.rawQuery(q1,null);

               if(cursor1.moveToNext())
                {
                    nam=cursor1.getString(0);
                     c=cursor1.getInt(1);
                     p=cursor1.getInt(2);
                    f=cursor1.getInt(3);
                   // Toast.makeText(CalEntryActivity.this, "CalEntry-inside if", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CalEntryActivity.this, "carbsof "+nam+":"+c, Toast.LENGTH_SHORT).show();
                    Toast.makeText(CalEntryActivity.this, "prosof "+nam+":"+p, Toast.LENGTH_SHORT).show();
                    Toast.makeText(CalEntryActivity.this, "fatsof "+nam+":"+f, Toast.LENGTH_SHORT).show();
                }
              else{
                   c=p=f=10;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(CalEntryActivity.this,quant[position],Toast.LENGTH_LONG).show();
                n=Integer.valueOf(quant[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        msubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String q2="select * from DayMacros";
                Cursor cursor2=FoodDB.rawQuery(q2,null);
                int cTemp = 0,rTemp=0,lTemp=0;
                if(cursor2.moveToNext()){


                    //while(cursor2.moveToNext())

                        cTemp = cursor2.getInt(0);
                        rTemp = cursor2.getInt(1);
                        lTemp = cursor2.getInt(2);


                        cTemp=c+cTemp;
                        rTemp=p+rTemp;
                         lTemp=f+lTemp;

                        // workoutDB.execSQL("update workout(running,cycling,weights) values('"+c+"','"+r+"','"+l+"',')");
                        FoodDB.execSQL("UPDATE DayMacros SET carbs = '" + cTemp + "', proteins = '" + rTemp + "', fats ='" + lTemp + "'");

                        Toast.makeText(CalEntryActivity.this, "Update", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CalEntryActivity.this, "new Carbs:"+c, Toast.LENGTH_SHORT).show();
                    Toast.makeText(CalEntryActivity.this, "new Proteins:"+p, Toast.LENGTH_SHORT).show();
                    Toast.makeText(CalEntryActivity.this, "new Fats:"+f, Toast.LENGTH_SHORT).show();

                    sum=(n*((c*4)+(p*4)+(f*9)));

                }
                else{
                    FoodDB.execSQL("insert into DayMacros('carbs','proteins','fats')values(0,0,0)");
                    Toast.makeText(CalEntryActivity.this, "ZERO Insert", Toast.LENGTH_SHORT).show();
                }




                Toast.makeText(CalEntryActivity.this, "Entry Has Been Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CalEntryActivity.this,CalorieMainActivity.class));
            }
        });
    }
}
