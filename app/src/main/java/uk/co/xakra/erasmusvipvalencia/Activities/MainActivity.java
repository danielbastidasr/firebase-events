package uk.co.xakra.erasmusvipvalencia.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import uk.co.xakra.erasmusvipvalencia.Data.MyTickets;
import uk.co.xakra.erasmusvipvalencia.Data.Tickets;
import uk.co.xakra.erasmusvipvalencia.MainFragments.FutureEvents;
import uk.co.xakra.erasmusvipvalencia.MainFragments.MyEvents;
import uk.co.xakra.erasmusvipvalencia.MainFragments.MyProfile;
import uk.co.xakra.erasmusvipvalencia.R;

public class MainActivity extends AppCompatActivity implements FutureEvents.OnFragmentInteractionListener, MyEvents.OnFragmentInteractionListener {

    //TicketHolder
    private static MainActivity mainActivity;

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

        mainActivity = this;

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

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    // VIEW HOLDERS

    public void loadFutureEventsDetailActivity(Tickets ticketSelected){

        Log.d("ticket", "this is the ticket = "+ticketSelected.getName());
        Intent intent = new Intent(this,FutureEventsDetailActivity.class);
        intent.putExtra("TICKET",ticketSelected);
        startActivity(intent);
    }

    public void loadMyEventsDetailActivity(MyTickets ticket){

        Log.d("ticket", "this is the ticket = "+ticket.getName());
        Intent intent = new Intent(this,MyEventDetailActivity.class);
        intent.putExtra("TICKET",ticket);
        startActivity(intent);
    }
}
