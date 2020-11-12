package in.techaddicts.bluenix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashscreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4400;

    Animation topAnim, rightToleft, leftToright, stayAnim;
    ImageView image1, image2, image3, image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  // This single line is to remove status bar
        setContentView(R.layout.activity_splashscreen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        stayAnim = AnimationUtils.loadAnimation(this, R.anim.stay_anim);
        rightToleft = AnimationUtils.loadAnimation(this, R.anim.right_to_left_anim);
        leftToright = AnimationUtils.loadAnimation(this,R.anim.left_to_right_anim);

        image1 = findViewById(R.id.abc);
        image2 = findViewById(R.id.abc2);
        image3 = findViewById(R.id.abc3);
        image4 = findViewById(R.id.abc4);

        image1.setAnimation(topAnim);
        image2.setAnimation(stayAnim);
        image3.setAnimation(rightToleft);
        image4.setAnimation(leftToright);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashscreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}