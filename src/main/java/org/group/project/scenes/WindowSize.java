package org.group.project.scenes;

/**
 * This enum class standardises the window size for all scenes used.
 *
 * @author azmi_maz
 */
public enum WindowSize {
    MAIN(1400, 700),
    SMALL(400, 400);

    public final int HEIGHT;
    public final int WIDTH;

    WindowSize(int height, int width) {
        this.HEIGHT = height;
        this.WIDTH = width;
    }

}
