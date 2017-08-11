package uk.co.xakra.erasmusvipvalencia.Data.Services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uk.co.xakra.erasmusvipvalencia.Data.DataBase;
import uk.co.xakra.erasmusvipvalencia.Data.Tickets;

/**
 * Created by dabasra on 09/08/2017.
 */

public class DataService {
    private static final DataService ourInstance = new DataService();


    private DataBase dataBase;
    private Tickets tickets[];
    private DatabaseReference fbDataBaseRef;
    private static FirebaseAuth mAuth;

    public static DataService getInstance() {
        return ourInstance;
    }

    private DataService() {

        mAuth = FirebaseAuth.getInstance();

        //Initialize Tickets
        if(tickets==null){
            getTickets();
        }


    }

    public Tickets[] getTickets() {

        fbDataBaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("gs://erasmusvipvalencia-2e126.firebaseio.com");

        fbDataBaseRef.child("tickets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tickets = new Tickets[(int)dataSnapshot.getChildrenCount()];

                int i= 0;
                for (DataSnapshot election : dataSnapshot.getChildren()) {
                    tickets[i]= election.getValue(Tickets.class);
                    if (tickets[i] == null) {
                        Log.d("MYDATABASE", "User-GET" + " is unexpectedly null");
                    } else {
                        tickets[i].setId(election.getKey());
                        tickets[i].setDay((String) election.child("time").child("day").getValue());
                        tickets[i].setHour((String) election.child("time").child("hour").getValue());
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



    public void initializeDataBase(String userId){
        this.dataBase = new DataBase(userId);
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public FirebaseAuth getmAuth(){
        //Initialize Firebase
        return mAuth;
    }
}
