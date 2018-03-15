package az.events.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import az.events.R;
import az.events.adapters.RecyclerSubCategories;
import az.events.datamodels.SubCategories;
import az.events.others.Constants;

public class FragmentSecond extends Fragment {

    public FragmentSecond() {}
    private View v;
    private RecyclerView recyclerView;
    private List<SubCategories> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addData();
        recyclerView.setAdapter(new RecyclerSubCategories(getActivity(),data, Constants.FROM_TIMELINE));
        return v;
    }

    private void addData(){
        SubCategories d1 = new SubCategories();
        SubCategories d2 = new SubCategories();
        d1.setIcon(R.drawable.party);
        d1.setPhoto(R.drawable.party1);
        d1.setName("Tomorrow will explode");
        d1.setDate("2016 Summer, 12:30");
        d1.setGoing("10+ going to event");
        d1.setVenue("Baku Boulevard Yacht, Neftchilar avenue, Azerbaijan");
        d1.setPrice("$200");

        d2.setIcon(R.drawable.party1);
        d2.setPhoto(R.drawable.party);
        d2.setName("Tomorrow will explode");
        d2.setDate("Friday night!");
        d2.setGoing("20+ going to event");
        d2.setVenue("Yacht");
        d2.setPrice("Free");

        data.add(d1);
        data.add(d2);
        data.add(d1);
        data.add(d2);
        data.add(d1);
    }

}
