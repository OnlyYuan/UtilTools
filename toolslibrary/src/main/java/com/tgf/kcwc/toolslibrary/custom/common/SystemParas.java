package com.tgf.kcwc.toolslibrary.custom.common;

import android.content.Context;
import android.content.res.Resources;

/**
 * 获取系统的参数
 * @author  cpf
 * @date 2021/9/9
 *
 */
public class SystemParas {

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

}
