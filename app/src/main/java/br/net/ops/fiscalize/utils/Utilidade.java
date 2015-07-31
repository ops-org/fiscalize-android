package br.net.ops.fiscalize.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.net.ops.fiscalize.BuildConfig;

public class Utilidade {

    private static final String TAG = "Utilidade";

    public static final boolean DEBUG = BuildConfig.DEBUG;

    // FACEBOOK
    public static final String PACKAGE = "br.net.ops.fiscalize";

    // SERVIDOR
    public static final String URL_SERVIDOR = "http://104.131.229.175"; // prod
    //public static final String URL_SERVIDOR = "http://192.168.0.222"; // dev

    // REST
    public static final String REST_SERVIDOR = URL_SERVIDOR + ":8080/fiscalize-web/rest/";
    public static final String REST_JSON_MENSAGEM = "mensagem";
    public static final String REST_JSON_ERRO = "erro";

    // IMAGENS
    public static final String IMG_PARLAMENTAR_URL = URL_SERVIDOR + "http://104.131.229.175/fiscalize/parlamentar/";
    public static final String IMG_PARLAMENTAR_EXT = ".jpg";

    public static final String IMG_PARTIDO_URL = URL_SERVIDOR + "/fiscalize/partido/";
    public static final String IMG_PARTIDO_EXT = ".gif";

    public static Date converterStringDate(String data) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return simpleDateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Data inválida: " + data);
            return null;
        }
    }

}
