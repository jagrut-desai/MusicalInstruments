package com.jdcompany.musicalinstruments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MusicalInstrumentDetailActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musical_intrument_item_detail);


        String instumentName = getIntent().getStringExtra("name");
        String instrumentId = getIntent().getStringExtra("id");
        String instrumentImageUrl = getIntent().getStringExtra("imageUrl");

        ImageView musicalInstrumentImage = (ImageView) findViewById(R.id.musical_instrument_image);
        SingleImageLoadTask singleImageLoadTask = new SingleImageLoadTask(musicalInstrumentImage);
        singleImageLoadTask.execute(instrumentImageUrl);

        TextView musicalInstumentName = (TextView) findViewById(R.id.musical_instrument_name);
        TextView musicalInstumentHeadText = (TextView) findViewById(R.id.headerText);
        TextView musicalInstumentDetailText = (TextView) findViewById(R.id.detailText);

        Toolbar toolbar  = (Toolbar) findViewById(R.id.musical_instrument_toolbar);
        toolbar.setTitle(instumentName);

//        musicalInstumentName.setText(instumentName);

        BufferedReader reader = null;
        String jsonString = "";
        try{
            reader = new BufferedReader(new InputStreamReader(getAssets().open(instrumentId+".json"), "UTF-8"));
            String line;
            while((line = reader.readLine()) != null)
            {
                jsonString = jsonString+line;
            }
        } catch (Exception e)
        {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).getLevel(), "Cannot open JSON File");
        }

        MusicalInstrumentDetail musicalInstrumentdata = new MusicalInstrumentDetail();
        if(jsonString  != null)
        {
            GsonBuilder gsonBuilder = new GsonBuilder();
            try{
                 musicalInstrumentdata = gsonBuilder.create().fromJson(jsonString, MusicalInstrumentDetail.class);
            }catch (Exception e)
            {
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).getLevel(), e.getMessage());
            }
        }

        musicalInstumentHeadText.setText(musicalInstrumentdata.data.get(0).name);
        StringBuilder detailTextBuilder = new StringBuilder();

        for(int i=0;i<musicalInstrumentdata.data.get(0).data.size();i++)
        {
            detailTextBuilder.append(musicalInstrumentdata.data.get(0).data.get(i));
            detailTextBuilder.append("\n\n");
        }

        musicalInstumentDetailText.setText(detailTextBuilder.toString());
    }


}
