package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.internship.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register;
    EditText txt_division, txt_phone, txt_address, txt_fullname, txt_email, txt_username, txt_password, txt_confirm_password;
    Spinner txt_status;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Config.registerPhp;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_register = (Button) findViewById(R.id.regist_btn_register);
        txt_fullname = (EditText) findViewById(R.id.regist_fullname_text);
        txt_username = (EditText) findViewById(R.id.regist_uname_text);
        txt_email = (EditText) findViewById(R.id.regist_email_text);
        txt_password = (EditText) findViewById(R.id.regist_pass_text);
        txt_confirm_password = (EditText) findViewById(R.id.regist_confpass_text);
        txt_address = (EditText) findViewById(R.id.regist_address_text);
        txt_division = (EditText) findViewById(R.id.regist_div_text);
        txt_phone = (EditText) findViewById(R.id.regist_phone_text);
        txt_status = (Spinner) findViewById(R.id.regist_status_spinner);

        btn_register.setOnClickListener(view -> {
            String username = txt_username.getText().toString();
            String password = txt_password.getText().toString();
            String confirm_password = txt_confirm_password.getText().toString();
            String email = txt_email.getText().toString();
            String fullname = txt_fullname.getText().toString();
            String address = txt_address.getText().toString();
            String division = txt_division.getText().toString();
            String status = txt_status.getSelectedItem().toString();
            String phone = txt_phone.getText().toString();

            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                checkRegister(username, password, confirm_password, email, fullname, address, division, status, phone);
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void checkRegister(final String username, final String password, final String confirm_password, final String email, final String fullname, final String address, final String division, final String status, final String phone) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Regis Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("fullname", fullname);
                params.put("username", username);
                params.put("password", password);
                params.put("confirm_password", confirm_password);
                params.put("address", address);
                params.put("email", email);
                params.put("phone", phone);
                params.put("division", division);
                params.put("status", status);


                return params;
            }

        };

        // Adding request to request queue
        Controller.getInstance().addToRequestQueue(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onClick(View view) {
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }
}