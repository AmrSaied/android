package com.example.amrsaid.isksecurity.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaid.isksecurity.R;
import com.example.amrsaid.isksecurity.adater.CustomListAdapter;
import com.example.amrsaid.isksecurity.app.AppConfig;
import com.example.amrsaid.isksecurity.helper.UserInformationManger;
import com.example.amrsaid.isksecurity.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Log extends Fragment {
    // Log tag

    private  final String TAG = Log.class.getSimpleName();



    // Movies json url
    public  final String url = AppConfig.url_Log;
    public ProgressDialog pDialog;
    public List<Movie> movieList = new ArrayList<Movie>();
    public ListView listView;
    public CustomListAdapter adapter;
    private UserInformationManger informationManger;


    public static Fragment newInstance(Context context) {
        Log f = new Log();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_log, null);
        listView = (ListView) root.findViewById(R.id.list);
        informationManger = new UserInformationManger(getActivity().getApplicationContext());
       adapter = new CustomListAdapter(getActivity(), movieList);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, day);
      listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();



        // Creating volley request obj
        StringRequest movieReq = new StringRequest (Request.Method.POST,AppConfig.get_Log,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        android.util.Log.d(TAG, response.toString());
//                        hidePDialog();
                        // Genre is json array
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray data =  data = jObj.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj  =  data.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getJSONObject("userOfGroupDTO").getString("fullName"));
                                movie.setThumbnailUrl(AppConfig.URL+obj.getJSONObject("userOfGroupDTO").getString("picture"));
                                // adding movie to movies array
                                movieList.add(movie);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Parsing json
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hidePDialog();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", informationManger.getUserId());
                params.put("group_id", informationManger.getGroupId());

                return params;
            }

        };;
        hidePDialog();
        // Adding request to request queue
        Volley.newRequestQueue(getActivity()).add(movieReq);
        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
