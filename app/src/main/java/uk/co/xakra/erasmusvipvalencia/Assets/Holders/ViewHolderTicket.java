package uk.co.xakra.erasmusvipvalencia.Assets.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.xakra.erasmusvipvalencia.Assets.ImageViewFromUrl;
import uk.co.xakra.erasmusvipvalencia.Data.Tickets;
import uk.co.xakra.erasmusvipvalencia.R;

/**
 * Created by dabasra on 09/08/2017.
 */

public class ViewHolderTicket extends RecyclerView.ViewHolder{

    private ImageView ticketView;
    private TextView dayTextView;
    private TextView nameTextView;

    public ViewHolderTicket(View itemView) {
        super(itemView);
        this.ticketView = (ImageView) itemView.findViewById(R.id.cardTicketImage);
        this.dayTextView = (TextView) itemView.findViewById(R.id.cardTextViewDay);
        this.nameTextView = (TextView)itemView.findViewById(R.id.cardTicketName);
    }

    public void updateUI(Tickets ticket){

        ImageViewFromUrl imageViewFromUrl = new ImageViewFromUrl(ticketView,ticket.getUrl());
        imageViewFromUrl.execute();
        dayTextView.setText(ticket.getDay());
        nameTextView.setText(ticket.getName());
    }

}
