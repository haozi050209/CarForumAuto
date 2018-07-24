package com.yonyou.friendsandaargang.info.activity.seeting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.adapter.JobAdapter;
import com.yonyou.friendsandaargang.info.bean.JobList;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 选择职位
 */

public class JobActivity extends BaseActivity {

    @BindView(R.id.job_list)
    ListView job_list;
    private JobAdapter jobAdapter;
    private List<JobList.ContentBean> list;
    private String form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择职位");
        //添加个标记判断是哪个职位进来的
        if (getIntent().getStringExtra("form") != null) {
            form = getIntent().getStringExtra("form");
        }
        getJobList();
    }


    private void getJobList() {
        showProgressDialog();
        Call<JobList> call = communityService().getJobList();
        call.enqueue(new RetrofitCallback<JobList>() {
            @Override
            public void onSuccess(JobList model) {
                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    initDatas(model);
                } else {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器异常").show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });

    }


    private void initDatas(JobList jobList) {
        list = jobList.getContent();
        jobAdapter = new JobAdapter(mContext, list);
        job_list.setAdapter(jobAdapter);
        job_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("jobname", list.get(position).getJob());
                intent.putExtra("jobid", list.get(position).getJobId());
                if (form.equals("job_one")) {
                    setResult(10, intent);
                    finish();
                } else if (form.equals("job_two")) {
                    setResult(20, intent);
                    finish();
                } else if (form.equals("job_therr")) {
                    setResult(30, intent);
                    finish();
                }else if (form.equals("job_dakai")){
                    setResult(40, intent);
                    finish();
                }else if (form.equals("job_user")){
                    setResult(50, intent);
                    finish();
                }
            }
        });
    }
}
