package com.example.mfekr.popularmoviesmaster.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.mfekr.popularmoviesmaster.Model.Movie;

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

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

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

    private static String makeHttpRequest (URL url) throws IOException{
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
            Log.e(LOG_TAG,"Problem retriveing the movie JSON results", e);
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

    private static List<Movie> extractResultsFromJson(String movieJSON){

        if(TextUtils.isEmpty(movieJSON)){
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        try {

            JSONObject baseJSONResponse = new JSONObject(movieJSON);

            JSONArray movieArray = baseJSONResponse.getJSONArray("results");


            for (int i = 0; i < movieArray.length(); i++){


                JSONObject currentMovie = movieArray.getJSONObject(i);

                long voteCount = currentMovie.getLong("vote_count");
                int id = currentMovie.getInt("id");
                String voteAvg = currentMovie.getString("vote_average");
                String title = currentMovie.getString("title");
                String posterPath = currentMovie.getString("poster_path");
                String overview = currentMovie.getString("overview");
                String releaseDate = currentMovie.getString("release_date");

                Movie movie = new Movie(voteCount, id, voteAvg, title, posterPath, overview, releaseDate);
                movies.add(movie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem with parsing JSON", e);

        }
        return movies;
    }


    public static List<Movie> fetchMovieData(String requestUrl) {

        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with making http request.", e);

        }
        List<Movie> movies = extractResultsFromJson(jsonResponse);
        return movies;
    }
}
