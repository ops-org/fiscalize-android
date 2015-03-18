package br.net.ops.fiscalize.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.util.Log;
import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.interfaces.LoginListener;
import br.net.ops.fiscalize.utils.Utilidade;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLoginVolley extends StringRequest {

	private static final String TAG = "FacebookLoginVolley";

    private static final int METHOD = Request.Method.POST;
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	
	public static final String REST_LOGIN_FACEBOOK = Utilidade.REST_SERVIDOR + "login.php";
	public static final String REST_LOGIN_FACEBOOK_POST_ID = "facebookId";
	public static final String REST_LOGIN_FACEBOOK_POST_NOME = "nome";
	
	private String facebookId;
	private String nome;
	
	public FacebookLoginVolley(String facebookId, String nome, final LoginListener listener, final Context context) {
		super(METHOD, REST_LOGIN_FACEBOOK, new Response.Listener<String>() {
	    	@Override
	    	public void onResponse(String response) {
	    		Log.d(TAG, "Response:" + response.toString());
	    		
	    		try {
	    			// Extrai tokenId
        			JSONObject json = new JSONObject(response);
    	    	String tokenId = json.getString("tokenId");
    	    		
    	    		listener.onLoginRealizado(tokenId);
	        	} catch(JSONException e) {
	        		try {
	        			// Procura Erro no retorno
		    			JSONObject json = new JSONObject(response);

	        		} catch(JSONException ex) {
	        			// Resposta inválida
	        			listener.onLoginErro(context.getString(R.string.exception_rest));
	        		}
	        	}
	    	}
	    }, new Response.ErrorListener() {
	    	@Override
	        public void onErrorResponse(VolleyError error) {
		    	Log.w(TAG, "Não logou com o Facebook: " + error.getLocalizedMessage());

		    	
		    	listener.onLoginErro(context.getString(R.string.exception_login_facebook));
	    	}
	    });
		
		this.facebookId = facebookId;
		this.nome = nome;
	}
	
	@Override
    protected Map<String,String> getParams(){
		Map<String, String>  params = new HashMap<String, String>();  
		params.put(REST_LOGIN_FACEBOOK_POST_ID, facebookId);  
		params.put(REST_LOGIN_FACEBOOK_POST_NOME, nome);
        return params;
    }
	
	@Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("Content-Type", CONTENT_TYPE);

        return params;
    }
	
}