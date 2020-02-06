package com.example.diplompart2.analyze_fragments.static_analyze_2.permission;

import android.util.Log;

import java.util.ArrayList;

public  class GetPermission {
    public ArrayList<String> getPermission(String XML){
        String delimeter = "<uses-permission name=\"";
        String delimeter2 = "<permission";

        String[] subStr;  // Содержит список uses-permission
        String[] onlyPermission;  // содержит список permission
        subStr = XML.split(delimeter);  // cut for <uses-permission and <uses-permission-sdk-23
        ArrayList<String> finList = new ArrayList<>();  // список вывода
        String finish = "";  // начало uses-permission
        String secFinish = "";  // вставка для конечного uses-permission

      //  permissionJSON = new ArrayList<>();  // добавления списка JSON


        for (int i = 0; i < subStr.length; i++){
            if (subStr[i].contains("</uses-permission")) {
                finish = subStr[i].split("\"")[0];
                finList.add(finish);
            }
            else {
                onlyPermission = subStr[i].split(delimeter2);
                for (int j = 0; j < onlyPermission.length; j++ ){
                    if (onlyPermission[j].contains("</permission")){
                        finish = onlyPermission[j].split("\"")[1];
                        secFinish = finish.split("\"")[0];
                        finList.add(secFinish);
                    }
                }
            }

        }
        for (int i = 0; i < finList.size(); i++){
            Log.e("perm: ",   "\n " +  finList.get(i));
        }
            return finList;
    }
}
