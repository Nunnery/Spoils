/*
 * This file is part of spoils-common, licensed under the ISC License.
 *
 * Copyright (c) 2014 Richard Harrah
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 */
package com.tealcube.minecraft.spigot.spoils.common.utils;

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
            COLOR_MAP.put("&" + String.valueOf(cc.getChar()).toLowerCase(), cc);
            COLOR_MAP.put("&" + String.valueOf(cc.getChar()).toUpperCase(), cc);
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

    public static ChatColor convertTag(String pString) {
        Validate.notNull(pString);
        for (String key : COLOR_MAP.keySet()) {
            if (key.equals(pString)) {
                return COLOR_MAP.get(key);
            }
        }
        return null;
    }

    public static List<String> color(List<String> pList) {
        Validate.notNull(pList, "pList cannot be null");
        List<String> list = new ArrayList<>();
        for (String s : pList) {
            list.add(color(s));
        }
        return list;
    }

    public static String color(String pString) {
        Validate.notNull(pString, "pString cannot be null");
        String s = pString;
        for (Map.Entry<String, ChatColor> entry : COLOR_MAP.entrySet()) {
            s = StringUtils.replace(s, entry.getKey(), entry.getValue() + "");
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

    public static String args(String pString, Map<String, String> placeholders) {
        Validate.notNull(pString, "pString cannot be null");
        String s = pString;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            s = StringUtils.replace(s, entry.getKey(), entry.getValue());
        }
        return s;
    }

    public static List<String> args(List<String> pList, String[][] placeholders) {
        Validate.notNull(pList, "pList cannot be null");
        List<String> list = new ArrayList<>();
        for (String s : pList) {
            list.add(args(s, placeholders));
        }
        return list;
    }

    public static String args(String pString, String[][] placeholders) {
        Validate.notNull(pString, "pString cannot be null");
        String s = pString;
        for (String[] placeholder : placeholders) {
            s = StringUtils.replace(s, placeholder[0], placeholder[1]);
        }
        return s;
    }

    public static List<String> insert(List<String> aList, List<String> bList, int index) {
        Validate.notNull(aList);
        Validate.notNull(bList);
        List<String> cList = new ArrayList<>(aList);
        while (cList.size() < index) {
            cList.add("");
        }
        cList.addAll(index, bList);
        return cList;
    }

}
