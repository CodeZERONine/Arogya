package com.gusain.arogya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class DiaryActivity extends AppCompatActivity {

    private EditText date;
    private EditText achCal;
    private EditText tarCal;
    private ImageButton mImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        date=(EditText)findViewById(R.id.date);
        achCal=(EditText)findViewById(R.id.achievedCal);
        tarCal=(EditText)findViewById(R.id.targetcal);
        mImageBtn=(ImageButton)findViewById(R.id.dateBtn);

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiaryActivity.this, "Comming Soon...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
