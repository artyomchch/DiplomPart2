package com.example.diplompart2.analyze_fragments.static_analyze_1.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroPart1 {
    @FormUrlEncoded
    @POST("/parts1")
    Call<Object> updatePart1(@FieldMap Map<String, String> map);
}
