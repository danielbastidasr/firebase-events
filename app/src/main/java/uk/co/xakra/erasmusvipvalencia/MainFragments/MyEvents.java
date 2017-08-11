package uk.co.xakra.erasmusvipvalencia.MainFragments;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import uk.co.xakra.erasmusvipvalencia.Assets.Adapters.TicketsBoughtAdapter;
import uk.co.xakra.erasmusvipvalencia.Data.MyTickets;
import uk.co.xakra.erasmusvipvalencia.Data.Services.DataService;
import uk.co.xakra.erasmusvipvalencia.R;


public class MyEvents extends Fragment {

    private View viewFragment;
    private OnFragmentInteractionListener mListener;

    public MyEvents() {
        // Required empty public constructor
    }


    public static MyEvents newInstance() {
        MyEvents fragment = new MyEvents();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewFragment=inflater.inflate(R.layout.fragment_my_events, container, false);
        RecyclerView recyclerView = (RecyclerView) viewFragment.findViewById(R.id.reciclerView);
        recyclerView.setHasFixedSize(true);
        //          WE SET ORIENTATION OF THE RECYCLERVIEW AS VERTICAL
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //          WE ADD SOME SEPARATION AMONG THE TICKETS
        recyclerView.addItemDecoration(new SpaceItems(20));
        //          GET THE TICKETS TO PUT IN THE ADAPTER
        DataService dataService = DataService.getInstance();
        final MyTickets tickets [];
        tickets = dataService.getDataBase().getMyTickets();
        //          CREATE AN ADAPTER
        TicketsBoughtAdapter ticketsBoughtAdapter = new TicketsBoughtAdapter(tickets);
        recyclerView.setAdapter(ticketsBoughtAdapter);


        return viewFragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class SpaceItems extends RecyclerView.ItemDecoration{

        private final int spacer;

        SpaceItems(int spacer) {
            this.spacer = spacer;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = spacer;
        }
    }
}
