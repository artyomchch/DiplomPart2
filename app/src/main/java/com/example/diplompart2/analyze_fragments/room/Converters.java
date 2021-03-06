package com.example.diplompart2.analyze_fragments.room;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {   // получение из базы данных
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {  // отправка в базу данных
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
