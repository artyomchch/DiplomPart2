package com.example.diplompart2.analyze_fragments.static_analyze_1;

import com.google.common.base.Strings;

public class TypeAtributes1 {
    static String model;
    static String imei;
    static String version;
    static Boolean root;

    TypeAtributes1(String model, String imei, String version, Boolean root) {
        TypeAtributes1.model = model;
        TypeAtributes1.imei = imei;
        TypeAtributes1.version = version;
        TypeAtributes1.root = root;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        TypeAtributes1.model = model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        TypeAtributes1.imei = imei;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        TypeAtributes1.version = version;
    }

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        TypeAtributes1.root = root;
    }
}
