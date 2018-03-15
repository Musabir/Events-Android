package az.events.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import az.events.R;
import az.events.others.Fonts;

public class FragmentThird extends Fragment {

    public FragmentThird() {}
    private View view;
    private TextView t1,t2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket, container, false);

        t1 = (TextView) view.findViewById(R.id.text1);
        t2 = (TextView) view.findViewById(R.id.text2);
        t1.setTypeface(Fonts.getTypeface(getActivity(), Fonts.helvetiacNeueLight));
        t2.setTypeface(Fonts.getTypeface(getActivity(), Fonts.helvetiacNeueLight));
        
        return view;
    }

}
