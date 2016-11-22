package com.jdcompany.musicalinstruments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by malware on 11/21/16.
 */

public class SingleImageLoadTask extends AsyncTask<String,Void,Bitmap>
{
    ImageView imageView;
    public SingleImageLoadTask(ImageView imageView)
    {
        this.imageView  = imageView;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        try
        {
            URL url = new URL(strings[0]);
            HttpURLConnection connection =(HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return myBitmap;

        } catch (Exception e)
        {
            Log.d("Image Exception: " , e.getMessage().toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        imageView.setImageBitmap(bitmap);
    }
}
