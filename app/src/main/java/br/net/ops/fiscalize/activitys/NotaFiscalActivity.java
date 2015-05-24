package br.net.ops.fiscalize.activitys;

import com.android.volley.RequestQueue;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.exception.UsuarioException;
import br.net.ops.fiscalize.helper.NotaFiscalLayoutHelper;
import br.net.ops.fiscalize.utils.Preferences;
import br.net.ops.fiscalize.vo.NotaFiscal;
import br.net.ops.fiscalize.vo.Suspeita;
import br.net.ops.fiscalize.vo.Usuario;
import br.net.ops.fiscalize.volley.NotaFiscalVolley;
import br.net.ops.fiscalize.volley.NotaFiscalVolley.DetalhesNotaFiscalListener;
import br.net.ops.fiscalize.volley.SuspeitaVolley;
import br.net.ops.fiscalize.volley.VolleySingleton;

public class NotaFiscalActivity extends Activity implements DetalhesNotaFiscalListener, SuspeitaVolley.SuspeitaListener {

    private static final String TAG = "NotaFiscalActivity";

    private ViewGroup viewGroup;
    private ViewGroup viewGroupProgress;

    private Button buttonSuspeita;
    private Button buttonLimpa;
    private Button buttonNaoSei;

    private Preferences preferences;

    private Usuario usuario;
    private Suspeita suspeita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_nota_fiscal);

        try {
            this.viewGroup = (ViewGroup) findViewById(R.id.view_group);
            this.viewGroupProgress = (ViewGroup) findViewById(R.id.view_group_progress);

            this.buttonSuspeita = (Button) findViewById(R.id.button_suspeita);
            this.buttonLimpa = (Button) findViewById(R.id.button_limpa);
            this.buttonNaoSei = (Button) findViewById(R.id.button_nao_sei);

            this.preferences = new Preferences(this);
            this.usuario = preferences.resgatarUsuario();

            carregarNotaFiscal();

        } catch (UsuarioException e) {
            Toast.makeText(this, getString(R.string.exception_resgatar_usuario), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void carregarNotaFiscal() {
        exibirModoCarregando();

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        NotaFiscalVolley mensagemVolley = new NotaFiscalVolley(this, this);
        queue.add(mensagemVolley);
    }

    @Override
    public void onDetalhesNotaFiscalRecebido(NotaFiscal notaFiscal) {
        // Log.d(TAG, notaFiscal.getParlamentar().getUrlImagem() + "|" + notaFiscal.getParlamentar().getPartido().getUrlImagem());
        NotaFiscalLayoutHelper.getInstance().exibirNotaFiscal(this, viewGroup, notaFiscal);

        buttonSuspeita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNotaSuspeita();
            }
        });

        buttonLimpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNotaLimpa();
            }
        });

        buttonNaoSei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarNotaFiscal();
            }
        });

        suspeita = new Suspeita();
        suspeita.setUsuario(usuario);
        suspeita.setNotaFiscal(notaFiscal);

        exibirDados();
    }

    @Override
    public void onDetalhesNotaFiscalErro(String erro) {
        Log.e(TAG, erro);
        Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuspeitaAdicionada(String mensagem) {
        carregarNotaFiscal();
    }

    @Override
    public void onSuspeitaErro(String erro) {
        Log.e(TAG, erro);
        Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
    }

    private void adicionarNotaSuspeita() {
        adicionarNota(false);
    }

    private void adicionarNotaLimpa() {
        adicionarNota(true);
    }

    private void adicionarNota(boolean isSuspeita) {
        exibirModoCarregando();

        suspeita.setSuspeita(isSuspeita);
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        SuspeitaVolley suspeitaVolley = new SuspeitaVolley(suspeita, this, this);
        queue.add(suspeitaVolley.getRequest());
    }

    private void exibirModoCarregando() {
        this.buttonSuspeita.setOnClickListener(clickAguarde);
        this.buttonLimpa.setOnClickListener(clickAguarde);
        this.buttonNaoSei.setOnClickListener(clickAguarde);

        viewGroupProgress.setVisibility(View.VISIBLE);
        viewGroup.setVisibility(View.GONE);
    }

    private void exibirDados() {
        viewGroupProgress.setVisibility(View.GONE);
        viewGroup.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener clickAguarde = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(NotaFiscalActivity.this, getString(R.string.mensagem_aguarde), Toast.LENGTH_SHORT).show();
        }
    };

}
