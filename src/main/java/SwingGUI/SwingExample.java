package SwingGUI;

/**
 * @author xiaosongChen
 * @create 2022-11-13 17:49
 * @description :SWING界面中的按钮启动jme的窗口
 */

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.AppSettings;

import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class SwingExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Start!");

        button.addActionListener(e -> startJme());
        panel.add(button);

        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void startJme() {
        SwingUtilities.invokeLater(() -> new Thread(new Application()::start).start());
    }
}

class Application extends SimpleApplication {

    @Override
    public void start() {
        AppSettings settings = new AppSettings(true);
        settings.setResizable(true);
        setSettings(settings);
        setShowSettings(false);
        super.start();
    }

    @Override
    public void simpleInitApp() {
        TheAppState appState = new TheAppState();
        stateManager.attach(appState);
    }
}

class TheAppState extends AbstractAppState {

    @Override
    public void initialize(AppStateManager stateManager, com.jme3.app.Application app) {
        super.initialize(stateManager, app);
        ((Application) app).getFlyByCamera().setDragToRotate(true); // Don't lock cursor
    }
}