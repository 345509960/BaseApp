package com.lyc.love.baselib.router;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class RouterBuilder {
    protected Context mContext;
    protected String mUrl;
    protected int mRequestCode;
    protected int mFlag;

    protected Bundle mBundle;


    protected RouterBuilder mRouterBuilder;

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

    public RouterBuilder withInteger(String key ,Integer value){
        checkBundleNotNull();
        this.mBundle.putInt(key,value);
        return this;
    }

    public RouterBuilder withByte(String key ,Byte value){
        checkBundleNotNull();
        this.mBundle.putByte(key,value);
        return this;
    }

    public RouterBuilder withByteArray(String key ,byte[] value){
        checkBundleNotNull();
        this.mBundle.putByteArray(key,value);
        return this;
    }

    public RouterBuilder withDouble(String key ,Double value){
        checkBundleNotNull();
        this.mBundle.putDouble(key,value);
        return this;
    }

    public RouterBuilder withFloat(String key ,Float value){
        checkBundleNotNull();
        this.mBundle.putFloat(key,value);
        return this;
    }

    public RouterBuilder withParcelable(String key ,Parcelable value){
        checkBundleNotNull();
        this.mBundle.putParcelable(key,value);
        return this;
    }

    public RouterBuilder withParcelableArrayList(String key , ArrayList<? extends Parcelable> value){
        checkBundleNotNull();
        this.mBundle.putParcelableArrayList(key,value);
        return this;
    }

    public RouterBuilder withSerializable(String key ,Serializable value){
        checkBundleNotNull();
        this.mBundle.putSerializable(key,value);
        return this;
    }
    public RouterBuilder withChar(String key ,char value){
        checkBundleNotNull();
        this.mBundle.putChar(key,value);
        return this;
    }
    public RouterBuilder withCharSequence(String key ,CharSequence value){
        checkBundleNotNull();
        this.mBundle.putCharSequence(key,value);
        return this;
    }
    public RouterBuilder withString(String key ,String value){
        checkBundleNotNull();
        this.mBundle.putString(key,value);
        return this;
    }
    public RouterBuilder withString(String key ,ArrayList<String> value){
        checkBundleNotNull();
        this.mBundle.putStringArrayList(key,value);
        return this;
    }

    private void checkBundleNotNull() {
        if (this.mBundle==null){
            this.mBundle=new Bundle();
        }
    }


    public RouterBuilder withRequestCode(int rquestCode){
        this.mRequestCode=rquestCode;
        return this;
    }

    public RouterBuilder withFlag(int flag){
        this.mFlag=flag;
        return this;
    }

}
