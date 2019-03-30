package com.example.rishabh.unihub;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadDocsActivity extends Fragment implements View.OnClickListener {

    final static int PICK_PDF_CODE = 2342;

    //these are the views
    TextView textViewStatus;
    EditText editTextFilename;
    ProgressBar progressBar;

    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    public UploadDocsActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_docs, container, false);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        textViewStatus =  view.findViewById(R.id.textViewStatus);
        editTextFilename =  view.findViewById(R.id.editTextFileName);
        progressBar =  view.findViewById(R.id.progressbar);

         view.findViewById(R.id.buttonUploadFile).setOnClickListener(this);
        view.findViewById(R.id.textViewUploads).setOnClickListener(this);


        return view;
    }
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
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
                Toast.makeText(getActivity(), "No file chosen", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void uploadFile(final Uri data) {
        Log.e("TAG", "uploadFile: "+data.toString() );
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        final StorageReference photoRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS).child((data.getLastPathSegment()));
       photoRef.putFile(data)
               .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                       double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                       textViewStatus.setText((int) progress + "% Uploading...");

                   }
               })
               .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri >>() {
                   @Override
                   public Task<Uri>  then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if (!task.isSuccessful()) {
                           throw task.getException();
                       }

                       Log.e("TAG", "uploadFromUri: upload success"+photoRef.getDownloadUrl());

                       // Request the public download URL
                       return photoRef.getDownloadUrl();
                   }
               })

               .addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Log.e("TAG", "new onSuccess:*** "+uri );
                       progressBar.setVisibility(View.GONE);
                       textViewStatus.setText("File Uploaded Successfully");
                       Log.e("TAG", "onSuccess: "+uri.toString() );
                       Upload upload = new Upload(editTextFilename.getText().toString(), uri.toString());
                       mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);

                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception exception) {
                       Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                   }
               });




       /* sRef.putFile(data)


//               .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<data >>() {
//                   @Override
//                   public Task<data> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//
//                       if (!task.isSuccessful()) {
//                           throw task.getException();
//                       }
//                       Log.e("TAG", "uploadFromUri: upload success");
//                        return sRef.getDownloadUrl();
//                   }
//               })

               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");


//                              Url url = taskSnapshot.getDownloadUrl()
//                            Upload upload = new Upload(editTextFilename.getText().toString() , taskSnapshot.)
//                        Log.e("TAG", "onSuccess: " + taskSnapshot.getMetadata().getName());
                        Log.e("TAG", "onSuccess: "+ editTextFilename.getText().toString() );
                        Log.e("TAG", "onSuccess: "+ sRef.getDownloadUrl().toString() );
                        Task downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        downloadUrl.continueWithTask(new Continuation() {
                            @Override
                            public Object then(@NonNull Task task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                Log.e("TAG", "uploadFromUri: upload success"+sRef.getDownloadUrl());

                                // Request the public download URL
                                return sRef.getDownloadUrl();                            }
                        });
//                        downloadUrl.addOnCompleteListener(new OnCompleteListener() {
//                            @Override
//                            public void onComplete(@NonNull Task task) {
//                                Log.e("TAG", "onSuccess: "+ taskSnapshot.getMetadata().getReference().getDownloadUrl().getResult().toString() );
//
//                            }
//                        });
//                        Log.e("TAG", "onSuccess: "+ taskSnapshot.getMetadata().getReference().getDownloadUrl() );

                        Upload upload = new Upload(editTextFilename.getText().toString(), sRef.getDownloadUrl().toString());
                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });*/
//*****************************************************************************************
//        final StorageReference ref = mStorageReference.child("your_REF");
    /*   UploadTask uploadTask = sRef.putFile(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL

                return sRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    textViewStatus.setText("File Uploaded Successfully");

                    Log.e("TAG", "onComplete: "+task.getResult() );
                    Uri downloadUri = task.getResult();
                } else {
                    // Handle failures
                    // ...
                    Toast.makeText(getActivity().getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();

                }
            }
        });
*/
//******************************************************************************************
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

                startActivity(new Intent(getActivity(), ViewDocsActivityyy.class));
                break;
        }
    }
}
