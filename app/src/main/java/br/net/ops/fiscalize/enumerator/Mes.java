package br.net.ops.fiscalize.enumerator;

public enum Mes {
	JANEIRO(1, "JAN"),
    FEVEREIRO(2, "FEV"),
    MARCO(3, "MAR"),
    ABRIL(4, "ABR"),
    MAIO(5, "MAI"),
    JUNHO(6, "JUN"),
    JULHO(7, "JUL"),
    AGOSTO(8, "AGO"),
    SETEMBRO(9, "SET"),
    OUTUBRO(10, "OUT"),
    NOVEMBRO(11, "NOV"),
    DEZEMBRO(12, "DEZ");

	private int numero;
    private String descricaoCurta;

	private Mes(int numero, String descricaoCurta) {
		this.numero = numero;
        this.descricaoCurta = descricaoCurta;
	}

    public static String getDescricaoCurta(Integer numero) {
        String retorno = "";
        if(numero!=null) {
            for (Mes mes : Mes.values()) {
                if (mes.getNumero() == numero.intValue()) {
                    retorno = mes.getDescricaoCurta();
                    break;
                }
            }
        }
        return retorno;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }
}