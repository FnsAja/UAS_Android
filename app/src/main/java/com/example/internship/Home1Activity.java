package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

public class Home1Activity extends AppCompatActivity implements ProjectAdapter.onListListener, InternAdapter.onListListener {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<User> mItems;
    Integer id, access;
    String getDataa;
    Button logout, project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        pd = new ProgressDialog(Home1Activity.this);
        mItems = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mManager = new LinearLayoutManager(Home1Activity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new InternAdapter(this, mItems, this);
        mRecyclerView.setAdapter(mAdapter);

        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        access = x.getIntExtra("access", 0);
        Log.d("ID + ACCESS : ", "ID " + id + "ACCESS" + access);
        if(access == 1){
            getDataa = Config.getDataIntern;
        }else {
            getDataa = Config.getDataInternNonAdm;
        }

        loadjson();

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            Intent intent = new Intent(Home1Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        project = findViewById(R.id.project);
        project.setOnClickListener(view -> {
            Intent intent = new Intent(Home1Activity.this, HomeActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
            finish();
        });
    }

    private void loadjson(){
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest arrayRequest = new StringRequest(Request.Method.POST, getDataa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.cancel();
                Log.d("Home", "response : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    for (int i  = 0; i < arr.length(); i++){
                        JSONObject data = arr.getJSONObject(i);
                        User usr = new User();
                        // memanggil nama array yang kita buat
                        usr.setNama(data.getString("nama"));
                        usr.setDivisi(data.getString("divisi"));
                        usr.setEmail(data.getString("email"));
                        mItems.add(usr);
                    }
                    mAdapter.notifyDataSetChanged();
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

                return params;
            }
        };
        Controller.getInstance().addToRequestQueue(arrayRequest);
    }

    @Override
    public void onListClick(int position) {
        Intent intent = new Intent(Home1Activity.this, DetailActivityIntern.class);
        intent.putExtra("id", id);
        intent.putExtra("idintern", position + 1);
        intent.putExtra("access", access);
        startActivity(intent);
    }
}