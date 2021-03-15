package com.furkanayaz.groupspad;

public class Notification {
    private String notificationtitle;
    private String notificationdescription;
    private String notificationdate;

    public Notification() {
    }

    public Notification(String notificationtitle, String notificationdescription, String notificationdate) {
        this.notificationtitle = notificationtitle;
        this.notificationdescription = notificationdescription;
        this.notificationdate = notificationdate;
    }

    public String getNotificationtitle() {
        return notificationtitle;
    }

    public void setNotificationtitle(String notificationtitle) {
        this.notificationtitle = notificationtitle;
    }

    public String getNotificationdescription() {
        return notificationdescription;
    }

    public void setNotificationdescription(String notificationdescription) {
        this.notificationdescription = notificationdescription;
    }

    public String getNotificationdate() {
        return notificationdate;
    }

    public void setNotificationdate(String notificationdate) {
        this.notificationdate = notificationdate;
    }
}
