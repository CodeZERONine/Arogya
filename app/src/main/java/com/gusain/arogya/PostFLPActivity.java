package com.gusain.arogya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class PostFLPActivity extends AppCompatActivity {
    private ImageButton mselectImage;
    private EditText mPostTitle;
    private EditText mPostDesc;

    private Button mMBPBtm;

    private Uri mImageUri = null;

    private static final int GALLERY_REQUEST=1;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String mPost_key=null;

    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_flp);
        mAuth=FirebaseAuth.getInstance();

        mCurrentUser= mAuth.getCurrentUser();

        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mStorage= FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("FLP");




        mDatabase= FirebaseDatabase.getInstance().getReference().child("FLP");

        mselectImage=(ImageButton)findViewById(R.id.imageSelect);

        mPostTitle=(EditText)findViewById(R.id.titleField);
        mPostDesc=(EditText)findViewById(R.id.descField);

        mMBPBtm=(Button)findViewById(R.id.MBPsubmit);


        String dev_uid="i209FTdHzQg7ThLOiZylYI2HFHm2";

        if(mCurrentUser.getUid().equals(dev_uid))
        {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();

            mMBPBtm.setVisibility(View.VISIBLE);

            mMBPBtm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StartPosting();

                }
            });


        }

        mProgress = new ProgressDialog(this);

        mselectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent (Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);


            }
        });
    }

    private void StartPosting()
    {
        mProgress.setMessage("Posting to Plan....");


        final String title_val = mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();

        if( !TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val)  && mImageUri != null ){
            mProgress.show();

            StorageReference filepath= mStorage.child("FLP_images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //noinspection VisibleForTests
                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    final DatabaseReference newPost = mDatabase.push();



                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            newPost.child("title").setValue(title_val);
                            newPost.child("desc").setValue(desc_val);
                            newPost.child("image").setValue(downloadUrl.toString());
                            newPost.child("uid").setValue(mCurrentUser.getUid());
                            newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        startActivity(new Intent(PostFLPActivity.this,MBPlansActivity.class));

                                    }else
                                    {

                                    }

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });

                    mProgress.dismiss();



                }
            });

        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if(requestCode==GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();

            mselectImage.setImageURI(mImageUri);

        }*/
        //----------Inserting Image cropper----------------------

        if(requestCode == GALLERY_REQUEST && resultCode==RESULT_OK){

            Uri imageUri= data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(16,9)
                    // .setCropShape(CropImageView.CropShape.OVAL)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri= result.getUri();
                mselectImage.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }





    }
}
