package br.net.ops.fiscalize.activitys;

import com.android.volley.RequestQueue;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import br.net.ops.fiscalize.R;
import br.net.ops.fiscalize.vo.NotaFiscal;
import br.net.ops.fiscalize.volley.DetalhesNotaFiscalVolley;
import br.net.ops.fiscalize.volley.DetalhesNotaFiscalVolley.DetalhesNotaFiscalListener;
import br.net.ops.fiscalize.volley.VolleySingleton;

public class DetalhesNotaFiscalActivity extends ActionBarActivity implements DetalhesNotaFiscalListener {

    private static final String TAG = "DetalhesNFActivity";

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_nota_fiscal);
        this.context = (Context) this;

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        DetalhesNotaFiscalVolley mensagemVolley = new DetalhesNotaFiscalVolley(this, context);
        queue.add(mensagemVolley);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_nota_fiscal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetalhesNotaFiscalRecebido(NotaFiscal notaFiscal) {

    }

    @Override
    public void onDetalhesNotaFiscalErro(String erro) {

    }
}
