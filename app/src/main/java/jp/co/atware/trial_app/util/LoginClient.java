/*
 * Copyright (c) 2017, atWare, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *  Neither the name of the atWare, Inc. nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL atWare, Inc. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jp.co.atware.trial_app.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.internal.http.HttpDate;

/**
 * ログイン用のWebViewClient
 */
public class LoginClient extends WebViewClient {

    public interface LoginCallBack {
        void onLoginSuccess(List<Cookie> cookies);
    }


    private final LoginCallBack callBack;

    public LoginClient(LoginCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onPageFinished(final WebView view, final String url) {
        super.onPageFinished(view, url);
        String cookies = null;
        if (url.equals(URLConstants.USER_DASHBOARD)
                && (cookies = CookieManager.getInstance().getCookie(url)) != null) {
            try {
                URL _url = new URL(url);
                final List<Cookie> cookieList = new ArrayList<>();
                for (String cookie : cookies.split(";")) {
                    String[] keyValue = cookie.trim().split("=");
                    Cookie.Builder cookieBuilder
                            = new Cookie.Builder()
                            .domain(_url.getHost())
                            .expiresAt(HttpDate.MAX_DATE)
                            .name(keyValue[0])
                            .value(keyValue[1]);
                    cookieList.add(cookieBuilder.build());
                }
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onLoginSuccess(cookieList);
                    }
                });

            } catch (MalformedURLException e) {
                Log.e("LoginClient", e.toString());
            }
        }
    }

}