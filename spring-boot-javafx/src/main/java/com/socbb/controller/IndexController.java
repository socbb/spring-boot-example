package com.socbb.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToolbar;
import com.socbb.annotation.TableField;
import com.socbb.bean.Ticket;
import com.socbb.enums.CityEnum;
import com.socbb.utils.JavaFxViewUtils;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

@FXMLController
public class IndexController extends BaseController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private JFXButton button;

    @FXML
    private JFXToolbar toolbar;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXComboBox<Label> sLocationComboBox;

    @FXML
    private JFXComboBox<Label> eLocationComboBox;

    @FXML
    private JFXDatePicker startDatePiker;

    @FXML
    private TableView<Ticket> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // 设置到表格头
        Class<Ticket> ticketClass = Ticket.class;
        List<TableColumn<Ticket, String>> columns = new ArrayList<>();
        Field[] fields = ticketClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            TableField annotation = field.getAnnotation(TableField.class);
            String name = field.getName();
            String value = annotation.value();
            TableColumn<Ticket, String> column = new TableColumn<>();
            column.setId(name);
            column.setCellValueFactory(new PropertyValueFactory<>(name));
            column.setText(value);
            column.setResizable(true);
            if ("train_no".equals(name)) {
                column.setVisible(false);
            }
            columns.add(column);
        });
        tableView.getColumns().addAll(columns);

        // 设置下拉框
        CityEnum[] values = CityEnum.values();
        List<Label> labels = new ArrayList<>();
        Arrays.stream(values).forEach(l -> {
            if (l.id != -1) {
                Label label = new Label(String.valueOf(l.name));
                label.setId(l.letterName);
                labels.add(label);
            }
        });
        sLocationComboBox.getItems().addAll(labels);
        eLocationComboBox.getItems().addAll(labels);

        // 下拉框选中事件
        AtomicReference<String> startLocation = new AtomicReference<>();
        AtomicReference<String> etartLocation = new AtomicReference<>();
        sLocationComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            startLocation.set(observable.getValue().getId());
        });
        eLocationComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            etartLocation.set(observable.getValue().getId());
        });

        // 登陆弹窗
        button.setOnAction(action -> {
            Parent target = null;
            try {
                target = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
                Stage stage = JavaFxViewUtils.getNewStage("登陆", "", target);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                stage.setWidth(screenSize.width / 3);
                stage.setHeight(screenSize.height / 3);
                stage.setX(screenSize.width / 3);
                stage.setY(screenSize.height / 4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 查询事件
        searchBtn.setOnAction(event -> {
            String s = startLocation.get();
            String e = etartLocation.get();
            LocalDate value = startDatePiker.getValue();
            List<Ticket> tickets = getTicket(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), s, e, "ADULT");
            tableView.getItems().addAll(tickets);
        });
    }
}
