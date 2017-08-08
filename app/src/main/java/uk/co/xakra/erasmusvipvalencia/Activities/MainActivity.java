package uk.co.xakra.erasmusvipvalencia.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import uk.co.xakra.erasmusvipvalencia.MainFragments.FutureEvents;
import uk.co.xakra.erasmusvipvalencia.MainFragments.MyEvents;
import uk.co.xakra.erasmusvipvalencia.MainFragments.MyProfile;
import uk.co.xakra.erasmusvipvalencia.R;

public class MainActivity extends AppCompatActivity implements FutureEvents.OnFragmentInteractionListener, MyEvents.OnFragmentInteractionListener, MyProfile.OnFragmentInteractionListener {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment newFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    newFragment = new FutureEvents();
                    break;

                case R.id.navigation_dashboard:

                    newFragment = new MyProfile();
                    break;

                case R.id.navigation_notifications:

                    newFragment = new MyEvents();
                    break;

            }

            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment newFragment = new FutureEvents();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
