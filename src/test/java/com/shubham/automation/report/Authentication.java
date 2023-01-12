package com.shubham.automation.report;

import javax.mail.PasswordAuthentication;

/**
 * @author Shubham Aggarwal
 */
class Authenticator extends javax.mail.Authenticator {
        
    private PasswordAuthentication authentication;

    public Authenticator(String username, String password) {
        authentication = new PasswordAuthentication(username, password);
    }

    @Override
	protected PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}

