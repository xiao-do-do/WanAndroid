package com.zhengsr.wanandroid.moudle.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhengsr.wanandroid.R;
import com.zhengsr.wanandroid.bean.ArticleData;
import com.zhengsr.wanandroid.bean.ArticleListBean;
import com.zhengsr.wanandroid.bean.BannerBean;
import com.zhengsr.wanandroid.moudle.adapter.HomeAdapter;
import com.zhengsr.wanandroid.moudle.fragment.base.BaseNetFragment;
import com.zhengsr.wanandroid.moudle.fragment.mine.LoginFragment;
import com.zhengsr.wanandroid.mvp.contract.IContractView;
import com.zhengsr.wanandroid.mvp.present.HomePresent;
import com.zhengsr.wanandroid.view.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author by  zhengshaorui on 2019/10/8
 * Describe:
 */
public class HomeFragment extends BaseNetFragment<HomePresent> implements IContractView.IHomeView, BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener, BannerView.BannerItemClickListener {


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private HomePresent mHomePresent;
    private BannerView mBannerView;
    private HomeAdapter mHomeAdapter;
    private List<ArticleData> mArticleBeans = new ArrayList<>();
    @Override
    public HomePresent getPresent() {
        mHomePresent = HomePresent.create(this);
        return mHomePresent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        initRefreshlayout();
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(manager);
        mHomeAdapter = new HomeAdapter(R.layout.item_article_recy_layout, mArticleBeans);
        mBannerView = new BannerView(_mActivity);
        mHomeAdapter.addHeaderView(mBannerView);
        mRecyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnItemChildClickListener(this);
        mHomeAdapter.setOnItemClickListener(this);
        mBannerView.setBannerItemClickListener(this);
    }

    @Override
    public void initDataAndEvent() {
        super.initDataAndEvent();
        mHomePresent.startLoad(true);
    }



    @Override
    public void loadMainData(List<BannerBean> bannerBeans, ArticleListBean articleListBean) {
        if (bannerBeans != null) {
            mBannerView.setData(bannerBeans);
        }
        if (articleListBean != null){
            mArticleBeans.clear();
            mArticleBeans.addAll(articleListBean.getDatas());
        }
        mHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadArticle(ArticleListBean articleListBean) {
        if (articleListBean != null) {
            mArticleBeans.addAll(articleListBean.getDatas());
            mHomeAdapter.notifyDataSetChanged();
        }
    }

    private void initRefreshlayout() {
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mHomePresent.onRefresh();
                mSmartRefreshLayout.finishRefresh(1000);

            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mHomePresent.loadMore();
                mSmartRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    @Override
    public void reload() {
        super.reload();
        mHomePresent.startLoad(true);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.item_article_like:
                //是否登录
                boolean isLogin = isLogin();
                if (!isLogin){
                    useParentStart(LoginFragment.newInstance());
                }
                break;
            default:break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void itemClick(View view, BannerBean bannerBean) {

    }
}
