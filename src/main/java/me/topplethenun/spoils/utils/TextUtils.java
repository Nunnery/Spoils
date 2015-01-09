package me.topplethenun.spoils.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TextUtils {

    private static final Map<String, ChatColor> COLOR_MAP = new HashMap<>();

    static {
        for (ChatColor cc : ChatColor.values()) {
            COLOR_MAP.put("&" + cc.getChar(), cc);
            COLOR_MAP.put("<" + cc.name().toUpperCase() + ">", cc);
            COLOR_MAP.put("<" + cc.name().toLowerCase() + ">", cc);
            COLOR_MAP.put("<" + cc.name().toUpperCase().replace("_", "") + ">", cc);
            COLOR_MAP.put("<" + cc.name().toLowerCase().replace("_", "") + ">", cc);
            COLOR_MAP.put("<" + cc.name().toUpperCase().replace("_", " ") + ">", cc);
            COLOR_MAP.put("<" + cc.name().toLowerCase().replace("_", " ") + ">", cc);
        }
    }

    private TextUtils() {
        // do nothing
    }

    public static String color(String pString) {
        Validate.notNull(pString, "pString cannot be null");
        String s = pString;
        for (Map.Entry<String, ChatColor> entry : COLOR_MAP.entrySet()) {
            s = StringUtils.replace(s, entry.getKey(), entry.getValue() + "");
        }
        return s;
    }

    public static List<String> color(List<String> pList) {
        Validate.notNull(pList, "pList cannot be null");
        List<String> list = new ArrayList<>();
        for (String s : pList) {
            list.add(color(s));
        }
        return list;
    }

    public static String args(String pString, Map<String, String> placeholders) {
        Validate.notNull(pString, "pString cannot be null");
        String s = pString;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            s = StringUtils.replace(s, entry.getKey(), entry.getValue());
        }
        return s;
    }

    public static List<String> args(List<String> pList, Map<String, String> placeholders) {
        Validate.notNull(pList, "pList cannot be null");
        List<String> list = new ArrayList<>();
        for (String s : pList) {
            list.add(args(s, placeholders));
        }
        return list;
    }

}
