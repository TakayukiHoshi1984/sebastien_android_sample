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

package jp.co.atware.trial_app.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog.Builder;

import jp.co.atware.trial_app.R;

/**
 * アクセストークンを更新
 */
public class UpdateAccessToken extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        return new Builder(getActivity()).setTitle("認証情報の有効期限切れ")
                .setMessage("有効期限を更新しますか")
                .setNegativeButton("キャンセル", null)
                .setPositiveButton("更新する", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.base_layout, UserDashboard.forUpdate()).commit();
                    }
                }).create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
