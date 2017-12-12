package itg8.com.wmcapp.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.model.TempNoticeBoardModel;

/**
 * Created by USER-pc on 12/11/2017.
 */

public class NBReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("Network Available ", "Flag No 1");
        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            String json = intent.getExtras().getString(CommonMethod.SYNC_NB_JOB);


            List<TempNoticeBoardModel> list = new Gson().fromJson(json,new TypeToken<List<TempNoticeBoardModel>>(){}.getType());
            for (TempNoticeBoardModel model:list) {
                MyApplication.getInstance().deleteNoticeItemFromServer(context.getString(R.string.url_delete_notice),model.getNoticeId());
                Log.d("Network Available ", "Flag No 1");
                Log.d("Network Available ", "model "+new Gson().toJson(model));
            }

        }
    }
}
