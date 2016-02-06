package com.example.amrsaid.isksecurity.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaid.isksecurity.R;
import com.example.amrsaid.isksecurity.adater.CustomUserAdapter;
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

public class AddUser extends Fragment {

    // Movies json url
    public  final String url = AppConfig.url_Log;
    // Log tag
    private static final String TAG = Log.class.getSimpleName();
//    public ProgressDialog pDialog;
    public List<Movie> movieList = new ArrayList<Movie>();
    public ListView listView;
    public CustomUserAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private UserInformationManger informationManger;
     ViewGroup root;

    public static Fragment newInstance(Context context) {
        AddUser f = new AddUser();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = (ViewGroup) inflater.inflate(R.layout.fragment_add_user, null);
        floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab);

        informationManger = new UserInformationManger(getActivity().getApplicationContext());
        floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        fixFloatingActionButtonMargin();
        listView = (ListView) root.findViewById(R.id.list);

        adapter = new CustomUserAdapter(getActivity(), movieList);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, day);
        listView.setAdapter(adapter);

//        pDialog = new ProgressDialog(getActivity());
//        // Showing progress dialog before making http request
//        pDialog.setMessage("Loading...");
//        pDialog.show();


        // Creating volley request obj
        StringRequest movieReq = new StringRequest (Request.Method.POST,AppConfig.url_get_users,
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
                                movie.setTitle(obj.getString("fullName"));
                                movie.setThumbnailUrl(AppConfig.URL+obj.getString("picture"));
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
                params.put("user_id", "2");
                params.put("group_id", "5");

                return params;
            }

        };
        // Adding request to request queue
        Volley.newRequestQueue(getActivity()).add(movieReq);
        return root;
    }


    public void fixFloatingActionButtonMargin() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams)
                    floatingActionButton.getLayoutParams();
            // get rid of margins since shadow area is now the margin
            p.setMargins(0, 0, dpToPx(getActivity(), 8), 0);
            floatingActionButton.setLayoutParams(p);
        }
    }

    public int dpToPx(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }

//    private void hidePDialog() {
//        if (pDialog != null) {
//            pDialog.dismiss();
//            pDialog = null;
//        }
//    }

    public void showCustomDialog() {
        // custom_dialog_pin_massage dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_add_user);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.setTitle("Add User...");

        String str[]={"Muhammad Elzeiny","Ahmed Magdy","Mohamed Elsawaf","Amr Elsakaan","Belal Elprmawy",
                "Ahmed Sami","Ahmed Mostafa"};

        AutoCompleteTextView t1 = (AutoCompleteTextView)
                dialog.findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> adp=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,str);

        t1.setThreshold(1);
        t1.setAdapter(adp);

        Spinner spnr;

        String[] celebrities = {
                "Master",
                "Slave ",
                "Per One Hour",
                "per One Day",
                "Per One Month"
        };

        spnr = (Spinner)dialog.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, celebrities);

        spnr.setAdapter(adapter);

        Button dialogButton = (Button)dialog.getWindow().findViewById(R.id.dialogButtonOK);
        Button button = (Button)dialog.getWindow().findViewById(R.id.button);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


}

