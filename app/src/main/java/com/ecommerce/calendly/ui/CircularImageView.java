package com.ecommerce.calendly.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircularImageView extends androidx.appcompat.widget.AppCompatImageView {

    private Paint paint;
    private TextPaint textPaint;
    private String text;
    private Rect textBounds = new Rect();

    public CircularImageView(Context context) {
        super(context);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;

        canvas.drawCircle(width / 2, height / 2, radius, paint);

        if (!TextUtils.isEmpty(text)) {
            textPaint.getTextBounds(text, 0, text.length(), textBounds);
            canvas.drawText(text, width / 2, height / 2 + textBounds.height() / 2, textPaint);
        }
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setCircleColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    public void setTextColor(int color) {
        textPaint.setColor(color);
        invalidate();
    }

    public void setTextSize(float size) {
        textPaint.setTextSize(size);
        invalidate();
    }
}