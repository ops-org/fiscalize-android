package br.net.ops.fiscalize.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import android.util.Log;

public class UtilVolley {

	private static final String TAG = "UtilVolley";

	public static void logarErro(VolleyError error) {
		if(Utilidade.DEBUG) {
			Log.w(TAG, "Erro Volley: " + error.getLocalizedMessage());
			
			// Retorno Cabeçalho HTTP
			NetworkResponse response = error.networkResponse;
	        if(response != null && response.data != null){
	            Log.i(TAG, "statusCode" + response.statusCode);
	            Log.i(TAG, "response.data" + String.valueOf(response.data.toString()));
	        }
			
	        // Tipo de erro gerado
	        if( error instanceof NetworkError) {
	            Log.i(TAG, "NetworkError");
	        } else if( error instanceof ServerError) {
	            Log.i(TAG, "ServerError");
	        } else if( error instanceof AuthFailureError) {
	            Log.i(TAG, "AuthFailureError");
	        } else if( error instanceof ParseError) {
	            Log.i(TAG, "ParseError");
	        } else if( error instanceof NoConnectionError) {
	            Log.i(TAG, "NoConnectionError");
	        } else if( error instanceof TimeoutError) {
	            Log.i(TAG, "TimeoutError");
	        }
		}
	}
	
	public static String tratarRetornoNull(String valor) {
		if(valor!=null) {
			if(!valor.equalsIgnoreCase("null")) {
				return valor;
			}
		}
		return null;
	}
	
}