package com.duatson.studentapp.network;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.duatson.studentapp.R;
import com.duatson.studentapp.model.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ServiceEntry {
    private static final String TAG = ServiceEntry.class.getSimpleName();

    public ServiceEntry() {
        // empty
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<Service> initServiceEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.services);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }

        String jsonServicesString = writer.toString();
        Gson gson = new Gson();
        Type serviceListType = new TypeToken<ArrayList<Service>>() {
        }.getType();

        return gson.fromJson(jsonServicesString, serviceListType);
    }
}
