package com.miaoyue91.speechrecognize.speechRecognize;

import android.content.Context;

import com.miaoyue91.counter.speechrecognize.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class BuddhaNames {

    public static final BuddhaName Amitabha = new  BuddhaName("Amitabha", R.string.Amitabha);
    public static final BuddhaName Avalokitesvara = new  BuddhaName("Avalokitesvara", R.string.Avalokitesvara);
    public static final BuddhaName Ksitigarbha = new  BuddhaName("Ksitigarbha", R.string.Ksitigarbha);

    private static List<BuddhaName> names;
    private static Map<String, BuddhaName> namesMap;

    synchronized public static List<BuddhaName> toList(){
        if(null == names){
            names = new ArrayList<>();
            names.add(Amitabha);
            names.add(Avalokitesvara);
            names.add(Ksitigarbha);
        }
        return names;
    }

    synchronized public static Map<String, BuddhaName> toMap(){
        if(null == namesMap){
            namesMap = new HashMap<>();
            List<BuddhaName> names = toList();
            for(BuddhaName name : names){
                namesMap.put(name.getName(), name);
            }
        }
        return namesMap;
    }

    public static BuddhaName getBuddhaNameByName(String name){
        Map<String, BuddhaName> map = toMap();
        return namesMap.get(name);
    }

    public static class BuddhaName{

        private String name;
        private int nativeNameResId;

        public BuddhaName(String name, int nativeNameResId) {
            this.name = name;
            this.nativeNameResId = nativeNameResId;
        }

        public String getName() {
            return name;
        }

        public String getNativeName(Context context) {
            return context.getString(nativeNameResId);
        }
    }
}
