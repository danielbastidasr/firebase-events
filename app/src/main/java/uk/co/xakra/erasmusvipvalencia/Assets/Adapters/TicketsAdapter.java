package uk.co.xakra.erasmusvipvalencia.Assets.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.xakra.erasmusvipvalencia.Activities.MainActivity;
import uk.co.xakra.erasmusvipvalencia.Assets.Holders.ViewHolderTicket;
import uk.co.xakra.erasmusvipvalencia.Data.Tickets;
import uk.co.xakra.erasmusvipvalencia.R;

/**
 * Created by dabasra on 09/08/2017.
 */

public class TicketsAdapter extends RecyclerView.Adapter<ViewHolderTicket>{

    private Tickets[] tickets;

    public TicketsAdapter(Tickets[] tickets ) {
        this.tickets = tickets;
    }


    @Override
    public ViewHolderTicket onCreateViewHolder(ViewGroup parent, int viewType) {
        View ticketCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ticket,parent,false);
        return new ViewHolderTicket(ticketCard);
    }

    @Override
    public void onBindViewHolder(ViewHolderTicket holder, int position) {

        final Tickets ticket = tickets[position];
        holder.updateUI(ticket);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getMainActivity().loadFutureEventsDetailActivity(ticket);
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
