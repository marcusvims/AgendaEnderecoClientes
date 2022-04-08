package br.com.clientes.validator;

import android.widget.EditText;

public class ValidacaoCep {

    private static final String ERRO_VALIDACAO_CEP = "O cep precisa ter 8 caracteres";
    private final EditText campoTextoCep;
    private final ValidacaoPadrao validadorPadrao;

    public ValidacaoCep(EditText campoTextoCep) {
        this.campoTextoCep = campoTextoCep;
        this.validadorPadrao = new ValidacaoPadrao(campoTextoCep);
    }

    public boolean estaValido(){
        if(!validadorPadrao.estaValido()) return false;
        if(!validaCampoComOitoDigitos()) return false;
        adicionaFormatacao();
        return true;
    }

    private boolean validaCampoComOitoDigitos() {
        String cep = campoTextoCep.getText().toString();
        if(cep.length() != 8) {
            campoTextoCep.setError(ERRO_VALIDACAO_CEP);
            return false;
        }
        return true;
    }

    private void adicionaFormatacao() {
        String cep = campoTextoCep.getText().toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cep.length(); i++) {
            char digito = cep.charAt(i);
            sb.append(digito);
            if(i==4){
                sb.append("-");
            }
        }
        String cepFormatado = sb.toString();
        campoTextoCep.setText(cepFormatado);
    }
}
