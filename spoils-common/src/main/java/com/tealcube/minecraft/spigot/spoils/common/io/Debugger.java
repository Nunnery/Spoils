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
package com.tealcube.minecraft.spigot.spoils.common.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.logging.Level;

public class Debugger {

    private final File file;

    public Debugger(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void debug(String... messages) {
        debug(Level.INFO, messages);
    }

    public void debug(Level level, String... messages) {
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
                pw.println(
                        "[" + level.getName() + "] " + Calendar.getInstance().getTime().toString() + " | " + message);
            }
            pw.flush();
            pw.close();
        } catch (IOException ignored) {
            // do nothing
        }
    }

}
