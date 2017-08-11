package uk.co.xakra.erasmusvipvalencia.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import uk.co.xakra.erasmusvipvalencia.Data.MyTickets;
import uk.co.xakra.erasmusvipvalencia.R;

public class MyEventDetailActivity extends AppCompatActivity {

    private MyTickets ticket;
    private TextView day,hour,info,name,quantity,reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ticket = (MyTickets) getIntent().getParcelableExtra("TICKET");

        setContentView(R.layout.activity_my_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        day     =(TextView) findViewById(R.id.textViewDay);
        hour    =(TextView) findViewById(R.id.textViewTime);
        info    =(TextView) findViewById(R.id.textViewInfo);
        name    =(TextView) findViewById(R.id.textViewPlace);
        quantity=(TextView) findViewById(R.id.textViewTickets);
        reference   =(TextView) findViewById(R.id.idTextView);

        day.setText(ticket.getDay());
        hour.setText(ticket.getHour());
        info.setText(ticket.getInfo());
        name.setText(ticket.getName());
        quantity.setText(ticket.getQuantity());
        reference.setText(ticket.getId());


    }

}
