package com.example.amrsaid.isksecurity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaid.isksecurity.app.AppConfig;
import com.example.amrsaid.isksecurity.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    EditText EdFristName;
    EditText EdLastName;
    EditText EdEmail;
    EditText EdPhone;
    EditText EEdPassword;
    String frist_name, last_name, mail, phone, password;
    private ProgressDialog pDialog;
    private SessionManager session;

    private static final String TAG = Registration.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EdFristName = (EditText) findViewById(R.id.fristname);
        EdLastName = (EditText) findViewById(R.id.lastname);
        EdEmail = (EditText) findViewById(R.id.email);
        EdPhone = (EditText) findViewById(R.id.phone);
        EEdPassword = (EditText) findViewById(R.id.password);
        session = new SessionManager(getApplicationContext());
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

    }

    public void registration(View v) {
        frist_name = EdFristName.getText().toString();
        last_name = EdLastName.getText().toString();
        mail = EdEmail.getText().toString();
        phone = EdPhone.getText().toString();
        password = EEdPassword.getText().toString();

        Registeration(mail, password, frist_name, last_name, phone, v);
    }

    private void Registeration(final String email, final String password,
                               final String frist_name, final String last_name, final String phone, final View V) {
        pDialog.setMessage("Registration ....");
        showDialog();
        final String URL_TO_REG = AppConfig.URL_REGISTER+ "?" + "email=" + email
                + "&password=" + password + "&first_name="+frist_name+"&last_name="+last_name+"&phone="+phone;
        Log.d(TAG, "URL Request: " +URL_TO_REG );

        StringRequest strRegisetr = new StringRequest(Request.Method.GET,
                URL_TO_REG, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Regstration Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("status");
                    // Check for error node in json
                    Log.d(TAG, "error Response: " + error);
                    if (error.equals("SUCCESS")) {
                        // user successfully logged in

                        GotoMain(jObj.getString("message"));
                    }else if (error.equals("FAILED")){
                        // Error in login. Get the error message
                       String errorMsg = jObj.getString("message");
                        Log.d(TAG, "error Response: " + errorMsg);
                       erroMassage(V, errorMsg);
                    }else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        erroMassage(V, errorMsg);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registeration Error: " + error.getMessage());
                erroMassage(V, error.getMessage());
                hideDialog();
            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(this).add(strRegisetr);

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void erroMassage(View v, String massage) {


        Snackbar snackbar = Snackbar
                .make(v, massage, Snackbar.LENGTH_LONG);
//                .setAction("RETRY", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
       /* YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(findViewById(R.id.test));
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(findViewById(R.id.test1));
*/
        // Changing message text color
//        snackbar.setActionTextColor(Color.RED);

// Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }


    public void GotoMain(String message) {

        // Launch login activity
        Intent intent = new Intent(Registration.this,
                LoginActivity.class);
        intent.putExtra("Registartion",message);
        startActivity(intent);
        finish();
    }
}
