package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.internship.config.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditInternActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;
    EditText txt_division, txt_phone, txt_about, txt_address, txt_fullname, txt_email, txt_username;
    Button btn_submit;
    Integer id, access, idIntern;

    private static final String TAG_ERROR = "error";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_intern);

        //inisialisasi semua komponen
        init();

        //mengeset edit text dengan data yang sudah ada
        setText();

        //mengambil data dari halaman sebelumnya
        Intent getData = getIntent();
        id = getData.getIntExtra("id", 0);
        access = getData.getIntExtra("access", 0);
        idIntern = getData.getIntExtra("idIntern", -1);

        btn_submit.setOnClickListener(view -> {
            String username = txt_username.getText().toString();
            String email = txt_email.getText().toString();
            String fullname = txt_fullname.getText().toString();
            String address = txt_address.getText().toString();
            String division = txt_division.getText().toString();
            String phone = txt_phone.getText().toString();
            String about = txt_about.getText().toString();

            //check apakah internet tersedia
            if (connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected()) {
                update(username, email, fullname, address, division, phone, about);
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setText(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getDataInternNonAdm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
                    JSONArray arr = new JSONArray(response);
                    JSONObject jsonObject = arr.getJSONObject(arr.length()-1);
                    txt_username.setText(jsonObject.getString("username"));
                    txt_email.setText(jsonObject.getString("email"));
                    txt_fullname.setText(jsonObject.getString("nama"));
                    txt_address.setText(jsonObject.getString("alamat"));
                    txt_division.setText(jsonObject.getString("divisi"));
                    txt_phone.setText(jsonObject.getString("notelp"));
                    txt_about.setText(jsonObject.getString("about"));
                } catch (JSONException e) {
                    //JSON exception
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VOLLEY exception
                progressDialog.cancel();
                Log.e(TAG_ERROR, error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                //masukan data yang akan di post disini berupa string
                Map<String, String> params = new HashMap<String, String>();
                params.put("iduser", idIntern.toString());

                return params;
            }
        };
        //menambahkan ke request queue untuk dipost ke alamat php yang dituju
        Controller.getInstance().addToRequestQueue(stringRequest);
    }

    private void update(final String username, final String email, final String fullname, final String address, final String division, final String phone, final String about) {
        progressDialog.setMessage("Updating ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.editIntern, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
                    //ambil data berupa JSON object
                    JSONObject jsonObject = new JSONObject(response);

                    //success disini merupakan TAG pembeda antara operasi yang sukses atau tidak
                    //jika 1 maka operasi sukses, jika 0 maka gagal
                    Integer success = jsonObject.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditInternActivity.this, Home1Activity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("access", access);
                        startActivity(intent);
                        finish();
                    } else {
                        //disini ditampilkan message kegagalan
                        Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON exception
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VOLLEY error
                progressDialog.cancel();
                Log.e(TAG_ERROR, error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                //masukan data yang akan di post disini berupa string
                Map<String, String> params = new HashMap<String, String>();
                params.put("idIntern", idIntern.toString());
                params.put("fullname", fullname);
                params.put("username", username);
                params.put("address", address);
                params.put("email", email);
                params.put("phone", phone);
                params.put("division", division);
                params.put("aboutme", about);

                return params;
            }
        };
        //menambahkan ke request queue untuk dipost ke alamat php yang dituju
        Controller.getInstance().addToRequestQueue(stringRequest);
    }

    public void init(){
        //inisiasi komponen
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        progressDialog = new ProgressDialog(EditInternActivity.this);
        btn_submit = findViewById(R.id.edit_btn_submit);
        txt_fullname = findViewById(R.id.edit_fullname_text);
        txt_email = findViewById(R.id.edit_email_text);
        txt_username = findViewById(R.id.edit_uname_text);
        txt_address = findViewById(R.id.edit_address_text);
        txt_division = findViewById(R.id.edit_div_text);
        txt_phone = findViewById(R.id.edit_phone_text);
        txt_about = findViewById(R.id.edit_about_text);
    }
}