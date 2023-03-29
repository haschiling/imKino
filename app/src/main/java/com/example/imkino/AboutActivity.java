package com.example.imkino;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AboutActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    TextView nameTextView;
    TextView genreTextView;
    TextView yearTextView;
    TextView limitTextView;
    TextView aboutTextView;


    ImageView imageView;
    private Button back;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nameTextView = findViewById(R.id.textViewName);
        genreTextView = findViewById(R.id.textViewGenre);
        yearTextView = findViewById(R.id.textViewYear);
        limitTextView = findViewById(R.id.textViewLimit);
        aboutTextView = findViewById(R.id.textViewAbout);
        imageView = findViewById(R.id.imageViewRead);




        Upload selectedUpload = (Upload) getIntent().getSerializableExtra("SELECTED_UPLOAD");
        if (selectedUpload != null) {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            String pathString = "your/path/string";
            if (pathString != null) {
                if (databaseRef != null) {
                    databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = selectedUpload.getName();
                            String genre = selectedUpload.getGenre();
                            String year = selectedUpload.getYear();
                            String limit = selectedUpload.getLimit();
                            String about = selectedUpload.getAbout();
                            String imageUrl = selectedUpload.getImageUri();

                            nameTextView.setText(name);
                            genreTextView.setText(genre);
                            yearTextView.setText(year);
                            limitTextView.setText(limit);
                            aboutTextView.setText(about);

                            Picasso.get().load(imageUrl).into(imageView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AboutActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d(TAG, "mDatabaseRef is null");
                }
            }
        }


    }
    public void openListActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}

