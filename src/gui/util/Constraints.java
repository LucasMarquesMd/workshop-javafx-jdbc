package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
	
	private static void addTextListener(TextField txt, int maxLength, String regex) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && (maxLength == -1 || newValue.length() <= maxLength)) {
                if (!newValue.matches(regex)) {
                    txt.setText(oldValue);
                }
            } else {
                txt.setText(oldValue);
            }
        });
    }
    
    public static void setTextFieldInteger(TextField txt) {
        addTextListener(txt, -1, "\\d*");
    }

    public static void setTextFieldMaxLength(TextField txt, int max) {
        addTextListener(txt, max, ".*");
    }

    public static void setTextFieldDouble(TextField txt) {
        addTextListener(txt, -1, "\\d*([\\.]\\d*)?");
    }
}