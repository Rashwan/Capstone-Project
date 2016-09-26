package com.rashwan.redditclient.DI;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.rashwan.redditclient.R;
import com.rashwan.redditclient.common.utilities.TokenAuthenticator;
import com.rashwan.redditclient.data.CommentDeserializer;
import com.rashwan.redditclient.data.ListingDeserializer;
import com.rashwan.redditclient.data.MyAdapterFactory;
import com.rashwan.redditclient.data.db.RedditPostDBHelper;
import com.rashwan.redditclient.data.model.ListingKind;
import com.rashwan.redditclient.data.model.RedditCommentDataModel;
import com.rashwan.redditclient.data.provider.RedditPostMeta;
import com.rashwan.redditclient.service.AuthService;
import com.rashwan.redditclient.service.AuthServiceImp;
import com.rashwan.redditclient.service.RedditService;
import com.rashwan.redditclient.service.RedditServiceImp;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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
    public OkHttpClient provideAuthOkhttpClient(){

        String credentials = application.getString(R.string.reddit_api_client_id) + ":" ;
        String basicAuth = "Basic " + Base64.encodeToString(credentials.getBytes()
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
    public OkHttpClient provideOkhttpClient(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC);
        TokenAuthenticator tokenAuthenticator = new TokenAuthenticator();
        return new OkHttpClient.Builder().addInterceptor(logger)
                .authenticator(tokenAuthenticator).build();
    }

    @Provides @Singleton
    public Gson provideGson(){
        return new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .registerTypeAdapter(ListingKind.class
                , new ListingDeserializer())
                .registerTypeAdapter(RedditCommentDataModel.class
                ,new CommentDeserializer())
                .create();
    }
    @Provides @Named("auth") @Singleton
    public Retrofit provideAuthRetrofit(@Named("auth") OkHttpClient okHttpClient,Gson gson){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(application.getString(R.string.reddit_auth_base_url))
                .client(okHttpClient)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient,Gson gson){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(application.getString(R.string.reddit_api_base_url))
                .client(okHttpClient)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides @Singleton
    public AuthService provideAuthService(Application application, @Named("auth") Retrofit retrofit, SharedPreferences sp){
        return new AuthServiceImp(application,retrofit,sp);
    }

    @Provides @Singleton
    public RedditService provideRedditService(Application application,Retrofit retrofit,SharedPreferences sp){
        return new RedditServiceImp(application,retrofit,sp);
    }

    @Provides @Singleton
    public RedditPostDBHelper provideRedditPostDBHelper(Application application){
        return new RedditPostDBHelper(application);

    }

    @Provides @Singleton
    public StorIOContentResolver provideStorIOContentResolver(){
        return DefaultStorIOContentResolver.builder()
                .contentResolver(application.getContentResolver())
                .addTypeMapping(ListingKind.class
                    , ContentResolverTypeMapping.<ListingKind>builder()
                    .putResolver(RedditPostMeta.PUT_RESOLVER)
                    .getResolver(RedditPostMeta.GET_RESOLVER)
                    .deleteResolver(RedditPostMeta.DELETE_RESOLVER)
                    .build())
                .build();

    }


}
