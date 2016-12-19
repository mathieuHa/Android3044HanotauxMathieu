package org.esiea.hanotauxmathieu.projetandroidhanotaux3044;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int NOTIF_ID = 551518185;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(getApplicationContext(),getString(R.string.msg), Toast.LENGTH_SHORT).show();
                NotificationCompat.Builder not = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).
                        setSmallIcon(R.mipmap.ic_launcher).
                        setContentText("Bonjour, Cher utilisateur").
                        setContentTitle("Bienvenue");
                NotificationManager notM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notM.notify(NOTIF_ID, not.build());
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent in = new Intent(getApplicationContext(),SearchMusicActivity.class);
                startActivity(in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.home_text).startAnimation(animation);

    }
}
