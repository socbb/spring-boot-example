package com.socbb;

import com.socbb.utils.JavaFxViewUtils;
import com.socbb.utils.XJavaFxSystemUtils;
import com.socbb.view.IndexView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.GUIState;
import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavafxApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        XJavaFxSystemUtils.addJarByLibs();//添加外部jar包

        SplashScreen splashScreen = new SplashScreen() {
            @Override
            public String getImagePath() {
                return "/img/javafx.jpg";
            }
        };
        launch(JavafxApplication.class, IndexView.class, splashScreen, args);
    }

    @Override
    public void beforeInitialView(Stage stage, ConfigurableApplicationContext ctx) {
        super.beforeInitialView(stage, ctx);
        Scene scene = JavaFxViewUtils.getJFXDecoratorScene(stage, "socbb", null, new AnchorPane());
        stage.setScene(scene);
        GUIState.setScene(scene);
    }

}
