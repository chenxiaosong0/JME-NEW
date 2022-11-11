package javaFX_GUI;

import Control.HelloControl;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * @author xiaosongChen
 * @create 2022-11-06 11:37
 * @description :JavaFX FXML加载示例，它加载一个FXML文件并返回其中声明的JavaFX GUI组件
 */
public class FXMLExample extends Application {
    BorderPane root;
    FXMLLoader loader;
    public static void main(String[] args) {
        launch(args);

    }

    @Override//此方法不会在JavaFX应用程序线程中调用。
    // 应用程序不能用这种方法构造场景或舞台。应用程序可以用这个方法构造其他JavaFX对象。
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SmallDTS.fxml"));
        root = loader.<BorderPane>load();

        Scene scene = new Scene(root);
        System.out.println();

        //获取fxml的controll层，并调用方法
        FXML_Controll_Example controller = loader.getController();

        primaryStage.setScene(scene);
//        primaryStage.setX(200);设置主窗口显示时的位置
        primaryStage.show();

    }

}
