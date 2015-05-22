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
import br.net.ops.fiscalize.vo.Usuario;

public class AutorizarVolley {

	private static final String TAG = "AutorizarVolley";

	private static final int METHOD = Request.Method.POST;
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	
	private static final String REST_LOGIN = Utilidade.REST_SERVIDOR + "usuario/autorizar";
	private static final String PARAM_USUARIO_ID = "usuarioId";
	private static final String PARAM_TOKEN_ID = "tokenId";

    private static final String JSON_USUARIO_ID = "usuarioId";
    private static final String JSON_TOKEN_ID = "tokenId";

    private Context context;

    private StringRequest request;
    private LoginListener loginListener;

    public AutorizarVolley(final Usuario usuario, final LoginListener loginListener, final Context context) {

        this.request = new StringRequest(METHOD, REST_LOGIN, listenerSucesso, listenerErro) {
            @Override
            protected Map<String,String> getParams(){
                int usuarioId = 0;
                if(usuario!=null && usuario.getUsuarioId()!=null) {
                    usuarioId = usuario.getUsuarioId();
                }

                Map<String, String> params = new HashMap<>();
                params.put(PARAM_USUARIO_ID, String.valueOf(usuarioId));
                params.put(PARAM_TOKEN_ID, String.valueOf(usuario.getTokenId()));

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
        this.loginListener = loginListener;

    }

	private Response.Listener<String> listenerSucesso = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			try {
				// Extrai tokenId
				JSONObject json = new JSONObject(response);
				Integer usuarioId = json.getInt(JSON_USUARIO_ID);
                String tokenId = json.getString(JSON_TOKEN_ID);

                Usuario usuario = new Usuario();
                usuario.setUsuarioId(usuarioId);
                usuario.setTokenId(tokenId);

                loginListener.onLoginRealizado(usuario);
			} catch(JSONException e) {
				try {
					// Procura Erro no retorno
					JSONObject json = new JSONObject(response);
					String erro = json.getString(Utilidade.REST_JSON_ERRO);
                    loginListener.onLoginErro(erro);
				} catch(JSONException ex) {
					// Resposta inválida
                    loginListener.onLoginErro(context.getString(R.string.exception_rest));
				}
			}
		}
	};

    private Response.ErrorListener listenerErro = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.w(TAG, "Não logou: " + error.getLocalizedMessage());
            UtilVolley.logarErro(error);
            loginListener.onLoginErro(context.getString(R.string.exception_comunicacao));
        }
    };

    public StringRequest getRequest() {
        return request;
    }

}