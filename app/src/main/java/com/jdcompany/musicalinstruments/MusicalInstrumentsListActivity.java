package com.jdcompany.musicalinstruments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MusicalInstrumentsListActivity extends AppCompatActivity {

    RecyclerView musicalInstrumentsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musical_instruments);

        musicalInstrumentsView = (RecyclerView) findViewById(R.id.musical_instrument_list);
        musicalInstrumentsView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        musicalInstrumentsView.setLayoutManager(llm);

        BufferedReader reader = null;
        String jsonString = "";
        try{
            reader = new BufferedReader(new InputStreamReader(getAssets().open("MusicalInstrument.json"), "UTF-8"));
            String line;
            while((line = reader.readLine()) != null)
            {
                jsonString = jsonString+line;
            }
        } catch (Exception e)
        {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).getLevel(), "Cannot open JSON File");
        }
        final List<MusicalInstrument> musicalInstruments = new ArrayList<MusicalInstrument>();
        if(jsonString  != null)
        {
            GsonBuilder gsonBuilder = new GsonBuilder();
            try{
                JSONArray array = new JSONArray(jsonString);
                for(int i=0; i<array.length(); i++)
                {
                    MusicalInstrument instrument = gsonBuilder.create().fromJson(array.get(i).toString(), MusicalInstrument.class);
                    musicalInstruments.add(instrument);
                }
            }catch (Exception e)
            {
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).getLevel(), "Cannot create musical instument objects");
            }
        }

        MusicalInstrumentAdapter adapter = new MusicalInstrumentAdapter(musicalInstruments, this);
        musicalInstrumentsView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListner() {
            @Override
            public void onItemClick(MusicalInstrument item) {
                Intent intent = new Intent(MusicalInstrumentsListActivity.this,MusicalInstrumentDetailActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("name",item.getName());
                intent.putExtra("imageUrl",item.getImageUrl());
                startActivity(intent);

            }
        });

    }
}
