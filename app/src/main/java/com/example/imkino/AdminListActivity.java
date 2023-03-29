package com.example.imkino;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.imkino.Upload;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminListActivity extends AppCompatActivity {
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploadsList;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        uploadsList = new ArrayList<>();
        imageAdapter = new ImageAdapter(AdminListActivity.this, uploadsList);
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
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
                Toast.makeText(AdminListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



       ListView listView = findViewById(R.id.ListAdmin);
       listView.setAdapter(imageAdapter);

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




    public void openAdminActivity(){
        Intent intent = new Intent(this,AdminActivity.class);
        startActivity(intent);
    }
}
