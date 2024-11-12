package com.example.appcadprodut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcadprodut.DBHelper.ProdutosBd;
import com.example.appcadprodut.Model.Produtos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    ProdutosBd dbHelper;
    ArrayList<Produtos> ListView_Produtos;
    Produtos produto;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.ListView_Produtos);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Produtos produtoEscolhido = (Produtos) adapterView.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, FormularioProdutos.class);
                i.putExtra("produto-escolhido", produtoEscolhido);
                startActivity(i);
            }
        });


        Button btn_Cadastrar = (Button) findViewById(R.id.btn_Cadastrar);
        btn_Cadastrar.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioProdutos.class);
                startActivity(intent);
            }
        });
    }
    protected void onResume(){
        super.onResume();
        carregarProduto();
    }

    public void carregarProduto(){
    dbHelper = new ProdutosBd(MainActivity.this);
    ListView_Produtos = dbHelper.getLista();
    dbHelper.close();

    if(ListView_Produtos !=null){
        adapter = new ArrayAdapter<Produtos>(MainActivity.this, android.R.layout.simple_list_item_1,ListView_Produtos);
        lista.setAdapter(adapter);
    }

    }
}