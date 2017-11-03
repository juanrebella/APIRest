package com.comingsolution.us.apirest.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comingsolution.us.apirest.Properties.ListaDatos;
import com.comingsolution.us.apirest.R;

import java.util.List;

/**
 * Created by jfrola on 10/18/17.
 */

public class AdapterListaDatos  extends BaseAdapter {

    private int layoutData;
    private List<ListaDatos> lista;
    private Activity activity;

    private String getId;


    public AdapterListaDatos(Activity activity, List<ListaDatos> list, int layout){

        this.activity=activity;
        this.lista=list;
        this.layoutData=layout;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView=inflater.inflate(layoutData, null);

        TextView lblid = (TextView)convertView.findViewById(R.id.txtidTitle);
        TextView lblName = (TextView)convertView.findViewById(R.id.lblName);
        TextView lblprecio = (TextView)convertView.findViewById(R.id.lblprecio);

        getId=Integer.toString(lista.get(position).getId());
        lblid.setText(getId);
        lblName.setText(lista.get(position).getName());
        lblprecio.setText(lista.get(position).getPrecio());
        return convertView;
    }
}
