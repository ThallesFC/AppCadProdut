package com.example.appcadprodut;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    // ActivityResultLauncher para iniciar FormularioProdutos
    ActivityResultLauncher<Intent> editProdutoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa o ActivityResultLauncher
        editProdutoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int position = data.getIntExtra("position", -1);
                            if (position != -1) {
                                Produtos produto = adapter.getItem(position);
                                produto.cor = data.getIntExtra("corSelecionada", Color.BLACK);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        Button btn_Buscar = findViewById(R.id.btn_Buscar);
        btn_Buscar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarActivity.class);
            startActivity(intent);
        });

        Button btn_Cadastrar = findViewById(R.id.btn_Cadastrar);
        btn_Cadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioProdutos.class);
            // Inicia FormularioProdutos usando o launcher
            editProdutoLauncher.launch(intent);
        });

        lista = findViewById(R.id.ListView_Produtos);
        registerForContextMenu(lista);

        lista.setOnItemClickListener((adapterView, view, position, id) -> {
            Produtos produtoEscolhido = (Produtos) adapterView.getItemAtPosition(position);
            Intent i = new Intent(MainActivity.this, FormularioProdutos.class);
            i.putExtra("produto-escolhido", produtoEscolhido);
            i.putExtra("position", position);
            // Inicia FormularioProdutos usando o launcher
            editProdutoLauncher.launch(i);
        });

        lista.setOnItemLongClickListener((adapterView, view, i, l) -> {
            produto = (Produtos) adapterView.getItemAtPosition(i);
            return false;
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
            adapter = new ArrayAdapter<Produtos>(MainActivity.this, android.R.layout.simple_list_item_1, ListView_Produtos) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);

                    // Define a cor de fundo do item com base na cor do produto
                    Produtos produto = getItem(position);
                    view.setBackgroundColor(produto.cor);

                    // Define a cor do texto para garantir a legibilidade
                    textView.setTextColor(Color.BLACK); // Ou outra cor que contraste com o fundo

                    return view;
                }
            };
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Nenhum produto encontrado.", Toast.LENGTH_SHORT).show();
        }
    }
}