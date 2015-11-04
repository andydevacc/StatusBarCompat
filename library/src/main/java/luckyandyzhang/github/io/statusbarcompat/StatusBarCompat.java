package luckyandyzhang.github.io.statusbarcompat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class StatusBarCompat {

    public enum Style {
        FILL, //会填充状态栏
        NORMAL //不会填充状态栏
    }

    public static void init(Activity activity, int color) {
        init(activity, color, Style.NORMAL);
    }

    public static void init(Activity activity, int color, Style style) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = buildFakeStatusBar(activity, color);

            ViewGroup contentView = (ViewGroup) rootView.getChildAt(0);
            if (!(contentView instanceof DrawerLayout)) {
                contentView.setFitsSystemWindows(true);
            }

            if (style == Style.FILL) {
                rootView.addView(statusBarView, 0);
            } else if (style == Style.NORMAL) {
                rootView.addView(statusBarView);
            }

            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (style == Style.NORMAL) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setStatusBarColor(color);
            } else if (style == Style.FILL) {
                //获取DrawerLayout
                ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
                ViewGroup drawerLayout = (ViewGroup) rootView.getChildAt(0);

                //FILL模式,仅支持DrawerLayout的布局
                if (drawerLayout instanceof DrawerLayout) {
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

                    rootView.removeView(drawerLayout);

                    //获取DrawerLayout的DrawerContentView (相对于NavigationView而言...)
                    ViewGroup drawerContentView = (ViewGroup) getContentView(drawerLayout);
                    int drawerContentViewIndex = drawerLayout.indexOfChild(drawerContentView);
                    drawerLayout.removeView(drawerContentView);

                    //用LinearLayout将我们定义的状态栏和DrawerContentView添加进去...
                    LinearLayout container = new LinearLayout(activity);
                    container.setOrientation(LinearLayout.VERTICAL);
                    container.addView(buildFakeStatusBar(activity, color));
                    container.addView(drawerContentView);
                    drawerLayout.addView(container, drawerContentViewIndex);

                    //加上一层HackFrameLayout,让视图能在statusBar上绘制
                    HackFrameLayout hackFrameLayout = new HackFrameLayout(activity);
                    hackFrameLayout.addView(drawerLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    rootView.addView(hackFrameLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }
        }

    }

    private static View buildFakeStatusBar(Activity activity, int color) {
        ViewGroup.LayoutParams statusBarLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        View statusBarView = new View(activity);
        statusBarView.setBackgroundColor(color);
        statusBarView.setLayoutParams(statusBarLayoutParams);
        return statusBarView;
    }

    private static int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? activity.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    private static View getContentView(ViewGroup view) {
        int count = view.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = view.getChildAt(i);
            if (isContentView(child)) {
                return child;
            }
        }
        return null;
    }

    private static boolean isContentView(View child) {
        return ((DrawerLayout.LayoutParams) child.getLayoutParams()).gravity == Gravity.NO_GRAVITY;
    }
}
