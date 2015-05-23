package br.net.ops.fiscalize.vo;


public class Suspeita {

    private Boolean suspeita;
    private Boolean suspeitaValor;
    private Boolean suspeitaObjeto;
    private Boolean suspeitaFornecedor;
    private String comentarios;

    private Usuario usuario;
    private NotaFiscal notaFiscal;

    public Boolean getSuspeita() {
        return suspeita;
    }

    public void setSuspeita(Boolean suspeita) {
        this.suspeita = suspeita;
    }

    public Boolean getSuspeitaValor() {
        return suspeitaValor;
    }

    public void setSuspeitaValor(Boolean suspeitaValor) {
        this.suspeitaValor = suspeitaValor;
    }

    public Boolean getSuspeitaObjeto() {
        return suspeitaObjeto;
    }

    public void setSuspeitaObjeto(Boolean suspeitaObjeto) {
        this.suspeitaObjeto = suspeitaObjeto;
    }

    public Boolean getSuspeitaFornecedor() {
        return suspeitaFornecedor;
    }

    public void setSuspeitaFornecedor(Boolean suspeitaFornecedor) {
        this.suspeitaFornecedor = suspeitaFornecedor;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
}
