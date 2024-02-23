package com.example.myjournal.utils;

import android.content.res.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JSONUtils {
    public static String loadJSONFromRawResource(Resources resources, int resourceId) {
        try {
            InputStream inputStream = resources.openRawResource(resourceId);

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
