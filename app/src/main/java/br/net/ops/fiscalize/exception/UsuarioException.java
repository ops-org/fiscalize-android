package br.net.ops.fiscalize.exception;

import android.content.Context;
import android.util.Log;

import br.net.ops.fiscalize.R;

@SuppressWarnings("serial")
public class UsuarioException extends BaseException {

	private final static int KEY = R.string.exception_resgatar_usuario;
	private final static int LEVEL = Log.WARN;
	
	public UsuarioException(String tag, Context context) {
		super(context, tag, KEY, LEVEL);
	}

	public UsuarioException(String tag, Context context, Throwable cause) {
		super(context, tag, KEY, cause, LEVEL);
	}
	
}
