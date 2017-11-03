package com.comingsolution.us.apirest.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.comingsolution.us.apirest.Adapter.AdapterListaDatos;
import com.comingsolution.us.apirest.Config.URLRest;
import com.comingsolution.us.apirest.Model.HTTPConecction;
import com.comingsolution.us.apirest.Properties.ListaDatos;
import com.comingsolution.us.apirest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewData;
    ProgressDialog progressDialog;

    private String url = URLRest.urlListArticulos;

    private JSONArray jsonArray;
    private HTTPConecction service;
    private int status = 0;



    AdapterListaDatos adapter;
    public List<ListaDatos>lista = new LinkedList<ListaDatos>();

    private ListView lstView;

    private Button addMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstView =(ListView)findViewById(R.id.lstView);
        //addMenu=(Button)findViewById(R.id.btnaddMenu);


        service = new HTTPConecction();
        new ListadoDatos().execute();


        /*addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddMenu = new Intent(MainActivity.this, ActivityAddMenu.class);
                startActivity(intentAddMenu);
            }
        });*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_tools, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_nuevo:
                Intent intentAddMenu = new Intent(MainActivity.this, ActivityAddMenu.class);
                startActivity(intentAddMenu);
                break;
        }

            return super.onOptionsItemSelected(item);
    }

    public class ListadoDatos extends AsyncTask<Void, Void, JSONArray> {

        String response = "";
        HashMap<String, String> postDataParams;
        //String urlparams = url + "title";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Buscando datos..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }



        @Override
        protected JSONArray doInBackground(Void... params) {

            postDataParams = new HashMap<String, String>();
            try {
                response = service.sendGet(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonResponse;
                jsonResponse = new JSONObject(response);
                status = jsonResponse.getInt("status");
                jsonArray = jsonResponse.optJSONArray("response");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (status == 200) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            ListaDatos objDatos = new ListaDatos();

                            int id;
                            String name;
                            String precio;

                            /* */

                            JSONObject objet = jsonArray.getJSONObject(i);

                            id = objet.getInt("id");
                            name = objet.getString("name");
                            precio = objet.getString("precio");

                            objDatos.setId(id);
                            objDatos.setName(name);
                            objDatos.setPrecio(precio);

                            lista.add(objDatos);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    adapter= new AdapterListaDatos(MainActivity.this,lista,R.layout.item_view);
                    lstView.setAdapter(adapter);

                }

            }
        }
    }
}
