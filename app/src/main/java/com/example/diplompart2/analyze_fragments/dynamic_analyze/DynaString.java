package com.example.diplompart2.analyze_fragments.dynamic_analyze;

import java.util.ArrayList;


public class DynaString {
    private  String apkId;
    private  String apk_name;
    private  ArrayList<String> javaSecurityMessageDigest = new ArrayList<>();   //#1
    private  ArrayList<String> javaxCryptoCipher = new ArrayList<>();   //#2
    private  ArrayList<String> javaLangSystem = new ArrayList<>();   //#3
    private  ArrayList<String> androidHardwareCamera = new ArrayList<>();   //#4
    private  ArrayList<String> androidTelephonySmsManager = new ArrayList<>();   //#5
    private  ArrayList<String> androidContentContentResolver = new ArrayList<>();   //#6
    private  ArrayList<String> androidLocationLocationManager = new ArrayList<>();   //#7
    private  ArrayList<String> androidContentContext = new ArrayList<>();   //#8
    private  ArrayList<String> javaIoFile = new ArrayList<>(); //#f
    private  ArrayList<URL> javaNetUri = new ArrayList<>(); //#u
    private  ArrayList<String> javaNetUrl = new ArrayList<>(); //#l
    private  ArrayList<String> javaNetServerSocket = new ArrayList<>(); //s

    public DynaString(String apkId, String apk_name,
                      ArrayList<String> javaSecurityMessageDigest,
                      ArrayList<String> javaxCryptoCipher,
                      ArrayList<String> javaLangSystem,
                      ArrayList<String> androidHardwareCamera,
                      ArrayList<String> androidTelephonySmsManager,
                      ArrayList<String> androidContentContentResolver,
                      ArrayList<String> androidLocationLocationManager,
                      ArrayList<String> androidContentContext,
                      ArrayList<String> javaIoFile,
                      ArrayList<URL> javaNetUri,
                      ArrayList<String> javaNetUrl,
                      ArrayList<String> javaNetServerSocket) {
        this.apkId = apkId;
        this.apk_name = apk_name;
        this.javaSecurityMessageDigest = javaSecurityMessageDigest;
        this.javaxCryptoCipher = javaxCryptoCipher;
        this.javaLangSystem = javaLangSystem;
        this.androidHardwareCamera = androidHardwareCamera;
        this.androidTelephonySmsManager = androidTelephonySmsManager;
        this.androidContentContentResolver = androidContentContentResolver;
        this.androidLocationLocationManager = androidLocationLocationManager;
        this.androidContentContext = androidContentContext;
        this.javaIoFile = javaIoFile;
        this.javaNetUri = javaNetUri;
        this.javaNetUrl = javaNetUrl;
        this.javaNetServerSocket = javaNetServerSocket;
    }

    public ArrayList<String> getAllMethodsOfHooks() {
        return allMethodsOfHooks;
    }

    private  ArrayList<String> allMethodsOfHooks = new ArrayList<>();

    public DynaString(ArrayList<String> allMethodsOfHooks) {
        this.allMethodsOfHooks = allMethodsOfHooks;
    }
}
