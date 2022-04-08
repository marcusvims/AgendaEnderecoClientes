package br.com.clientes.validator;

import android.widget.EditText;

public class ValidacaoPadrao {

    private static final String ERRO_VALIDACAO_PADRAO = "Campo obrigatório";
    private final EditText campoTexto;

    public ValidacaoPadrao(EditText campoTexto) {
        this.campoTexto = campoTexto;
    }

    private boolean validaCampoObrigatorio() {
        String texto = campoTexto.getText().toString();
        if (texto.isEmpty()) {
            campoTexto.setError(ERRO_VALIDACAO_PADRAO);
            return false;
        }
        return true;
    }

    public boolean estaValido(){
        if(!validaCampoObrigatorio()) return false;
        return true;
    }

}
