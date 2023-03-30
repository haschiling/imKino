package com.example.imkino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends AppCompatActivity {
    private Button add;
    private Button filters;
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploadsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uploadsList = new ArrayList<>();
        imageAdapter = new ImageAdapter(PermissionActivity.this, uploadsList);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadsList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
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
                Toast.makeText(PermissionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        ListView listView = findViewById(R.id.listViewPermission);
        listView.setAdapter((ListAdapter) imageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Upload selectedUpload = uploadsList.get(position);
                Intent intent = new Intent(getApplicationContext(), PermissionListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SELECTED_UPLOAD", selectedUpload);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}