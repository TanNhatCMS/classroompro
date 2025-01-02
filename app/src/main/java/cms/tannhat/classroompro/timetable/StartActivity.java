package cms.tannhat.classroompro.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import cms.tannhat.classroompro.R;

public class StartActivity extends AppCompatActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        final ImageView imageView = findViewById(R.id.imageView);
        final Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.start_demo);
        imageView.setAnimation(loadAnimation);
        loadAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(loadAnimation);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        splashScreen();
    }

    public void splashScreen() {
        new Handler().postDelayed(() -> {
            StartActivity.this.startActivity(new Intent(StartActivity.this, TimeTableActivity.class));
            StartActivity.this.overridePendingTransition(R.anim.side_in, R.anim.side_out);
            StartActivity.this.finish();
        }, 1500L);
    }
}