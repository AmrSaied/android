package com.example.amrsaid.isksecurity.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaid.isksecurity.MainActivity;
import com.example.amrsaid.isksecurity.R;
import com.example.amrsaid.isksecurity.adater.RecyclerAdapter;
import com.example.amrsaid.isksecurity.app.AppConfig;
import com.example.amrsaid.isksecurity.helper.UserInformationManger;
import com.example.amrsaid.isksecurity.model.CardItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PinMassage extends Fragment {
    private static final String TAG = PinMassage.class.getSimpleName();
    private List<CardItemModel> cardItems = new ArrayList<>(30);
    private MainActivity mainActivity;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private RecyclerAdapter recyclerAdapter;
    private ProgressDialog pDialog;
    private UserInformationManger informationManger;


    public static Fragment newInstance(Context context) {
        PinMassage f = new PinMassage();

        return f;
    }

    public static int dpToPx(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dp * scale) + 0.5f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_pin_massage, null);
        floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab);
        informationManger = new UserInformationManger(getActivity().getApplicationContext());
        // Progress dialog
        pDialog = new ProgressDialog(getActivity().getApplicationContext());
        pDialog.setCancelable(false);
        floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        fixFloatingActionButtonMargin();

        recyclerView = (RecyclerView) root.findViewById(R.id.fab_recycler_view);

        setupRecyclerView();
        return root;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        recyclerView.setHasFixedSize(true);
        initializeCardItemList();
        recyclerAdapter = new RecyclerAdapter(cardItems);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initializeCardItemList() {
  //     CardItemModel cardItemModel;
    //    String[] cardTitles = getResources().getStringArray(R.array.card_titles);
      //  String[] cardContents = getResources().getStringArray(R.array.card_contents);
        //final int length = cardTitles.length;
        //for (int i = 0; i < length; i++) {
          //  cardItemModel = new CardItemModel(cardTitles[i], cardContents[i]);
            //cardItems.add(cardItemModel);
        //}

        StringRequest movieReq = new StringRequest (AppConfig.get_user_massage,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String  response) {
                        android.util.Log.d(TAG, response.toString());
//                        hidePDialog();
                        // Genre is json array
                        try {
                            CardItemModel cardItemModel;
                            JSONObject jObj = new JSONObject(response);
                            JSONArray data =  data = jObj.getJSONArray("data");
                            android.util.Log.d(TAG, data.toString());
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj  =  data.getJSONObject(i);
                              //  cardItemModel = new CardItemModel("From :"+obj.getJSONObject("fromUserId").getString("fullName"),obj.getString("messageContent"));
                             //  cardItems.add(cardItemModel);
                                addItem("From : "+obj.getJSONObject("fromUserId").getString("fullName"),obj.getString("messageContent"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hidePDialog();


            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(getActivity()).add(movieReq);
    }

    public void addItem(String title, String content) {
        android.util.Log.d(TAG, title + content);
        recyclerAdapter.cardItems.add(new CardItemModel(title, content));
        recyclerAdapter.notifyDataSetChanged();
    }

    public void removeItem() {
        recyclerAdapter.cardItems.remove(recyclerAdapter.cardItems.size() - 1);
        recyclerAdapter.notifyDataSetChanged();
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


    public void showCustomDialog() {
        // custom_dialog_pin_massage dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_pin_massage);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setTitle("Add User...");

        String str[]={"All","Muhammad Elzeiny","Ahmed Magdy","Mohamed Elsawaf","Amr Elsakaan","Belal Elprmawy",
                "Ahmed Sami","Ahmed Mostafa"};

        AutoCompleteTextView t1 = (AutoCompleteTextView)
                dialog.findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> adp=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,str);

        t1.setThreshold(1);
        t1.setAdapter(adp);



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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
