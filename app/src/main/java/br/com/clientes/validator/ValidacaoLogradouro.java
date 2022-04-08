package br.com.clientes.validator;

import android.widget.EditText;

public class ValidacaoLogradouro {

    private static final String ERRO_VALIDACAO_LOGRADOURO = "O logradouro precisa ter entre 10 a 100 caracteres";
    private final EditText campoTextoLogradouro;
    private final ValidacaoPadrao validadorPadrao;

    public ValidacaoLogradouro(EditText campoTextoLogradouro) {
        this.campoTextoLogradouro = campoTextoLogradouro;
        this.validadorPadrao = new ValidacaoPadrao(campoTextoLogradouro);
    }

    public boolean estaValido(){
        if(!validadorPadrao.estaValido()) return false;
        if(!validaCampoEntreDezeCemDigitos()) return false;
        return true;
    }

    private boolean validaCampoEntreDezeCemDigitos() {
        String logradouro = campoTextoLogradouro.getText().toString();
        if(logradouro.length() < 10 || logradouro.length() > 100) {
            campoTextoLogradouro.setError(ERRO_VALIDACAO_LOGRADOURO);
            return false;
        }
        return true;
    }
}
