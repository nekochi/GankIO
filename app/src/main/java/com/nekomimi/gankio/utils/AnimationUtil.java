package com.nekomimi.gankio.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/26.
 */
public class AnimationUtil {

    public static void rotationYView(final TextView view,final String msg, final String tag){
        view.clearAnimation();
        final ObjectAnimator anima1 = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f);
        anima1.setDuration(200);

        final ObjectAnimator anima2 = ObjectAnimator.ofFloat(view, "rotationY", -90f, 0f);
        anima2.setDuration(200);
        anima1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                anima2.start();
                view.setText(msg);
                view.setTag(tag);
            }
        });
        anima1.start();
    }
}
