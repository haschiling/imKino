package com.example.imkino;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddFilmAdminActivity extends AppCompatActivity {

    private EditText filmName;
    private EditText aboutFilm;
    private EditText filmGenre;
    private EditText filmData;
    private EditText filmLimit;
    private ImageView imageOfFilm;
    private Button List;
    private Button addImage;
    private Button addToList;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film_admin);


        Picasso picasso = new Picasso.Builder(this).build();



        imageOfFilm = (ImageView) findViewById(R.id.imageViewAddA);
        filmName = (EditText) findViewById(R.id.editTextNameOfTheFilmA);
        aboutFilm = (EditText) findViewById(R.id.editTextAboutFilmA);
        filmGenre = (EditText) findViewById(R.id.editTextGenreA);
        filmData = (EditText) findViewById(R.id.editTextDataA);
        filmLimit = (EditText) findViewById(R.id.editTextAgeA);
        addImage = (Button) findViewById(R.id.buttonAddPhotoA);
        addToList = (Button) findViewById(R.id.buttonAddToTheListA);
        List = (Button) findViewById(R.id.buttonToTheListA);
        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminListActivity();
            }
        });
        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(AddFilmAdminActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
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

    private void openAdminListActivity(){
        Intent intent = new Intent(this,AdminListActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageOfFilm);


        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadFile(){
        if(imageUri != null){
            StorageReference fileReference = storageRef.child( System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 5000);
                    Toast.makeText(AddFilmAdminActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(filmName.getText().toString().trim(), filmGenre.getText().toString().trim(),
                                    filmData.getText().toString().trim(), filmLimit.getText().toString().trim(), aboutFilm.getText().toString().trim(),
                                    uri.toString());

                            String uploadId = databaseRef.push().getKey();
                            databaseRef.child(uploadId).setValue(upload);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddFilmAdminActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    Toast.makeText(AddFilmAdminActivity.this, "Upload progress: " + (int) progress + "%", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void openListActivity(){
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }
}

