package com.example.imkino;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class AboutAdminActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_about_admin);

        nameTextView = findViewById(R.id.textViewNameAdmin);
        genreTextView = findViewById(R.id.textViewGenreAdmin);
        yearTextView = findViewById(R.id.textViewYearAdmin);
        limitTextView = findViewById(R.id.textViewLimitAdmin);
        aboutTextView = findViewById(R.id.textViewAboutAdmin);
        imageView = findViewById(R.id.imageViewReadAdmin);




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
                            Toast.makeText(AboutAdminActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d(TAG, "mDatabaseRef is null");
                }
            }
        }


    }
    public void openListActivity(){
        Intent intent = new Intent(this, AdminListActivity.class);
        startActivity(intent);
    }
}

