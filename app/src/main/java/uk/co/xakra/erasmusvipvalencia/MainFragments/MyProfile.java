package uk.co.xakra.erasmusvipvalencia.MainFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

import uk.co.xakra.erasmusvipvalencia.Data.Services.DataService;
import uk.co.xakra.erasmusvipvalencia.R;

import static uk.co.xakra.erasmusvipvalencia.Assets.Functions.imageIntern;
import static uk.co.xakra.erasmusvipvalencia.Assets.Functions.storeMyImageIntern;


public class MyProfile extends Fragment {


    private EditText newName;
    private TextView nameV;
    private ImageView newImage;
    private ProgressBar progressBar;
    private boolean changedImage;
    private int PICK_IMAGE;
    private Bitmap bm;
    private Button button;
    private String name;
    private static FirebaseAuth mAuth;


    public MyProfile() {
        // Required empty public constructor
    }


    public static MyProfile newInstance() {
        MyProfile fragment = new MyProfile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        changedImage = false;
        bm = null;
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBarChangeImageName);
        progressBar.setVisibility(View.INVISIBLE);

        nameV = (TextView) view.findViewById(R.id.userName);
        nameV.setText("Welcome "+ mAuth.getCurrentUser().getDisplayName());
        newName  = (EditText)view.findViewById(R.id.nextName);
        newImage = (ImageView)view.findViewById(R.id.imagePersonal);
        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changedImage = true;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        Uri imgUri = imageIntern(getActivity(),"myimage.png");
        if(imgUri != null) {
            Picasso.with(getActivity())
                    .load(imgUri)
                    .skipMemoryCache()
                    .into(newImage);

        }


        button = (Button) view.findViewById(R.id.submitChangeImageName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = newName.getText().toString();

                if (bm != null && !name.isEmpty()){

                    storeMyImageIntern(getActivity(),bm,"myimage.png");
                    new Thread(new Runnable() {
                        public void run(){
                            //change name in data base
                            changeNameUser(name);

                        }
                    }).start();
                    nameV.setText("Welcome "+name);
                    new BackgroundTask().execute();

                }
                else if(bm != null){
                    storeMyImageIntern(getActivity(),bm,"myimage.png");
                    new BackgroundTask().execute();
                }
                else if(!name.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);

                    new Thread(new Runnable() {
                        public void run(){
                            //change name in data base
                            changeNameUser(name);
                        }
                    }).start();

                    nameV.setText("Welcome "+name);
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }


            InputStream inputStream = null;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bm = BitmapFactory.decodeStream(inputStream);
            newImage.setImageBitmap(bm);

        }
    }




    private void changeNameUser(String name){
        //CHANGE USER NAME
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        mAuth.getCurrentUser();
        mAuth.getCurrentUser().updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("CHANGEUSER", "User profile updated.");
                        }
                    }
                });
    }


    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            newName.setText(name);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

}
