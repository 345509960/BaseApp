package com.yuanyuan.baseapp.ui;

import android.app.SearchManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lyc.love.baselib.ui.AbstractActivity;
import com.yuanyuan.baseapp.R;

public class SearchResultActivity extends AbstractActivity {

    private TextView mTextView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView(View view) {
        mTextView=view.findViewById(R.id.text);
        String SearchContent = getIntent().getStringExtra(SearchManager.QUERY);
        mTextView.setText(SearchContent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
