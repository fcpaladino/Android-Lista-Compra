package com.filipepaladino.applistacompra.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.filipepaladino.applistacompra.models.ModelListaCompra;

import java.util.ArrayList;
import java.util.Random;


public class ControllerListaCompra extends SQLiteOpenHelper {

    private static final String BANCO    = "AppListaCompra.db";
    private static final String TABLE    = "lista_de_compra";
    private static final Integer VERSAO  = 1;
    private Cursor cursor;

    private String SQL_CREATE = "CREATE TABLE "+TABLE+" (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome TEXT, " +
            "ativo INTEGER DEFAULT 1, " +
            "valor REAL" +
            ");";

    private String SQL_DROP = "DROP TABLE " + TABLE;



    public ControllerListaCompra(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }

    public void createRegisters(){
        for (int i = 0; i < 11; i++) {

            Random r = new Random();
            double valor = (r.nextInt(80 - 65) + i) * 100;

            ModelListaCompra item = new ModelListaCompra();
            item.setNome("Item " + (i + 1));
            item.setValor(valor);

            insert(item);
        }
    }


    public int insert(ModelListaCompra item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome",      item.getNome());
        values.put("valor",     item.getValor());

        int cod = (int)db.insert(TABLE, null, values);
        db.close();

        return cod;
    }

    public boolean update(ModelListaCompra item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome",      item.getNome());
        values.put("valor", item.getValor());

        return db.update(TABLE, values, "id = ?", new String[] { String.valueOf(item.getId()) }) > 0;
    }

    public boolean delete(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE, "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE, null, null) > 0;
    }

    public ModelListaCompra find(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        cursor = db.query(TABLE,
                new String[]{"id", "nome", "valor"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ModelListaCompra item = new ModelListaCompra();
        item.setId(columnInt("id"));
        item.setNome(columnString("nome"));
        item.setValor(columnDouble("valor"));

        return item;
    }


    public ArrayList<ModelListaCompra> all() {
        ArrayList<ModelListaCompra> retorno = new ArrayList<ModelListaCompra>();

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                ModelListaCompra item = new ModelListaCompra();
                                 item.setId(columnInt("id"));
                                 item.setNome(columnString("nome"));
                                 item.setValor(columnDouble("valor"));


                retorno.add(item);
            } while (cursor.moveToNext());
        }



        return retorno;
    }






    private int columnInt(String column){
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    private float columnFloat(String column){
        return cursor.getFloat(cursor.getColumnIndex(column));
    }

    private String columnString(String column){
        return cursor.getString(cursor.getColumnIndex(column));
    }

    private double columnDouble(String column){
        return cursor.getDouble(cursor.getColumnIndex(column));
    }

    private long columnLong(String column){
        return cursor.getLong(cursor.getColumnIndex(column));
    }

}
