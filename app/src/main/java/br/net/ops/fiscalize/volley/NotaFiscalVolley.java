package br.net.ops.fiscalize.volley;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.util.Log;
import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.utils.UtilVolley;
import br.net.ops.fiscalize.utils.Utilidade;
import br.net.ops.fiscalize.vo.Cota;
import br.net.ops.fiscalize.vo.NotaFiscal;
import br.net.ops.fiscalize.vo.Parlamentar;
import br.net.ops.fiscalize.vo.Partido;
import br.net.ops.fiscalize.vo.Uf;
import br.net.ops.fiscalize.vo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class NotaFiscalVolley {

    private static final String TAG = "NotaFiscalVolley";

    public static final String URL = Utilidade.REST_SERVIDOR + "notafiscal/recuperar";
    private static final int METHOD = Request.Method.GET;

    public static final String PARAM_USUARIO_ID = "usuarioId";

    private Context context;
    private JsonObjectRequest request;
    private DetalhesNotaFiscalListener listener;

    public interface DetalhesNotaFiscalListener {
        void onDetalhesNotaFiscalRecebido(NotaFiscal notaFiscal);
        void onDetalhesNotaFiscalErro(String erro);
    }

    public NotaFiscalVolley(final Context context, final Usuario usuario, final DetalhesNotaFiscalListener listener){

        String url = URL + "?" + PARAM_USUARIO_ID + "=" + usuario.getUsuarioId();

        this.context = context;
        this.listener = listener;
        this.request = new JsonObjectRequest(METHOD, url, null, listenerSucesso, listenerErro);
    }

    private Response.Listener<JSONObject> listenerSucesso = new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject json) {
            try {
                JSONObject jsonParlamentar = json.getJSONObject("parlamentar");

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

                JSONObject jsonCota = json.getJSONObject("cota");

                Cota cota = new Cota();
                cota.setCotaId(jsonCota.getInt("cotaId"));
                cota.setNome(jsonCota.getString("nome"));

                JSONObject jsonUf = json.getJSONObject("uf");

                Uf uf = new Uf();
                uf.setUfId(jsonUf.getInt("ufId"));
                uf.setSigla(jsonUf.getString("sigla"));
                uf.setNome(jsonUf.optString("nome"));

                NotaFiscal notaFiscal = new NotaFiscal();

                parlamentar.setPartido(partido);
                notaFiscal.setParlamentar(parlamentar);
                notaFiscal.setCota(cota);
                notaFiscal.setUf(uf);

                notaFiscal.setNotaFiscalId(json.getInt("notaFiscalId"));
                notaFiscal.setDescricao(json.optString("descricao"));
                notaFiscal.setDescricaoSubCota(json.optString("descricaoSubCota"));
                notaFiscal.setBeneficiario(json.optString("beneficiario"));
                notaFiscal.setCpfCnpj(json.optString("cpfCnpj"));
                notaFiscal.setAno(json.getInt("ano"));
                notaFiscal.setMes(json.getInt("mes"));

                notaFiscal.setNumeroDocumento(json.optString("numeroDocumento"));
                notaFiscal.setParcela(json.optInt("parcela"));
                notaFiscal.setTipoDocumentoFiscal(json.getInt("tipoDocumentoFiscal"));

                notaFiscal.setNomePassageiro(json.optString("nomePassageiro"));
                notaFiscal.setTrechoViagem(json.optString("trechoViagem"));

                notaFiscal.setDataEmissao(Utilidade.converterStringDate(json.optString("dataEmissao")));

                notaFiscal.setValor(new BigDecimal(json.getDouble("valor")));
                notaFiscal.setValorGlosa(new BigDecimal(json.optDouble("valorGlosa", 0)));
                notaFiscal.setValorLiquido(new BigDecimal(json.optDouble("valorLiquido", 0)));

                notaFiscal.setDataInclusao(Utilidade.converterStringDate(json.getString("dataInclusao")));

                listener.onDetalhesNotaFiscalRecebido(notaFiscal);

            } catch (JSONException e){
                try {
                    // Procura Erro no retorno
                    String erro = json.getString(Utilidade.REST_JSON_ERRO);
                    listener.onDetalhesNotaFiscalErro(erro);
                } catch(JSONException ex) {
                    // Resposta invalida
                    Log.i(TAG, "erro " + e.getLocalizedMessage());
                    listener.onDetalhesNotaFiscalErro(context.getString(R.string.exception_detalhes_nota_fiscal));
                }
            }
        }
    };

    private Response.ErrorListener listenerErro = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            UtilVolley.logarErro(error);
            listener.onDetalhesNotaFiscalErro(context.getString(R.string.exception_detalhes_nota_fiscal));
        }
    };

    public JsonObjectRequest getRequest() {
        return request;
    }

}