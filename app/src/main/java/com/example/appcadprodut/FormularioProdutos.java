package com.example.appcadprodut;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcadprodut.DBHelper.ProdutosBd;
import com.example.appcadprodut.Model.Produtos;

public class FormularioProdutos extends AppCompatActivity {

    EditText editText_NomeProduto, editText_DescricaoProduto, editText_Quantidade;
    Button btn_Polimorf;
    Produtos editarProduto, produto;
    ProdutosBd dbHelper;

    private CheckBox cbMonster;
    private CheckBox cbSpell;
    private CheckBox cbTrap;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produtos);

        // Inicialização de componentes
        produto = new Produtos();
        dbHelper = new ProdutosBd(FormularioProdutos.this);
        editText_NomeProduto = findViewById(R.id.editText_NomeProduto);
        editText_DescricaoProduto = findViewById(R.id.editText_DescricaoProduto);
        editText_Quantidade = findViewById(R.id.editText_Quantidade);
        btn_Polimorf = findViewById(R.id.btn_Polimorf);
        cbMonster = findViewById(R.id.cbMonster);
        cbSpell = findViewById(R.id.cbSpell);
        cbTrap = findViewById(R.id.cbTrap);

        // Obter dados da Intent (se houver)
        Intent intent = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");

        // Configurar o botão e campos de texto (se estiver editando)
        if (editarProduto != null) {btn_Polimorf.setText("Modificar");
            editText_NomeProduto.setText(editarProduto.getNomeProduto());
            editText_DescricaoProduto.setText(editarProduto.getDescricao());
            editText_Quantidade.setText(String.valueOf(editarProduto.getQuantidade()));
            produto.setId(editarProduto.getId());
        } else {
            btn_Polimorf.setText("Cadastrar");
        }

        // Configurar listeners para os checkboxes
        cbMonster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbSpell.setChecked(false);
                    cbTrap.setChecked(false);
                }
            }
        });

        cbSpell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbMonster.setChecked(false);
                    cbTrap.setChecked(false);
                }
            }
        });

        cbTrap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbMonster.setChecked(false);
                    cbSpell.setChecked(false);
                }
            }
        });

        // Configurar listener para o botão
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
                finish(); // Finaliza a atividade
            }
        });
    }
}