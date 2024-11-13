package com.example.appcadprodut;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.appcadprodut.DBHelper.ProdutosBd;
import com.example.appcadprodut.Model.Produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormularioProdutos extends AppCompatActivity {
    EditText editText_NomeProduto, editText_DescricaoProduto, editText_Quantidade;
    Button btn_Polimorf;
    Produtos editarProduto, produto;
    ProdutosBd dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produtos);
        produto = new Produtos();
        dbHelper = new ProdutosBd(FormularioProdutos.this);

        Intent intent = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");

        editText_NomeProduto = (EditText) findViewById(R.id.editText_NomeProduto);
        editText_DescricaoProduto = (EditText) findViewById(R.id.editText_DescricaoProduto);
        editText_Quantidade = (EditText) findViewById(R.id.editText_Quantidade);

        btn_Polimorf = (Button) findViewById(R.id.btn_Polimorf);

        if (editarProduto != null) {
            btn_Polimorf.setText("Modificar");

            editText_NomeProduto.setText(editarProduto.getNomeProduto());
            editText_DescricaoProduto.setText(editarProduto.getDescricao());
            editText_Quantidade.setText(String.valueOf(editarProduto.getQuantidade()));

            produto.setId(editarProduto.getId());
        } else {
            btn_Polimorf.setText("Cadastrar");
        }

        btn_Polimorf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto.setNomeProduto(editText_NomeProduto.getText().toString());
                produto.setDescricao(editText_DescricaoProduto.getText().toString());

                String quantidadeText = editText_Quantidade.getText().toString();
                int quantidade = quantidadeText.isEmpty() ? 0 : Integer.parseInt(quantidadeText);
                produto.setQuantidade(quantidade);

                if (btn_Polimorf.getText().toString().equals("Cadastrar")) {
                    dbHelper.salvarProduto(produto);
                } else {
                    dbHelper.alterarProduto(produto);
                }
                dbHelper.close();
                finish();  // Finaliza a atividade
            }
        });
    }
}