package com.lyc.love.baselib.router;

import android.content.Context;
import android.os.Bundle;

public class RouterBuilder {
    protected Context mContext;
    protected String mUrl;
    protected int mRequestCode;
    protected int mFlag;

    protected Bundle mBundle;

    public RouterBuilder withContext(Context context){
        this.mContext=context;
        return this;
    }

    public RouterBuilder withUrl(String url){
        this.mUrl=url;
        return this;
    }


    public RouterBuilder withBundle(Bundle bundle){
        this.mBundle=bundle;
        return this;
    }


    public RouterBuilder withRequestCode(int rquestCode){
        this.mRequestCode=rquestCode;
        return this;
    }

    public RouterBuilder withFlag(int flag){
        this.mFlag=flag;
        return this;
    }

    public RouterBuilder build(){
        return new RouterBuilder();
    }
}
