package br.com.clientes.validator;

import android.widget.EditText;

public class ValidacaoTelefone {

    private static final String ERRO_VALIDACAO_TELEFONE = "O telefone precisa ter 14 caracteres";
    private final EditText campoTextoTelefone;
    private final ValidacaoPadrao validadorPadrao;

    public ValidacaoTelefone(EditText campoTextoTelefone) {
        this.campoTextoTelefone = campoTextoTelefone;
        this.validadorPadrao = new ValidacaoPadrao(campoTextoTelefone);
    }

    public boolean estaValido(){
        if(!validadorPadrao.estaValido()) return false;
        if(!validaCampoComQuatorzeDigitos()) return false;
        adicionaFormatacao();
        return true;
    }

    private void adicionaFormatacao() {
        String telefone = campoTextoTelefone.getText().toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < telefone.length(); i++) {
            if(i==0){
                sb.append("+");
            }
            char digito = telefone.charAt(i);
            sb.append(digito);
            if(i==1){
                sb.append(" (");
            }
            if (i == 4) {
                sb.append(") ");
            }
            if(i == 9){
                sb.append("-");
            }
        }
        String telefoneFormatado = sb.toString();
        campoTextoTelefone.setText(telefoneFormatado);
    }

    private boolean validaCampoComQuatorzeDigitos() {
        String telefone = campoTextoTelefone.getText().toString();
        if(telefone.length() != 14) {
            campoTextoTelefone.setError(ERRO_VALIDACAO_TELEFONE);
            return false;
        }
        return true;
    }
}
