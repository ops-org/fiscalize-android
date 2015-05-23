package br.net.ops.fiscalize.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.interfaces.LoginListener;
import br.net.ops.fiscalize.utils.UtilVolley;
import br.net.ops.fiscalize.utils.Utilidade;
import br.net.ops.fiscalize.vo.Suspeita;
import br.net.ops.fiscalize.vo.Usuario;

public class SuspeitaVolley {

	private static final String TAG = "SuspeitaVolley";

	private static final int METHOD = Request.Method.POST;
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

	private static final String URL = Utilidade.REST_SERVIDOR + "suspeita/adicionar";
	private static final String PARAM_USUARIO_ID = "usuarioId";
	private static final String PARAM_NOTA_FISCAL_ID = "notaFiscalId";
    private static final String PARAM_SUSPEITA = "suspeita";
    private static final String PARAM_SUSPEITA_VALOR = "suspeitaValor";
    private static final String PARAM_SUSPEITA_OBJETO = "suspeitaObjeto";
    private static final String PARAM_SUSPEITA_FORNECEDOR = "suspeitaFornecedor";
    private static final String PARAM_COMENTARIOS = "comentarios";

    private Context context;

    private StringRequest request;
    private SuspeitaListener suspeitaListener;

    public interface SuspeitaListener {
        void onSuspeitaAdicionada(String mensagem);
        void onSuspeitaErro(String erro);
    }

    public SuspeitaVolley(final Suspeita suspeita, final SuspeitaListener suspeitaListener, final Context context) {

        this.request = new StringRequest(METHOD, URL, listenerSucesso, listenerErro) {
            @Override
            protected Map<String,String> getParams(){
                String usuarioId = "";
                if(suspeita.getUsuario()!=null && suspeita.getUsuario().getUsuarioId()!=null) {
                    usuarioId = String.valueOf(suspeita.getUsuario().getUsuarioId());
                }

                String notaFiscalId = "";
                if(suspeita.getNotaFiscal()!=null && suspeita.getNotaFiscal().getNotaFiscalId()!=null) {
                    notaFiscalId = String.valueOf(suspeita.getNotaFiscal().getNotaFiscalId());
                }

                Map<String, String> params = new HashMap<>();
                params.put(PARAM_USUARIO_ID, String.valueOf(usuarioId));
                params.put(PARAM_NOTA_FISCAL_ID, String.valueOf(notaFiscalId));
                params.put(PARAM_SUSPEITA, String.valueOf(suspeita.getSuspeita()));
                params.put(PARAM_SUSPEITA_VALOR, String.valueOf(suspeita.getSuspeitaValor()));
                params.put(PARAM_SUSPEITA_OBJETO, String.valueOf(suspeita.getSuspeitaObjeto()));
                params.put(PARAM_SUSPEITA_FORNECEDOR, String.valueOf(suspeita.getSuspeitaFornecedor()));
                params.put(PARAM_COMENTARIOS, suspeita.getComentarios());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", CONTENT_TYPE);
                return params;
            }
        };

        this.context = context;
        this.suspeitaListener = suspeitaListener;

    }

	private Response.Listener<String> listenerSucesso = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			try {
				// Extrai tokenId
				JSONObject json = new JSONObject(response);
				String mensagem = json.getString(Utilidade.REST_JSON_MENSAGEM);

                suspeitaListener.onSuspeitaAdicionada(mensagem);
			} catch(JSONException e) {
				try {
					// Procura Erro no retorno
					JSONObject json = new JSONObject(response);
					String erro = json.getString(Utilidade.REST_JSON_ERRO);
                    suspeitaListener.onSuspeitaErro(erro);
				} catch(JSONException ex) {
					// Resposta inválida
                    suspeitaListener.onSuspeitaErro(context.getString(R.string.exception_rest));
				}
			}
		}
	};

    private Response.ErrorListener listenerErro = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            UtilVolley.logarErro(error);
            suspeitaListener.onSuspeitaErro(context.getString(R.string.exception_comunicacao));
        }
    };

    public StringRequest getRequest() {
        return request;
    }

}