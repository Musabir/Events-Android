package az.events.others;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Requests {

    public static String urlCategories = "http://events.moko.az/api/getCategory?language=";
    public static String lang = "az";
    public static RequestQueue mRequestQueue;

    private static List<String> texts = new ArrayList<>();
    private static List<String> id = new ArrayList<>();

    public static void requestCategories(final Context context, final String type){

        if (type.equals("first")){
//            FragmentFirst.swipeRefresh.post(new Runnable() {
//                @Override
//                public void run() {
//                    FragmentFirst.swipeRefresh.setRefreshing(true);
//                }
//            });
        }

        mRequestQueue = CustomVolleyRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        JsonObjectRequest requestCat = new JsonObjectRequest(Request.Method.GET, urlCategories + lang, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        texts.clear();
//                        id.clear();
//
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            int i;
//                            for (i = 0;i<jsonArray.length(); i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                id.add(jsonObject.getString("_id"));
//                                texts.add(jsonObject.getString("name"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        FragmentFirst.swipeRefresh.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                FragmentFirst.swipeRefresh.setRefreshing(false);
//                            }
//                        });
//
//                        if (type.equals("first")){
//                            FragmentFirst.recyclerView.setAdapter(new RecyclerFragmentFirst(context,FragmentFirst.photos, FragmentFirst.icons,texts,id));
//                        }
//                        else if (type.equals("refresh")){
//                            FragmentFirst.recyclerView.getAdapter().notifyDataSetChanged();
//                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("onErrorResponse", error.getMessage());
                    }
                }
        );

        mRequestQueue.add(requestCat);
    }
}
