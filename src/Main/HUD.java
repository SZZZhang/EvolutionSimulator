package Main;

import gameEngine.Input;
import gameEngine.Utils;
import gameEngine.Window;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.nanovg.NVGColor;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class HUD {

    private float TEXT_FONT = 20f;

    private static final String FONT_NAME = "BOLD";
    private static final float BUTTON_RADIUS = 4f;

    private static final float ADD_CREATURE_BUTTONX = 600f;
    private static final float ADD_CREATURE_BUTTONY = 10f;

    private static final float BUTTONS_WIDTH = 115f;
    private static final float BUTTONS_HEIGHT = 30f;
    private static final float BUTTONS_FONT_SIZE = 20f;
    private static final float BUTTONS_PADDING = 5f;

    private static final float ADD_CREATURE_WINDOWX = 40f;
    private static final float ADD_CREATURE_WINDOWY = 200f;
    private static final float ADD_CREATURE_WINDOW_HEIGHT= 200f;
    private static final float ADD_CREATURE_WINDOW_WIDTH = 400f;
    private static final float BUTTONS_PADDING = 5f;
    private static final float BUTTONS_PADDING = 5f;
    private static final float BUTTONS_PADDING = 5f;
    private static final float BUTTONS_PADDING = 5f;

    private long vg;

    private NVGColor colour;

    private ByteBuffer fontBuffer;

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private DoubleBuffer posx;

    private DoubleBuffer posy;

    private int counter;

    private NVGColor charcoalColour;

    private Window window;

    static final NVGColor
            colourA = NVGColor.create(),
            colorB = NVGColor.create(),
            colorC = NVGColor.create();

    static final NVGPaint
            paintA = NVGPaint.create(),
            paintB = NVGPaint.create(),
            paintC = NVGPaint.create();

    //abstract class for colours
    private static abstract class COLOUR {
        int r, g, b, a;

        COLOUR(int r, int g, int b, int a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public NVGColor rgba(NVGColor colour) {
            colour.r(this.r / 255.0f);
            colour.g(this.g / 255.0f);
            colour.b(this.b / 255.0f);
            colour.a(this.a / 255.0f);

            return colour;
        }

    }

    //colour constants
    private static class CHARCOAL extends COLOUR {
        public CHARCOAL() {
            super(58, 72, 97, 255);
        }
    }

    private static class WHITE extends COLOUR {
        public WHITE() {
            super(255, 255, 255, 255);
        }
    }

    private static class GREY extends COLOUR {
        public GREY() {
            super(200, 200, 200, 255);
        }
    }

    //source:
    public void init(Window window) {

        this.window = window;
        try {
            //creates a NanoVG context with an OpenGL 3.0 rendering back-end
            this.vg = window.getOptions().antialiasing ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES) : nvgCreate(NVG_STENCIL_STROKES);
            if (this.vg == NULL) {
                throw new Exception("Could not init nanovg");
            }

            fontBuffer = Utils.ioResourceToByteBuffer(System.getProperty("user.dir") + "/fonts/OpenSans-Bold.ttf", 150 * 1024);
            int font = nvgCreateFontMem(vg, FONT_NAME, fontBuffer, 0);
            if (font == -1) {
                throw new Exception("Could not add font");
            }
            colour = NVGColor.create();

            posx = MemoryUtil.memAllocDouble(1);
            posy = MemoryUtil.memAllocDouble(1);

            counter = 0;
        } catch (Exception e) {
            System.out.println("Failed to initialize HUD: " + e);
        }
    }

    float pos = 0;

    public void render(Window window) {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

        drawRectangle(0, 0, window.getWidth(), 50, new CHARCOAL().rgba(colour));

        if (hoverCircle(pos * 100 + 10, 10 + 17.5f, 5) &&
                Input.isButtonDown(GLFW_MOUSE_BUTTON_LEFT)) pos = (float) (Input.getMouseX() - 10) / 100;

        drawSlider(vg, pos, 10, 10, 100, 25);

        boolean hoverAddCreatureButton = hoverRectangle(ADD_CREATURE_BUTTONX,
                ADD_CREATURE_BUTTONY, BUTTONS_WIDTH, BUTTONS_HEIGHT);
        if (hoverAddCreatureButton) {
            drawButton(ADD_CREATURE_BUTTONX, ADD_CREATURE_BUTTONY, BUTTONS_WIDTH, BUTTONS_HEIGHT, BUTTONS_FONT_SIZE,
                    FONT_NAME, new CHARCOAL().rgba(colour), new GREY().rgba(colourA), "Add Creature");
        } else {
            drawButton(ADD_CREATURE_BUTTONX, ADD_CREATURE_BUTTONY, BUTTONS_WIDTH, BUTTONS_HEIGHT, BUTTONS_FONT_SIZE,
                    FONT_NAME, new CHARCOAL().rgba(colour), new WHITE().rgba(colourA), "Add Creature");
        }


        // Upper ribbon
        nvgBeginPath(vg);
        nvgRect(vg, 0, window.getHeight() - 100, window.getWidth(), 50);
        nvgFillColor(vg, rgba(0x23, 0xa1, 0xf1, 200, colour));
        nvgFill(vg);

        // Lower ribbon
        nvgBeginPath(vg);
        nvgRect(vg, 0, window.getHeight() - 50, window.getWidth(), 10);
        nvgFillColor(vg, rgba(0xc1, 0xe3, 0xf9, 200, colour));
        nvgFill(vg);

        glfwGetCursorPos(window.getWindowHandle(), posx, posy);
        int xcenter = 50;
        int ycenter = window.getHeight() - 75;
        int radius = 20;
        int x = (int) posx.get(0);
        int y = (int) posy.get(0);
        boolean hover = Math.pow(x - xcenter, 2) + Math.pow(y - ycenter, 2) < Math.pow(radius, 2);

        // Circle
        nvgBeginPath(vg);
        nvgCircle(vg, xcenter, ycenter, radius);
        nvgFillColor(vg, rgba(0xc1, 0xe3, 0xf9, 200, colour));
        nvgFill(vg);

        // Clicks Text
        nvgFontSize(vg, 25.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_CENTER);


        if (hover) {
            nvgFillColor(vg, rgba(0x00, 0x00, 0x00, 255, colour));
        } else {
            nvgFillColor(vg, rgba(0x23, 0xa1, 0xf1, 255, colour));

        }
        nvgText(vg, 50, window.getHeight() - 87, String.format("%02d", counter));

        // Render hour text
        nvgFontSize(vg, 40.0f);
        nvgFontFace(vg, FONT_NAME);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, rgba(0xe6, 0xea, 0xed, 255, colour));
        nvgText(vg, window.getWidth() - 150, window.getHeight() - 95, dateFormat.format(new Date()));

        nvgEndFrame(vg);

        // Restore state
        window.restoreState();
    }

    public void incCounter() {
        counter++;
        if (counter > 99) {
            counter = 0;
        }
    }

    private static NVGColor rgba(int r, int g, int b, int a, NVGColor colour) {
        colour.r(r / 255.0f);
        colour.g(g / 255.0f);
        colour.b(b / 255.0f);
        colour.a(a / 255.0f);

        return colour;
    }

    public void drawRectangle(float xPos, float yPos, float width, float height, NVGColor rgba) {
        nvgBeginPath(vg);
        nvgRect(vg, xPos, yPos, width, height);
        nvgFillColor(vg, rgba);
        nvgFill(vg);
    }

    public void drawButton(float xPos, float yPos, float width, float height, float fontSize, String fontName,
                           NVGColor textColor, NVGColor buttonColor, String title) {

        //draws rounded rectangle
        nvgBeginPath(vg);
        nvgRoundedRect(vg, xPos, yPos, width, height, BUTTON_RADIUS); //sets attributes
        nvgFillColor(vg, buttonColor); //sets fill
        nvgFill(vg); //draws rectangle

        //text
        nvgFontSize(vg, fontSize); //sets font size
        nvgFontFace(vg, fontName); //sets font
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP); //sets text alignment
        nvgFillColor(vg, textColor); //sets text colour
        nvgText(vg, xPos + BUTTONS_PADDING, yPos + BUTTONS_PADDING, title); //renders text
    }

    private void drawAddCreatureWindow() {
        drawRectangle(ADD_CREATURE_WINDOWX, ADD_CREATURE_WINDOWY, ADD_CREATURE_WINDOW_WIDTH, ADD_CREATURE_WINDOW_HEIGHT,
                new CHARCOAL().rgba(colour));

    }

    private void drawCubeAttributes() {

    }

    //checks if cursor hovers over a circle at (centerX, centerY)
    private boolean hoverCircle(float centerX, float centerY, float radius) {
        return Math.pow(Input.getMouseX() - centerX, 2) + Math.pow(Input.getMouseY() - centerY, 2) <= Math.pow(radius, 2);
    }

    //checks if cursor hovers over a rectangle at (posX, posY)
    private boolean hoverRectangle(float posX, float posY, float width, float height) {
        float mouseX = (float) Input.getMouseX();
        float mouseY = (float) Input.getMouseY();
        return (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height);
    }
    //method source: https://github.com/LWJGL/lwjgl3/blob/master/modules/samples/src/test/java/org/lwjgl/demo/nanovg/Demo.java
    private void drawSlider(long vg, float pos, float x, float y, float w, float h) {
        NVGPaint bg = paintA, knob = paintB;
        float cy = y + (int) (h * 0.5f);
        float kr = (int) (h * 0.25f);

        nvgSave(vg);
        //nvgClearState(vg);

        // Slot
        nvgBoxGradient(vg, x, cy - 2 + 1, w, 4, 2, 2, rgba(0, 0, 0, 32, colourA), rgba(0, 0, 0, 128, colorB), bg);
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, cy - 2, w, 4, 2);
        nvgFillPaint(vg, bg);
        nvgFill(vg);


        float xcenter = pos * w + x;
        float ycenter = y + kr;
        int radius = 10;
        double mouseX = Input.getMouseX();
        double mouseY = Input.getMouseY();
        boolean hover = Math.pow(mouseX - xcenter, 2) + Math.pow(mouseY - ycenter, 2) < Math.pow(radius, 2);


        if (hover && Input.isButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            pos = (float) (mouseX - x) / w;
        }

        // Knob Shadow
        nvgRadialGradient(vg, x + (int) (pos * w), cy + 1, kr - 3, kr + 3, rgba(0, 0, 0, 64, colourA), rgba(0, 0, 0, 0, colorB), bg);
        nvgBeginPath(vg);
        nvgRect(vg, x + (int) (pos * w) - kr - 5, cy - kr - 5, kr * 2 + 5 + 5, kr * 2 + 5 + 5 + 3);
        nvgCircle(vg, x + (int) (pos * w), cy, kr);
        nvgPathWinding(vg, NVG_HOLE);
        nvgFillPaint(vg, bg);
        nvgFill(vg);

        // Knob
        nvgLinearGradient(vg, x, cy - kr, x, cy + kr, rgba(255, 255, 255, 16, colourA), rgba(0, 0, 0, 16, colorB), knob);
        nvgBeginPath(vg);
        nvgCircle(vg, x + (int) (pos * w), cy, kr - 1);
        nvgFillColor(vg, rgba(40, 43, 48, 255, colourA));
        nvgFill(vg);
        nvgFillPaint(vg, knob);
        nvgFill(vg);

        nvgBeginPath(vg);
        nvgCircle(vg, x + (int) (pos * w), cy, kr - 0.5f);
        nvgStrokeColor(vg, rgba(0, 0, 0, 92, colourA));
        nvgStroke(vg);

        nvgRestore(vg);
    }


    public void cleanup() {
        nvgDelete(vg);
        if (posx != null) {
            MemoryUtil.memFree(posx);
        }
        if (posy != null) {
            MemoryUtil.memFree(posy);
        }
    }

}
