package com.example.lab2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bdtrev on 19/01/2016.
 */
public class LightsView extends View implements View.OnClickListener, View.OnTouchListener {
    // some color constants;
    static int bg = Color.BLACK;
    static int bgGrid = Color.BLUE;
    static int fgOff = Color.DKGRAY;
    static int fgOn = Color.YELLOW;

    // an array of color to show the state of each switch
    static int[] cols = {fgOff, fgOn};

    static String tag = "LightsView: ";

    LightsModel model;
    int size;
    int n;

    public LightsView(Context context) {
        super(context);
        setup(context, "Constructor 1");
    }

    public LightsView(Context context, LightsModel model) {
        super(context);
        this.model = model;
        setup(context, "Constructor 1a");
    }

    public LightsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, "Constructor 2");
    }

    public LightsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, "Constructor 3");
    }

    private void setup(Context context, String cons) {
        System.out.println(tag + cons);
        checkModel();
        setOnTouchListener(this);
        setOnClickListener(this);
    }

    private void checkModel() {
        if (model != null) {
            n = model.n;
        } else {
            // this is an emergency: we got here without a model!
            System.out.println(tag + "Oops: Null model");
            n = 5;
            model = new LightsModel(n);
        }
    }

    @Override
    public void onClick(View view) {
        // use the x, y coords from the onTouch event, the assume that the geometry params have already been set
        int i = (int) ((curX - xOff) / size);
        int j = (int) ((curY - yOff) / size);
        model.tryFlip(i, j);
        postInvalidate();
    }

    float curX, curY;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        curX = motionEvent.getX();
        curY = motionEvent.getY();
        // return false to ensure that the event can help make an onClick event
        return false;
    }

    // these offsets are to cope with the fact that we might be drawinga square within a rectangle
    int xOff, yOff, minLen, gridSquareLen;

    public void setGeometry() {
        int midX = getWidth() / 2;
        int midY = getHeight() / 2;
        minLen = Math.min(getWidth(), getHeight());
        // gridSquareLen will be the size of the lights grid in pixels
        gridSquareLen = (minLen / n) * n;
        //size of individual tiles
        size = gridSquareLen / n;
        xOff = midX - gridSquareLen / 2;
        yOff = midY - gridSquareLen / 2;
    }

    public void draw(Canvas g) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(bg);
        setGeometry();
        // draw view background
        g.drawRect(0, 0, getWidth(), getHeight(), p);
        // draw the grid background
        p.setColor(bgGrid);
        g.drawRoundRect(new RectF(xOff, yOff, xOff + gridSquareLen, yOff + gridSquareLen), 5, 5, p);
        // draw the switches
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cx = xOff + size * i + size / 2;
                int cy = yOff + size * j + size / 2;
                p.setColor(cols[model.grid[i][j]]);
                drawTile(g, cx, cy, p);
            }
        }
    }

    public void drawTile(Canvas g, int cx, int cy, Paint p) {
        int length = (size * 7) / 8;
        int rad = size / 6;

        int x = cx - length / 2;
        int y = cy - length / 2;
        RectF rect = new RectF(x, y, x + length, y + length);
        g.drawRoundRect(rect, rad, rad, p);
    }
}
