package br.com.clientes.model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id = 0;

    private String nome;
    private String telefone;
    private Endereco endereco;

    public Cliente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean temIdValido() {
        return id > 0;
    }

    @Override
    public String toString() {
        return  nome;
    }
}
