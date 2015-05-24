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
import br.net.ops.fiscalize.vo.Cota;
import br.net.ops.fiscalize.vo.NotaFiscal;
import br.net.ops.fiscalize.vo.Parlamentar;
import br.net.ops.fiscalize.vo.Partido;
import br.net.ops.fiscalize.vo.Uf;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class NotaFiscalVolley extends JsonObjectRequest {

    private static final String TAG = "NotaFiscalVolley";

    private static final int METHOD = Request.Method.GET;

    public static final String URL = Utilidade.REST_SERVIDOR + "notafiscal/recuperar";


    public interface DetalhesNotaFiscalListener {
        void onDetalhesNotaFiscalRecebido(NotaFiscal notaFiscal);
        void onDetalhesNotaFiscalErro(String erro);
    }

    public NotaFiscalVolley(final DetalhesNotaFiscalListener listener, final Context context){

        super(METHOD, URL, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonNotaFiscal) {

                try {

                    JSONObject jsonParlamentar = jsonNotaFiscal.getJSONObject("parlamentar");

                    Parlamentar parlamentar = new Parlamentar();
                    parlamentar.setParlamentarId(jsonParlamentar.getInt("parlamentarId"));
                    parlamentar.setNome(jsonParlamentar.getString("nome"));
                    parlamentar.setNomeCivil(jsonParlamentar.optString("nomeCivil"));
                    parlamentar.setEmail(jsonParlamentar.optString("email"));
                    parlamentar.setProfissao(jsonParlamentar.optString("profissao"));
                    parlamentar.setIdeCadastro(jsonParlamentar.optInt("ideCadastro"));

                    JSONObject jsonPartido = jsonParlamentar.getJSONObject("partido");

                    Partido partido = new Partido();
                    partido.setPartidoId(jsonPartido.getInt("partidoId"));
                    partido.setSigla(jsonPartido.getString("sigla"));
                    partido.setNome(jsonPartido.optString("nome"));

                    JSONObject jsonCota = jsonNotaFiscal.getJSONObject("cota");

                    Cota cota = new Cota();
                    cota.setCotaId(jsonCota.getInt("cotaId"));
                    cota.setNome(jsonCota.getString("nome"));

                    JSONObject jsonUf = jsonNotaFiscal.getJSONObject("uf");

                    Uf uf = new Uf();
                    uf.setUfId(jsonUf.getInt("ufId"));
                    uf.setSigla(jsonUf.getString("sigla"));
                    uf.setNome(jsonUf.optString("nome"));

                    NotaFiscal notaFiscal = new NotaFiscal();

                    parlamentar.setPartido(partido);
                    notaFiscal.setParlamentar(parlamentar);
                    notaFiscal.setCota(cota);
                    notaFiscal.setUf(uf);

                    notaFiscal.setNotaFiscalId(jsonNotaFiscal.getInt("notaFiscalId"));
                    notaFiscal.setDescricao(jsonNotaFiscal.optString("descricao"));
                    notaFiscal.setDescricaoSubCota(jsonNotaFiscal.optString("descricaoSubCota"));
                    notaFiscal.setBeneficiario(jsonNotaFiscal.optString("beneficiario"));
                    notaFiscal.setCpfCnpj(jsonNotaFiscal.optString("cpfCnpj"));
                    notaFiscal.setAno(jsonNotaFiscal.getInt("ano"));
                    notaFiscal.setMes(jsonNotaFiscal.getInt("mes"));

                    notaFiscal.setNumeroDocumento(jsonNotaFiscal.optString("numeroDocumento"));
                    notaFiscal.setParcela(jsonNotaFiscal.optInt("parcela"));
                    notaFiscal.setTipoDocumentoFiscal(jsonNotaFiscal.getInt("tipoDocumentoFiscal"));

                    notaFiscal.setNomePassageiro(jsonNotaFiscal.optString("nomePassageiro"));
                    notaFiscal.setTrechoViagem(jsonNotaFiscal.optString("trechoViagem"));

                    notaFiscal.setDataEmissao(Utilidade.converterStringDate(jsonNotaFiscal.optString("dataEmissao")));

                    notaFiscal.setValor(new BigDecimal(jsonNotaFiscal.getDouble("valor")));
                    notaFiscal.setValorGlosa(new BigDecimal(jsonNotaFiscal.optDouble("valorGlosa", 0)));
                    notaFiscal.setValorLiquido(new BigDecimal(jsonNotaFiscal.optDouble("valorLiquido", 0)));

                    notaFiscal.setDataInclusao(Utilidade.converterStringDate(jsonNotaFiscal.getString("dataInclusao")));

                    listener.onDetalhesNotaFiscalRecebido(notaFiscal);

                } catch (JSONException e){
                    Log.i(TAG, "erro " + e.getLocalizedMessage());
                    listener.onDetalhesNotaFiscalErro(context.getString(R.string.exception_detalhes_nota_fiscal));
                }

            }


        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                listener.onDetalhesNotaFiscalErro(context.getString(R.string.exception_detalhes_nota_fiscal));
            }
        });

    }
}