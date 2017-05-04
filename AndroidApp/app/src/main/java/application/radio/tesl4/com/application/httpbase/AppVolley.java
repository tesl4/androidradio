package application.radio.tesl4.com.application.httpbase;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NoCache;
import com.navercorp.volleyextensions.cache.universalimageloader.disc.impl.UniversalUnlimitedDiscCache;
import com.navercorp.volleyextensions.cache.universalimageloader.memory.impl.UniversalLimitedAgeMemoryCache;
import com.navercorp.volleyextensions.cache.universalimageloader.memory.impl.UniversalLruMemoryCache;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;

import java.io.File;

import static com.navercorp.volleyextensions.volleyer.Volleyer.volleyer;

/**
 * Created by nowmkt on 2016-08-01.
 */
public class AppVolley
{
    private static final int DEF_CACHE_SIZE = 20 * 1024 * 1024;
    private static final long DEF_MAX_AGE = 60;
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLaoder;
    private static ImageLoader.ImageCache memoryCache;

    public static void init(Context context)
    {
        if (context == null)
            throw new NullPointerException("context must not be null.");

        //Cache diskCache = getDefaultDiskCache(context);
        memoryCache = getDefaultImageCache(context);
        mRequestQueue = new DefaultRequestQueueFactory().create(context);
        mRequestQueue.start();
        mImageLaoder = new ImageLoader(mRequestQueue, new LruBitmapCache(context));

        initVolleyer();
    }

    static void initVolleyer()
    {
        volleyer(mRequestQueue).settings().setAsDefault().done();
    }

    static public Volleyer buildVolleyer()
    {
        return volleyer(mRequestQueue);
    }


    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            throw new IllegalStateException("RequestQueue is not initialized.");
        return mRequestQueue;
    }

    public static ImageLoader getImageLoader() {
        if (mImageLaoder == null)
            throw new IllegalStateException("ImageLoader is not initialized.");
        return mImageLaoder;
    }




    private static ImageLoader.ImageCache getDefaultImageCache(Context context)
    {
        return new UniversalLimitedAgeMemoryCache(new UniversalLruMemoryCache(DEF_CACHE_SIZE), DEF_MAX_AGE);
    }


    private static Cache  getDefaultDiskCache(Context context)
    {
        File cacheDir = getCacheDir(context);
        if(cacheDir == null)
        {
            return new NoCache();
        }
        return new UniversalUnlimitedDiscCache(cacheDir);
    }

    private static File getCacheDir(Context context)
    {
        File file = new File(context.getCacheDir().getPath()+"/wys");
        return file;

    }
}
