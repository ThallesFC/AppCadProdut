package com.example.appcadprodut;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;import androidx.recyclerview.widget.RecyclerView;

import com.example.appcadprodut.DBHelper.ProdutosBd;
import com.example.appcadprodut.Model.Produtos;

public class DetalhesAdapter extends RecyclerView.Adapter<DetalhesAdapter.DetalhesViewHolder> {

    private final ProdutosBd dbHelper;
    private final Produtos produto;

    public DetalhesAdapter(ProdutosBd dbHelper, Produtos produto) {
        this.dbHelper = dbHelper;
        this.produto = produto;
    }

    @NonNull
    @Override
    public DetalhesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(16, 16, 16, 16); // Defina o padding desejado
        return new DetalhesViewHolder(textView);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull DetalhesViewHolder holder, int position) {
        String detalhe = "";

        try (Cursor cursor = dbHelper.getProdutoDetalhes(produto.getId())) {
            if (cursor != null && cursor.moveToFirst()) {
                switch (position) {
                    case 0:
                        detalhe = "Nome: " + cursor.getString(cursor.getColumnIndex("nomeproduto"));
                        break;
                    case 1:
                        detalhe = "Descrição: " + cursor.getString(cursor.getColumnIndex("descricao"));
                        break;
                    case 2:
                        detalhe = "Quantidade: " + cursor.getInt(cursor.getColumnIndex("quantidade"));
                        break;
                }
            }
        }

        holder.textView.setText(detalhe);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class DetalhesViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public DetalhesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}