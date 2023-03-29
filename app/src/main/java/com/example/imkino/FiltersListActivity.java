package com.example.imkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class FiltersListActivity extends AppCompatActivity {
    private Button back;
    private byte[] imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters_list);
        try {


            Item[] arr = {new Item(imageView,"Name","Genre","Year"), new Item(imageView,"Name", "Genre","Year"),};
            ListView lv = (ListView) findViewById(R.id.lv6);
            AdapterH adp = new AdapterH(this, arr);
            lv.setAdapter(adp);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void openActvity2(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }


}