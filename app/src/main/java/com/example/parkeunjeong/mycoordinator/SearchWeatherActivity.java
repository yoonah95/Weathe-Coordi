package com.example.parkeunjeong.mycoordinator;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchWeatherActivity extends AppCompatActivity {
    Handler handler = new Handler();
    EditText editTextCityName;
    Button search_b, clothRe_b;
    Intent intent;
    TextView t_temp, t_temMin, t_temMax, t_city, t_hum, t_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weather);

        editTextCityName = (EditText) findViewById(R.id.search_edit);
        t_temp = (TextView)findViewById(R.id.temp);
        t_temMax = (TextView)findViewById(R.id.temp_max);
        t_temMin = (TextView)findViewById(R.id.temp_min);
        t_city = (TextView)findViewById(R.id.city);
        t_date = (TextView)findViewById(R.id.date);
        t_hum = (TextView)findViewById(R.id.hum);

        search_b =(Button)findViewById(R.id.search_btn);
        search_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = editTextCityName.getText().toString();

                ConnectThread thread = new ConnectThread(urlStr);
                thread.start();
            }
        });
    }
    public class ConnectThread extends Thread {
        OkHttpClient client = new OkHttpClient();
        String city;

        public ConnectThread(String inStr) {
            city = inStr;
        }
        public void run() {
            try {
                Request request = new Request.Builder()
                        .url("http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+"563ac7f8d9170be773ad9e9a0f2194c8")
                        .build();

                Response response = client.newCall(request).execute();

                JSONObject jsonObject = new JSONObject(response.body().string());

                String temp = ((JSONObject)jsonObject.get("main")).getString("temp");
                String temp_min = ((JSONObject)jsonObject.get("main")).getString("temp_min");
                String temp_max = ((JSONObject)jsonObject.get("main")).getString("temp_max");

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE-MM-dd");

                double temp_int = Double.parseDouble(temp);
                double temp_min_int = Double.parseDouble(temp_min);
                double temp_max_int = Double.parseDouble(temp_max);
                double temp_int_c = temp_int-273.15;
                double temp_min_int_c = temp_min_int-273.15;
                double temp_max_int_c = temp_max_int-273.15;

                final int i1 = (int)temp_int_c;
                final int i2 = (int)temp_min_int_c;
                final int i3 = (int)temp_max_int_c;
                final String date = simpleDateFormat.format(calendar.getTime());
                final String hum = ((JSONObject)jsonObject.get("main")).getString("humidity");
                final String city_name = jsonObject.getString("name");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        t_temp.setText(String.valueOf(i1));
                        t_temMin.setText(String.valueOf(i2));
                        t_temMax.setText(String.valueOf(i3));
                        t_date.setText(date);
                        t_city.setText(city_name);
                        t_hum.setText(hum);

                        clothRe_b = (Button)findViewById(R.id.clothRecom_btn);
                        clothRe_b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent = new Intent(getApplicationContext(), AClothesActivity.class);
                                intent.putExtra("temp",i1);
                                startActivity(intent);
                               // if(i1>28&&i1<=23){
                                  //  intent = new Intent(getApplicationContext(),AClothesActivity.class);
                                  //  intent.putExtra("temp",i1);
                                   // startActivity(intent);
                               // }
                               // if(i1>=17&&i1<=22){
                               //     intent = new Intent(getApplicationContext(),BClothesActivity.class);
                                //    intent.putExtra("temp",i1);
                                //    startActivity(intent);
                               // }
                               // if(i1>=9&&i1<=16){
                               //     intent = new Intent(getApplicationContext(),CClothesActivity.class);
                               //     intent.putExtra("temp",i1);
                               //     startActivity(intent);
                                    //Toast.makeText(SearchWeatherActivity.this,"OK",Toast.LENGTH_SHORT).show();
                               // }
                                //if(i1>=4&&i1<=8){
                                 //   intent = new Intent(getApplicationContext(),DClothesActivity.class);
                                 //   intent.putExtra("temp",i1);
                                  //  startActivity(intent);
                               // }
                            }
                        });
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
