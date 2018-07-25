package scwen.com.dialynote.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import scwen.com.dialynote.R;
import scwen.com.dialynote.adapter.publish.LocationAdapter;
import scwen.com.dialynote.appbase.BaseMvpActivity;
import scwen.com.dialynote.contract.LocationContract;
import scwen.com.dialynote.dialog.LoadingDialog;
import scwen.com.dialynote.presenter.LocationPresenter;

public class ChooseLocationActivity extends BaseMvpActivity<LocationPresenter> implements LocationContract.LocationView {


    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ry_location)
    RecyclerView mRyLocation;

    private LoadingDialog loadingDialog;

    LocationAdapter mLocationAdapter;

    List<PoiItem> mItemList = new ArrayList<>();


    private int page = 1;


    public static void start(Context context) {
        Intent intent = new Intent(context, ChooseLocationActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initData() {

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        showLoading();
                        mPresenter.getLocationList(page);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();


    }

    @Override
    public LocationPresenter createPresenter() {
        return new LocationPresenter(this);
    }

    @Override
    protected void initView() {

        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCanceledOnTouchOutside(false);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRyLocation.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mRyLocation.setLayoutManager(new LinearLayoutManager(this));

        mLocationAdapter = new LocationAdapter(mItemList);

        mLocationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mLocationAdapter.setCheckIndex(position);
                mLocationAdapter.notifyDataSetChanged();

                PoiItem poiItem = mItemList.get(position);
                EventBus.getDefault().post(poiItem);
                finish();
            }
        });


        mLocationAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                mPresenter.getLocationList(page);
            }
        }, mRyLocation);

        mRyLocation.setAdapter(mLocationAdapter);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ChooseLocationActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                page = 0;
                mPresenter.getLocationList(query, page);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_location;
    }


    @Override
    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showLocationList(List<PoiItem> poiItems, int page) {

        if (page == 0) {
            mItemList = poiItems;
            mLocationAdapter.setNewData(poiItems);
        } else {
            if (poiItems.size() > 0) {
                mLocationAdapter.addData(poiItems);
                mLocationAdapter.loadMoreComplete();
            } else {
                mLocationAdapter.loadMoreEnd();
            }
        }

    }

    @Override
    public void locationFailed() {
        Snackbar.make(mRyLocation, R.string.location_failed, Snackbar.LENGTH_SHORT).show();
    }
}
