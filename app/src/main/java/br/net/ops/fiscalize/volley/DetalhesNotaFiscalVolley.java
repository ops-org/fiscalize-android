package br.net.ops.fiscalize.volley;


import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import android.content.Context;
import android.util.Log;
import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.utils.Utilidade;
import br.net.ops.fiscalize.vo.NotaFiscal;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalhesNotaFiscalVolley extends JsonObjectRequest {

    private static final String TAG = "NotaFiscalVolley";

    private static final int METHOD = Request.Method.GET;

    public static final String REST_DETALHES_NOTA_FISCAL = Utilidade.REST_SERVIDOR;
    public static final String REST_DETALHES_NOTA_FISCAL_TOKEN_ID = "&tokenId=";


    public interface DetalhesNotaFiscalListener {
        public void onDetalhesNotaFiscalRecebido(NotaFiscal notaFiscal);
        public void onDetalhesNotaFiscalErro(String erro);
    }

    public DetalhesNotaFiscalVolley(String tokenId, final DetalhesNotaFiscalListener listener, final Context context){

        super(METHOD, REST_DETALHES_NOTA_FISCAL + REST_DETALHES_NOTA_FISCAL_TOKEN_ID + tokenId, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                NotaFiscal notaFiscal = new NotaFiscal();

                Log.i(TAG, jsonObject.toString());

                try {
                    notaFiscal.setNotaFiscalId(jsonObject.getInt("notaFiscalId"));

                    JSONObject jsonObjectParlamentar = jsonObject.getJSONObject("parlamentar");

                    notaFiscal.setNomeParlamentar(jsonObjectParlamentar.getString("nome"));
                    notaFiscal.setEmailParlamentar(jsonObjectParlamentar.getString("email"));
                    notaFiscal.setUrlFotoParlamentar(jsonObjectParlamentar.getString("urlFoto"));

                    JSONObject jsonObjectPartido = jsonObject.getJSONObject("partido");

                    notaFiscal.setNomePartido(jsonObjectPartido.getString("nome"));
                    notaFiscal.setSiglaPartido(jsonObjectPartido.getString("sigla"));
                    notaFiscal.setUrlFotoPartido(jsonObjectPartido.getString("urlFoto"));

                    notaFiscal.setCota(jsonObject.getString("cota"));
                    notaFiscal.setUF(jsonObject.getString("uf"));
                    notaFiscal.setDataEmissao(Utilidade.converterStringDate(jsonObject.getString("dataEmissao")));
                    notaFiscal.setDescricao(jsonObject.getString("descricao"));
                    notaFiscal.setFornecedor(jsonObject.getString("fornecedor"));
                    notaFiscal.setCpfCnpj(jsonObject.getString("cpfcnpj"));
                    notaFiscal.setAno(jsonObject.getInt("ano"));
                    notaFiscal.setMes(jsonObject.getInt("mes"));
                    notaFiscal.setNumeroDocumento(jsonObject.getInt("numeroDocumento"));
                    notaFiscal.setValor(jsonObject.getString("valor"));
                    notaFiscal.setValorGlosa(jsonObject.getString("valorGlosa"));
                    notaFiscal.setValorLiquido(jsonObject.getString("valorLiquido"));

                }
                catch (JSONException e){
                    Log.i(TAG, "erro " + e.getLocalizedMessage());

                }

                listener.onDetalhesNotaFiscalRecebido(notaFiscal);
            }


        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                listener.onDetalhesNotaFiscalErro(context.getString(R.string.exception_detalhes_nota_fiscal));
            }
        });

    }
}