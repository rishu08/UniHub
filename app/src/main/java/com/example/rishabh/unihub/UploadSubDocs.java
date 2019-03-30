package com.example.rishabh.unihub;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadSubDocs extends AppCompatActivity implements View.OnClickListener{

    final static int PICK_PDF_CODE = 2342;
        String sub;
    TextView textViewStatus;
    EditText editTextFilename;
    ProgressBar progressBar;
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sub_docs);

        Intent i = getIntent();
        sub = i.getStringExtra("name");


        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        textViewStatus =  findViewById(R.id.textViewStatus);
        editTextFilename =  findViewById(R.id.editTextFileName);
        progressBar =  findViewById(R.id.progressbar);

        findViewById(R.id.buttonUploadFile).setOnClickListener(this);
        findViewById(R.id.textViewUploads).setOnClickListener(this);



    }


    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + this.getPackageName()));
            startActivity(intent);
            return;
        }

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void uploadFile(final Uri data) {
        Log.e("TAG", "uploadFile: " + data.toString());
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        final StorageReference photoRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS).child(sub).child((data.getLastPathSegment()));
        photoRef.putFile(data)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");

                    }
                })
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        Log.e("TAG", "uploadFromUri: upload success" + photoRef.getDownloadUrl());

                        // Request the public download URL
                        return photoRef.getDownloadUrl();
                    }
                })

                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("TAG", "new onSuccess:*** " + uri);
                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");
                        Log.e("TAG", "onSuccess: " + uri.toString());
                        Upload upload = new Upload(editTextFilename.getText().toString(), uri.toString());
                        mDatabaseReference.child(sub).child(mDatabaseReference.push().getKey()).setValue(upload);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(UploadSubDocs.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }


        @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonUploadFile:
                    getPDF();
                    break;
                case R.id.textViewUploads:
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentContainer,new ViewDocsActivity())
//                        .commit();
                        Intent intent = new Intent(this, ViewDocsActivityyy.class);
                        intent.putExtra("name",sub);
                        startActivity(intent);

//                    startActivity(new Intent(this, ViewDocsActivityyy.class));
                    break;
            }
    }
}
