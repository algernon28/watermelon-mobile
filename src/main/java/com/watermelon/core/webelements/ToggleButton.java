package com.watermelon.core.webelements;

import org.openqa.selenium.WebElement;

public class ToggleButton {

    private final WebElement button;

    public ToggleButton(final WebElement checkboxField) {
        this.button = checkboxField;
    }

    public void check() {
        if (!this.isChecked()) {
            button.click();
        }
    }

    public void uncheck() {
        if (this.isChecked()) {
            button.click();
        }
    }

    public boolean isChecked() {
        //Sometimes the attribute is 'checked', sometimes is 'selected' (especially iOS)
        return (button.getAttribute("checked").equals("true") || button.isSelected());
    }
}
