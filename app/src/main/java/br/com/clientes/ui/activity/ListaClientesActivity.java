package br.com.clientes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.clientes.R;
import br.com.clientes.dao.ClienteDao;
import br.com.clientes.model.Cliente;

import static br.com.clientes.ui.activity.ConstantesActivities.CHAVE_CLIENTE_EDITAR;
import static br.com.clientes.ui.activity.ConstantesActivities.CHAVE_CLIENTE_MOSTRAR;

public class ListaClientesActivity extends AppCompatActivity {

    ListView listaDeClientes;
    private ArrayAdapter<Cliente> adapter;
    FloatingActionButton fabNovoCliente;
    private final ClienteDao clienteDao = new ClienteDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        setTitle("Lista de Clientes");
        configuraFabNovoCliente();
        configuraLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_clientes_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_Lista_clientes_menu_editar){
            Cliente clienteSelecionado = encontraClienteSelecionado(item);
            abreFormularioModoEditaCliente(clienteSelecionado);
        }
        if(itemId == R.id.activity_lista_clientes_menu_deletar){
            Cliente clienteSelecionado = encontraClienteSelecionado(item);
            deleta(clienteSelecionado);
        }
        return super.onContextItemSelected(item);
    }

    private void deleta(Cliente cliente) {
        clienteDao.deleta(cliente);
        adapter.remove(cliente);
    }

    private Cliente encontraClienteSelecionado(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        return adapter.getItem(menuInfo.position);
    }

    private void abreFormularioModoEditaCliente(Cliente clienteSlecionado) {
        Intent vaiParaFormularioClienteActivity = new Intent(this, FormularioClienteActivity.class);
        vaiParaFormularioClienteActivity.putExtra(CHAVE_CLIENTE_EDITAR,clienteSlecionado);
        startActivity(vaiParaFormularioClienteActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaListaClientes();
    }

    private void atualizaListaClientes() {
        adapter.clear();
        adapter.addAll(clienteDao.todos());
    }

    private void configuraLista() {
        listaDeClientes = findViewById(R.id.activity_lista_clientes_listView);
        configuraAdapter();
        configuraListenerDeCliquePorItem();
        registerForContextMenu(listaDeClientes);
    }

    private void configuraListenerDeCliquePorItem() {
        listaDeClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Cliente clienteSelecionado = (Cliente) adapterView.getItemAtPosition(posicao);
                abreActivityApresentaDadosCliente(clienteSelecionado);
            }
        });
    }

    private void abreActivityApresentaDadosCliente(Cliente clienteSelecionado) {
        Intent vaiParaApresentaDadosClienteActivity = new Intent(this, ApresentaDadosCliente.class);
        vaiParaApresentaDadosClienteActivity.putExtra(CHAVE_CLIENTE_MOSTRAR,clienteSelecionado);
        startActivity(vaiParaApresentaDadosClienteActivity);
    }

    private void configuraAdapter() {
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listaDeClientes.setAdapter(adapter);
    }

    private void configuraFabNovoCliente() {
        fabNovoCliente = findViewById(R.id.activity_lista_clientes_fab_novo_cliente);
        fabNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioAcrescentaNovoCliente();
            }
        });
    }

    private void abreFormularioAcrescentaNovoCliente() {
        startActivity(new Intent(this, FormularioClienteActivity.class));
    }
}
