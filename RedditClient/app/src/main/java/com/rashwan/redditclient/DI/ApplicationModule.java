package com.rashwan.redditclient.DI;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.rashwan.redditclient.R;
import com.rashwan.redditclient.data.MyAdapterFactory;
import com.rashwan.redditclient.service.AuthService;
import com.rashwan.redditclient.service.AuthServiceImp;
import com.squareup.moshi.Moshi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.schedulers.Schedulers;


/**
 * Created by rashwan on 9/7/16.
 */

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public Application provideApplication(){
        return application;
    }

    @Provides @Named("auth") @Singleton
    public OkHttpClient provideOkhttpClient(){

        String credentials = application.getString(R.string.reddit_api_client_id) + ":" ;
        final String basicAuth = "Basic " + Base64.encodeToString(credentials.getBytes()
                ,Base64.NO_WRAP);

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);


        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization",basicAuth)
                    .method(original.method(),original.body()).build();
            return chain.proceed(request);
        }).addInterceptor(logger).build();
    }

    @Provides @Singleton
    public Moshi provideMoshi(){
        return new Moshi.Builder().add(MyAdapterFactory.create()).build();
    }
    @Provides @Named("auth") @Singleton
    public Retrofit provideRetrofit(@Named("auth") OkHttpClient okHttpClient,Moshi moshi){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(application.getString(R.string.reddit_auth_base_url))
                .client(okHttpClient)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }
    @Provides
    public SharedPreferences provideSharedPrefrences(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides @Singleton
    public AuthService provideAuthService(Application application,@Named("auth") Retrofit retrofit,SharedPreferences sp){
        return new AuthServiceImp(application,retrofit,sp);
    }
}
