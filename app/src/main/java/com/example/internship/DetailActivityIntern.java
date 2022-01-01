package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class DetailActivityIntern extends AppCompatActivity {

    ArrayList<Model> mItems;
    ProgressDialog pd;
    Integer id, idIntern, access;
    String getDataa;
    TextView nama, email, notelp, alamat, aboutme, project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);
        init();

        pd = new ProgressDialog(DetailActivityIntern.this);
        mItems = new ArrayList<>();

        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        idIntern = x.getIntExtra("idintern", -1);
        access = x.getIntExtra("access", 0);

        if(access == 1){
            getDataa = Config.getDataDetailIntern;
        }else {
            getDataa = Config.getDataDetailInternNonAdm;
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
                pd.cancel();
                Log.d("Detail", "response : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    String temp = "";

                    for(int i = 0; i < arr.length(); i++){
                        JSONObject data = arr.getJSONObject(0);
                        nama.setText("Nama\t: " + data.getString("nama"));
                        email.setText("E-mail\t: " + data.getString("email"));
                        notelp.setText("NoTelp\t: " + data.getString("notelp"));
                        alamat.setText("Alamat\t: " + data.getString("alamat"));
                        aboutme.setText("About Me\t: " + data.getString("about"));
                        temp += "\n" + data.getString("namaproject");
                    }
                    project.setText("Project\t: " + temp);
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
                params.put("idintern", idIntern.toString());

                return params;
            }
        };
        Controller.getInstance().addToRequestQueue(arrayRequest);
    }

    private void init(){
        nama = findViewById(R.id.textViewNamaIntern);
        email = findViewById(R.id.textViewEmailIntern);
        notelp = findViewById(R.id.textViewNomorTelpon);
        alamat = findViewById(R.id.textViewAlamatIntern);
        aboutme = findViewById(R.id.textViewAboutMe);
        project = findViewById(R.id.textViewNamaProjectIntern);
    }
}