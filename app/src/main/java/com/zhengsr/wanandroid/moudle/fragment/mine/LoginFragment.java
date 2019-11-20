package com.zhengsr.wanandroid.moudle.fragment.mine;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gyf.immersionbar.ImmersionBar;
import com.zhengsr.wanandroid.R;
import com.zhengsr.wanandroid.moudle.fragment.base.BaseMvpFragment;
import com.zhengsr.wanandroid.view.PasswordView;
import com.zhengsr.wanandroid.view.UserView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @auther by zhengshaorui on 2019/11/13
 * describe:
 */
public class LoginFragment extends BaseMvpFragment {
    private static final String TAG = "LoginFragment";
    private static final int ANIM_TIME = 400;
    private EditText mLoginUserEt;
    private EditText mLoginPassEt;

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.login_in)
    View mLoginView;
    @BindView(R.id.login_register)
    View mRegisterView;
    @BindView(R.id.password_again)
    PasswordView mPasswordAgainView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }



    @Override
    public void initView(View view) {
        super.initView(view);
        mRegisterView.setVisibility(View.VISIBLE);

        initLoginView();
    }

    private void initLoginView() {
        UserView userView = mLoginView.findViewById(R.id.user_name);
        PasswordView passView = mLoginView.findViewById(R.id.password);
        mLoginUserEt = userView.getEditText();
        mLoginPassEt = passView.getEditText();
        mLoginUserEt.setText("zhengsr");
        mLoginPassEt.setText("17320220zsr");
    }

    @Override
    public void initDataAndEvent() {
        super.initDataAndEvent();
        slideAnim(true,0);
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(_mActivity)
                .statusBarDarkFont(true)
                .init();
    }

    @OnClick({R.id.login_register_iv,R.id.login_login_iv,R.id.bar_back})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.login_register_iv:
                slideAnim(false,ANIM_TIME);
                break;
            case R.id.login_login_iv:
                slideAnim(true,ANIM_TIME);
                break;
            case R.id.bar_back:
                pop();
                break;
                default:break;
        }
    }

    @Override
    protected int setTitleBar() {
        return R.id.common_bar;
    }



    /**
     * 实现左右划页
     * @param left
     * @param time
     */
    private void slideAnim(boolean left,int time){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator loginAnimTrans;
        ObjectAnimator reAnimTrans;
        ObjectAnimator reAnimAlpha;
        ObjectAnimator loginAnimAlpha;
        if (left){
            loginAnimTrans = ObjectAnimator.ofFloat(mLoginView,"translationX",mLoginView.getWidth(),0);
            loginAnimAlpha = ObjectAnimator.ofFloat(mLoginView,"alpha",0,1);
            reAnimTrans = ObjectAnimator.ofFloat(mRegisterView,"translationX",0,-mRegisterView.getWidth());
            reAnimAlpha = ObjectAnimator.ofFloat(mRegisterView,"alpha",1,0);
        }else{
            reAnimTrans = ObjectAnimator.ofFloat(mRegisterView,"translationX",-mRegisterView.getWidth(),0);
            reAnimAlpha = ObjectAnimator.ofFloat(mRegisterView,"alpha",0,1);
            loginAnimTrans = ObjectAnimator.ofFloat(mLoginView,"translationX",0,mLoginView.getWidth());
            loginAnimAlpha = ObjectAnimator.ofFloat(mLoginView,"alpha",1,0);
        }
        animatorSet.setDuration(time);
        animatorSet.play(loginAnimTrans).with(loginAnimAlpha).with(reAnimTrans).with(reAnimAlpha);
        animatorSet.start();
    }
}