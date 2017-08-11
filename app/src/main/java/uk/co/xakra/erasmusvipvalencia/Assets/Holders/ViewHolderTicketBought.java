package uk.co.xakra.erasmusvipvalencia.Assets.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.xakra.erasmusvipvalencia.Assets.ImageViewFromUrl;
import uk.co.xakra.erasmusvipvalencia.Data.MyTickets;
import uk.co.xakra.erasmusvipvalencia.R;

/**
 * Created by dabasra on 11/08/2017.
 */

public class ViewHolderTicketBought extends RecyclerView.ViewHolder {

    private ImageView ticketView;
    private TextView dayTextView;
    private TextView nameTextView;



    public ViewHolderTicketBought(View itemView) {
        super(itemView);
        this.ticketView = (ImageView) itemView.findViewById(R.id.cardTicketBoughtImage);
        this.dayTextView = (TextView) itemView.findViewById(R.id.cardTicketBoughtWhen);
        this.nameTextView = (TextView)itemView.findViewById(R.id.cardTicketBoughtName);
    }

    public void updateUI(MyTickets ticket){

        ImageViewFromUrl imageViewFromUrl = new ImageViewFromUrl(ticketView,ticket.getImg());
        imageViewFromUrl.execute();

        dayTextView.setText("Day: "+ticket.getDay()+"\n"+"Hour: "+ticket.getHour() );
        nameTextView.setText(ticket.getName());


    }


}
