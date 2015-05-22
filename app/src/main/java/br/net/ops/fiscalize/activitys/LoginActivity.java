package br.net.ops.fiscalize.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.exception.SalvarUsuarioException;
import br.net.ops.fiscalize.exception.UsuarioException;
import br.net.ops.fiscalize.interfaces.LoginListener;
import br.net.ops.fiscalize.utils.Preferences;
import br.net.ops.fiscalize.vo.Usuario;
import br.net.ops.fiscalize.volley.AutorizarVolley;
import br.net.ops.fiscalize.volley.VolleySingleton;


public class LoginActivity extends Activity implements LoginListener {

    private static final String TAG = "LoginActivity";

    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Usuario usuario;
        try {
            this.preferences = new Preferences(this);
            usuario = preferences.resgatarUsuario();
        } catch (UsuarioException e) {
            usuario = new Usuario(); // usuario novo
        }

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        AutorizarVolley autorizarVolley = new AutorizarVolley(usuario, this, this);
        queue.add(autorizarVolley.getRequest());

    }

    @Override
    public void onLoginRealizado(Usuario usuario) {
        try {
            preferences.salvarUsuario(usuario);
            Intent irNotaFiscal = new Intent(this, NotaFiscalActivity.class);
            startActivity(irNotaFiscal);
            finish();
        } catch (SalvarUsuarioException e) {
            onLoginErro(e.getLocalizedMessage());
        }
    }

    @Override
    public void onLoginErro(String erro) {
        Log.e(TAG, erro);
        Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
        finish();
    }
}
