package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.internship.config.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterProject1 extends AppCompatActivity {

    Integer id, access, success;
    Spinner spinner;
    ArrayList<String> internName = new ArrayList<>();
    Button btn_register, btn_complete;
    EditText jobDesc;
    ProgressDialog pDialog;
    ConnectivityManager conMgr;

    private static final String TAG = RegisterProject.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_project1);

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

        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        access = x.getIntExtra("access", 0);

        loadjson();

        btn_complete = findViewById(R.id.proj_btn_selesai);
        btn_register = findViewById(R.id.proj_btn_register);
        jobDesc = findViewById(R.id.proj_jobdesc_text);
        spinner = findViewById(R.id.proj_name_spinner);

        btn_register.setOnClickListener(view -> {
            Integer idIntern = spinner.getSelectedItemPosition() + 1;
            String jobdesc = jobDesc.getText().toString();

            conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            {
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(idIntern.toString(), jobdesc);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_complete.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterProject1.this, Home2Activity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
            finish();
        });
    }

    private void loadjson(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Adding Project ...");
        showDialog();
        StringRequest str = new StringRequest(Config.spinnerName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Hasil Nama: " + response);
                hideDialog();
                try {
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++){
                        JSONObject jsonObject = arr.getJSONObject(i);
                        internName.add(jsonObject.getString("nama"));
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, internName);
                    spinner.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error : ", error.getMessage());
            }
        });
        Controller.getInstance().addToRequestQueue(str);
    }

    private void checkRegister(final String idIntern, final String JobDesc) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Adding Project ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.addDetailProject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        Log.e("Successfully Added!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Add Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idIntern", idIntern);
                params.put("jobDesc", JobDesc);

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
}