package Control;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;

/**
 * @author xiaosongChen
 * @create 2022-10-21 17:04
 * @description :
 */
public class RotateControl implements Control {
    private Spatial spatial;

    // 旋转速度：每秒180°
    private float rotateSpeed = FastMath.PI;

    public RotateControl(float rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public RotateControl(Spatial spatial) {
        this.spatial = spatial;
    }

    public RotateControl(Spatial spatial, float rotateSpeed) {
        this.spatial = spatial;
        this.rotateSpeed = rotateSpeed;
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        RotateControl c = new RotateControl(rotateSpeed);
        c.setSpatial(spatial);
        return c;
    }

    @Override
    public void setSpatial(Spatial spatial) {

    }

    @Override
    public void update(float v) {
        spatial.rotate(0, v * rotateSpeed, 0);
    }

    @Override
    public void render(RenderManager renderManager, ViewPort viewPort) {

    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {
        throw new IOException("暂不支持");
    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {
        throw new IOException("暂不支持");
    }
}
