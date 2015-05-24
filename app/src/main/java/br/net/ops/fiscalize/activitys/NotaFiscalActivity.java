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
    private static final int MAX_TENTATIVAS = 3;

    private ViewGroup viewGroupNotaFiscal;
    private ViewGroup viewGroupProgress;
    private ViewGroup viewGroupRecarregar;

    private Button buttonSuspeita;
    private Button buttonLimpa;
    private Button buttonNaoSei;
    private Button buttonRecarregar;

    private Preferences preferences;

    private Usuario usuario;
    private Suspeita suspeita;

    private int numeroTentativas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_nota_fiscal);

        try {
            this.viewGroupNotaFiscal = (ViewGroup) findViewById(R.id.view_group);
            this.viewGroupProgress = (ViewGroup) findViewById(R.id.view_group_progress);
            this.viewGroupRecarregar = (ViewGroup) findViewById(R.id.view_group_recarregar);

            this.buttonSuspeita = (Button) findViewById(R.id.button_suspeita);
            this.buttonLimpa = (Button) findViewById(R.id.button_limpa);
            this.buttonNaoSei = (Button) findViewById(R.id.button_nao_sei);
            this.buttonRecarregar = (Button) findViewById(R.id.button_recarregar);
            this.buttonRecarregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numeroTentativas=0;
                    carregarNotaFiscal();
                }
            });

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
        numeroTentativas=0;

        suspeita = new Suspeita();
        suspeita.setUsuario(usuario);
        suspeita.setNotaFiscal(notaFiscal);

        NotaFiscalLayoutHelper.getInstance().exibirNotaFiscal(this, viewGroupNotaFiscal, notaFiscal);

        exibirModoNotaFiscal();
    }

    @Override
    public void onDetalhesNotaFiscalErro(String erro) {
        Log.e(TAG, erro);
        numeroTentativas++;
        if(numeroTentativas<=MAX_TENTATIVAS) {
            carregarNotaFiscal();
        } else {
            Toast.makeText(this, getString(R.string.erro_carregar_nota_fiscal), Toast.LENGTH_LONG).show();
            exibirModoRecarregar();
        }
    }

    @Override
    public void onSuspeitaAdicionada(String mensagem) {
        carregarNotaFiscal();
    }

    @Override
    public void onSuspeitaErro(String erro) {
        Log.e(TAG, erro);
        Toast.makeText(this, getString(R.string.erro_adicionar_suspeita), Toast.LENGTH_LONG).show();
        exibirModoNotaFiscal();
    }

    private void adicionarSuspeitaNotaSuspeita() {
        adicionarSuspeita(false);
    }

    private void adicionarSuspeitaNotaLimpa() {
        adicionarSuspeita(true);
    }

    private void adicionarSuspeita(boolean isSuspeita) {
        exibirModoCarregando();

        suspeita.setSuspeita(isSuspeita);
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        SuspeitaVolley suspeitaVolley = new SuspeitaVolley(suspeita, this, this);
        queue.add(suspeitaVolley.getRequest());
    }

    private View.OnClickListener clickAguarde = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(NotaFiscalActivity.this, getString(R.string.mensagem_aguarde), Toast.LENGTH_SHORT).show();
        }
    };

    private void exibirModoCarregando() {
        this.buttonSuspeita.setOnClickListener(clickAguarde);
        this.buttonLimpa.setOnClickListener(clickAguarde);
        this.buttonNaoSei.setOnClickListener(clickAguarde);

        viewGroupNotaFiscal.setVisibility(View.GONE);
        viewGroupProgress.setVisibility(View.VISIBLE);
        viewGroupRecarregar.setVisibility(View.GONE);
    }

    private void exibirModoNotaFiscal() {
        buttonSuspeita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarSuspeitaNotaSuspeita();
            }
        });
        buttonLimpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarSuspeitaNotaLimpa();
            }
        });
        buttonNaoSei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarNotaFiscal();
            }
        });

        viewGroupNotaFiscal.setVisibility(View.VISIBLE);
        viewGroupProgress.setVisibility(View.GONE);
        viewGroupRecarregar.setVisibility(View.GONE);
    }

    private void exibirModoRecarregar() {
        viewGroupNotaFiscal.setVisibility(View.GONE);
        viewGroupProgress.setVisibility(View.GONE);
        viewGroupRecarregar.setVisibility(View.VISIBLE);
    }

}
