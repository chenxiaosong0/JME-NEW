package javaFX_GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author xiaosongChen
 * @create 2022-11-06 11:37
 * @description :JavaFX FXML加载示例，它加载一个FXML文件并返回其中声明的JavaFX GUI组件
 */
public class FXMLExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
//        System.out.println(getClass().toString());//javaFX_GUI.FXMLExample
        loader.setLocation(getClass().getClassLoader().getResource("hello_world.fxml"));
        VBox vBox = loader.<VBox>load();

//        1。在fxml中指定设置控制器
//        2.在FXMLLoader上设置控制器
        //controller controller = new controller();
//        loader.setController(controller);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
