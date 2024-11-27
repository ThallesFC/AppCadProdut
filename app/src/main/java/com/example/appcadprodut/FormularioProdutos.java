package com.example.appcadprodut;

import android.annotation.SuppressLint;
import android.content.Intent;import android.graphics.Color;
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

    private int corSelecionada = Color.BLACK; // Cor padrão: preto

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

        // Obter dados daIntent (se houver)
        Intent intent = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");

        // Configurar o botão e campos de texto (se estiver editando)
        if (editarProduto != null) {
            btn_Polimorf.setText("Modificar");
            editText_NomeProduto.setText(editarProduto.getNomeProduto());
            editText_DescricaoProduto.setText(editarProduto.getDescricao());
            editText_Quantidade.setText(String.valueOf(editarProduto.getQuantidade()));
            produto.setId(editarProduto.getId());

            // Restaurar a cor selecionada se estiver editando
            corSelecionada = editarProduto.cor; // Acessar diretamente o atributo 'cor'

            // Atualizar os checkboxes de acordo com a cor restaurada
            cbMonster.setChecked(corSelecionada == Color.parseColor("#FFA500"));
            cbSpell.setChecked(corSelecionada == Color.parseColor("#008000"));
            cbTrap.setChecked(corSelecionada == Color.parseColor("#FF69B4"));
        } else {
            btn_Polimorf.setText("Cadastrar");
        }

        // Configurar listeners para os checkboxes (com lógica de seleção única e definição de cor)
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Desmarcar outros checkboxes para garantir seleção única
                    cbMonster.setChecked(buttonView.getId() == R.id.cbMonster);
                    cbSpell.setChecked(buttonView.getId() == R.id.cbSpell);
                    cbTrap.setChecked(buttonView.getId() == R.id.cbTrap);

                    // Definir a cor selecionada com base no checkbox clicado
                    if (buttonView.getId() == R.id.cbMonster) {
                        corSelecionada = Color.parseColor("#FFA500"); // Laranja
                    } else if (buttonView.getId() == R.id.cbSpell) {
                        corSelecionada = Color.parseColor("#008000"); // Verde
                    } else if (buttonView.getId() == R.id.cbTrap) {corSelecionada = Color.parseColor("#FF69B4"); // Rosa
                    }
                } else if (!cbMonster.isChecked() && !cbSpell.isChecked() && !cbTrap.isChecked()) {
                    // Resetar a cor para preto se nenhum checkbox estiver selecionado
                    corSelecionada = Color.BLACK;
                }
            }
        };

        cbMonster.setOnCheckedChangeListener(checkedChangeListener);
        cbSpell.setOnCheckedChangeListener(checkedChangeListener);
        cbTrap.setOnCheckedChangeListener(checkedChangeListener);

        // Configurar listener para o botão
        btn_Polimorf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto.setNomeProduto(editText_NomeProduto.getText().toString());
                produto.setDescricao(editText_DescricaoProduto.getText().toString());

                String quantidadeText = editText_Quantidade.getText().toString();
                int quantidade = quantidadeText.isEmpty() ? 0 : Integer.parseInt(quantidadeText);
                produto.setQuantidade(quantidade);

                // Salvar a cor selecionada no objeto produto
                produto.cor = corSelecionada; // Atribuir diretamente ao atributo 'cor'

                if (btn_Polimorf.getText().toString().equals("Cadastrar")) {
                    dbHelper.salvarProduto(produto);
                } else {
                    dbHelper.alterarProduto(produto);
                }

                // Passar a cor selecionada para a activity_main
                Intent intent = new Intent();
                intent.putExtra("corSelecionada", corSelecionada);
                setResult(RESULT_OK, intent);

                dbHelper.close();
                finish(); // Finaliza a atividade
            }
        });
    }
}