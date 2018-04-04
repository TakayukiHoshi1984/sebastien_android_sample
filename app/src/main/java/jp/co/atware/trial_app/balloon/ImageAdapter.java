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

package jp.co.atware.trial_app.balloon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.co.atware.trial_app.chat.ChatApplication;

/**
 * ImageViewにネット画像を設定
 */
public class ImageAdapter extends AsyncTask<Void, Void, Bitmap> {

    private final ImageView imageView;
    private final String imageUrl;
    private boolean scroll;

    /**
     * コンストラクタ
     *
     * @param imageView ImageView
     * @param imageUrl  画像URL
     */
    public ImageAdapter(ImageView imageView, String imageUrl) {
        imageView.setTag(imageUrl);
        this.imageView = imageView;
        this.imageUrl = imageUrl;
    }

    /**
     * スクロールフラグをセット
     *
     * @param scroll 画像表示後にスクロールする場合にtrue
     */
    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        HttpURLConnection http = null;
        try {
            URL url = new URL(imageUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            try (InputStream input = http.getInputStream()) {
                return BitmapFactory.decodeStream(input);
            }

        } catch (Exception e) {
            Log.e("ImageAdapter", "unexpected error occurred.", e);
        } finally {
            if (http != null) {
                http.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && imageView.getTag().equals(imageUrl)) {
            imageView.setImageBitmap(bitmap);
            if (scroll) {
                ChatApplication.getInstance().scrollDown();
            }
        }
    }

}
