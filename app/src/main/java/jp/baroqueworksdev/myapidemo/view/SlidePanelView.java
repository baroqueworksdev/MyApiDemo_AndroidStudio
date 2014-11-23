
package jp.baroqueworksdev.myapidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import jp.baroqueworksdev.myapidemo.R;

public class SlidePanelView extends FrameLayout implements Animation.AnimationListener {

    private boolean mIsExpand;

    private int mExpandHeight = 0;

    private int mCollapseHeight;

    public SlidePanelView(Context context) {
        super(context);
    }

    public SlidePanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidePanelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(Context context) {
        inflate(context, R.layout.slide_panel_base, this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.slide_panel_contents);
        inflate(context, R.layout.contents, layout);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ImageView imageView = (ImageView) findViewById(R.id.slide_panel_tab);
        imageView.setOnTouchListener(mOnTouchListener);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        ImageView imageView = (ImageView) findViewById(R.id.slide_panel_tab);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.slide_panel);
        mCollapseHeight = layout.getHeight() - imageView.getHeight();
        layout.setTop(mCollapseHeight);

        mIsExpand = false;
    }

    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        private float mTouchDownY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchDownY = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    togglePanel();
                    break;

                case MotionEvent.ACTION_MOVE: {
                    float postion = mTouchDownY - event.getRawY();
                    setHeight(postion);
                }
                break;
            }

            return true;
        }

    };

    private void setHeight(float postion) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.slide_panel);
        if (mIsExpand) {
            layout.setTop(mExpandHeight - (int) postion);

        } else {
            layout.setTop(mCollapseHeight - (int) postion);
        }
    }

    private void togglePanel() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.slide_panel);
        float fromYDelta;
        float toYDelta;
        if (mIsExpand) {
            fromYDelta = mCollapseHeight - layout.getTop();
            toYDelta = 0.0f;
        } else {
            fromYDelta = 0.0f;
            toYDelta = layout.getTop();
            layout.setTop(mExpandHeight);
        }
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, toYDelta, fromYDelta);
        animation.setDuration((long) (500 * (Math.abs(fromYDelta - toYDelta) / Math
                .abs(mExpandHeight - mCollapseHeight))));
        animation.setAnimationListener(this);
        layout.startAnimation(animation);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.slide_panel);
        if (mIsExpand) {
            layout.setTop(mCollapseHeight);
        }
        mIsExpand = !mIsExpand;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

}
