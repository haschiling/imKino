package com.example.imkino;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PermissionListActivity extends AppCompatActivity {
    TextView filmName;
    TextView aboutFilm;
    TextView filmGenre;
    TextView filmData;
    TextView filmLimit;
    Button accept;
    Button delete;
    Button changePhoto;
    ImageView imageOfFilm;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private StorageTask uploadTask;
    private static final String TAG = "PermissionListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_list);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Picasso picasso = new Picasso.Builder(this).build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseRef = FirebaseDatabase.getInstance().getReference("users");



        filmName = findViewById(R.id.TextNameOfTheFilmPermission);
        aboutFilm  = findViewById(R.id.TextAboutFilmPermission);
        filmGenre = findViewById(R.id.TextGenrePermission);
        filmData  = findViewById(R.id.TextDataPermission);
        filmLimit  = findViewById(R.id.TextAgePermission);
        accept = findViewById(R.id.buttonAccept);
        delete = findViewById(R.id.buttonDelete);
        changePhoto = findViewById(R.id.buttonChangePhoto);
        imageOfFilm = findViewById(R.id.imageViewReadPermission);





        Upload selectedUpload = (Upload) getIntent().getSerializableExtra("SELECTED_UPLOAD");
        if (selectedUpload != null) {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            String pathString = "your/path/string";
            if (pathString != null) {
                if (databaseRef != null) {
                    databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Upload film = snapshot.getValue(Upload.class);
                            String name = film.getName();
                            String genre = film.getGenre();
                            String year = film.getYear();
                            String limit = film.getLimit();
                            String about = film.getAbout();
                            String imageUrl = film.getImageUri();

                            filmName.setText(name);
                            filmGenre.setText(genre);
                            filmData.setText(year);
                            filmLimit.setText(limit);
                            aboutFilm.setText(about);

                            Picasso.get().load(imageUrl).into(imageOfFilm);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(PermissionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d(TAG, "mDatabaseRef is null");
                }
            }
        }



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUri = selectedUpload.getImageUri();
                if (imageUri != null) {
                    StorageReference fileRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUri);
                    fileRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Object deleted successfully
                            Toast.makeText(getApplicationContext(), "Object deleted", Toast.LENGTH_SHORT).show();
                            // Delete the data from the database
                            databaseRef.child(selectedUpload.getKey()).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Data deleted successfully
                                            Toast.makeText(getApplicationContext(), "Data deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle errors
                                            Toast.makeText(getApplicationContext(), "Error deleting data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle errors
                            if (e instanceof StorageException && ((StorageException) e).getErrorCode() == StorageException.ERROR_OBJECT_NOT_FOUND) {
                                // Object doesn't exist
                                Toast.makeText(getApplicationContext(), "Object not found", Toast.LENGTH_SHORT).show();
                            } else {
                                // Other errors
                                Toast.makeText(getApplicationContext(), "Error deleting object", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(PermissionListActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(PermissionListActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });


    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageOfFilm);
            Log.d(TAG, "Selected image URI: " + imageUri);
            Toast.makeText(this, "image is not null", Toast.LENGTH_SHORT).show();
        }else {
            Log.d(TAG, "onActivityResult: something went wrong");
        }
    }



    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            // Get the file extension
            String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(imageUri));

            // Create a reference to the file path in Firebase Storage
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + fileExtension);

            // Upload file to Firebase Storage
            uploadTask = fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the download URL of the uploaded file
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Save the upload information to Firebase Database
                                    String name = filmName.getText().toString().trim();
                                    String genre = aboutFilm.getText().toString().trim();
                                    String year = filmGenre.getText().toString().trim();
                                    String limit = filmData.getText().toString().trim();
                                    String about = filmLimit.getText().toString().trim();
                                    String imageUri = uri.toString();

                                    Upload upload = new Upload(name, genre, year, limit, about, imageUri);
                                    String uploadId = databaseRef.push().getKey();
                                    databaseRef.child(uploadId).setValue(upload);

                                    Toast.makeText(PermissionListActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PermissionListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            Toast.makeText(PermissionListActivity.this, "Upload progress: " + (int) progress + "%", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }



}

