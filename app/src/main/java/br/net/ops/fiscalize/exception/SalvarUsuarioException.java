package br.net.ops.fiscalize.exception;

import android.content.Context;
import android.util.Log;

import br.net.ops.fiscalize.R;

@SuppressWarnings("serial")
public class SalvarUsuarioException extends BaseException {

	private final static int KEY = R.string.exception_salvar_usuario;
	private final static int LEVEL = Log.ERROR;
	
	public SalvarUsuarioException(String tag, Context context) {
		super(context, tag, KEY, LEVEL);
	}

	public SalvarUsuarioException(String tag, Context context, Throwable cause) {
		super(context, tag, KEY, cause, LEVEL);
	}
	
}
