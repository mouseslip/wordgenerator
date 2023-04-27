package com.generator.ui;

import com.generator.backend.PhoneNumber;
import com.generator.backend.WordGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class HelloController {
    @FXML
    private TextField textInput;

    @FXML
    private ListView outputList;

    private WordGenerator wg = new WordGenerator();

    @FXML
    protected void onGenerateButtonClick() {
        String inputPhoneNumberText = textInput.getText();
        PhoneNumber phoneNumber = new PhoneNumber(inputPhoneNumberText);
        List<String> thingsToDisplay = wg.generateWords(phoneNumber);
        outputList.getItems().clear();
        for (String s : thingsToDisplay) {
            outputList.getItems().add(s);
        }
    }
}