package br.net.ops.fiscalize.helper;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.vo.NotaFiscal;
import br.net.ops.fiscalize.volley.VolleySingleton;

public class NotaFiscalLayoutHelper {

    private static final String TAG = "NotaFiscalLayoutHelper";

    private static NotaFiscalLayoutHelper instance;

    public static synchronized NotaFiscalLayoutHelper getInstance() {
        if(instance==null) {
            instance = new NotaFiscalLayoutHelper();
        }
        return instance;
    }

    public void exibirNotaFiscal(Activity activity, ViewGroup viewGroup, NotaFiscal notaFiscal) {

        final ViewHolderNotaFiscal viewHolder = new ViewHolderNotaFiscal(viewGroup);

        viewHolder.nomeParlamentar.setText(notaFiscal.getParlamentar().getNome());
        viewHolder.emailParlamentar.setText(notaFiscal.getParlamentar().getEmail());
        viewHolder.nomePartido.setText(notaFiscal.getParlamentar().getPartido().getNome());
        viewHolder.siglaPartido.setText(notaFiscal.getParlamentar().getPartido().getSigla());
        viewHolder.cota.setText(notaFiscal.getCota().getNome());
        viewHolder.uf.setText(notaFiscal.getUf().getSigla());
        viewHolder.descricao.setText(notaFiscal.getDescricao());
        viewHolder.dataEmissao.setText(notaFiscal.getDataEmissao().toString());
        viewHolder.fornecedor.setText(notaFiscal.getFornecedor());
        viewHolder.cpfCnpj.setText(notaFiscal.getCpfCnpj());
        viewHolder.numeroDocumento.setText(notaFiscal.getNumeroDocumento());
        viewHolder.ano.setText(notaFiscal.getAno().toString());
        viewHolder.mes.setText(notaFiscal.getMes().toString());
        viewHolder.valor.setText(notaFiscal.getValor().toString());
        viewHolder.valorGlosa.setText(notaFiscal.getValorGlosa().toString());
        viewHolder.valorLiquido.setText(notaFiscal.getValorLiquido().toString());

        VolleySingleton.getInstance(activity).getImageLoader().get(notaFiscal.getParlamentar().getUrlImagem(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response != null && response.getBitmap() != null) {
                    viewHolder.fotoParlamentar.setImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Impossível resgatar foto do parlamentar!");
            }
        });

        VolleySingleton.getInstance(activity).getImageLoader().get(notaFiscal.getParlamentar().getPartido().getUrlImagem(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response != null && response.getBitmap() != null) {
                    viewHolder.fotoPartido.setImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Impossível resgatar foto do partido!");
            }
        });

    }

    private class ViewHolderNotaFiscal {

        public ImageView fotoParlamentar;
        public ImageView fotoPartido;

        public TextView nomeParlamentar;
        public TextView emailParlamentar;
        public TextView nomePartido;
        public TextView siglaPartido;
        public TextView cota;
        public TextView uf;
        public TextView descricao;
        public TextView dataEmissao;
        public TextView fornecedor;
        public TextView cpfCnpj;
        public TextView numeroDocumento;
        public TextView ano;
        public TextView mes;
        public TextView valor;
        public TextView valorGlosa;
        public TextView valorLiquido;

        public ViewHolderNotaFiscal(ViewGroup viewGroup) {
            fotoParlamentar = (ImageView) viewGroup.findViewById(R.id.image_foto_parlamentar);
            fotoPartido = (ImageView) viewGroup.findViewById(R.id.image_foto_partido);

            nomeParlamentar = (TextView) viewGroup.findViewById(R.id.text_nome_parlamentar);
            emailParlamentar = (TextView) viewGroup.findViewById(R.id.text_email_parlamentar);
            nomePartido = (TextView) viewGroup.findViewById(R.id.text_nome_partido);
            siglaPartido = (TextView) viewGroup.findViewById(R.id.text_sigla_partido);
            cota = (TextView) viewGroup.findViewById(R.id.text_cota);
            uf = (TextView) viewGroup.findViewById(R.id.text_uf);
            descricao = (TextView) viewGroup.findViewById(R.id.text_descricao);
            dataEmissao = (TextView) viewGroup.findViewById(R.id.text_data_emissao);
            fornecedor = (TextView) viewGroup.findViewById(R.id.text_fornecedor);
            cpfCnpj = (TextView) viewGroup.findViewById(R.id.text_cpf_cnpj);
            numeroDocumento = (TextView) viewGroup.findViewById(R.id.text_numero_documento);
            ano = (TextView) viewGroup.findViewById(R.id.text_ano);
            mes = (TextView) viewGroup.findViewById(R.id.text_mes);
            valor = (TextView) viewGroup.findViewById(R.id.text_valor);
            valorGlosa = (TextView) viewGroup.findViewById(R.id.text_valor_glosa);
            valorLiquido = (TextView) viewGroup.findViewById(R.id.text_valor_liquido);
        }

    }

}
