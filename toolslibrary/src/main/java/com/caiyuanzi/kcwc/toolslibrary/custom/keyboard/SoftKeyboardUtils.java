package com.caiyuanzi.kcwc.toolslibrary.custom.keyboard;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 系统软键盘：1.监听隐藏显示 2.显示键盘  3.隐藏键盘 4.键盘是否弹出 5.键盘高度 6.
 */
public class SoftKeyboardUtils {
    public interface OnShowHideListener {
        public void onStatusChange(boolean status);
    }

    /**
     * 软键盘显示隐藏监听
     *
     * @param onShowHideListener 软键盘显示隐藏回调。true：显示；false：隐藏；
     */
    public static void setOnStatusChangedListener(Activity activity, OnShowHideListener onShowHideListener) {
        AtomicBoolean isShown = new AtomicBoolean(false);//纪录根视图的显示高度
        ViewTreeObserver observer = activity.getWindow().getDecorView().getViewTreeObserver();
        observer.addOnGlobalLayoutListener(() -> {
            boolean isShowing = isShowing(activity);
            if (isShown.compareAndSet(!isShowing, isShowing)) {
                onShowHideListener.onStatusChange(isShowing);
            }
        });
    }

    /**
     * 显示键盘
     */
    public static void show(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    public static void hide(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏键盘
     */
    public static void hide(Activity activity) {
        hide(activity.getCurrentFocus());
    }

    /**
     * 判断软键盘是否弹出
     */
    public static boolean isShowing(Activity activity) {
        return getSoftKeyboardHeight(activity) > 0;
    }

    /**
     * 获取软键盘高度。
     * 注意：需要在软键盘弹出后获取
     */
    public static int getSoftKeyboardHeight(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int screenHeight = decorView.getHeight();
        int statusBarHeight = getStatusBarHeight(activity);
        int navigationBarHeight = getNavigationBarHeight(activity);
        Rect r = new Rect();
        decorView.getWindowVisibleDisplayFrame(r);//可见的内容区域
        int displayHeight = r.bottom - r.top;
        return screenHeight - statusBarHeight - navigationBarHeight - displayHeight;
    }

    /**
     * 获得状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取底部导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 关于fragment中软键盘的关闭
     */
    public static void hideFromDialogFragment(Context context,Activity activity){
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(context).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;

        if (SoftKeyboardUtils.isShowing(activity)){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
