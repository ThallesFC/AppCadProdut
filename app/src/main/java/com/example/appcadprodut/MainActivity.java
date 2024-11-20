package com.example.appcadprodut;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appcadprodut.DBHelper.ProdutosBd;
import com.example.appcadprodut.Model.Produtos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    ProdutosBd dbHelper;
    ArrayList<Produtos> ListView_Produtos;
    Produtos produto;
    ArrayAdapter<Produtos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_Buscar = findViewById(R.id.btn_Buscar);btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuscarActivity.class);
                startActivity(intent);
            }
        });

        Button btn_Cadastrar = (Button) findViewById(R.id.btn_Cadastrar);
        btn_Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioProdutos.class);
                startActivity(intent);
            }
        });

        lista = (ListView) findViewById(R.id.ListView_Produtos);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Produtos produtoEscolhido = (Produtos) adapter.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, FormularioProdutos.class);
                i.putExtra("produto-escolhido", produtoEscolhido);
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {
                produto = (Produtos) adapter.getItemAtPosition(i);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar este Produto");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                dbHelper = new ProdutosBd(MainActivity.this);
                dbHelper.deletarProduto(produto);
                dbHelper.close();
                carregarProduto();

                adapter.remove(produto);

                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProduto();
    }

    public void carregarProduto() {
        dbHelper = new ProdutosBd(MainActivity.this);
        ListView_Produtos = dbHelper.getLista();
        dbHelper.close();

        if (ListView_Produtos != null && !ListView_Produtos.isEmpty()) {
            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, ListView_Produtos);
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Nenhum produto encontrado.", Toast.LENGTH_SHORT).show();
        }
    }
}