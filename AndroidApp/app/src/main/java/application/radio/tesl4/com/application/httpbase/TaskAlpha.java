package application.radio.tesl4.com.application.httpbase;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.navercorp.volleyextensions.volleyer.builder.GetBuilder;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

import application.radio.tesl4.com.application.App;

/**
 * Created by nowmkt on 2016-08-01.
 */
public class TaskAlpha<Result> /*extends BaseAsyncTask<String, Void, >*/
{
    protected String TAG = "Task";
    protected Class<Result> mResultClass;
    protected String mUrl;
    protected boolean showMsg;
    Context mContext;
    TaskListener<Result> mListener;
    ProgressDialog mProgress;


    //Result mResult;

    protected TaskAlpha(Context context,Class<Result> resClass, TaskListener<Result> listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mResultClass = resClass;
        this.showMsg = App.bDebug;
    }

    protected void showProgressDialog(String title, String message)
    {
        if(showMsg) mProgress = ProgressDialog.show(mContext, title, message);
    }

    protected void killProgressDialog()
    {
        if(mProgress != null)mProgress.dismiss();
    }

    protected void executeInternal(HashMap<String, Object> params, boolean checkUrl)
    {
        mUrl += assembleParameters(params);

        if(checkUrl) Log.w(TAG, mUrl);

        GetBuilder builder  = AppVolley.buildVolleyer().get(mUrl);

        builder.withListener(new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                //Log.w("onResponse", "response : " + response.toString());
                killProgressDialog();
                Result res;
                if(mResultClass == String.class)
                {
                    Log.w("onResponse", "encodedresponse : " + response);
                    mListener.onSucceed((Result)response);
                    postExecute((Result)response);
                }
                else
                {
                    Serializer serializer = new Persister(new AnnotationStrategy());
                    try
                    {
                        response = response.substring(response.indexOf("<?xml"));
                        Log.w("onResponse", "encodedresponse : " + response);
                        response = response.replace("\\r\\n", "\r\n");
                        res = serializer.read(mResultClass, response);
                        mListener.onSucceed(res);
                        postExecute(res);

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }


            }
        })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        killProgressDialog();
                        Log.w("onResponse", "uid : " + error.toString());
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }).execute();


    }

    public void execute(Object... params)
    {
        return;
    }

    protected void postExecute(Result resele)
    {

    }

    private String assembleParameters(HashMap<String, Object> params)
    {
        String assembleString = "";
        int i =0;
        for(HashMap.Entry<String, Object> it : params.entrySet())
        {
            //������ ��� ��� �ؾ� �ұ�...
            if(it != null && it.getValue().toString().isEmpty())
            {
                continue;
            }
            if(i == 0)
            {
                assembleString += "?"+it.getKey()+"="+it.getValue().toString();
            }
            else
            {
                assembleString += "&"+it.getKey()+"="+it.getValue().toString();
            }
            i++;
        }
        return assembleString;
    }


    public interface TaskListener<Result>
    {
        void onSucceed(Result result);
    }
}
