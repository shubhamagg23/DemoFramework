package com.shubham.automation.utils;

public class MyLogger implements com.jcraft.jsch.Logger {

    public static java.util.Hashtable name = new java.util.Hashtable();

    static{
        name.put(new Integer(DEBUG), "DEBUG: ");
        name.put(new Integer(INFO), "INFO: ");
        name.put(new Integer(WARN), "WARN: ");
        name.put(new Integer(ERROR), "ERROR: ");
        name.put(new Integer(FATAL), "FATAL: ");
    }

    public boolean isEnabled(int level) {
        return true;
    }

    public void log(int level, String message){
        System.out.print(name.get(new Integer(level)));
        System.out.println(message);
    }
}