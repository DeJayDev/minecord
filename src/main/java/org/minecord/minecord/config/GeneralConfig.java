package org.minecord.minecord.config;

public class GeneralConfig {

    private boolean enableServerApi = true;

    private boolean enableOfflinePresence = true;

    private boolean enableToasts = true;

    private boolean disableAnalytics = false;

    public boolean isEnableServerApi() {
        return enableServerApi;
    }

    public void setEnableServerApi(boolean enableServerApi) {
        this.enableServerApi = enableServerApi;
    }

    public boolean isEnableOfflinePresence() {
        return enableOfflinePresence;
    }

    public void setEnableOfflinePresence(boolean enableOfflinePresence) {
        this.enableOfflinePresence = enableOfflinePresence;
    }

    public boolean isEnableToasts() {
        return enableToasts;
    }

    public void setEnableToasts(boolean enableToasts) {
        this.enableToasts = enableToasts;
    }

    public boolean isDisableAnalytics() {
        return disableAnalytics;
    }

    public void setDisableAnalytics(boolean disableAnalytics) {
        this.disableAnalytics = disableAnalytics;
    }
}
