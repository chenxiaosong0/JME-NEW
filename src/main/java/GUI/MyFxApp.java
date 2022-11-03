package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author xiaosongChen
 * @create 2022-11-02 22:26
 * @description :JavaFX 应用程序需要一个主启动类。
 * 这个类必须扩展这个类，这是自Java 8以来Java中的一个标准类。javafx.application.Application
 *
 * 下面是一个示例子类：Application
 */
public class MyFxApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("My First JavaFX App");
        Label label = new Label("Hello World,JavaFX!");
        Scene scene = new Scene(label, 800, 600);
        primaryStage.setScene(scene);

        Stage stage = new Stage();
        VBox vBox = new VBox();
        Scene scene1 = new Scene(vBox);
        stage.setScene(scene1);
        stage.setFullScreen(false);
//        stage.initModality(Modality.APPLICATION_MODAL);//它将阻塞这个JavaFX应用程序打开的所有其他窗口(阶段)。
        stage.initModality(Modality.WINDOW_MODAL);//新创建的舞台将会阻塞“拥有”新创建舞台的舞台窗口，
//        stage.initModality(Modality.NONE);//此阶段不会阻塞在此应用程序中打开的任何其他窗口。
        stage.initOwner(primaryStage);

        stage.initStyle(StageStyle.DECORATED);
//        stage.initStyle(StageStyle.UNDECORATED);
//stage.initStyle(StageStyle.TRANSPARENT);
//stage.initStyle(StageStyle.UNIFIED);
//stage.initStyle(StageStyle.UTILITY);
        stage.setOnCloseRequest(event -> {
            System.out.println("Closing stage");
        });
        primaryStage.setOnHiding(event -> {//舞台隐藏事件监听器在舞台被隐藏之前调用，但在请求隐藏之后调用。
            System.out.println("Hidding Satge");
        });
        primaryStage.setOnHiding(event -> {//舞台隐藏事件监听器在舞台被隐藏之后被调用。
            System.out.println("Hidden Stage");
        });
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Colsing Stage");
        });
        primaryStage.setOnShown((event) -> {//显示的舞台事件侦听器在显示舞台之后调用。
            System.out.println("Stage Shown");
        });
        primaryStage.setOnShowing((event) -> {//显示事件侦听器的舞台在请求显示舞台之后，但在显示舞台之前调用。
            System.out.println("Showing Stage");
        });
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,event -> {//只能监听primaryStage界面上的按键
            System.out.println("Key pressed: " + event.toString());
            switch (event.getCode()){
                case ESCAPE:{
                    primaryStage.setWidth( primaryStage.getWidth() * 2);
                    break;
                }
                case SPACE:{
                    primaryStage.close();
                }
                default:{
                    System.out.println("Unrecognized Key");
                }
            }
        });

        primaryStage.show();
        stage.showAndWait();
    }
}
