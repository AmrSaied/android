package com.example.amrsaid.isksecurity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.amrsaid.isksecurity.app.AppConfig;
import com.example.amrsaid.isksecurity.helper.SessionManager;
import com.example.amrsaid.isksecurity.helper.UserInformationManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    // LogCat tag
    private static final String TAG = LoginActivity.class.getSimpleName();
    // CallbackManager callbackManager;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private UserInformationManger informationManger;
    // private LoginButton loginButton;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        //    callbackManager = CallbackManager.Factory.create();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);


//This line for define To motion
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.test1);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.test);
        // Session manager
        session = new SessionManager(getApplicationContext());
        informationManger = new UserInformationManger(getApplicationContext());

//            Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (!session.isLoggedIn()) {
            //LoginManager.getInstance().logOut();
        }
        // FaceBookLogin();


        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));


    }

    class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.email:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
            }
        }
    }

    public void login(View V) {
        submitForm();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        if (email.trim().length() > 0 && password.trim().length() > 0) {
            // login user

            checkLogin(email, password, V);
            Log.d(TAG, "Login Response: " + email + password);

        } else {
            erroMassage(V, "Please enter the credentials!");
            // Prompt user to enter credentials

        }
    }

    private boolean submitForm() {


        if (!validateEmail()) {
            return true;
        }

        if (!validatePassword()) {
            return true;
        }

        return false;


    }

    public void Register(View V) {
        Intent i = new Intent(getApplicationContext(),
                Registration.class);
        startActivity(i);
        finish();

    }



    private void checkLogin(final String email, final String password, final View V) {
        HashMap<String, String> params = new HashMap<String, String>();
        //params.put("token", "AbCdEfGh123456");
        JsonObjectRequest req = new JsonObjectRequest(AppConfig.URL_LOGIN,  new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       Log.d(TAG, response.toString());
                        try{
                        if(response.getString("status").equals("SUCCESS")){

                            informationManger.setFullName(response.getJSONObject("data").getString("fullName").toString());
                            informationManger.setProfilePicture(AppConfig.URL + response.getJSONObject("data").getString("picture").toString());
                            informationManger.setEmail(response.getJSONObject("data").getString("email").toString());
                            informationManger.setUserId(response.getJSONObject("data").getString("id").toString());
                            informationManger.setUserId(response.getJSONObject("data").getString("id").toString());
                            session.setLogin(true);
                            GotoMain();


                        }else {
                            erroMassage(V, response.getString("message"));
                        }

                        }catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error
                Log.d(TAG,error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("email", email);
                headers.put("password", password);
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(req);




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
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(findViewById(R.id.test));
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(findViewById(R.id.test1));

        // Changing message text color
//        snackbar.setActionTextColor(Color.RED);

// Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void GotoMain() {
        session.setLogin(true);
        // Launch login activity
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);


        startActivity(intent);
        finish();
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}

