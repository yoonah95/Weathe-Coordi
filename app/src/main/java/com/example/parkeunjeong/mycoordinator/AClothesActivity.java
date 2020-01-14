package com.example.parkeunjeong.mycoordinator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class AClothesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aclothes);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        adapter.add("TOP");
        adapter.add("BOTTOM");
        adapter.add("COORDI GOGO");

        ListView listView = (ListView)findViewById(R.id.listview);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    Intent intent = new Intent(getApplicationContext(),ATopListActivity.class);
                    startActivity(intent);

                }
                else if(i==1){
                    Intent intent = new Intent(getApplicationContext(),APantsActivity.class);
                    startActivity(intent);

                }
                else if(i==2){
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    startActivity(intent);

                }

            }
        });



    }
}