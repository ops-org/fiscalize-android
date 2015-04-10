package br.net.ops.fiscalize.volley;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.util.Log;
import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.interfaces.LoginListener;
import br.net.ops.fiscalize.utils.UtilVolley;
import br.net.ops.fiscalize.utils.Utilidade;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginAnonimoVolley extends StringRequest {

    private static final String TAG = "LoginAnonimoVolley";

    private static final int METHOD = Request.Method.GET;

    public static final String REST_LOGIN_ANONIMO = Utilidade.REST_SERVIDOR + "loginanonimo.php?";


    public LoginAnonimoVolley( final LoginListener listener, final Context context) {
        super(METHOD, REST_LOGIN_ANONIMO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response:" + response.toString());

                try {
                    // Extrai tokenId
                    JSONObject json = new JSONObject(response);
                    String tokenId = json.getString(Utilidade.REST_JSON_TOKEN_ID);
                    listener.onLoginRealizado(tokenId);
                } catch(JSONException e) {
                    try {
                        // Procura Erro no retorno
                        JSONObject json = new JSONObject(response);
                        String erro = json.getString(Utilidade.REST_JSON_ERRO);
                        listener.onLoginErro(erro);
                    } catch(JSONException ex) {
                        // Resposta inválida
                        listener.onLoginErro(context.getString(R.string.exception_rest));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(TAG, "Não logou: " + error.getLocalizedMessage());
                UtilVolley.logarErro(error);
                listener.onLoginErro(context.getString(R.string.exception_comunicacao));
            }
        });

    }

}