package com.zhengsr.wanandroid.mvp.present;

import com.zhengsr.wanandroid.bean.PageDataInfo;
import com.zhengsr.wanandroid.bean.ProjectBean;
import com.zhengsr.wanandroid.bean.ProjectListBean;
import com.zhengsr.wanandroid.mvp.base.BasePresent;
import com.zhengsr.wanandroid.mvp.base.IBaseView;
import com.zhengsr.wanandroid.mvp.contract.IContractView;
import com.zhengsr.wanandroid.mvp.model.DataManager;
import com.zhengsr.wanandroid.net.CusSubscribe;
import com.zhengsr.wanandroid.utils.RxUtils;

import java.util.List;

/**
 * @author by  zhengshaorui on 2019/10/8
 * Describe:
 */
public class ProjectPresent extends BasePresent<IBaseView> {


    public ProjectPresent(IBaseView view) {
        super(view);
    }

    public void startLoad(){
        mView.showLoading();
        addSubscribe(
                mDataManager.getProjectSort()
                .compose(RxUtils.rxScheduers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CusSubscribe<List<ProjectListBean>>(mView){
                    @Override
                    public void onNext(List<ProjectListBean> projectListBeans) {
                        super.onNext(projectListBeans);
                        if (mView instanceof IContractView.IProjectListView){
                            ((IContractView.IProjectListView) mView).getProjectList(projectListBeans);
                        }
                    }
                })
        );
    }

    public void getDetailProject(int page,int cid){
         getDetailProject(page,cid,true);
    }

    private void getDetailProject(int page,int cid,boolean isRefresh){
        if (isRefresh) {
            mView.showLoading();
        }
        addSubscribe(
                mDataManager.getProjectInfo(page,cid)
                .compose(RxUtils.rxScheduers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CusSubscribe<PageDataInfo<List<ProjectBean>>>(mView){
                    @Override
                    public void onNext(PageDataInfo<List<ProjectBean>> info) {
                        super.onNext(info);
                        if (mView instanceof IContractView.IProjectDetailView){
                            ((IContractView.IProjectDetailView) mView)
                                    .getProjectDetail(info.getDatas(),info.getPageCount(),isRefresh);
                        }
                    }
                })
        );
    }

    public void loadMore(int page,int cid){
        getDetailProject(page,cid,false);
    }

}
