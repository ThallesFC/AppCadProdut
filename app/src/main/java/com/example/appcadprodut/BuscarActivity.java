package com.example.appcadprodut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcadprodut.DBHelper.ProdutosBd;
import com.example.appcadprodut.Model.Produtos;

import java.util.ArrayList;
import java.util.List;

public class BuscarActivity extends AppCompatActivity {

    private EditText editTextBusca;
    private Button btn_Localizar;
    private ListView ListViewResultado;
    private ProdutosBd dbHelper;
    private List<Produtos> produtosList; // Lista completa de produtos
    private ArrayAdapter<Produtos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        editTextBusca = findViewById(R.id.editTextBusca);
        btn_Localizar = findViewById(R.id.btn_Localizar);
        ListViewResultado = findViewById(R.id.ListViewResultado);

        dbHelper = new ProdutosBd(this);
        produtosList = dbHelper.getLista();

        btn_Localizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termoBusca = editTextBusca.getText().toString();
                List<Produtos> resultados = buscarProdutos(termoBusca);

                adapter = new ArrayAdapter<>(BuscarActivity.this, android.R.layout.simple_list_item_1, resultados);
                ListViewResultado.setAdapter(adapter);
            }
        });
        ListViewResultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produtos produtoSelecionado = (Produtos) parent.getItemAtPosition(position);
                Intent intent = new Intent(BuscarActivity.this, DetalhesActivity.class);
                intent.putExtra("produto", produtoSelecionado);
                startActivity(intent);
            }
        });
    }

    private List<Produtos> buscarProdutos(String termoBusca) {
        List<Produtos> resultados= new ArrayList<>();
        for (Produtos produto : produtosList) {
            if (produto.getNomeProduto().toLowerCase().contains(termoBusca.toLowerCase())) {
                resultados.add(produto);
            }
        }
        return resultados;
    }
}