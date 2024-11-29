package com.example.appcadprodut.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appcadprodut.Model.Produtos;

import java.util.ArrayList;

public class ProdutosBd extends SQLiteOpenHelper {

    private static final String DATABASE = "bdprodutos";
    private static final int VERSION = 1;

    public ProdutosBd(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String produto = "CREATE TABLE produtos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomeproduto TEXT NOT NULL, descricao TEXT NOT NULL, quantidade INTEGER, caminhoImagem TEXT);";
        sqLiteDatabase.execSQL(produto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String produto = "DROP TABLE IF EXISTS produtos";
        sqLiteDatabase.execSQL(produto);
        onCreate(sqLiteDatabase);
    }

    public void salvarProduto(Produtos produto) {
        ContentValues values = new ContentValues();
        values.put("nomeproduto", produto.getNomeProduto());
        values.put("descricao", produto.getDescricao());
        values.put("quantidade", produto.getQuantidade());
        values.put("caminhoImagem", produto.getCaminhoImagem()); // Armazena o caminho da imagem

        getWritableDatabase().insert("produtos", null, values);
    }

    public void alterarProduto(Produtos produto) {
        ContentValues values = new ContentValues();
        values.put("nomeproduto", produto.getNomeProduto());
        values.put("descricao", produto.getDescricao());
        values.put("quantidade", produto.getQuantidade());
        values.put("caminhoImagem", produto.getCaminhoImagem()); // Armazena o caminho da imagemString[] args = {String.valueOf(produto.getId())};

        String[] args = {String.valueOf(produto.getId())};

        getWritableDatabase().update("produtos", values, "id=?", args);
    }

    public void deletarProduto(Produtos produto) {
        String[] args = {String.valueOf(produto.getId())};
        getWritableDatabase().delete("produtos", "id=?", args);
    }

    public ArrayList<Produtos> getLista() {
        String[] columns = {"id", "nomeproduto", "descricao", "quantidade", "caminhoImagem"}; // Inclui caminhoImagem
        Cursor cursor = getReadableDatabase().query("produtos", columns, null, null, null, null, null);
        ArrayList<Produtos> produtos = new ArrayList<>();

        while (cursor.moveToNext()) {
            Produtos produto = new Produtos();
            produto.setId(cursor.getLong(0));
            produto.setNomeProduto(cursor.getString(1));
            produto.setDescricao(cursor.getString(2));
            produto.setQuantidade(cursor.getInt(3));
            produto.setCaminhoImagem(cursor.getString(4)); // Define o caminho da imagem

            produtos.add(produto);
        }
        cursor.close();
        return produtos;
    }

    public Cursor getProdutoDetalhes(long produtoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"nomeproduto", "descricao", "quantidade", "caminhoImagem"}; // Inclui caminhoImagem
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(produtoId)};

        return db.query("produtos", columns, selection, selectionArgs, null, null, null);
    }
}