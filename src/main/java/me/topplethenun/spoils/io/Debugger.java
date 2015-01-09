package me.topplethenun.spoils.io;

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
                pw.println("[" + level.getName() + "] " + Calendar.getInstance().getTime().toString() + " | " + message);
            }
            pw.flush();
            pw.close();
        } catch (IOException ignored) {
            // do nothing
        }
    }

}
