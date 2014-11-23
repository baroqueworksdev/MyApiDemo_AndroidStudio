
package jp.baroqueworksdev.myapidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    public interface ScrolledToBottomListener {

        void onScrollToBottom();
    }

    private ScrolledToBottomListener mScrolledToBottomListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollToBottomListener(ScrolledToBottomListener listener) {
        mScrolledToBottomListener = listener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (mScrolledToBottomListener != null) {
            View content = getChildAt(0);
            if (content != null) {
                if (y + this.getHeight() >= content.getHeight()) {
                    mScrolledToBottomListener.onScrollToBottom();
                }
            }
        }
    }

}
