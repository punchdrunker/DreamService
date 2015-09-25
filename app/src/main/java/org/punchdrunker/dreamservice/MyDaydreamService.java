package org.punchdrunker.dreamservice;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.service.dreams.DreamService;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.Random;

/**
 * This class is a sample implementation of a DreamService. When activated, a
 * TextView will repeatedly, move from the left to the right of screen, at a
 * random y-value.
 * <p/>
 * Daydreams are only available on devices running API v17+.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MyDaydreamService extends DreamService {

    private static final String[] words = {
            "革命的パラダイムシフト",
            "フルコミット",
            "誰よりも責任を取りにいく",
            "消さない明かり",
            "労働歓喜",
            "終わらないランナーズ・ハイ",
            "当事者意識",
            "圧倒的成長",
            "超絶ロジカル",
            "絶対的求心力",
            "破壊的リーダーシップ",
            "根性",
            "かわいがり",
            "妥協なきソリューション",
            "一日は二十四時間じゃない",
            "石橋は薙ぎ払え",
            "力をねじ伏せる「パワー」",
            "地球を味方につける",
            "ガソリンを飲む",
            "神業的共感力",
            "ショウ・マスト・ゴー・オン",
            "生きていることに感謝",
            "ありがとう",
            "挨拶が全て",
            "目で語れ",
            "心からの笑顔",
            "今日お前は何をした",
            "あと何日生きられる",
            "のし上がれ",
            "ビッグデータ",
            "エコシステム",
            "キーパーこそ\nロングシュートを打つ",
            "お前はどうしたい？\nあなたは何したい？",
            "私心を捨てよ\n大義を抱け",
    };
    private static final TimeInterpolator sInterpolator = new LinearInterpolator();

    private final AnimatorListener mShowAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            // Start animation again
            startTextViewHideAnimation();
        }
    };

    private final AnimatorListener mHideAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            // Start animation again
            startTextViewShowAnimation();
        }
    };
    private final Random mRandom = new Random();

    private TextView mDreamTextView;
    private ViewPropertyAnimator mShowAnimator;
    private ViewPropertyAnimator mHideAnimator;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Exit dream upon user touch?
        setInteractive(false);

        // Hide system UI?
        setFullscreen(true);

        // Keep screen at full brightness?
        setScreenBright(true);

        // Set the content view, just like you would with an Activity.
        setContentView(R.layout.my_daydream);

        mDreamTextView = (TextView) findViewById(R.id.dream_text);
        mDreamTextView.setTypeface(Typeface.createFromAsset(getAssets(), "ipam.ttf"));
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        startTextViewShowAnimation();
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

        mShowAnimator.cancel();
        mHideAnimator.cancel();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        // TODO: Dismantle resources
        // (for example, detach from handlers and listeners).
    }

    private void startTextViewShowAnimation() {
        mDreamTextView.setText(getGreatWord());

        mShowAnimator = mDreamTextView.animate()
                .alpha(1.0f)
                .setDuration(1000)
                .setStartDelay(100)
                .setListener(mShowAnimListener)
                .setInterpolator(sInterpolator);
    }

    private void startTextViewHideAnimation() {
        mHideAnimator = mDreamTextView.animate()
                .alpha(0.0f)
                .setDuration(5000)
                .setStartDelay(5000)
                .setListener(mHideAnimListener)
                .setInterpolator(sInterpolator);
        mHideAnimator.start();
    }

    private String getGreatWord() {
        Integer key = mRandom.nextInt(words.length);
        return words[key];
    }
}
