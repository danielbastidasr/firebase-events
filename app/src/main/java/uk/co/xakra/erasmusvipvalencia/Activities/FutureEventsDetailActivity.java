package uk.co.xakra.erasmusvipvalencia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.File;
import java.math.BigDecimal;

import uk.co.xakra.erasmusvipvalencia.Assets.Functions;
import uk.co.xakra.erasmusvipvalencia.Assets.ImageViewFromUrl;
import uk.co.xakra.erasmusvipvalencia.Data.Services.DataService;
import uk.co.xakra.erasmusvipvalencia.Data.Tickets;
import uk.co.xakra.erasmusvipvalencia.R;

public class FutureEventsDetailActivity extends AppCompatActivity  implements  NumberPicker.OnValueChangeListener{

    private NumberPicker numberPicker ;
    private TextView tvPrice,tvDiscount,tvTotal,tvInfo,tvPlace,tvTime,tvDay;
    private Button bConfirm;
    private ShareActionProvider mShareActionProvider;
    private View screen;

    private final String TPRICE = " €/ticket";
    private final String TOTAL = "Total = ";
    private int price ;
    private int sumTotal;
    private int currentNTickets;
    private int discount;
    private Tickets ticket;
    private String info;
    private String name;
    private ImageView imageToolbar;
    private DataService dataService;
    private Intent intent;

    private AlertDialog loading,visitMyEvents;

    // PAYPAL

    private static final String paypalId = "AV_URd6oZCKoxsGu_4P_QTQ-SUEdBZ00Y5tP2uX_7FR-Nxp3JYPHtcGC34xq2a_iOApU5TlaQIGfsqxL";
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(paypalId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PAYPAL
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        //LOADING Window
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View dView = getLayoutInflater().inflate(R.layout.loading,null);
        mBuilder.setView(dView);
        loading = mBuilder.create();
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loading.setCanceledOnTouchOutside(false);
        // END LOADING

        ticket = getIntent().getParcelableExtra("TICKET");
        price = Integer.parseInt(ticket.getPrice());
        discount = 2;
        sumTotal = 0;
        currentNTickets = 0;
        name = ticket.getName();
        info = ticket.getInfo();
        dataService = DataService.getInstance();

        setContentView(R.layout.activity_future_events_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageToolbar = (ImageView) findViewById(R.id.imageViewToolbar);



        ImageViewFromUrl imageViewFromUrl = new ImageViewFromUrl(imageToolbar,ticket.getUrl());
        imageViewFromUrl.execute();

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setTitle(" ");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_btn);

        numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        numberPicker.setOnValueChangedListener(this);

        tvPrice = (TextView) findViewById(R.id.textViewPrice);
        tvTotal = (TextView) findViewById(R.id.textViewTotal);
        tvDiscount = (TextView) findViewById(R.id.textViewDiscount);
        tvInfo  = (TextView) findViewById(R.id.textViewInfo);
        tvPlace = (TextView) findViewById(R.id.textViewPlace);
        tvDay   = (TextView) findViewById(R.id.textViewDay);
        tvTime  = (TextView) findViewById(R.id.textViewTime);

        tvPrice.setText(price+TPRICE);
        tvDiscount.setText("Descuento "+"0€");
        tvTotal.setText(TOTAL+"0€");
        tvInfo.setText(info);
        tvPlace.setText(name);
        tvDay.setText(ticket.getDay());
        tvTime.setText(ticket.getHour());

        bConfirm = (Button) findViewById(R.id.buttonConfirmPayment);

        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              PayPalPayment payment = new PayPalPayment(new BigDecimal(sumTotal), "EUR", currentNTickets+"Tickets"+name,
                        PayPalPayment.PAYMENT_INTENT_SALE);


                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                // send the same configuration for restart resiliency
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intent, 0);

            }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.colapsingtoolbar);
        AppBarLayout bar = (AppBarLayout) findViewById(R.id.appBarLayout);
        bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(name+" " + "Detallado");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        //Screenshot
        screen = findViewById(R.id.futureEventsScreen);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.

        getMenuInflater().inflate(R.menu.share_action_option, menu);

        MenuItem item = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        new BackgroundTask().execute(menu);

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
           // stopService(new Intent(this, PayPalService.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // Call to update the share intent
    private void setShareIntent() {
        if (mShareActionProvider != null) {

            String shareBodyText = "Check it out. We should go to this amazing event"+"\n"+"It's just "+ticket.getPrice()+"€!!";
            intent = new Intent();
            intent.setType("image/png");
            Bitmap bitmap = Functions.getScreenShot(screen);
            File img = Functions.store(bitmap,"eventweshouldgo.png");

            Uri fileUri = FileProvider.getUriForFile(this, "uk.co.xakra.erasmusvipvalencia.fileprovider", img);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);

            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey buddy, this event looks awesome");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            intent.setAction(Intent.ACTION_SEND);

        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int newVal) {


        if(newVal > 4 ) {
            sumTotal = (newVal * price) - discount;
            tvDiscount.setText("Descuento " + String.valueOf(discount) + "€");
            tvTotal.setText(TOTAL + String.valueOf(sumTotal) + "€");
        }
        else {
            sumTotal = (newVal * price);
            tvDiscount.setText("Descuento "+"0€");
            tvTotal.setText(TOTAL + String.valueOf(sumTotal) + "€");
        }
        currentNTickets = newVal;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    String state = confirm.getProofOfPayment().getState();
                    if(state.equals("approved")){

                        dataService.getDataBase().buyTicket(ticket,currentNTickets);
                        //VISIT MY EVENTS
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                        final View vView = getLayoutInflater().inflate(R.layout.visitmyevents,null);
                        Button vButton = (Button) vView.findViewById(R.id.visitMyEventsButton);
                        vButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                visitMyEvents.dismiss();
                                FutureEventsDetailActivity.this.finish();
                            }
                        });
                        mBuilder.setView(vView);
                        visitMyEvents = mBuilder.create();
                        visitMyEvents.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        visitMyEvents.setCanceledOnTouchOutside(false);
                        visitMyEvents.show();
                        //VISIT MY EVENTS END

                    }
                    else{
                        Toast.makeText(this,"Ocurrió un error en el Pago, por favor intentelo de nuevo.",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }



    private class BackgroundTask extends AsyncTask<Menu, Void, Void> {

        @Override
        protected void onPreExecute(){
            loading.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Menu... menu) {


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setShareIntent();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mShareActionProvider.setShareIntent(intent);
            loading.dismiss();
            super.onPostExecute(result);

        }

    }

}
