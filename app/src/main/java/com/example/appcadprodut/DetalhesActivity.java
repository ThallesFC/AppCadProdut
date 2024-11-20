package com.example.appcadprodut;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcadprodut.DBHelper.ProdutosBd;
import com.example.appcadprodut.Model.Produtos;

public class DetalhesActivity extends AppCompatActivity {

    private RecyclerView RecycleViewDetalhes;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        RecycleViewDetalhes = findViewById(R.id.RecycleViewDetalhes);
        RecycleViewDetalhes.setLayoutManager(new LinearLayoutManager(this));

        ProdutosBd dbHelper = new ProdutosBd(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("produto")) {
            Produtos produtoSelecionado = (Produtos) intent.getSerializableExtra("produto");

            DetalhesAdapter adapter = new DetalhesAdapter(dbHelper, produtoSelecionado);
            RecycleViewDetalhes.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}