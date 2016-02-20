package com.kpmc.accelrc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.base.Preconditions;
import com.kpmc.accelrc.android.logging.LogHelper;
import com.kpmc.accelrc.android.logging.Logger;

import static android.graphics.Color.BLACK;
import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by matthijs on 29/08/15.
 */
public class RCStick extends View {
    private final Logger log = LogHelper.getLogger(this);

    /* Resolutions; total throw will be 2*[resolution]. (both negative and positive) */
    public int vResolution = 100;//Integer.MAX_VALUE;
    public int hResolution = 100;//Integer.MAX_VALUE;

    /* Current positions. Updated via #update... methods */
    private int vValue = 0;
    private int hValue = 0;

    private final Paint dashedStrokePaint;
    private final Paint wideStrokePaint;
    private final Paint redPaint;


    public RCStick(Context context, AttributeSet attrs) {
        super(context, attrs);

        dashedStrokePaint = createDashedStrokePaint();
        wideStrokePaint = createWideStrokePaint();
        redPaint = createRedPaint();
    }

    public void updateVertical(int value) {
        Preconditions.checkArgument(Math.abs(value) <= vResolution, "New vertical value (%s) may not exceed resolution.", value);
        this.vValue = value;

        this.invalidate();
    }

    public void updateHorizontal(int value) {
        Preconditions.checkArgument(Math.abs(value) <= hResolution, "New horizontal value (%s) may not exceed resolution.", value);
        this.hValue = value;

        this.invalidate();
    }

    public void update(int verticalValue, int horizontalValue) {
        updateVertical(verticalValue);
        updateHorizontal(horizontalValue);

        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        int squareSize = Math.min(parentWidth, parentHeight);

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(squareSize, EXACTLY),
                MeasureSpec.makeMeasureSpec(squareSize, EXACTLY)
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // store in floats to calculation results more precise.
        final float height = getHeight();
        final float width = getWidth();

        // Draw box
        canvas.drawRect(0, 0, width, height, wideStrokePaint);

        // Draw x/y lines
        canvas.drawLine(width / 2, 0, width / 2, height, dashedStrokePaint);
        canvas.drawLine(0, height / 2, width, height / 2, dashedStrokePaint);

        final float hPos = width / 2f + ((float) hValue / (float) hResolution * (width / 2f));
        final float vPos = height / 2f - ((float) vValue / (float) vResolution * (height / 2f));

        // Draw red circle to represent the stick position
        canvas.drawCircle(hPos, vPos, 10, redPaint);

    }


    private static Paint createDashedStrokePaint() {
        Paint paint = new Paint();
        paint.setColor(BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 5));
        paint.setAlpha(128);
        return paint;
    }

    private static Paint createWideStrokePaint() {
        Paint paint = new Paint();
        paint.setColor(BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        return paint;
    }

    private static Paint createRedPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        return paint;
    }
}
