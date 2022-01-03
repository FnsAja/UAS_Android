package com.example.internship;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.internship.config.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivityProject extends AppCompatActivity {

    ArrayList<Model> mItems;
    ProgressDialog pd;
    Integer id, idProj, access;
    String getDataa;
    TextView nama, deskripsi, startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

        pd = new ProgressDialog(DetailActivityProject.this);
        mItems = new ArrayList<>();

        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        idProj = x.getIntExtra("idproj", -1);
        access = x.getIntExtra("access", 0);

        if(access == 1){
            getDataa = Config.getDataDetail;
        }else {
            getDataa = Config.getDataDetailNonAdm;
        }
        loadjson();
    }

    private void loadjson(){
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest arrayRequest = new StringRequest(Request.Method.POST, getDataa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                pd.cancel();
                Log.d("Detail", "response : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    String temp1 = "";

                    for (int i = 0; i < arr.length(); i++){
                        JSONObject data = arr.getJSONObject(i);
                        nama.setText(data.getString("namaproj"));
                        startDate.setText(data.getString("startdate"));
                        endDate.setText(data.getString("enddate"));
                        deskripsi.setText(data.getString("descpro"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.e("error", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("iduser", id.toString());
                params.put("idproj", idProj.toString());

                return params;
            }
        };
        Controller.getInstance().addToRequestQueue(arrayRequest);
    }

    private void init(){
        nama = findViewById(R.id.textViewNamaProject);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        deskripsi = findViewById(R.id.projectDescription);
    }
}