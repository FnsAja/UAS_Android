package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.internship.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterProject extends AppCompatActivity {

    DatePicker startDate, endDate;
    EditText projName, projDesc;
    Button btn_register;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    Integer id, access, success;

    private static final String TAG = RegisterProject.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_project);

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

        projName = findViewById(R.id.proj_projname_text);
        projDesc = findViewById(R.id.proj_desc_text);
        startDate = findViewById(R.id.proj_start_date);
        endDate = findViewById(R.id.proj_end_date);
        btn_register = findViewById(R.id.proj_btn_register);

        btn_register.setOnClickListener(view -> {
            String ProjName = projName.getText().toString();
            String ProjDesc = projDesc.getText().toString();

            SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
            SimpleDateFormat afterformat = new SimpleDateFormat("dd MMMM yyyy");
            String S_Date = "" + startDate.getDayOfMonth();
            String S_Month = "" + startDate.getMonth();
            String E_Date = "" + endDate.getDayOfMonth();
            String E_Month = "" + endDate.getMonth();

            if (startDate.getDayOfMonth() < 10){
                S_Date = "0" + startDate.getDayOfMonth();
            }
            if (startDate.getMonth() < 9){
                S_Month = "0" + (startDate.getMonth() + 1);
            }
            if (endDate.getDayOfMonth() < 10){
                E_Date = "0" + endDate.getDayOfMonth();
            }
            if (endDate.getMonth() < 9){
                E_Month = "0" + (endDate.getMonth() + 1);
            }

            String StartDate = S_Date + " " + S_Month + " " + startDate.getYear();
            String EndDate = E_Date + " " + E_Month + " " + endDate.getYear();

            Date localDate1 = null, localDate2 = null;
            try {
                localDate1 = format.parse(StartDate);
                localDate2 = format.parse(EndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (localDate1.compareTo(localDate2) <= 0){
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(ProjName, ProjDesc, afterformat.format(localDate1), afterformat.format(localDate2));
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Start Date harus lebih kecil dari End Date", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void checkRegister(final String ProjName, final String ProjDesc, final String StartDate, final String EndDate) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Adding Project ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Config.addProject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        Log.e("Successfully Added!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterProject.this, Home2Activity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("access", access);
                        startActivity(intent);
                        finish();
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
                params.put("ProjName", ProjName);
                params.put("ProjDesc", ProjDesc);
                params.put("StartDate", StartDate);
                params.put("EndDate", EndDate);

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