package com.socbb.view;

import com.jfoenix.controls.JFXDecorator;
import com.socbb.utils.JavaFxViewUtils;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.Parent;
import javafx.stage.Stage;

@FXMLView(value = "/fxml/index.fxml")
public class IndexView extends AbstractFxmlView {

    @Override
    public Parent getView() {
        Stage stage = GUIState.getStage();
        JFXDecorator decorator = JavaFxViewUtils.getJFXDecorator(stage, stage.getTitle(), "/img/icon.jpg", super.getView());
        decorator.setOnCloseButtonAction(() -> System.exit(0));
        return decorator;
    }
}
