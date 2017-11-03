package com.comingsolution.us.apirest.View;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.comingsolution.us.apirest.Config.URLRest;
import com.comingsolution.us.apirest.Model.HTTPConecction;
import com.comingsolution.us.apirest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by josefrola on 25/10/17.
 */

public class ActivityAddMenu extends AppCompatActivity {

    private ProgressDialog progresDialog;
    private JSONObject json;
    private HTTPConecction service;
    private String url = URLRest.urlInsertArticulo;
    private int status = 0;
    private String request;


    private EditText txtNameResturante;
    private EditText txtNameMenu;
    private EditText txtPrecio;
    private EditText txtDescription;
    private Button btnSave;
    private ScrollView mScrollView;
    private LinearLayout ltsContainer;

    String idResturante;
    String nameMenu;
    String precio;
    String description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_menu);

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        ltsContainer=(LinearLayout)findViewById(R.id.ltsContainer);
        txtNameResturante=(EditText)findViewById(R.id.txtNameResturante);
        txtNameMenu=(EditText)findViewById(R.id.txtNameMenu);
        txtPrecio=(EditText)findViewById(R.id.txtPrecio);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        btnSave=(Button) findViewById(R.id.btnAddMenu);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                idResturante = txtNameResturante.getText().toString();
                nameMenu =txtNameMenu.getText().toString();
                precio = txtPrecio.getText().toString();
                description = txtDescription.getText().toString();

                if(idResturante.equals("") | nameMenu.equals("") | precio.equals("") | description.equals("")){
                    Toast.makeText(getApplicationContext(), "Verifique los datos a enviar", Toast.LENGTH_LONG).show();
                }else{
                    service = new HTTPConecction();
                    new DataPostRestaurat().execute();
                }

            }
        });



    }


    public class DataPostRestaurat extends AsyncTask<Void, Void, Void> {

        String response = "";
        HashMap<String, String> postDataParams;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresDialog = new ProgressDialog(ActivityAddMenu.this);
            progresDialog.setTitle("Procesando....");
            progresDialog.setCancelable(false);
            progresDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            postDataParams = new HashMap<String, String>();
            postDataParams.put("idRestaurante", idResturante);
            postDataParams.put("name", nameMenu);
            postDataParams.put("description", description);
            postDataParams.put("precio", precio);
            response = service.ServerDataHeader(url, postDataParams);

            try {
                json = new JSONObject(response);
                status = json.getInt("status");
                request = json.getString("response");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        private void scrollToButton() {
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(0, ltsContainer.getBottom());
                }
            });
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progresDialog.isShowing()) {
                progresDialog.dismiss();
                String resultdata = request;

                response = "";
                if (status == 200) {
                    Toast.makeText(getApplicationContext(), resultdata, Toast.LENGTH_LONG).show();
                    ClearEditText();
                } else if (status == 900) {
                    Toast.makeText(getApplicationContext(), resultdata, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), resultdata, Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void ClearEditText(){
        txtNameResturante.setText("");
        txtNameMenu.setText("");
        txtDescription.setText("");
        txtPrecio.setText("");

    }




}
