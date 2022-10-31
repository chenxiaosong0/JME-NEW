package test;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import javax.annotation.Nonnull;

/**
 * @author xiaosongChen
 * @create 2022-10-22 15:39
 * @description :
 */
public class StartScreenState extends AbstractAppState implements ScreenController {
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private Screen screen;

    @Override
    public void initialize(AppStateManager stateManager,
                           Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.guiViewPort = this.app.getGuiViewPort();
        this.audioRenderer = this.app.getAudioRenderer();

        this.niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager, audioRenderer, guiViewPort);
        this.nifty = niftyDisplay.getNifty();
        this.nifty.fromXml("scenes/screens.xml",
                "start_screen", this);
        this.screen = nifty.getScreen("start_screen");
        inputManager.setCursorVisible(true);
        guiViewPort.addProcessor(niftyDisplay);
    }

    // 在intellij中该方法会显示没有用过（灰色），没关系，正常
    public void addDropClick(String num) {
        // do something
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {

    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }

}

