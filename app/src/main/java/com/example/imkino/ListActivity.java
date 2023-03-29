package com.example.imkino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchText = newText.toLowerCase();
                Query query;
                if (searchText.isEmpty()) {
                    query = databaseReference.orderByChild("name");
                } else {
                    query = databaseReference.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
                }
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        uploadsList.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            if (upload != null) {
                                if (upload.getName() == null) {
                                    upload.setName("No name");
                                }
                                if (searchText.isEmpty() || upload.getName().toLowerCase().contains(searchText)) {
                                    uploadsList.add(upload);
                                }
                            }
                        }
                        imageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String searchText = searchView.getQuery().toString().toLowerCase();
                uploadsList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    if (upload != null) {
                        if (upload.getName() == null) {
                            upload.setName("No name");
                        }
                        if (searchText.isEmpty() || upload.getName().toLowerCase().contains(searchText)) {
                            uploadsList.add(upload);
                        }
                    }
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        uploadsList = new ArrayList<>();
        imageAdapter = new ImageAdapter(ListActivity.this, uploadsList);
        add = findViewById(R.id.buttonaddFILM);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdd();
            }
        });
        filters = findViewById(R.id.buttonFilters);
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFiltersActivity();
            }
        });

        ListView listView = findViewById(R.id.listList);
        listView.setAdapter(imageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Upload selectedUpload = uploadsList.get(position);
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SELECTED_UPLOAD", selectedUpload);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }


    public void openFiltersActivity(){
        Intent intent = new Intent(this,FiltersActivity.class);
        startActivity(intent);
    }
    public void openAdd(){
        Intent intent = new Intent(this,AddFilmActivity.class);
        startActivity(intent);
    }

}