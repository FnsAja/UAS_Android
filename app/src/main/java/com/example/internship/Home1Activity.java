package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    ArrayList<User> user = new ArrayList<>();
    ArrayList<Integer> idIntern = new ArrayList<>();

    Integer id, access;
    String url;
    Button btn_logout, btn_project, btn_admin;

    private static final String TAG_ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        //inisialisasi semua komponen
        init();

        //mendapat data dari halaman sebelumnya
        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        access = x.getIntExtra("access", 0);

        //membedakan antara akses admin dan non admin
        if(access == 1){
            url = Config.getDataIntern;
        }else {
            url = Config.getDataInternNonAdm;
            btn_admin.setVisibility(View.INVISIBLE);
        }

        //load data berupa json kedalam activity
        loadData();

        btn_logout.setOnClickListener(view -> {
            Intent intent = new Intent(Home1Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

       btn_project.setOnClickListener(view -> {
            Intent intent = new Intent(Home1Activity.this, HomeActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
            finish();
        });

        btn_admin.setOnClickListener(view -> {
            Intent intent = new Intent(Home1Activity.this, Home2Activity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
            finish();
        });
    }

    private void loadData(){
        progressDialog.setMessage("Mengambil Data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
                    //mengambil data dalam bentuk array dari string
                    JSONArray arr = new JSONArray(response);

                    //mengisi setiap item dengan data yang tadi diambil
                    for (int i  = 0; i < arr.length(); i++){
                        JSONObject data = arr.getJSONObject(i);
                        User usr = new User();
                        usr.setNama(data.getString("nama"));
                        usr.setDivisi(data.getString("divisi"));
                        usr.setEmail(data.getString("email"));
                        user.add(usr);
                        idIntern.add(data.getInt("id"));
                    }
                    adapter.notifyDataSetChanged();
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
                params.put("iduser", id.toString());

                return params;
            }
        };
        //menambahkan ke request queue untuk dipost ke alamat php yang dituju
        Controller.getInstance().addToRequestQueue(arrayRequest);
    }

    @Override
    public void onListClick(int position) {
        //jika item di click, maka akan berpindah
        Intent intent = new Intent(Home1Activity.this, DetailActivityIntern.class);
        intent.putExtra("id", id);
        intent.putExtra("idintern", idIntern.get(position));
        intent.putExtra("access", access);
        startActivity(intent);
    }

    public void init(){
        //komponen inisiasi
        recyclerView = findViewById(R.id.recyclerView);
        btn_logout = findViewById(R.id.logout);
        btn_project = findViewById(R.id.project);
        btn_admin = findViewById(R.id.admin);
        progressDialog = new ProgressDialog(Home1Activity.this);
        layoutManager = new LinearLayoutManager(Home1Activity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new InternAdapter(this, user, this);
        recyclerView.setAdapter(adapter);
    }
}