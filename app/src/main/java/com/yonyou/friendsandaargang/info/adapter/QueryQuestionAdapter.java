package com.yonyou.friendsandaargang.info.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.activity.operationmanager.ServiceRatingActivity;
import com.yonyou.friendsandaargang.info.bean.RecordBean;
import com.yonyou.friendsandaargang.info.bean.Tel;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/6/20.
 */

public class QueryQuestionAdapter extends BaseAdapter {

    private Context context;
    private List<RecordBean.ContentBean> list;
    private String userId;
    private String tel;
    private DialogSureCancel SureCancel;
    private ApiService apiService;

    public QueryQuestionAdapter(Context context, List<RecordBean.ContentBean> list, String userId) {
        this.context = context;
        this.list = list;
        this.userId = userId;
        apiService = ApiClient.retrofit().create(ApiService.class);
        getCustServTel();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_queryquestion, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvInfoccRdid.setText("问题编号：" + list.get(position).getInfoccRdid());
        holder.tvRecordContent.setText("问题描述：" + list.get(position).getRecordContent());
        holder.tvLastSolution.setText("解决方案：" + list.get(position).getLastSolution());
        //10331001暂存 10331002未处理 10331003已解决 10331004处理中 10331005待评价 10331006已关闭
        String status = list.get(position).getStatus();
        String status2 = "";
        switch (status) {
            case "10331001":
                status2 = "暂存";
                break;
            case "10331002":
                status2 = "未处理";
                break;
            case "10331003":
                status2 = "已解决";
                holder.tvLastSolution.setVisibility(View.VISIBLE);
                holder.tvCd.setVisibility(View.GONE);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv1.setImageResource(R.drawable.wc);
                holder.iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirm2evaluate(list.get(position).getRecordId());
                    }
                });
                break;
            case "10331004":
                status2 = "处理中";
                holder.tvLastSolution.setVisibility(View.GONE);
                holder.iv1.setVisibility(View.GONE);
                holder.tvCd.setVisibility(View.VISIBLE);
                holder.tvCd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remindrec(list.get(position).getRecordId());
                    }
                });
                break;
            case "10331005":
                status2 = "待评价";
                holder.tvLastSolution.setVisibility(View.VISIBLE);
                holder.tvCd.setVisibility(View.GONE);
                holder.iv1.setVisibility(View.VISIBLE);
                holder.iv1.setImageResource(R.drawable.pj);
                holder.iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ServiceRatingActivity.class);
                        intent.putExtra("infoccRdid", list.get(position).getInfoccRdid());
                        intent.putExtra("recordContent", list.get(position).getRecordContent());
                        context.startActivity(intent);
                    }
                });
                break;
            case "10331006":
                status2 = "已关闭";
                holder.tvLastSolution.setVisibility(View.VISIBLE);
                holder.iv1.setVisibility(View.GONE);
                holder.tvCd.setVisibility(View.GONE);
                break;
        }
        holder.tvStatus.setText("解决状态：" + status2);
        holder.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        return convertView;
    }

    public class ViewHolder {

        @BindView(R.id.tv_infoccRdid)
        TextView tvInfoccRdid;
        @BindView(R.id.tv_recordContent)
        TextView tvRecordContent;
        @BindView(R.id.tv_lastSolution)
        TextView tvLastSolution;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_cd)
        TextView tvCd;
        @BindView(R.id.iv1)
        ImageView iv1;
        @BindView(R.id.iv2)
        ImageView iv2;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //催单（运营管家）
    private void remindrec(String recordId) {
        Call<HttpResult> call = apiService.remindrec(userId, recordId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(context, model.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(context, "已成功催单").show();
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(context, "服务器异常").show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    //问题确认解决（运营管家）
    private void confirm2evaluate(String recordId) {
        Call<HttpResult> call = apiService.confirm2evaluate(userId, recordId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(context, model.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(context, "问题确认解决").show();
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(context, "服务器异常").show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 拨打电话
     */
    private void ShowDialog() {
        SureCancel = new DialogSureCancel(context);
        SureCancel.setCancelable(false);
        SureCancel.getTitleView().setText("提示");
        SureCancel.getContentView().setText("是否联系友车帮客服（工作时间：周一至周五9:00-18:00）" + "（" + tel + ")");
        SureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + tel);
                intent.setData(data);
                context.startActivity(intent);
                SureCancel.dismiss();
            }
        });
        SureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SureCancel.dismiss();
            }
        });
        SureCancel.show();
    }

    //获取客服电话
    private void getCustServTel() {
        Call<Tel> call = apiService.getCustServTel();
        call.enqueue(new RetrofitCallback<Tel>() {
            @Override
            public void onSuccess(Tel model) {
                if (model.getReturnMsg().equals("success") && model.getReturnCode() == 0) {
                    tel = model.getContent().toString();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(context, "服务器异常").show();

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
