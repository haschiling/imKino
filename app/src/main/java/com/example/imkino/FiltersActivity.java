package com.example.imkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

public class FiltersActivity extends AppCompatActivity {
    private Button back;
    byte [] image;
    ListView listView;
    ArrayAdapter<String> adapter;
    int counter = 1;
    public FILMS about = new FILMS(image,"Name","Genre","Year","Top" );
    AutoCompleteTextView dataMenu;
    AutoCompleteTextView ageMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        ageMenu =(AutoCompleteTextView) findViewById(R.id.ageMenu);
        String [] age = {"0+","6+","12+","16+","18+","21+"};
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,age);
        ageMenu.setAdapter(ageAdapter);


        dataMenu =(AutoCompleteTextView) findViewById(R.id.dataMenu);
        String[] data={"2023","2022","2021","2020","2019","2018"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);
        dataMenu.setAdapter(dataAdapter);


        String[] myGenres = {  "Action","Adventure","Comedy","Crime","Detective","Drama","Horror","Mystery","Romance","Thriller"  };

        listView = (ListView) findViewById(R.id.listFilters);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,myGenres);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    about.setGenre("Action");
                }else if(position == 2){
                    about.setGenre("Comedy");
                }else if(position == 3){
                    about.setGenre("Crime");
                }else if(position == 4){
                    about.setGenre("Detective");
                }else if(position == 5){
                    about.setGenre("Drama");
                }else if (position == 6){
                    about.setGenre("Horror");
                }else if(position == 7){
                    about.setGenre("Mystery");
                }else if(position == 8){
                    about.setGenre("Romance");
                }else if (position == 9){
                    about.setGenre("Thriller");
                }

                Intent intent = new Intent(getApplicationContext(), FiltersListActivity.class);
                startActivity(intent);
            }
        });




    }

    public void back(){
        if(counter == 1){
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        }
    }

}