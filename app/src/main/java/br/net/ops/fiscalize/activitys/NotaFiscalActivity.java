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

    private ViewGroup viewGroupSuspeita;
    private ViewGroup viewGroupSuspeitaValor;
    private ViewGroup viewGroupSuspeitaObjeto;
    private ViewGroup viewGroupSuspeitaBeneficiario;

    private Button buttonSuspeita;
    private Button buttonLimpa;
    private Button buttonNaoSei;
    private Button buttonRecarregar;

    private Button buttonValorSim;
    private Button buttonValorNao;
    private Button buttonObjetoSim;
    private Button buttonObjetoNao;
    private Button buttonBeneficiarioSim;
    private Button buttonBeneficiarioNao;

    private Preferences preferences;

    private Usuario usuario;
    private Suspeita suspeita;

    private int numeroTentativas = 0;
    private boolean perguntandoSuspeita = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_nota_fiscal);

        try {
            this.viewGroupNotaFiscal = (ViewGroup) findViewById(R.id.view_group);
            this.viewGroupProgress = (ViewGroup) findViewById(R.id.view_group_progress);
            this.viewGroupRecarregar = (ViewGroup) findViewById(R.id.view_group_recarregar);

            this.viewGroupSuspeita = (ViewGroup) findViewById(R.id.view_group_suspeita);
            this.viewGroupSuspeitaValor = (ViewGroup) findViewById(R.id.view_group_suspeita_valor);
            this.viewGroupSuspeitaObjeto = (ViewGroup) findViewById(R.id.view_group_suspeita_objeto);
            this.viewGroupSuspeitaBeneficiario = (ViewGroup) findViewById(R.id.view_group_suspeita_beneficiario);

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

            this.buttonValorSim = (Button) findViewById(R.id.button_suspeita_valor_sim);
            this.buttonValorNao = (Button) findViewById(R.id.button_suspeita_valor_nao);
            this.buttonObjetoSim = (Button) findViewById(R.id.button_suspeita_objeto_sim);
            this.buttonObjetoNao = (Button) findViewById(R.id.button_suspeita_objeto_nao);
            this.buttonBeneficiarioSim = (Button) findViewById(R.id.button_suspeita_beneficiario_sim);
            this.buttonBeneficiarioNao = (Button) findViewById(R.id.button_suspeita_beneficiario_nao);

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
        NotaFiscalVolley mensagemVolley = new NotaFiscalVolley(this, usuario, this);
        queue.add(mensagemVolley.getRequest());
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
            Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
            exibirModoRecarregar();
        }
    }

    @Override
    public void onSuspeitaAdicionada(String mensagem) {
        carregarNotaFiscal();
        exibirSuspeita();
    }

    @Override
    public void onSuspeitaErro(String erro) {
        Log.e(TAG, erro);
        Toast.makeText(this, getString(R.string.erro_adicionar_suspeita), Toast.LENGTH_LONG).show();
        exibirModoNotaFiscal();
        exibirSuspeita();
    }

    @Override
    public void onBackPressed() {
        if(perguntandoSuspeita) {
            exibirSuspeita();
        } else {
            super.onBackPressed();
        }
    }

    private void adicionarSuspeitaNotaSuspeita() {
        suspeita.setSuspeita(true);
        adicionarSuspeita();
    }

    private void adicionarSuspeitaNotaLimpa() {
        suspeita.setSuspeita(false);
        suspeita.setSuspeitaValor(false);
        suspeita.setSuspeitaObjeto(false);
        suspeita.setSuspeitaBeneficiario(false);
        adicionarSuspeita();
    }

    private void adicionarSuspeita() {
        exibirModoCarregando();

        suspeita.setComentarios("android"); // todo: Alterar no futuro para uma flag

        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        SuspeitaVolley suspeitaVolley = new SuspeitaVolley(this, suspeita, this);
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
        buttonSuspeita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perguntandoSuspeita = true;
                exibirSuspeitaValor();
            }
        });

        buttonValorSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suspeita.setSuspeitaValor(true);
                exibirSuspeitaObjeto();
            }
        });
        buttonValorNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suspeita.setSuspeitaValor(false);
                exibirSuspeitaObjeto();
            }
        });

        buttonObjetoSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suspeita.setSuspeitaObjeto(true);
                exibirSuspeitaFornecedor();
            }
        });
        buttonObjetoNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suspeita.setSuspeitaObjeto(false);
                exibirSuspeitaFornecedor();
            }
        });

        buttonBeneficiarioSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perguntandoSuspeita = false;
                suspeita.setSuspeitaBeneficiario(true);
                adicionarSuspeitaNotaSuspeita();
            }
        });
        buttonBeneficiarioNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perguntandoSuspeita = false;
                suspeita.setSuspeitaBeneficiario(false);
                adicionarSuspeitaNotaSuspeita();
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

    private void exibirSuspeitaValor() {
        viewGroupSuspeita.setVisibility(View.GONE);
        viewGroupSuspeitaValor.setVisibility(View.VISIBLE);
    }

    private void exibirSuspeitaObjeto() {
        viewGroupSuspeitaValor.setVisibility(View.GONE);
        viewGroupSuspeitaObjeto.setVisibility(View.VISIBLE);
    }

    private void exibirSuspeitaFornecedor() {
        viewGroupSuspeitaObjeto.setVisibility(View.GONE);
        viewGroupSuspeitaBeneficiario.setVisibility(View.VISIBLE);
    }

    private void exibirSuspeita() {
        viewGroupSuspeitaValor.setVisibility(View.GONE);
        viewGroupSuspeitaObjeto.setVisibility(View.GONE);
        viewGroupSuspeitaBeneficiario.setVisibility(View.GONE);
        viewGroupSuspeita.setVisibility(View.VISIBLE);
    }

}
