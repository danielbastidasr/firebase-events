package uk.co.xakra.erasmusvipvalencia.Data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dabasra on 09/08/2017.
 */

public class DataBase {

    private String userId;
    private MyTickets tickets[];
    private DatabaseReference fbDataBaseRef;


    public DataBase(String id){

        this.userId = id;
        fbDataBaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("gs://erasmusvipvalencia-2e126.firebaseio.com");
        getMyTickets();
    }

    public MyTickets[] getMyTickets() {
        fbDataBaseRef.child("users").child(userId).child("tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tickets = new MyTickets[(int)dataSnapshot.getChildrenCount()];

                int i= 0;
                for (DataSnapshot election : dataSnapshot.getChildren()) {
                    String img,quantity,day,hour,info,name,id;

                    id = (election.getKey());
                    day = ((String) election.child("time").child("day").getValue());
                    hour = ((String) election.child("time").child("hour").getValue());
                    img = ((String) election.child("img").getValue());
                    quantity = ((String) election.child("quantity").getValue());
                    info = ((String) election.child("info").getValue());
                    name = ((String) election.child("name").getValue());

                    tickets[i]= new MyTickets(img,quantity,day,hour,info,name,id);

                    if (tickets[i] == null) {
                        Log.d("MYDATABASE", "User-GET" + userId + " is unexpectedly null");
                    } else {

                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tickets;
    }


    public void buyTicket(Tickets ticket, int nTickets){
        MyTickets ticketBought = new MyTickets(ticket.getUrl(),nTickets+"",ticket.getDay(),ticket.getHour(),ticket.getInfo(),ticket.getName(),ticket.getId());

        Map<String, Object> childUpdates = new HashMap<>();
            //MyTicket class
            //Time
            childUpdates.put("/users/" + userId +"/tickets/"+ticketBought.getId()+"/time/day", ticketBought.getDay());
            childUpdates.put("/users/" + userId +"/tickets/"+ticketBought.getId()+"/time/hour", ticketBought.getHour());
            //Info
            childUpdates.put("/users/" + userId +"/tickets/"+ticketBought.getId()+"/info", ticketBought.getInfo());
            //Name
            childUpdates.put("/users/" + userId +"/tickets/"+ticketBought.getId()+"/name", ticketBought.getName());
            //Image
            childUpdates.put("/users/" + userId +"/tickets/"+ticketBought.getId()+"/img", ticketBought.getImg());
            //Quantity
            childUpdates.put("/users/" + userId +"/tickets/"+ticketBought.getId()+"/quantity",ticketBought.getQuantity());

        fbDataBaseRef.updateChildren(childUpdates);

        getMyTickets();


    }
}
