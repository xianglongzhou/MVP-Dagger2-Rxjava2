package com.zenglb.framework.retrofit.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.zenglb.framework.R;

import java.lang.ref.WeakReference;


/**
 * 这里有内存泄漏
 * <p>
 * http://www.jianshu.com/p/3aa1a706d74c
 */
public class CustomWaitDialogUtil {
//    public static CustomWaitDialog waitDialog = null;

    private static WeakReference<Context> mThreadActivityRef;//弱引用
    private static WeakReference<CustomWaitDialog> waitDialog;//弱引用

    /**
     * 自定义用于等待的dialog,有动画和message提示
     * 调用stopWaitDialog()方法来取消
     *
     * @param mContext
     * @param message
     * @param canceledOnTouchOutside
     */
    public static void showWaitDialog(Context mContext, String message, boolean canceledOnTouchOutside) {

        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }

        Log.e("极端的关闭了",mContext+"哈哈哈哈哈哈哈============================="+((Activity) mContext).isFinishing());

        if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing()){
            Log.e("极端的关闭了","异步极端的关闭了Activity,但是还想显示Dialog=============================");
            return;
        }

        mThreadActivityRef = new WeakReference<>(mContext);
        waitDialog = new WeakReference<>(new CustomWaitDialog(mThreadActivityRef.get(), R.style.CustomHttpWaitDialog, message));

        waitDialog.get().setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (waitDialog != null && waitDialog.get() != null && !waitDialog.get().isShowing()) {
            waitDialog.get().show();
        }

    }

    /**
     * 自定义用于等待的dialog,有动画无message
     * 调用stopWaitDialog()方法来取消
     *
     * @param context
     * @param canceledOnTouchOutside
     */
    public static void showWaitDialog(Context context, boolean canceledOnTouchOutside) {
        showWaitDialog(context, null, canceledOnTouchOutside);
    }

    /**
     * 自定义用于等待的dialog,有动画和message提示
     * 调用stopWaitDialog()方法来取消
     *
     * @param context
     * @param canceledOnTouchOutside
     */
    public static void showWaitDialog(Context context, int messageId, boolean canceledOnTouchOutside) {
        showWaitDialog(context, context.getResources().getString(messageId), canceledOnTouchOutside);
    }

    /**
     * 取消等待dialog
     */
    public static void stopWaitDialog() {
        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }
    }
}
