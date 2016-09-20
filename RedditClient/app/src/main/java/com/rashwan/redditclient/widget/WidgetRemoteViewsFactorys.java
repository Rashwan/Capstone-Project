package com.rashwan.redditclient.widget;

/**
 * Created by rashwan on 9/20/16.
 */
public class WidgetRemoteViewsFactorys {
//    private Context context;
//    private int widgetId;
//    private List<ListingKind> posts;
////    @Inject StorIOContentResolver storIOContentResolver;
//
//    public WidgetRemoteViewsFactorys(Context context, Intent intent) {
//        this.context = context;
//        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
////        RedditClientApplication.get(context).getApplicationComponent().inject(this);
//    }
//
//    @Override
//    public void onCreate() {
//    }
//
//    @Override
//    public void onDataSetChanged() {
//        posts = storIOContentResolver.get().listOfObjects(ListingKind.class).withQuery(Query.builder()
//                .uri(RedditPostMeta.CONTENT_URI).build()).prepare().executeAsBlocking();
//    }
//
//    @Override
//    public void onDestroy() {
//
//    }
//
//    @Override
//    public int getCount() {
//        return posts.size();
//    }
//
//    @Override
//    public RemoteViews getViewAt(int position) {
//        RedditPostDataModel post = (RedditPostDataModel) posts.get(position);
//        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
//        rv.setTextViewText(R.id.widget_tv_title,post.title());
//        Picasso.with(context).load(post.thumbnail()).into(rv,R.id.widget_iv_thumbnail,new int[]{widgetId});
//        return rv;
//    }
//
//    @Override
//    public RemoteViews getLoadingView() {
//        return null;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 1;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
}
