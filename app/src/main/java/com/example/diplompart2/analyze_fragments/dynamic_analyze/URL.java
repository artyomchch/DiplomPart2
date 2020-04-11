package com.example.diplompart2.analyze_fragments.dynamic_analyze;

import java.util.ArrayList;

public class URL {
    public String url;
    public ArrayList<String> time = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public URL(String url, ArrayList<String> time) {
        this.url = url;
        this.time = time;
    }
}
