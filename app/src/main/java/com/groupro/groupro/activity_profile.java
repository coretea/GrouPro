package com.groupro.groupro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class activity_profile extends AppCompatActivity {
    TextView tv_email;
    TextView tv_name;
    ImageView iv_profile;
    ImageView iv_back;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    ListView lv_projects;
    List list = new ArrayList();
    ArrayAdapter adapter;
    final ArrayList<Project> user_projects = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_name = (TextView)findViewById(R.id.tv_name);

        // gets name and email from DB
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mainSnapshot) {
                User userProfile = mainSnapshot.getValue(User.class);
                tv_name.setText(userProfile.getName());
                tv_email.setText(userProfile.getEmail());
                Glide.with(activity_profile.this).load(user.getPhotoUrl()).into(iv_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        iv_profile = (ImageView)findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(intent, 1910);
                }
            }
        });

        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("projects");
        // show list
        lv_projects = (ListView)findViewById(R.id.lv_projects);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project value=dataSnapshot.getValue(Project.class);
                if (value.getUsers().indexOf(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()) != -1 && value.getStatus().equals("Public"))
                {
                    String show = "   "+value.getName()+"\n   Manager: "+value.getManager();
                    list.add(show);
                    adapter = new ArrayAdapter<String>(activity_profile.this, R.layout.simple_list_item_1, list);
                    lv_projects.setAdapter(adapter);
                    user_projects.add(value);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




// ---------------------------- funcs ----------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1910)
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                    iv_profile.setImageBitmap(bitmap);
                    upload_pic(bitmap);
                    break;
                default:
                    Toast.makeText(activity_profile.this, "Profile Picture Update Failed!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    private void upload_pic (Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        final StorageReference store_ref = FirebaseStorage.getInstance().getReference()
                    .child("profileImages")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()+ ".jpeg");
        store_ref.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadUrl(store_ref);
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(activity_profile.this, "Profile Picture Update Failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getDownloadUrl(StorageReference store_ref)
    {
        store_ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                setUserProfile(uri);
            }
        });
    }

    private void setUserProfile(Uri uri)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity_profile.this, "Profile Picture Updated!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_profile.this, "Profile Picture Update Failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
