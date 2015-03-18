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

public class Utilidade {

    public static final boolean DEBUG = true;

    // FACEBOOK
    public static final String PACKAGE = "br.com.myflyers";
    public static final String FACEBOOK_CRYPTOGRAPHY = "SHA";

    private static final String TAG = "Utilidade";

    public static final String REST_SERVIDOR = "http://104.131.229.175/ops/";

    public static Date converterStringDate(String data) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Data inválida: " + data);
            return null;
        }
    }

    public static String converterDateString(Date data) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(data);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Data inválida: " + data);
            return null;
        }
    }

    public static void showFacebookHashKey(Context context) {
        if(DEBUG) {
            try {
                PackageInfo info = context.getPackageManager().getPackageInfo(Utilidade.PACKAGE, PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance(FACEBOOK_CRYPTOGRAPHY);
                    md.update(signature.toByteArray());
                    Log.i(TAG, "KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (Exception e) {
                Log.w(TAG, "Impossível recuperar Hash do Facebook!");
            }
        }
    }

}
