package com.zhengsr.wanandroid.net;



import com.zhengsr.wanandroid.bean.ArticleListBean;
import com.zhengsr.wanandroid.bean.BannerBean;
import com.zhengsr.wanandroid.bean.BaseResponse;
import com.zhengsr.wanandroid.bean.LoginBean;
import com.zhengsr.wanandroid.bean.RankBean;
import com.zhengsr.wanandroid.bean.RankListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author by  zhengshaorui on 2019/10/9
 * Describe: 统一网络服务接口类
 */
public interface HttpServerApi {
    @GET
    Observable<String> getJson(@Url String url);


    /**
     * https://www.wanandroid.com/banner/json
     * 获取 Banner 数据
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> getBanner();

    /**
     * 获取文章
     * https://www.wanandroid.com/article/list/num/json
     * @param num 页码
     * @return
     */
    @GET("article/list/{num}/json")
    Observable<BaseResponse<ArticleListBean>> getArticle(@Path("num") int num);

    /**
     * 登录
     * https://www.wanandroid.com/user/login
     * @param username
     * @param password
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<BaseResponse<LoginBean>> loginIn(@Field("username") String username,
                                                @Field("password") String password);

    /**
     * 获取积分排行版
     * https://www.wanandroid.com/coin/rank/page/json
     * @param page 页码
     * @return
     */
    @GET("coin/rank/{page}/json")
    Observable<BaseResponse<RankListBean>> getRankInfo(@Path("page") int page);

    /**
     * 获取个人积分，需要登录后
     * https://www.wanandroid.com/lg/coin/userinfo/json
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<BaseResponse<RankBean>> getUserRank();



}
