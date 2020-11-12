package com.example.nizamify;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    View oncekiView;
    ListView listView;
    Drawable orjArkaFon;
    TextView textBilgi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Field[] fields = R.raw.class.getFields();
        String[] mediaList = new String[fields.length];
        for (int count = 0; count < fields.length; count++)
            mediaList[count] = fields[count].getName();

        listView = (ListView) findViewById(R.id.listView2);
        textBilgi = (TextView) findViewById(R.id.textBilgi1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1,
                mediaList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long id) {
                if (oncekiView != null)
                    ((TextView) oncekiView).setBackgroundDrawable(orjArkaFon);
                orjArkaFon = ((TextView) v).getBackground();

                String uri = "android.resource://" + getPackageName() + "/raw/"
                        + ((TextView) v).getText();

                Intent intent = new Intent(MainActivity.this,Player.class );
                intent.putExtra("Uri",uri);
                intent.putExtra("SoundName",((TextView) v).getText());
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}