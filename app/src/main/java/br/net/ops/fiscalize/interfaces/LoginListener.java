package br.net.ops.fiscalize.interfaces;

import br.net.ops.fiscalize.vo.Usuario;

public interface LoginListener {

    void onLoginRealizado(Usuario usuario);
    void onLoginErro(String erro);

}