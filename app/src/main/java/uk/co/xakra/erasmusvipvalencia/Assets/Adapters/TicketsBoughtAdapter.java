package uk.co.xakra.erasmusvipvalencia.Assets.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.xakra.erasmusvipvalencia.Activities.MainActivity;
import uk.co.xakra.erasmusvipvalencia.Assets.Holders.ViewHolderTicketBought;
import uk.co.xakra.erasmusvipvalencia.Data.MyTickets;
import uk.co.xakra.erasmusvipvalencia.R;

/**
 * Created by dabasra on 11/08/2017.
 */

public class TicketsBoughtAdapter extends RecyclerView.Adapter<ViewHolderTicketBought> {
    MyTickets tickets [];

    public TicketsBoughtAdapter(MyTickets[] tickets) {
        this.tickets = tickets;
    }

    @Override
    public ViewHolderTicketBought onCreateViewHolder(ViewGroup parent, int viewType) {

        View ticketCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ticket_bought,parent,false);
        return new ViewHolderTicketBought(ticketCard);

    }

    @Override
    public void onBindViewHolder(ViewHolderTicketBought holder, int position) {

        final MyTickets ticket = tickets[position];
        holder.updateUI(ticket);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getMainActivity().loadMyEventsDetailActivity(ticket);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tickets!= null){
            return tickets.length;
        }else return 0;
    }
}

