package com.filipepaladino.applistacompra.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.filipepaladino.applistacompra.R;
import com.filipepaladino.applistacompra.controller.ControllerListaCompra;
import com.filipepaladino.applistacompra.models.ModelListaCompra;

public class Cadastrar extends AppCompatActivity {

    private TextView edxId;
    private TextView edxNome;
    private TextView edxValor;
    private Button atualizar;
    private Button cadastrar;

    private ControllerListaCompra controller;
    private ModelListaCompra model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);

        edxId       = (TextView)findViewById(R.id.edxId);
        edxNome     = (TextView)findViewById(R.id.edxNome);
        edxValor    = (TextView)findViewById(R.id.edxValor);

        controller  = new ControllerListaCompra(this);

        Intent intent       = getIntent();
        String action       = intent.getStringExtra("action");

        switch (action){
            case "create":

                edxId.setText("");
                edxNome.setText("");
                edxValor.setText("");

                enableButtonStore();
                break;

            case "edit":
                Integer id = Integer.valueOf( intent.getStringExtra("id") );
                ModelListaCompra item = controller.find(id);

                edxId.setText( String.valueOf(item.getId()) );
                edxNome.setText( String.valueOf(item.getNome()) );
                edxValor.setText( String.valueOf(item.getValor()) );

                enableButtonUpdate();
                break;
        }

    }


    // Cria um novo registro
    public void actionStore(View view) {

        model   = new ModelListaCompra();
        model.setNome(edxNome.getText().toString());
        model.setValor(Double.parseDouble(edxValor.getText().toString()));

        int id = (int)controller.insert(model);

        controller.close();

        Intent home = new Intent();
        home.putExtra("id", id);
        setResult(100, home);
        finish();
    }

    // Atualiza o registro
    public void actionUpdate(View view){

        model   = new ModelListaCompra();
        model.setId(Integer.valueOf(edxId.getText().toString()));
        model.setNome(edxNome.getText().toString());
        model.setValor(Double.parseDouble(edxValor.getText().toString()));

        controller.update(model);
        controller.close();

        Intent home = new Intent();
        setResult(200, home);
        finish();
    }

    // Volta pra tela inicial
    public void actionCancel(View view) {
        finish();
    }

    // habilita o botão cadastrar, desabilita o botão atualizar
    private void enableButtonStore(){
        atualizar = (Button)findViewById(R.id.button_atualizar);
        atualizar.setVisibility(View.INVISIBLE);

        cadastrar = (Button)findViewById(R.id.button_cadastrar);
        cadastrar.setVisibility(View.VISIBLE);
    }

    // habilita o botão atualizar, desabilita o botão cadastrar
    private void enableButtonUpdate(){
        atualizar = (Button)findViewById(R.id.button_atualizar);
        atualizar.setVisibility(View.VISIBLE);

        cadastrar = (Button)findViewById(R.id.button_cadastrar);
        cadastrar.setVisibility(View.INVISIBLE);
    }




}
