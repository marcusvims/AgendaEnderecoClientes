package br.com.clientes.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.clientes.model.Cliente;

public class ClienteDao {

    private static int contadorIds = 1;
    private final static List<Cliente> clientes = new ArrayList<>();

    public ArrayList<Cliente> todos() {
        return new ArrayList<>(clientes);
    }

    public void salva(Cliente cliente) {
        cliente.setId(contadorIds);
        clientes.add(cliente);
        atualizaIds();
    }

    private void atualizaIds() {
        contadorIds ++;
    }

    public void edita(Cliente cliente) {
        Cliente clienteSelecionado;
        clienteSelecionado = buscaClientePeloId(cliente);
        if (cliente != null){
            int posicaoCliente = clientes.indexOf(clienteSelecionado);
            clientes.set(posicaoCliente,cliente);
        }
    }

    private Cliente buscaClientePeloId(Cliente cliente){
        for (Cliente cli: clientes) {
            if(cli.getId() == cliente.getId()){
                return cli;
            }
        }
        return null;
    }


    public void deleta(Cliente cliente) {
        Cliente clienteDevolvido = buscaClientePeloId(cliente);
        if(clienteDevolvido != null){
            clientes.remove(clienteDevolvido);
        }
    }
}
