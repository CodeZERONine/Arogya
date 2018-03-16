package com.gusain.arogya;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FlPlansActivity extends AppCompatActivity {

    private RecyclerView mBlogList;

    private DatabaseReference FLPDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fl_plans);

        FLPDatabase = FirebaseDatabase.getInstance().getReference().child("FLP");

        mBlogList =(RecyclerView)findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
///// ----------Firebase Recycelr Adapter--------------
        FirebaseRecyclerAdapter<FLP,FlPlansActivity.FLPViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FLP, FlPlansActivity.FLPViewHolder>(
                FLP.class,R.layout.plan_row,FlPlansActivity.FLPViewHolder.class,FLPDatabase)
        {
            @Override
            protected void populateViewHolder(FlPlansActivity.FLPViewHolder viewHolder, FLP model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };
//////// -------------End of Firebase Recycler Adapter------------
        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }
    //////View Holder
    public static class FLPViewHolder extends RecyclerView.ViewHolder{


        View mView;

        public FLPViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

        }
        public void setTitle(String title){

            TextView post_title=(TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_text);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx, String image){
            ImageView post_image=(ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);


        }
    }

    ////////////View Holder End









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mbp_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id==R.id.action_add){
            startActivity(new Intent(FlPlansActivity.this,PostFLPActivity.class));

        }
        else if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
