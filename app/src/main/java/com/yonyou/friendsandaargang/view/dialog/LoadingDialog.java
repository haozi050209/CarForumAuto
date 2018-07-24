package com.yonyou.friendsandaargang.view.dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.yonyou.friendsandaargang.R;



/**
 * Created by Luo on 2016/9/23.
 * desc:加载等待的Dialog
 */
public class LoadingDialog extends DialogUtils {


    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
    }


    public static class Builder{

        private Context context;
        private String message;
        private boolean isCancelable;
        private boolean isCancelOutside;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         * @param message
         * @return
         */

        public Builder setMessage(String message){
            this.message=message;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return
         */

        public Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         * @param isCancelOutside
         * @return
         */
        public Builder setCancelOutside(boolean isCancelOutside){
            this.isCancelOutside=isCancelOutside;
            return this;
        }

        public LoadingDialog create(){

            LayoutInflater inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.dialog_loading,null);
            LoadingDialog loadingDailog=new LoadingDialog(context, R.style.MyDialogStyle);
            TextView msgText= (TextView) view.findViewById(R.id.tipTextView);
            msgText.setText(message);
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(isCancelable);
            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);
            return  loadingDailog;

        }


    }
}