package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.internship.config.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements ProjectAdapter.onListListener {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<Model> mItems;
    Integer id, access;
    String getDataa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set selected nav
        bottomNavigationView.setSelectedItemId(R.id.project);
        //navigation onclick
        //noinspection deprecation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.project:
                        return true;
                    case R.id.intern:
                        Intent intent = new Intent(HomeActivity.this, Home1Activity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("access", access);
                        overridePendingTransition(0,0);
                        startActivity(intent);
                        return true;
                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        pd = new ProgressDialog(HomeActivity.this);
        mItems = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new ProjectAdapter(this, mItems, this);
        mRecyclerView.setAdapter(mAdapter);

        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        access = x.getIntExtra("access", 0);

        if(access == 1){
            getDataa = Config.getDataAdm;
        }else {
            getDataa = Config.getDataNonAdm;
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
                Log.d("Home", "response : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    for (int i  = 0; i < arr.length(); i++){
                        JSONObject data = arr.getJSONObject(i);
                        Model md = new Model();
                        // memanggil nama array yang kita buat
                        if (data.getString("namaproject") != "null"){
                            md.setNamaProject(data.getString("namaproject"));
                            md.setJumlahMember(data.getString("jumlah"));
                            mItems.add(md);
                        }
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
        Intent intent = new Intent(HomeActivity.this, DetailActivityProject.class);
        intent.putExtra("id", id);
        intent.putExtra("idproj", position + 1);
        intent.putExtra("access", access);
        startActivity(intent);
    }
}