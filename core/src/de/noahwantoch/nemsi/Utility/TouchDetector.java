package de.noahwantoch.nemsi.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TouchDetector {
    private static final String TAG = TouchDetector.class.getSimpleName();

    private ShapeRenderer shapeRenderer;
    private boolean debugMode = false;

    public TouchDetector(){
        shapeRenderer = new ShapeRenderer();
    }

    public boolean isRectangleTouched(float x, float y, float width, float height){
        if(isHoveredOverRectangle(x, y, width, height)){
            if(Gdx.input.isTouched(0)){
                return true;
            }
        }
        return false;
    }

    public boolean isHoveredOverRectangle(float x, float y, float width, float height){
        float touchedX = Gdx.input.getX();
        float touchedY = Gdx.graphics.getHeight() - Gdx.input.getY(); //Transformiert

        if(debugMode){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();
        }

        if(touchedX > x && touchedX < x + width && touchedY > y && touchedY < y + height){
            return true;
        }
        return false;
    }

    public void showDebug(boolean bool){
        debugMode = bool;
        shapeRenderer.setColor(1f, 0,0, 0.1f);
    }
}
