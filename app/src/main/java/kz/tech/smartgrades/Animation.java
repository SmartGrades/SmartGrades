package kz.tech.smartgrades;

import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

public class Animation {

    public enum Type {
        LEFT {
            public int toInt() {
                return 0;
            }
        },
        TOP {
            public int toInt() {
                return 1;
            }
        },
        RIGHT {
            public int toInt() {
                return 2;
            }
        },
        BOT {
            public int toInt() {
                return 3;
            }
        }
    }

    private OnFinishListener onFinishListener;
    public interface OnFinishListener {
        void onFinish();
    }
    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public void Play(View view, Type type, long dur){
        TranslateAnimation translateAnimation = null;
        if (type == Type.LEFT)
            translateAnimation = new TranslateAnimation(0, view.getWidth(), 0, 0);
        else if (type == Type.TOP){
            translateAnimation = new TranslateAnimation(0, 0, 0, view.getHeight());
        }
        else if (type == Type.RIGHT)
            translateAnimation = new TranslateAnimation(view.getWidth(), 0, 0, 0);
        else if (type == Type.BOT)
            translateAnimation = new TranslateAnimation(0, 0, view.getHeight(), 0);
        assert translateAnimation != null;
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setDuration(dur);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {

            }
            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                if (onFinishListener != null) onFinishListener.onFinish();
            }
            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
    }
}
