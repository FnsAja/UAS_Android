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
import android.widget.Toast;

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

public class HomeActivity extends AppCompatActivity implements ProjectAdapter.onListListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    ArrayList<Model> model = new ArrayList<>();
    ArrayList<Integer> idProj = new ArrayList<>();

    Integer id, access;
    String url;
    Button btn_logout, btn_intern, btn_admin;

    private static final String TAG_ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //inisialisasi semua komponen
        init();

        //mendapat data dari halaman sebelumnya
        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        access = x.getIntExtra("access", 0);

        //membedakan antara akses admin dan non admin
        if(access == 1){
            url = Config.getDataAdm;
        }else {
            url = Config.getDataNonAdm;
            btn_admin.setVisibility(View.INVISIBLE);
        }

        //load data berupa json kedalam activity
        loadData();

        btn_logout.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btn_intern.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, Home1Activity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
            finish();
        });

        btn_admin.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, Home2Activity.class);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                try {
                    //mengambil data dalam bentuk array dari string
                    JSONArray arr = new JSONArray(response);

                    //mengisi setiap item dengan data yang tadi diambil
                    for (int i  = 0; i < arr.length(); i++){
                        JSONObject data = arr.getJSONObject(i);
                        Model md = new Model();
                        //jika user tidak mengerjakan project apapun, maka project tidak muncul
                        if (data.getString("namaproject") != "null"){
                            md.setJumlahMember(data.getString("jumlah"));
                            md.setNamaProject(data.getString("namaproject"));
                            model.add(md);
                            idProj.add(data.getInt("idproject"));
                        }
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
        Controller.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onListClick(int position) {
        //jika item di click, maka akan berpindah
        Intent intent = new Intent(HomeActivity.this, DetailActivityProject.class);
        intent.putExtra("id", id);
        intent.putExtra("idproj", idProj.get(position));
        intent.putExtra("access", access);
        startActivity(intent);
    }

    public void init(){
        //komponen inisiasi
        recyclerView = findViewById(R.id.recyclerView);
        btn_logout = findViewById(R.id.logout);
        btn_intern = findViewById(R.id.intern);
        btn_admin = findViewById(R.id.admin);
        progressDialog = new ProgressDialog(HomeActivity.this);
        layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new ProjectAdapter(this, model, this);
        recyclerView.setAdapter(adapter);
    }
}