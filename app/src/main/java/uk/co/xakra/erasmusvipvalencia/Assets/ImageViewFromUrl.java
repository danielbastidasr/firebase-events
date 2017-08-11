package uk.co.xakra.erasmusvipvalencia.Assets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dabasra on 09/08/2017.
 */

public class ImageViewFromUrl extends AsyncTask<Void, Void, Drawable> {
    private final ImageView imageView;
    private final String url;
    public ImageViewFromUrl(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }


    @Override
    protected Drawable doInBackground(Void... voids) {
        Drawable drawable = null;
        try {
            drawable = drawable_from_url(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return drawable;
    }


    @Override
    protected void onPostExecute(Drawable drawable) {
        super.onPostExecute(drawable);
        if(drawable != null){
            imageView.setImageDrawable(drawable);
        }
        else Log.d("es null", "onPostExecute: ");
    }

    public Drawable drawable_from_url(String url)  throws  java.io.IOException  {
        Bitmap bm;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        bm = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(null,bm);
    }


}
