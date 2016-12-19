package org.esiea.hanotauxmathieu.projetandroidhanotaux3044;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class GetMusicService extends IntentService {

    private static final String TAG = "GetMusicService";
    private static final String ACTION_MUSIC = "org.esiea.hanotauxmathieu.projetandroidhanotaux3044.action.MUSIC";
    private static final String NAME_MUSIC = "org.esiea.hanotauxmathieu.projetandroidhanotaux3044.extra.NAME_MUSIC";
    private static final String URL_MUSIC = "org.esiea.hanotauxmathieu.projetandroidhanotaux3044.extra.URL_MUSIC";
    private static final String ACTION_DETAIL_MUSIC = "org.esiea.hanotauxmathieu.projetandroidhanotaux3044.action.DETAIL_MUSIC";

    public GetMusicService() {
        super("GetMusicService");
    }

    public static void startActionMusic(Context context, String nameMusic) {
        Log.d(TAG,"start action music");
        Intent intent = new Intent(context, GetMusicService.class);
        intent.setAction(ACTION_MUSIC);
        intent.putExtra(NAME_MUSIC, nameMusic);
        context.startService(intent);
    }

    public static void startActionDetailMusic(Context context, String urlMusic) {
        Log.d(TAG, "startActionDetailMusic: ");
        Intent intent = new Intent(context, GetMusicService.class);
        intent.setAction(ACTION_DETAIL_MUSIC);
        intent.putExtra(URL_MUSIC, urlMusic);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_MUSIC.equals(action)) {
                Log.d(TAG,"onHandleIntent");
                final String nameMusic = intent.getStringExtra(NAME_MUSIC);
                handleActionMusic(nameMusic);
            } else if (ACTION_DETAIL_MUSIC.equals(action)){
                Log.d(TAG, "onHandleIntent: ");
                final String urlMusic = intent.getStringExtra(URL_MUSIC);
                handleActionDetailMusic(urlMusic);
            }
        }
    }

    private void handleActionMusic(String nameMusic) {
        Log.d(TAG,"IT WORKED");
        URL url = null;
        try {
            Log.d(TAG,"https://api.spotify.com/v1/search?q="+nameMusic+"&type=track");
            url = new URL ("https://api.spotify.com/v1/search?q="+nameMusic+"&type=track");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                copyInputStreamToFile(connection.getInputStream(),
                        new File(getCacheDir(), "/musics.json"));
                Log.d(TAG,"DL complete");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SearchMusicActivity.MUSIC_UPDATE));
    }

    private void handleActionDetailMusic(String urlMusic) {
        Log.d(TAG,"IT WORKED");
        URL url = null;
        try {
            Log.d(TAG,urlMusic);
            url = new URL (urlMusic);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                copyInputStreamToFile(connection.getInputStream(),
                        new File(getCacheDir(), "/musics_detail.json"));
                Log.d(TAG,"DL complete");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(DetailMusicActivity.MUSIC_LOAD));
    }

    private void copyInputStreamToFile (InputStream in, File file){
        try {
            OutputStream ou =  new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))>0){
                ou.write(buf,0,len);
            }
            ou.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
