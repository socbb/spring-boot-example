package com.socbb.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class LoginController extends BaseController implements Initializable {

    @FXML
    private GridPane root;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private ImageView image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        Image image = new Image(getImage());
//        this.image.setImage(image);

        root.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("登陆");
            }
        });

        loginBtn.setOnAction(event -> {
            System.out.println("登陆");
        });

        // 关闭登陆窗口
        cancelBtn.setOnAction(event -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        });
    }
}
