package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author xiaosongChen
 * @create 2022-11-03 22:43
 * @description :
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private VBox rootLayout;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("App");
        initRootLayout();
    }

    public void initRootLayout(){
        try {
            //Load root layout from fxml file
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getClassLoader().getResource("/SceneBuilderTest.fxml"));
            rootLayout = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SceneBuilderTest.fxml")));
//             = (VBox) loader.load();

            //show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
