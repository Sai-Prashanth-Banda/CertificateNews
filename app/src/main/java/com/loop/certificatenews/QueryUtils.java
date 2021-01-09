package com.loop.certificatenews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String TAGS = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    private static List<News> extractFeatureFromJson(String nJson) {
        if (TextUtils.isEmpty(nJson)) {
            return null;
        }

        List<News> list = new ArrayList<>();

        try {

            JSONObject objectJson = new JSONObject(nJson);
            JSONObject newobjectJsom = objectJson.getJSONObject("response");
            JSONArray arrayJson = newobjectJsom.getJSONArray("results");

            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject objJson = arrayJson.getJSONObject(i);
                String mainTitle = objJson.getString("webTitle");
                String mainCategory = objJson.getString("sectionName");
                String mainDate = objJson.getString("webPublicationDate");
                String mainUrl = objJson.getString("webUrl");
                JSONArray mainTags = objJson.getJSONArray("tags");
                String mainAuthor = "";
                if (mainTags.length() != 0) {
                    JSONObject currentTag = mainTags.getJSONObject(0);
                    mainAuthor = currentTag.getString("webTitle");
                } else mainAuthor = "Anonymous";
                News newNews = new News(mainTitle, mainCategory, mainDate, mainUrl, mainAuthor);
                list.add(newNews);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Error", e);
        }
        return list;
    }

    public static List<News> fetchNewsData(String requestUrl) {
        URL newUrl = createUrl(requestUrl);

        String nJson = null;
        try {
            nJson = makeHttpRequest(newUrl);
        } catch (IOException e) {
            Log.e(TAGS, "Error", e);
        }

        List<News> newNews = extractFeatureFromJson(nJson);
        return newNews;
    }

    private static URL createUrl(String stringUrl) {
        URL nUrl = null;
        try {
            nUrl = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAGS, "Error ", e);
        }
        return nUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String nJson = "";

        if (url == null)
            return nJson;


        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                nJson = readFromStream(inputStream);
            } else Log.e(TAGS, "Error" + httpURLConnection.getResponseCode());
        } catch (IOException e) {
            Log.e(TAGS, "Error", e);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return nJson;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("Unicode Transformation"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                resultBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return resultBuilder.toString();
    }
}
