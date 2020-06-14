/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.android.background.MainActivity;

import java.io.IOException;
import java.net.URL;

/**
 * This class contains utility methods which update water and charging counts in SharedPreferences
 */
public final class PreferenceUtilities {

    public static String result ;
    public static final String KEY_INTERNET_CONTENT = "internet-content";
    private static final String DEFAULT_CONTENT = "其實我也不知道查老要抓什麼網路資料";

    synchronized private static void setInternetContent(Context context, String internetContent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_INTERNET_CONTENT, internetContent);
        editor.apply();
    }

    public static String getInternetContent(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String internetContent = prefs.getString(KEY_INTERNET_CONTENT, DEFAULT_CONTENT);
        return internetContent;
    }

    synchronized public static void updateInternetContent(Context context) {
        ////在這裡處理網路上抓到ㄉ資料!
        makeGithubSearchQuery(context);//會更新result//
        PreferenceUtilities.setInternetContent(context, result);
    }

    private static void makeGithubSearchQuery(Context context) {
        String query = "aaa";//看你想查詢什麼關鍵字
        URL searchUrl = NetworkUtils.buildUrl(query);
        new GithubQueryTask().doInBackground(searchUrl);


    }

    public static class GithubQueryTask {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }

//        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            result = githubSearchResults;
            return result;
        }

//        @Override
//        protected void onPostExecute(String githubSearchResults) {
//            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
//            if (githubSearchResults != null && !githubSearchResults.equals("")) {
//                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
//                result = githubSearchResults;
//            } else {
//                return;
//            }
//        }

    }


}