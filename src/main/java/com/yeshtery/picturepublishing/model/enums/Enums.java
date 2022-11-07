package com.yeshtery.picturepublishing.model.enums;

public enum Enums {
    IMG_TYPE_JPG("image/jpeg"),
    IMG_TYPE_PNG("image/png"),
    IMG_TYPE_GIF("image/gif"),

    STATUS_STATE_ACCEPTED("accepted"),
    STATUS_STATE_PENDING("pending"),
    STATUS_STATE_REJECTED("rejected"),

    STATUS_ACTION_ACCEPT("accept"),
    STATUS_ACTION_REJECT("reject"),

    ;



    private final String text;

    Enums(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
