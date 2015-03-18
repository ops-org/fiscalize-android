package br.net.ops.fiscalize.interfaces;

public interface LoginListener {

	public void onLoginRealizado(String tokenId);

    public void onLoginErro(String erro);
	
}