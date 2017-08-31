package uk.co.xakra.erasmusvipvalencia.Assets;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by dabasra on 10/08/2017.
 */

public class Functions {


    public static Bitmap getScreenShot(@NonNull View view) {
        // View screenView = view.getRootView();
        //screenView.setDrawingCacheEnabled(true);
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public static void storeIntern(Context context, @NonNull Bitmap bm, String fileName){

        String intStorageDirectory =context.getFilesDir().toString();
        File folder = new File(intStorageDirectory, "ErasmusVipTickets");

        if(!folder.exists())
            folder.mkdir();


        File file = new File(folder, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void storeMyImageIntern(Context context,@NonNull Bitmap bm, String fileName){

        String intStorageDirectory =context.getFilesDir().toString();
        File folder = new File(intStorageDirectory, "ErasmusVipMyImage");

        if(!folder.exists())
            folder.mkdir();

        else {
            File f = new File(folder, "myimage.png");
            if(f.exists())
                f.delete();
        }
        File file = new File(folder, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();

            Log.d("STORAGE", "storeMyImageIntern: "+"Succesfully stored my Image in a File");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Nullable
    public static File store(@NonNull Bitmap bm, @Nullable String fileName){


        if(isExternalStorageWritable()) {
            String extStorageDirectory = Environment.getExternalStorageDirectory()
                    .toString();
            File folder = new File(extStorageDirectory, "ErasmusVipTickets");

            if (!folder.exists())
                folder.mkdir();
            File file;
            if( fileName!= null)
                file = new File(folder,fileName);
            else
                file = new File(folder, "yourticket.png");

            try {
                FileOutputStream fOut = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
                return file;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }



    public static void shareImage(Context context, @NonNull File file ,@Nullable String msg){

        Uri fileUri = Uri.fromFile(file);
        Intent intent = new Intent();

        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if(msg != null)
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, msg);
        else
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "This is your ticket!");
        intent.setAction(Intent.ACTION_SEND);

        try {
            context.startActivity(Intent.createChooser(intent, "Share Ticket"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    public static Uri imageIntern(Context context, String namefile){
        String intStorageDirectory =context.getFilesDir().toString();
        File folder = new File(intStorageDirectory, "ErasmusVipMyImage");
        if(!folder.exists())
            folder.mkdir();

        File file = new File(folder,namefile);
        if(file.exists())
            return Uri.fromFile(file);

        return null;
    }

}
