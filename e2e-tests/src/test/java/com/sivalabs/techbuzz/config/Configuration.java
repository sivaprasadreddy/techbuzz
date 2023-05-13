package com.sivalabs.techbuzz.config;

public class Configuration {
    private boolean headlessMode;
    private double slowMo;
    private String rootUrl;
    private String normalUserEmail;
    private String normalUserPassword;
    private String adminUserEmail;
    private String adminUserPassword;

    public boolean isHeadlessMode() {
        return headlessMode;
    }

    public void setHeadlessMode(boolean headlessMode) {
        this.headlessMode = headlessMode;
    }

    public double getSlowMo() {
        return slowMo;
    }

    public void setSlowMo(double slowMo) {
        this.slowMo = slowMo;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getNormalUserEmail() {
        return normalUserEmail;
    }

    public void setNormalUserEmail(String normalUserEmail) {
        this.normalUserEmail = normalUserEmail;
    }

    public String getNormalUserPassword() {
        return normalUserPassword;
    }

    public void setNormalUserPassword(String normalUserPassword) {
        this.normalUserPassword = normalUserPassword;
    }

    public String getAdminUserEmail() {
        return adminUserEmail;
    }

    public void setAdminUserEmail(String adminUserEmail) {
        this.adminUserEmail = adminUserEmail;
    }

    public String getAdminUserPassword() {
        return adminUserPassword;
    }

    public void setAdminUserPassword(String adminUserPassword) {
        this.adminUserPassword = adminUserPassword;
    }
}
