package javaFX_GUI;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * @author xiaosongChen
 * @create 2022-11-06 10:58
 * @description :显示了如何访问 [JavaFX 窗格]，以及如何向两者添加更改侦听器。
 * 请注意，其中一个更改侦听器是如何实现为匿名 Java类
 * 另一个是作为 [Java Lambda 表达式]实现的。
 * 这只是为了向您展示实现将事件侦听器附加到 JavaFX 属性的相同目标的两种不同方法。
 */
public class PropertyExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();//创建一个窗格布局对象

        ReadOnlyDoubleProperty widthProperty = pane.widthProperty();
        widthProperty.addListener(new ChangeListener<Number>() {//监听窗格宽度，鼠标拖动改变窗格宽度就触发
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("widthProperty changed from "
                        + oldValue.doubleValue() + " to " + newValue.doubleValue());
            }
        });

        DoubleProperty prefWidthProperty = pane.prefWidthProperty();
        prefWidthProperty.addListener((ObservableValue<? extends Number> prop,//监听初始值变化就触发：初值为-1
        Number oldVal,Number newVal) ->{
            System.out.println("prefWidthProperty changed from" + oldVal.doubleValue()
            + " to " + newVal.doubleValue());
        });
        prefWidthProperty.set(123);//prefWidthProperty changed from-1.0 to 123.0
        Scene scene = new Scene(pane, 1024, 800, true);//widthProperty changed from 0.0 to 1024.0
        primaryStage.setScene(scene);
        primaryStage.setTitle("2D Example");
        primaryStage.show();
    }
}
