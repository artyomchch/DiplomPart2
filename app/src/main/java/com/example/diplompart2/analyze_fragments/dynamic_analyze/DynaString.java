package com.example.diplompart2.analyze_fragments.dynamic_analyze;

import java.util.ArrayList;

public class DynaString {
    private  ArrayList<String> androidAccountsAccount = new ArrayList<>();   //#1
    private  ArrayList<String> androidServiceVoiceVoiceInteractionSession = new ArrayList<>();   //#2
    private  ArrayList<String> androidTelephonyPhoneStateListener = new ArrayList<>();   //#3
    private  ArrayList<String> androidViewInputmethodBaseInputConnection = new ArrayList<>();   //#4
    private  ArrayList<String> javaLangReflectMethod = new ArrayList<>();   //#5
    private  ArrayList<String> javaIoFile = new ArrayList<>(); //#f
    private  ArrayList<URL> javaNetUri = new ArrayList<>(); //#u


    public DynaString(ArrayList<String> androidAccountsAccount,
                      ArrayList<String> androidServiceVoiceVoiceInteractionSession,
                      ArrayList<String> androidTelephonyPhoneStateListener,
                      ArrayList<String> androidViewInputmethodBaseInputConnection,
                      ArrayList<String> javaLangReflectMethod,
                      ArrayList<String> javaIoFile,
                      ArrayList<URL> javaNetUri) {
        this.androidAccountsAccount = androidAccountsAccount;
        this.androidServiceVoiceVoiceInteractionSession = androidServiceVoiceVoiceInteractionSession;
        this.androidTelephonyPhoneStateListener = androidTelephonyPhoneStateListener;
        this.androidViewInputmethodBaseInputConnection = androidViewInputmethodBaseInputConnection;
        this.javaLangReflectMethod = javaLangReflectMethod;
        this.javaIoFile = javaIoFile;
        this.javaNetUri = javaNetUri;
    }

    public ArrayList<String> getAllMethodsOfHooks() {
        return allMethodsOfHooks;
    }

    private  ArrayList<String> allMethodsOfHooks = new ArrayList<>();

    public DynaString(ArrayList<String> allMethodsOfHooks) {
        this.allMethodsOfHooks = allMethodsOfHooks;
    }
}
