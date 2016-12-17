package com.ikould.musicpro;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

    }

    public void testHttpGet() {
        //String urlStr = "http://api.53iq.com/device/status/online?access_token=AtgfA5jZWjDHHWxHts0IJwX913TzV74Jz8qMofmHKUpBmO1nGC1Dki2CTOFQcqVA_cc2f1342144fc11be06d7455a8618b5cf0717472&device_id=100000651";
        String urlStr = "http://192.168.1.239:8080/ikould_page/cmdFind.do";
       /* String str = HttpsHelper.getInstance().
                get(urlStr);*/
        //Log.d("liudong", str);
    }

    public void testHttpPost() {
       /* String urlStr = "https://api.53iq.com/2/device?access_token=AtgfA5jZWjDHHWxHts0IJwX913TzV74Jz8qMofmHKUpBmO1nGC1Dki2CTOFQcqVA_cc2f1342144fc11be06d7455a8618b5cf0717472&mac=7c:c7:09:7b:b7:07&serial=OPDPVVOGOQ";
        //String urlStr = "http://192.168.1.239:8080/ikould_page/cmdFind.do";
        HttpsHelper.HttpCallBack callBack = new HttpsHelper.HttpCallBack() {
            @Override
            public void onFailure(Exception e) {
                Log.d("liudong", e.toString());
            }

            @Override
            public void onSuccess(String response) {
                Log.d("liudong", response);
            }
        };
        HttpsHelper.getInstance().post(urlStr, null, callBack);*/
    }
}