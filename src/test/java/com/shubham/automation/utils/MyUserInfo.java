package com.shubham.automation.utils;

import com.jcraft.jsch.UserInfo;


   public class MyUserInfo implements UserInfo {

        public String getPassphrase() {
            System.out.println("JSch asked passphrase");
            return null;
        }

        public String getPassword() {
            System.out.println("JSch asked password");
            return null;
        }

        public boolean promptPassphrase(String arg0) {
            System.out.println("promptPassphrase: " + arg0);
            return true;
        }

        public boolean promptPassword(String arg0) {
            System.out.println("promptPassword: " + arg0);
            return true;
        }

        public boolean promptYesNo(String arg0) {
            System.out.println("promptYesNo: " + arg0);
            return true;
        }

        public void showMessage(String arg0) {
            System.out.println("showMessage: " + arg0);
        }
    }

