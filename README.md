# Capstone-Project
Capstone project for Udacity's [Android Developer Nanodegree Program](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801).
The project is a [Reddit](https://www.reddit.com) client (Reddit is a social news aggregation, web content rating, and discussion website). The project is a showcase for what we have learned throughout the entire program.

### Features:ssas
* See the reddit front page to discover trending posts.
* Choose a post to see its details (author, subreddit, points, comments)
* Choose a subreddit to see its details(tagline, subscribers, posts)
* Discover top trending subreddits and see thier content.
* Search for any post with keyword searching.
* Use the app's Widget to view trending posts at a glance.
### Screenshots
<p align="center">
<img src="https://github.com/Rashwan/Capstone-Project/blob/master/redame-art/reddit_client_home.png" width="30%" />
<img src="https://github.com/Rashwan/Capstone-Project/blob/master/redame-art/reddit_client_post.png" width="30%" />
<img src="https://github.com/Rashwan/Capstone-Project/blob/master/redame-art/reddit_client_search.png" width="30%" />
</p>
<p align="center">
<img src="https://github.com/Rashwan/Capstone-Project/blob/master/redame-art/reddit_client_subreddit.png" width="30%" />
</p>

### Inspiration 
The app is built using  [MVP (Model View Presenter)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) pattern, it draws inspiration from many open-sourced apps especially:
* [bourbon](https://github.com/hitherejoe/Bourbon) (Architicture).asd
* [plaid](https://github.com/nickbutcher/plaid) (Animation & Transitions).
* [Ribot's android-boilerplate](https://github.com/ribot/android-boilerplate) (Architicure & coding style).

### Requirements 
* JDK 1.8
* [Android SDK](http://developer.android.com/sdk/index.html).
* [Android Nougat (API 25)](https://developer.android.com/studio/releases/platforms.html) .
* Latest Android SDK Tools(Android studio 3.0 canary) and build tools.

### How to use : 
* Get an api key from [here](https://github.com/reddit/reddit/wiki/OAuth2) and add it to `gradle.properties` file.
* To build install and run a debug version, run this from the root of the project:

    `./gradlew app:assembleDebug`


### Libraries : 
* [Firebase admob](https://firebase.google.com/docs/admob/admob-firebase)
* [Firebase analytics](https://firebase.google.com/docs/analytics)
* [Picasso](http://square.github.io/picasso)
* [Retrofit](http://square.github.io/retrofit)
* [Gson](https://github.com/google/gson)
* [Okhttp](http://square.github.io/okhttp)
* [StoreIO](https://github.com/pushtorefresh/storio)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [AutoValue](https://github.com/google/auto/tree/master/value)
* [AutoValue GSON](https://github.com/rharter/auto-value-gson)
* [AutoValue parcel](https://github.com/rharter/auto-value-parcel)
* [Dagger 2](http://google.github.io/dagger)
* [ButterKnife](http://jakewharton.github.io/butterknife)
* [Timber](https://github.com/JakeWharton/timber)
