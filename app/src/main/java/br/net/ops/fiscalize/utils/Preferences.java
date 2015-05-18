package br.net.ops.fiscalize.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;

import br.net.ops.fiscalize.exception.DeletarUsuarioException;
import br.net.ops.fiscalize.exception.SalvarUsuarioException;
import br.net.ops.fiscalize.exception.UsuarioException;
import br.net.ops.fiscalize.vo.Usuario;

public class Preferences {

private static final String TAG = "Preferences";

	public static final String LOGIN_PREFERENCES = "login_preferences";
    public static final String PREFERENCES_USUARIO_ID = "usuario_id";
	public static final String PREFERENCES_TOKEN_ID = "token_id";

	private SharedPreferences loginPreferences = null;
	private Context context;
	
	public Preferences(Context context) {
		this.context = context;
	}
	
	private SharedPreferences getLoginPreferences() {
		if(loginPreferences==null) {
			return context.getSharedPreferences(LOGIN_PREFERENCES, Activity.MODE_PRIVATE);
		} else {
			return loginPreferences;
		}
	}
	
	public Usuario resgatarUsuario() throws UsuarioException {
		String tokenId = getLoginPreferences().getString(PREFERENCES_TOKEN_ID, "");
        Integer usuarioId = getLoginPreferences().getInt(PREFERENCES_USUARIO_ID, 0);
		if(StringUtils.isEmpty(tokenId) || usuarioId<=0) {
			throw new UsuarioException(TAG, context);
		} else {
            Usuario usuario = new Usuario();
            usuario.setUsuarioId(usuarioId);
            usuario.setTokenId(tokenId);
			return usuario;
		}
	}
	
	public boolean salvarUsuario(Usuario usuario) throws SalvarUsuarioException {
		SharedPreferences.Editor editor = getLoginPreferences().edit();
		editor.putString(PREFERENCES_TOKEN_ID, usuario.getTokenId());
        editor.putInt(PREFERENCES_USUARIO_ID, usuario.getUsuarioId());
		if(!editor.commit()) {
			throw new SalvarUsuarioException(TAG, context);
		}
		return true;
	}
	
	public boolean deletarUsuario() throws DeletarUsuarioException {
		SharedPreferences.Editor editor = getLoginPreferences().edit();
		editor.remove(PREFERENCES_TOKEN_ID);
        editor.remove(PREFERENCES_USUARIO_ID);
		if(!editor.commit()) {
			throw new DeletarUsuarioException(TAG, context);
		}
		return true;
	}
	
}
