package com.seabreeze.robot.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seabreeze.robot.R;
import com.seabreeze.robot.base.mvpbase.BaseView;
import com.seabreeze.robot.view.MultipleStatusView;

public abstract class BaseFragment extends Fragment implements BaseView {

    private MultipleStatusView pager;
    protected BaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();

        View view = View.inflate(getContext(), R.layout.base_fragment, null);
        pager = view.findViewById(R.id.pager);

        pager.setLoadListener(new MultipleStatusView.LoadListener() {
            @Override
            public void loading() {
                BaseFragment.this.load();
            }

            @Override
            public void loadView() {
                BaseFragment.this.initView();
            }
        });

        pager.setSuccessView(createSuccessView());
        return view;
    }

    public void show() {
        if (pager != null) {
            pager.showStateView();
        }
    }

    public void setState(MultipleStatusView.LoadResult result) {
        if (pager != null) {
            pager.setState(result);
        }
    }

    @Override
    public void showToast(String msg) {
        mActivity.showToast(msg);
    }

    public abstract View createSuccessView();

    public abstract void load();

    public abstract void initView();
}
