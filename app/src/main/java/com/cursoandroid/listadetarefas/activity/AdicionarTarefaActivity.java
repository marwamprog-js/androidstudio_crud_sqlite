package com.cursoandroid.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.listadetarefas.R;
import com.cursoandroid.listadetarefas.helper.TarefaDAO;
import com.cursoandroid.listadetarefas.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private EditText editTextTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTextTarefa = findViewById(R.id.editTextTarefa);

        //recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if( tarefaAtual != null ) {
            editTextTarefa.setText( tarefaAtual.getNomeTarefa() );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.itemSalvar:
                //Executar ação para o item salvar
                TarefaDAO tarefaDAO = new TarefaDAO( getApplicationContext() );
                String nomeTarefa = editTextTarefa.getText().toString();
                Tarefa tarefa = new Tarefa();

                if( tarefaAtual != null ) { //Editar

                    if( !nomeTarefa.isEmpty() ) {
                        tarefa.setNomeTarefa( nomeTarefa );
                        tarefa.setId( tarefaAtual.getId() );

                        //Atualizar no banco de dados
                        if( tarefaDAO.atualizar( tarefa ) ) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao atualizar tarefa!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar tarefa!", Toast.LENGTH_LONG).show();
                        }
                    }

                } else { //Salvar

                    if( !nomeTarefa.isEmpty() ) {

                        tarefa.setNomeTarefa( editTextTarefa.getText().toString() );

                        if( tarefaDAO.salvar( tarefa ) ) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!", Toast.LENGTH_LONG).show();
                        }


                    }

                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}