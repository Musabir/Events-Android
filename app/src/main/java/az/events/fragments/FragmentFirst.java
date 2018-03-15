package az.events.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import az.events.R;
import az.events.adapters.RecyclerFilter;
import az.events.adapters.RecyclerFragmentFirst;
import az.events.adapters.RecyclerSubCategories;
import az.events.datamodels.SubCategories;
import az.events.others.Constants;
import az.events.others.CustomVolleyRequestQueue;

public class FragmentFirst extends Fragment {

    //How it works
    /*
    When this fragment opens first time getCategoryApi called 1 time and sets response data to arraylists and shows
    when you pull to refresh arraylists get cleared and getcategory api called again with notifyDataSetChanged
     */
    public FragmentFirst() {
    }

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private List<String> images = new ArrayList<>();
    private List<String> ids = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<SubCategories> data = new ArrayList<>();

    private TextInputEditText inputSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addData();
        setData(recyclerView);
        inputSearch = (TextInputEditText) v.findViewById(R.id.inputSearch);
        // recyclerView.setAdapter(new RecyclerSubCategories(getActivity(),data, Constants.FROM_TIMELINE));
        getCategoryAPI(recyclerView);
        swipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(recyclerView);
                swipeRefresh.setRefreshing(false);

            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence constraint, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //   MainActivity.this.adapter.getFilter().filter(cs);

                     List<String> images_search = new ArrayList<>();
                     List<String> ids_search = new ArrayList<>();
                     List<String> names_search = new ArrayList<>();
                    for(int i=0;i<names.size();i++){
                        String s = names.get(i).toLowerCase();
                        String c = constraint.toString().toLowerCase();
                        if(s.contains(c))
                        {
                            images_search.add(images.get(i));
                            ids_search.add(ids.get(i));
                            names_search.add(names.get(i));
                        }
                }
                recyclerView.setAdapter(new RecyclerFragmentFirst(getActivity(), ids_search, names_search, images_search));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        return v;
    }

    private void addData() {
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
        for (int i = 0; i < 4; i++) {
            ids.add("21");
            names.add(i + "");
            images.add("musa");
        }
    }

    private void getCategoryAPI(final RecyclerView recyclerView) {
        String urlCategories = "http://events.moko.az/api/getCategory?language=";
        String lang = "az";
        RequestQueue mRequestQueue = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, urlCategories + lang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        clearData();
                        try {
                            JSONObject convert = new JSONObject(response);
                            if (convert.getString("code").equals("200")) {
                                JSONArray data = convert.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject category = (JSONObject) data.get(i);
                                    ids.add(category.getString("_id"));
                                    names.add(category.getString("name"));
                                    images.add(category.getString("images"));
                                }
                                if (recyclerView.getAdapter() == null) {
                                    setData(recyclerView);
                                } else {
                                    refreshData(recyclerView);
                                }
                                Toast.makeText(getActivity(), names.size() + "", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        mRequestQueue.add(request);
    }

    private void clearData() {
        ids.clear();
        names.clear();
        images.clear();
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    private void setData(RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecyclerFragmentFirst(getActivity(), ids, names, images));
    }

    private void refreshData(RecyclerView recyclerView) {
        recyclerView.getAdapter().notifyDataSetChanged();

    }

}
