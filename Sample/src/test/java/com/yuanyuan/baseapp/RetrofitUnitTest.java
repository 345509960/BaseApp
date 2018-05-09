package com.yuanyuan.baseapp;

import android.graphics.Movie;

import com.lyc.love.baselib.http.RetrofitManage;
import com.lyc.love.baselib.http.fastJsonConver.FastJsonConverterFactory;
import com.yuanyuan.baseapp.http.ApiService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RetrofitUnitTest {
    private Retrofit mRetrofit;
    @Before
    public void setUp(){
        mRetrofit= RetrofitManage.getInstance();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        ApiService apiService=mRetrofit.create(ApiService.class);
        apiService.getTopMovie(0,10)
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        System.out.println(movie);
                        Assert.assertNotNull(movie);
                    }
                });
    }
}