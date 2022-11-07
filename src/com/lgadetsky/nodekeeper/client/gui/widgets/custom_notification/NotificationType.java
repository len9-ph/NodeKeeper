package com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification;

import com.lgadetsky.nodekeeper.client.util.StylesNames;

public enum NotificationType {
    DEFAULT(StylesNames.DEFAULT_MESSAGE),
    ERROR(StylesNames.ERROR_MESSAGE);

    private String styleName;

    public String getStyleName() {
        return styleName;
    }

    NotificationType(String styleName) {
        this.styleName = styleName;
    }
}
