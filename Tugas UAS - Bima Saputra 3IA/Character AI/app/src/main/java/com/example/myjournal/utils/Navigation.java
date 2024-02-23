package com.example.myjournal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Navigation {
    public static void goToActivity(Activity activity1, Class<?> activity2Class) {
        Intent intent = new Intent(activity1, activity2Class);
        activity1.startActivity(intent);
    }
}
