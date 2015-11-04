package luckyandyzhang.github.io.statusbarcompat;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

class HackFrameLayout extends FrameLayout {
    private Rect mInsets;

    public HackFrameLayout(Context context) {
        this(context, null);
    }

    public HackFrameLayout(Context context, AttributeSet attributeset) {
        super(context, attributeset);

        setWillNotDraw(true);

        ViewCompat.setOnApplyWindowInsetsListener(this,
                new android.support.v4.view.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                        if (null == mInsets) {
                            mInsets = new Rect();
                        }
                        mInsets.set(insets.getSystemWindowInsetLeft(),
                                insets.getSystemWindowInsetTop(),
                                insets.getSystemWindowInsetRight(),
                                insets.getSystemWindowInsetBottom());
                        setWillNotDraw(mInsets.isEmpty());
                        ViewCompat.postInvalidateOnAnimation(HackFrameLayout.this);
                        return insets.consumeSystemWindowInsets();
                    }
                });
    }
}
