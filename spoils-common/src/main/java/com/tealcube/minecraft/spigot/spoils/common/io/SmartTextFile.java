/*
 * This file is part of spoils-common, licensed under the ISC License.
 *
 * Copyright (c) 2014-2015 Richard Harrah
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
package com.tealcube.minecraft.spigot.spoils.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SmartTextFile {

    private final File file;

    public SmartTextFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public boolean exists() {
        return file.getParentFile().exists() && file.exists();
    }

    public boolean create() {
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return false;
        }
        File saveTo = file;
        try {
            return !(!saveTo.exists() && !saveTo.createNewFile());
        } catch (Exception e) {
            return false;
        }
    }

    public void write(InputStream inputStream) {
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return;
            }
            File saveTo = file;
            if (!saveTo.exists() && !saveTo.createNewFile()) {
                return;
            }
            FileWriter fw = new FileWriter(saveTo.getPath(), true);
            PrintWriter pw = new PrintWriter(fw);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String p;
            while ((p = br.readLine()) != null) {
                if (p.length() > 0) {
                    pw.println(p);
                }
            }
            br.close();
            isr.close();
            pw.flush();
            pw.close();
            fw.flush();
            fw.close();
        } catch (IOException e) {
            // do nothing
        }
    }

    public void write(String... messages) {
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return;
            }
            File saveTo = file;
            if (!saveTo.exists() && !saveTo.createNewFile()) {
                return;
            }
            FileWriter fw = new FileWriter(saveTo.getPath(), true);
            PrintWriter pw = new PrintWriter(fw);
            for (String message : messages) {
                pw.println(message);
            }
            pw.flush();
            pw.close();
            fw.flush();
            fw.close();
        } catch (IOException ignored) {
            // do nothing
        }
    }

    public List<String> read() {
        List<String> list = new ArrayList<>();
        try {
            if (!file.exists() && !file.getParentFile().mkdirs()) {
                return list;
            }
            File readFile = file;
            if (!readFile.exists() && !readFile.createNewFile()) {
                return list;
            }
            FileReader fileReader = new FileReader(readFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String p;
            while ((p = bufferedReader.readLine()) != null) {
                if (!p.contains("#") && p.length() > 0) {
                    list.add(p);
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException ignored) {
            // do nothing
        }
        return list;
    }

}
