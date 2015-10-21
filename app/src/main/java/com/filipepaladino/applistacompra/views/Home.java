package com.filipepaladino.applistacompra.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.filipepaladino.applistacompra.R;
import com.filipepaladino.applistacompra.adapter.AdapterListaCompra;
import com.filipepaladino.applistacompra.controller.ControllerListaCompra;
import com.filipepaladino.applistacompra.models.ModelListaCompra;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private ListView lsvListaCompra;
    private AdapterListaCompra adapter;
    private ArrayList<ModelListaCompra> listacompra;
    private ControllerListaCompra controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        lsvListaCompra  = (ListView)findViewById(R.id.lsvListaCompra);

        controller      = new ControllerListaCompra(this);
        listacompra     = controller.all();
        adapter         = new AdapterListaCompra(this, listacompra);

        lsvListaCompra.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int acao = item.getItemId();

        switch (acao){
            case R.id.menu_cadastrar:
                menuCadastrar();
                break;

            case R.id.menu_deletar_todos:
                menuDeletarTodos();
                break;

            case R.id.menu_cria_registro:
                controller.createRegisters();
                updateListView();
                break;

            case R.id.menu_reset_table:
                controller.reset();
                updateListView();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void menuDeletarTodos() {
        if(controller.deleteAll()){
            updateListView();
            Toast.makeText(Home.this, "Todos os itens removidos..", Toast.LENGTH_SHORT).show();
        }
    }

    private void menuCadastrar() {
        Intent cadastro = new Intent(this, Cadastrar.class);
        cadastro.putExtra("action", "create");
        startActivityForResult(cadastro, 100);
    }

    public void onActivityResult(int cod, int status, Intent intent){
        updateListView();

        switch (status){
            case 100:
                Toast.makeText(Home.this, "Cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                break;

            case 200:
                Toast.makeText(Home.this, "Atualizado com sucesso.", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private void updateListView(){
        adapter = new AdapterListaCompra(this, controller.all());
        lsvListaCompra.setAdapter(adapter);
    }

}
