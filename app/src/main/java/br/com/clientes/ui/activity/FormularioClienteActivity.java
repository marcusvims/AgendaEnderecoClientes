package br.com.clientes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.clientes.R;
import br.com.clientes.dao.ClienteDao;
import br.com.clientes.model.Cliente;
import br.com.clientes.model.Endereco;
import br.com.clientes.validator.ValidacaoCep;
import br.com.clientes.validator.ValidacaoLogradouro;
import br.com.clientes.validator.ValidacaoPadrao;
import br.com.clientes.validator.ValidacaoTelefone;

import static br.com.clientes.ui.activity.ConstantesActivities.CHAVE_CLIENTE_EDITAR;

public class FormularioClienteActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_NOVO_CLIENTE = "Cadastro de Endereço";
    public static final String TITULO_APPBAR_EDITA_CLIENTE = "Edição de Endereço";
    public static final String PREENCHIMENTO_INCORRETO_DOS_CAMPOS = "Há campos preenchidos incorretamente";

    EditText campoNome,campoCep, campoLogradouro, campoNumero,
             campoBairro, campoCidade, campoEstado,
             campoComplemento,campoTelefone;

    String  nome, telefone, cep, logradouro,
            numero, bairro, complemento,
            cidade,estado;

    ClienteDao clienteDao = new ClienteDao();
    Cliente cliente;
    Endereco endereco = new Endereco();

    boolean validaDadosPadrao = false;
    boolean validaCEP = false;
    boolean validaLogradouro = false;
    boolean validaTelefone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cliente);
        inicializacaoDosCamposEditText();
        carregaCliente();
    }

    private void carregaCliente() {
        Intent dados = getIntent();
        if(dados.hasExtra((CHAVE_CLIENTE_EDITAR))){
            setTitle(TITULO_APPBAR_EDITA_CLIENTE);
            cliente = (Cliente) dados.getSerializableExtra(CHAVE_CLIENTE_EDITAR);
            RetiraRevalidacaoDosDadosJaPreenchidos();
            insereCampos();
        }else{
            setTitle(TITULO_APPBAR_NOVO_CLIENTE);
            cliente = new Cliente();
        }
    }

    private void RetiraRevalidacaoDosDadosJaPreenchidos() {
        validaDadosPadrao = true;
        validaCEP = true;
        validaLogradouro = true;
        validaTelefone = true;
    }

    private void insereCampos() {
        campoNome.setText(cliente.getNome());
        campoTelefone.setText(cliente.getTelefone());
        campoCep.setText(cliente.getEndereco().getCep());
        campoLogradouro.setText(cliente.getEndereco().getLogradouro());
        campoNumero.setText(cliente.getEndereco().getNumero());
        campoBairro.setText(cliente.getEndereco().getBairro());
        campoComplemento.setText(cliente.getEndereco().getComplemento());
        campoCidade.setText(cliente.getEndereco().getCidade());
        campoEstado.setText(cliente.getEndereco().getEstado());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_cliente_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_cliente_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void finalizaFormulario() {
        if(verificacaoParaSalvarDadosCliente()) {
            preencheCliente();
            if (cliente.temIdValido()) {
                clienteDao.edita(cliente);
            } else {
                clienteDao.salva(cliente);
            }
            finish();
        }else{
            Toast.makeText(this, PREENCHIMENTO_INCORRETO_DOS_CAMPOS, Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializacaoDosCamposEditText() {
        configuraCampoNome();
        configuraCampoTelefone();
        configuraCampoCep();
        configuraCampoLogradouro();
        configuraCampoNumero();
        configuraCampoBairro();
        configuraCampoComplemento();
        configuraCampoCidade();
        configuraCampoEstado();
    }

    private void configuraCampoTelefone() {
        campoTelefone = findViewById(R.id.activity_formulario_cliente_telefone);
        final ValidacaoTelefone validador = new ValidacaoTelefone(campoTelefone);
        campoTelefone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean temFoco) {
                String telefone = campoTelefone.getText().toString();
                if(!temFoco){
                    validaTelefone = validador.estaValido();
                }else{
                    removeFormatacaoTelefone(telefone);
                }
            }
        });
    }

    private void removeFormatacaoTelefone(String telefone) {
        String telefoneSemFormatacao =  telefone.replace("(","")
                .replace(")","")
                .replace("+","")
                .replace(" ","")
                .replace(" ","");
        campoTelefone.setText(telefoneSemFormatacao);
    }

    private void configuraCampoCep() {
        campoCep = findViewById(R.id.activity_formulario_cliente_cep);
        final ValidacaoCep validador = new ValidacaoCep(campoCep);
        campoCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            String cep = campoCep.getText().toString();
            @Override
            public void onFocusChange(View view, boolean temFoco) {
                if(!temFoco){
                    validaCEP = validador.estaValido();
                }else{
                    removeFormatacaoCep(cep);
                }
            }
        });
    }

    private void removeFormatacaoCep(String cep){
        String cepSemFormatacao = cep.replace("-","");
        campoCep.setText(cepSemFormatacao);
    }

    private void configuraCampoComplemento() {
        campoComplemento = findViewById(R.id.activity_formulario_cliente_complemento);
    }

    private void configuraCampoEstado() {
        campoEstado = findViewById(R.id.activity_formulario_cliente_estado);
        adicionaValidacaoPadrao(campoEstado);
    }

    private void configuraCampoCidade() {
        campoCidade = findViewById(R.id.activity_formulario_cliente_cidade);
        adicionaValidacaoPadrao(campoCidade);
    }

    private void configuraCampoBairro() {
        campoBairro = findViewById(R.id.activity_formulario_cliente_bairro);
        adicionaValidacaoPadrao(campoBairro);
    }

    private void configuraCampoNumero() {
        campoNumero = findViewById(R.id.activity_formulario_cliente_numero);
        adicionaValidacaoPadrao(campoNumero);
    }

    private void configuraCampoLogradouro() {
        campoLogradouro = findViewById(R.id.activity_formulario_cliente_logradouro);
        final ValidacaoLogradouro validador = new ValidacaoLogradouro(campoLogradouro);
        campoLogradouro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean temFoco) {
                if(!temFoco){
                    validaLogradouro = validador.estaValido();
                }
            }
        });
    }

    private void configuraCampoNome() {
        campoNome = findViewById(R.id.activity_formulario_cliente_nome);
        adicionaValidacaoPadrao(campoNome);
    }

    private void adicionaValidacaoPadrao(final EditText campoPreenchido){
        final ValidacaoPadrao validador = new ValidacaoPadrao(campoPreenchido);
        campoPreenchido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean temFoco) {
                if(!temFoco){
                   validaDadosPadrao = validador.estaValido();
                }
            }
        });
    }

    private boolean verificacaoParaSalvarDadosCliente() {
        return validaDadosPadrao && validaCEP && validaLogradouro && validaTelefone;
    }

    private void preencheCliente() {
        capturaDadosCliente();

        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setComplemento(complemento);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
    }

    private void capturaDadosCliente() {
        nome = campoNome.getText().toString();
        telefone = campoTelefone.getText().toString();
        cep = campoCep.getText().toString();
        logradouro = campoLogradouro.getText().toString();
        numero = campoNumero.getText().toString();
        bairro = campoBairro.getText().toString();
        complemento = campoComplemento.getText().toString();
        cidade = campoCidade.getText().toString();
        estado = campoEstado.getText().toString();
    }
}