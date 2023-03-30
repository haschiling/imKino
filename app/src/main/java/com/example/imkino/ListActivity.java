package com.example.imkino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private Button add;
    private Button filters;
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploadsList;

    SearchView searchView;

    private ListView movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        AutoCompleteTextView genreManu;

        uploadsList = new ArrayList<>();
        imageAdapter = new ImageAdapter(ListActivity.this, uploadsList);
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");


        genreManu =(AutoCompleteTextView) findViewById(R.id.genreMenu);
        String [] genre = {  "Action","Adventure","Comedy","Crime","Detective","Drama","Horror","Mystery","Romance","Thriller"  };
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genre);
        genreManu.setAdapter(ageAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadsList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    if (upload != null) {
                        if (upload.getName() == null) {
                            upload.setName("No name");
                        }
                        uploadsList.add(upload);
                    }
                }
                imageAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        ListView listView = findViewById(R.id.listList);
        listView.setAdapter(imageAdapter);

        genreManu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedGenre = genreManu.getText().toString();
                List<Upload> filteredUploads = new ArrayList<>();
                for (Upload upload : uploadsList) {
                    if (upload.getGenre().equals(selectedGenre)) {
                        filteredUploads.add(upload);
                    }
                }
                imageAdapter = new ImageAdapter(ListActivity.this, filteredUploads);
                listView.setAdapter(imageAdapter);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Upload selectedUpload = uploadsList.get(position);
                Intent intent = new Intent(getApplicationContext(), AboutAdminActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SELECTED_UPLOAD", selectedUpload);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }






}