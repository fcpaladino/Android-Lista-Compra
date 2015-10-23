package com.filipepaladino.applistacompra.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.filipepaladino.applistacompra.R;
import com.filipepaladino.applistacompra.controller.ControllerListaCompra;
import com.filipepaladino.applistacompra.models.ModelListaCompra;
import com.filipepaladino.applistacompra.views.Cadastrar;

import java.util.ArrayList;

public class AdapterListaCompra  extends BaseAdapter {

    private Activity activity;
    private ArrayList<ModelListaCompra> listacompra;


    public AdapterListaCompra(Activity activity, ArrayList<ModelListaCompra> listacompra){
        this.activity       = activity;
        this.listacompra    = listacompra;
    }

    @Override
    public int getCount() {
        return listacompra.size();
    }

    @Override
    public Object getItem(int position) {
        return listacompra.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listacompra.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        View v                          = convertView;
        final ModelListaCompra item     = listacompra.get(position);

        if (v == null) {

            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.lista_compra_item, null, true);

            holder = new ViewHolder();
            holder.itemId       = (TextView) v.findViewById(R.id.itemId);
            holder.texto1       = (TextView) v.findViewById(R.id.texto1);
            holder.texto2       = (TextView) v.findViewById(R.id.texto2);
            holder.btnDeletar   = (Button) v.findViewById(R.id.btnItemDelete);
            holder.listItem     = (RelativeLayout) v.findViewById(R.id.listItem);
            holder.checkbox     = (CheckBox) v.findViewById(R.id.chkItem);

            holder.itemId.setText( String.valueOf(item.getId()) );
            holder.texto1.setText( item.getNome() );
            holder.texto2.setText( String.valueOf(item.getValor()) );

            if(item.getAtivo() == 1)
                holder.checkbox.setChecked(true);
            else
                holder.checkbox.setChecked(false);

        } else {
            holder = (ViewHolder) v.getTag();
        }


        holder.listItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent tela = new Intent(activity, Cadastrar.class);
                tela.putExtra("action", "edit");
                tela.putExtra("id", String.valueOf(item.getId()));
                activity.startActivityForResult(tela, 200);
            }
        });
        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ControllerListaCompra controller    = new ControllerListaCompra(activity);
                boolean result                      = controller.delete(item.getId());

                if(result){
                    listacompra.remove(item);
                    notifyDataSetChanged();
                    Toast.makeText(activity, "Item deletado.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "Erro ao deletar o item.", Toast.LENGTH_LONG).show();
                }

            }
        });


        v.setTag(holder);

        return v;
    }

    protected static class ViewHolder{
        TextView itemId;
        TextView texto1;
        TextView texto2;
        Button btnDeletar;
        CheckBox checkbox;
        RelativeLayout listItem;
    }
}
