package com.example.administrator.assetsmanagement.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.assetsmanagement.Interface.ToolbarClickListener;
import com.example.administrator.assetsmanagement.R;
import com.example.administrator.assetsmanagement.adapter.AssetRecyclerViewAdapter;
import com.example.administrator.assetsmanagement.base.ParentWithNaviActivity;
import com.example.administrator.assetsmanagement.bean.AssetInfo;
import com.example.administrator.assetsmanagement.bean.Person;
import com.example.administrator.assetsmanagement.utils.AssetsUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/12/31 0031.
 */

public class MyAssetListActivity extends ParentWithNaviActivity {

    List<AssetInfo> myAssetsList;
    AssetRecyclerViewAdapter adapter;
    @BindView(R.id.rc_my_assets_list)
    RecyclerView mRcMyAssetsList;

    @Override
    public String title() {
        return "我管理的资产";
    }

    @Override
    public Object left() {
        return R.drawable.ic_left_navi;
    }

    @Override
    public ToolbarClickListener getToolbarListener() {
        return new ToolbarClickListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {

            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assets);
        ButterKnife.bind(this);
        initNaviView();
        LinearLayoutManager ll = new LinearLayoutManager(this);
        mRcMyAssetsList.setLayoutManager(ll);
        AssetsUtil.AndQueryAssets(this,"mOldManager", BmobUser.getCurrentUser(Person.class),handler);
    }

    MyAssetHandler handler = new MyAssetHandler();
    class MyAssetHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AssetsUtil.SEARCH_ONE_ASSET:
                    myAssetsList = (List<AssetInfo>) msg.getData().getSerializable("assets");
                    List<AssetInfo> list = AssetsUtil.GroupAfterMerge(myAssetsList);
                    adapter = new AssetRecyclerViewAdapter(MyAssetListActivity.this, list, true);
                    mRcMyAssetsList.setAdapter(adapter);
                    break;
            }
        }
    }
}