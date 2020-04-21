package com.example.diplompart2.analyze_fragments.dynamic_analyze;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.diplompart2.analyze_fragments.static_analyze_1.retrofit.RetroPart1;
import com.example.diplompart2.analyze_fragments.static_analyze_2.TypeAtribute2;
import com.example.diplompart2.ui.home.HomeFragment;
import com.google.gson.Gson;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SaveJson {

    private static final String URL = "https://aqueous-temple-55115.herokuapp.com" ;
    private static String apkId = "2";
    private static String apk_name = "This";
    private static ArrayList<String> javaSecurityMessageDigest = new ArrayList<>();   //#1
    private static ArrayList<String> javaxCryptoCipher = new ArrayList<>();   //#2
    private static ArrayList<String> javaLangSystem = new ArrayList<>();   //#3
    private static ArrayList<String> androidHardwareCamera = new ArrayList<>();   //#4
    private static ArrayList<String> androidTelephonySmsManager = new ArrayList<>();   //#5
    private static ArrayList<String> androidContentContentResolver = new ArrayList<>();   //#6
    private static ArrayList<String> androidLocationLocationManager = new ArrayList<>();   //#7
    private static ArrayList<String> androidContentContext = new ArrayList<>();   //#8
    private static ArrayList<String> javaIoFile = new ArrayList<>(); //#f
    private static ArrayList<String> javaNetUri = new ArrayList<>(); //#u
    private static ArrayList<String> javaNetUrl = new ArrayList<>(); //#l
    private static ArrayList<String> javaNetServerSocket = new ArrayList<>(); //s


    private static ArrayList<String> javaIoFileDub = new ArrayList<>(); //#f2
    private static ArrayList<String> javaNetUriDub = new ArrayList<>(); //#u2

    private static ArrayList<String> allMethodsOfHooks = new ArrayList<>();
    private static ArrayList<String> deDupStringList; // обработка файлов
    private static ArrayList<String> deDupStringListUri; // обработка uri;
    private static ArrayList<String> deDupStringListUrl; // обработка url;
    private static ArrayList<String> deDupStringListSocket; // обработка socket;


    private static ArrayList<URL> listURL = new ArrayList<URL>();

    HomeFragment homeFragment = new HomeFragment();
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


    public static void setApkId(String apkId) {
        SaveJson.apkId = apkId;
    }

    public static void setApk_name(String apk_name) {
        SaveJson.apk_name = apk_name;
    }

    // variable to hold context
    private Context context;

//save the context recievied via constructor in a local variable

    public SaveJson(Context context){
        this.context=context;
    }


    // получаем данные
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
                javaSecurityMessageDigest.add(param);
            } else if (mainIndex.equals("2")) {
                javaxCryptoCipher.add(param);
            } else if (mainIndex.equals("3")) {
                javaLangSystem.add(param);
            } else if (mainIndex.equals("4")) {
                androidHardwareCamera.add(param);
            } else if (mainIndex.equals("5")) {
                androidTelephonySmsManager.add(param);
            } else if (mainIndex.equals("6")) {
                androidContentContentResolver.add(param);
            } else if (mainIndex.equals("7")) {
                androidLocationLocationManager.add(param);
            } else if (mainIndex.equals("8")) {
                androidContentContext.add(param);
            } else if (mainIndex.equals("f")) {
                javaIoFile.add(param);
            } else if (mainIndex.equals("u")) {
                javaNetUri.add(param);
            } else if (mainIndex.equals("l")) {
                javaNetUrl.add(param);
            } else if (mainIndex.equals("s")) {
                javaNetServerSocket.add(param);
            }

        }
        //обработка файлов
        deDupStringList = new ArrayList<>(new HashSet<>(javaIoFile));
        //обработка url
        javaNetUrl = new ArrayList<>(new HashSet<>(javaNetUrl));
        //обработка socket
        javaNetServerSocket = new ArrayList<>(new HashSet<>(javaNetServerSocket));
        // обработка uri
        deDupStringListUri = new ArrayList<>(new HashSet<>(javaNetUri));
        javaNetUri = deDupStringListUri;

        for (int i = 0; i < javaNetUri.size(); i++){
            int c = 1;
            StringBuilder timeToUri = new StringBuilder(javaNetUri.get(i));
            ArrayList<String> times = new ArrayList<>();
            String uri = timeToUri.substring(14);
            String time = timeToUri.substring(0,13);
            times.add(time);
            for (int j = c; j< javaNetUri.size(); j++){
                StringBuilder timeToUriDub = new StringBuilder(javaNetUri.get(j));
                String uriDub = timeToUriDub.substring(14);
                if (uri.equals(uriDub)) {
                    if (!time.equals(timeToUriDub.substring(0,13))){
                        times.add(timeToUriDub.substring(0,13));
                    }
                    javaNetUri.remove(c);
                }
                else
                    c++;
            }
//            Gson gsonUri = new Gson();
//            //URL url = new URL(uri, times);
            listURL.add(new URL(uri, times));
        }


    }

    //переводим обратно данные в json
    public String objectToJson(){
        Gson gson = new Gson();
        DynaString dynaString = new DynaString(
                apkId,
                apk_name,
                javaSecurityMessageDigest,
                javaxCryptoCipher,
                javaLangSystem,
                androidHardwareCamera,
                androidTelephonySmsManager,
                androidContentContentResolver,
                androidLocationLocationManager,
                androidContentContext,
                deDupStringList,
                listURL,
                javaNetUrl,
                javaNetServerSocket
                );
        return gson.toJson(dynaString);
    }

    //записываем данные в файл
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

        Toast toast = Toast.makeText(context,
                "Данные отправленны на сервер", Toast.LENGTH_LONG);
        toast.show();
    }


    public void retrofit(){
        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //interface
        RetroPart1 intf = retrofit.create(RetroPart1.class);
        Call<Object> putPart3 = intf.getPart3(objectToJson());
        try {
            Response<Object> reer = putPart3.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("retro", "retrofit: done");
    }


    //отправка сигнала об окончании процесса в фрагмент
    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    //отправка сигнала об окончании процесса в фрагмент
    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("getApplication", oldValue, newValue);
    }

}
