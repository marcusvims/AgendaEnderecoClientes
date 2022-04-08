package br.com.clientes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.clientes.R;
import br.com.clientes.model.Cliente;

import static br.com.clientes.ui.activity.ConstantesActivities.CHAVE_CLIENTE_MOSTRAR;

public class ApresentaDadosCliente extends AppCompatActivity {

    Cliente cliente;

    TextView nome;
    TextView telefone;
    TextView cep;
    TextView logradouro;
    TextView numero;
    TextView bairro;
    TextView complemento;
    TextView cidade;
    TextView estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresenta_dados_cliente);
        setTitle("Dados Cliente");
        inicializaTextoDados();
        carregaDadosCliente();
    }

    private void inicializaTextoDados() {
        nome = findViewById(R.id.activity_apresenta_cliente_nome);
        telefone = findViewById(R.id.activity_apresenta_cliente_telefone);
        cep = findViewById(R.id.activity_apresenta_cliente_cep);
        logradouro = findViewById(R.id.activity_apresenta_cliente_logradouro);
        numero = findViewById(R.id.activity_apresenta_cliente_numero);
        bairro = findViewById(R.id.activity_apresenta_cliente_bairro);
        complemento = findViewById(R.id.activity_apresenta_cliente_complemento);
        cidade = findViewById(R.id.activity_apresenta_cliente_cidade);
        estado = findViewById(R.id.activity_apresenta_cliente_estado);
    }

    private void carregaDadosCliente() {
        Intent dadosCliente = getIntent();
        cliente = (Cliente) dadosCliente.getSerializableExtra(CHAVE_CLIENTE_MOSTRAR);
        insereDadosCliente();
    }

    private void insereDadosCliente() {
        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());
        cep.setText(cliente.getEndereco().getCep());
        logradouro.setText(cliente.getEndereco().getLogradouro());
        numero.setText(cliente.getEndereco().getNumero());
        bairro.setText(cliente.getEndereco().getBairro());
        complemento.setText(cliente.getEndereco().getComplemento());
        cidade.setText(cliente.getEndereco().getCidade());
        estado.setText(cliente.getEndereco().getEstado());
    }
}