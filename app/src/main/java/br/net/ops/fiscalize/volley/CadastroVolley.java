package br.net.ops.fiscalize.volley;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.util.Log;
import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.utils.Utilidade;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class CadastroVolley  extends StringRequest {

    private static final String TAG = "CadastroVolley";

    private static final int METHOD = Request.Method.POST;
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final String REST_CADASTRO = Utilidade.REST_SERVIDOR + "cadastro.php";
    public static final String REST_CADASTRO_POST_USUARIO = "usuario";
    public static final String REST_CADASTRO_POST_SENHA = "senha";
    public static final String REST_CADASTRO_POST_NOME = "nome";
    public static final String REST_CADASTRO_POST_DISPOSITIVO = "dispositivo";
    private String usuario;
    private String senha;
    private String nome;
    private String dispositivo;

    public interface CadastroListener {
        public void onCadastroRealizado(String tokenId);
        public void onCadastroErro(String erro);
    }

    public CadastroVolley(String usuario, String senha, String nome, String dispositivo, final CadastroListener listener, final Context context) {
        super(METHOD, REST_CADASTRO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response:" + response.toString());

                try {
                    // Extrai json
                    JSONObject json = new JSONObject(response);
                    String tokenId = json.getString(Utilidade.REST_JSON_TOKEN_ID);
                    listener.onCadastroRealizado(tokenId);
                } catch(JSONException e) {
                    try {
                        // Procura Erro no retorno
                        JSONObject json = new JSONObject(response);
                        String erro = json.getString(Utilidade.REST_JSON_ERRO);
                        listener.onCadastroErro(erro);
                    } catch(JSONException ex) {
                        // Resposta inválida
                        listener.onCadastroErro(context.getString(R.string.exception_rest));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(TAG, "Não cadastrou: " + error.getLocalizedMessage());

            }
        });

        this.usuario = usuario;
        this.senha = senha;
        this.nome = nome;
        this.dispositivo = dispositivo;
    }

    @Override
    protected Map<String,String> getParams(){
        Map<String, String>  params = new HashMap<String, String>();
        params.put(REST_CADASTRO_POST_USUARIO, usuario);
        params.put(REST_CADASTRO_POST_SENHA, senha);
        params.put(REST_CADASTRO_POST_NOME, nome);
        params.put(REST_CADASTRO_POST_DISPOSITIVO, dispositivo);
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("Content-Type", CONTENT_TYPE);

        return params;
    }

}