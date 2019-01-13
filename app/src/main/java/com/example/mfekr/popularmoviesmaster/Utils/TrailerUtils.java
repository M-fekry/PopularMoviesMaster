package com.example.mfekr.popularmoviesmaster.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.mfekr.popularmoviesmaster.Model.Trailer;

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

public class TrailerUtils {
    private static final String LOG_TAG = TrailerUtils.class.getSimpleName();

    private TrailerUtils() {

    }

    private static URL createURL (String stringUrl){

        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem with building URL.", e);
        }
        return url;
    }

    private static String makeHttpRequest (URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG, "Error response code" +urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving the trailer JSON results", e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Trailer> extractResultsFromJson(String trailerJSON){

        if(TextUtils.isEmpty(trailerJSON)){
            return null;
        }

        List<Trailer> trailers = new ArrayList<>();

        try {

            JSONObject baseJSONResponse = new JSONObject(trailerJSON);

            JSONArray movieArray = baseJSONResponse.getJSONArray("results");


            for (int i = 0; i < movieArray.length(); i++){


                JSONObject currentTrailer = movieArray.getJSONObject(i);


                String name = currentTrailer.getString("name");
                String key = currentTrailer.getString("key");


                Trailer trailer = new Trailer(key, name);
                trailers.add(trailer);
            }

        } catch (JSONException e) {
            Log.e("TrailerUtils", "Problem with parsing JSON", e);

        }
        return trailers;
    }


    public static List<Trailer> fetchTrailerData(String requestUrl) {

        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with making http request.", e);

        }
        List<Trailer> trailers = extractResultsFromJson(jsonResponse);
        return trailers;
    }
}


