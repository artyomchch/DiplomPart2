package com.example.diplompart2.analyze_fragments.dynamic_analyze;

import android.util.Log;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SaveJson {

    private static ArrayList<String> androidAccountsAccount = new ArrayList<>();   //#1
    private static ArrayList<String> androidServiceVoiceVoiceInteractionSession = new ArrayList<>();   //#2
    private static ArrayList<String> androidTelephonyPhoneStateListener = new ArrayList<>();   //#3
    private static ArrayList<String> androidViewInputmethodBaseInputConnection = new ArrayList<>();   //#4
    private static ArrayList<String> javaLangReflectMethod = new ArrayList<>();   //#5
    private static ArrayList<String> javaIoFile = new ArrayList<>(); //#f
    private static ArrayList<String> javaNetUri = new ArrayList<>(); //#u

    private static ArrayList<String> javaIoFileDub = new ArrayList<>(); //#f2
//    private static ArrayList<String> javaNetUriDub = new ArrayList<>(); //#u2

    private static ArrayList<String> allMethodsOfHooks = new ArrayList<>();
    private static ArrayList<String> deDupStringList;




    // получили данные
    public String read() throws IOException {

        // инициализируем поток вывода из файлу
        // Класс для работы потоком вывода из файла
        FileInputStream inputStream = new FileInputStream("/storage/emulated/0/Download/EdXposedManager/HookFolder/hookOfApp.txt");
        String charToString = "";
        // читаем первый символ с потока байтов
        int data = inputStream.read();
        char content;
        // если data будет равна 0 то это значит,
        // что файл пуст
        while (data != -1) {
            // переводим байты в символ
            content = (char) data;
            String str2 = String.valueOf(content);
            charToString += str2;
            // читаем следующий байты символа
            data = inputStream.read();
        }
        // закрываем поток чтения файла
        inputStream.close();


        return charToString;

    }

    //преброзование из json в обьект
    public void jsonToObject() throws IOException {
        Gson gson = new Gson();
        DynaString dynaString = gson.fromJson(read(), DynaString.class);
       // System.out.println(dynaString);
        allMethodsOfHooks = dynaString.getAllMethodsOfHooks();
    }

    //расскидка по переменным
    public void setParamName() {
        String param = "";
        for (int i = 0; i < allMethodsOfHooks.size(); i++) {
            StringBuilder sb = new StringBuilder(allMethodsOfHooks.get(i));
            sb.deleteCharAt(0);
            char firstIndex = allMethodsOfHooks.get(i).charAt(0);
            String mainIndex = String.valueOf(firstIndex);
            param = sb.toString();
            //  Log.d("I/EdXposed-Bridge:", param);
            //Log.d("I/EdXposed-Bridge:", String.valueOf(firstIndex));

            if (mainIndex.equals("1")) {
                androidAccountsAccount.add(param);
            } else if (mainIndex.equals("2")) {
                androidServiceVoiceVoiceInteractionSession.add(param);
            } else if (mainIndex.equals("3")) {
                androidTelephonyPhoneStateListener.add(param);
            } else if (mainIndex.equals("4")) {
                androidViewInputmethodBaseInputConnection.add(param);
            } else if (mainIndex.equals("5")) {
                javaLangReflectMethod.add(param);
            } else if (mainIndex.equals("f")) {
                javaIoFile.add(param);
            } else if (mainIndex.equals("u")) {
                //Log.d("I/EdXposed-Bridge:", javaNetUri.get(5));
                javaNetUri.add(param);
            }

        }
        //обработка файлов
        String paramFile = "";
        deDupStringList = new ArrayList<>(new HashSet<>(javaIoFile));
//        int c = 1;
//        for(int i = 0; i < javaIoFile.size(); i++){
//            paramFile = javaIoFile.get(i);
//            for (int j = c; j < javaIoFile.size(); j++){
//                if (javaIoFile.get(j).equals(paramFile)){
//                    javaIoFile.remove(j);
//                }
//            }
//            c++;
//        }
        // обработка url
//        String paramUrl;
//
//        for (int i = 0; i < javaNetUri.size(); i++){
//            int c = 0;
//            StringBuffer timeToUri = new StringBuffer(javaNetUri.get(i));
//            ArrayList<String> times = new ArrayList<>();
//            String uri = timeToUri.substring(14);
//            String time = timeToUri.substring(0,13);
//            for (int j = c; j< javaNetUri.size(); j++){
//
//            }
//        }


    }

    //переводим обратно данные в json
    public String objectToJson(){
        Gson gson = new Gson();
        DynaString dynaString = new DynaString(androidAccountsAccount,
                androidServiceVoiceVoiceInteractionSession,
                androidTelephonyPhoneStateListener,
                androidViewInputmethodBaseInputConnection,
                javaLangReflectMethod,
                deDupStringList,
                javaNetUri
                );
        return gson.toJson(dynaString);
    }

    public void write() throws IOException {

        // открываем поток ввода в файл
        // Класс для работы потоком ввода в файл
        FileOutputStream outputStreamFile = new
                FileOutputStream("/storage/emulated/0/Download/EdXposedManager/HookFolder/finishHook.txt");
        // записываем данные в файл, но
        // пока еще данные не попадут в файл,
        // а просто будут в памяти
        outputStreamFile.write(objectToJson().getBytes());
        Log.d("ssss", "write File All Hook");

        // только после закрытия потока записи,
        // данные попадают в файл
        outputStreamFile.close();
    }

}
