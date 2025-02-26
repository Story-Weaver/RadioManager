package by.roman.worldradio2.ui.elements.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircularTimerView extends View {

    // Максимальное время (миллисекунды). Можно менять динамически через setMaxTimeMillis().
    private long maxTimeMillis = 10 * 60_000L; // по умолчанию 10 минут

    // Текущее оставшееся время (миллисекунды).
    private long currentTimeMillis = maxTimeMillis;

    // Толщина обводки круга
    private float strokeWidth = 20f;

    // Краска для фонового круга (серого)
    private Paint backgroundPaint;

    // Краска для прогресса (цветная дуга)
    private Paint progressPaint;

    // Краска для индикатора (кружок на вершине дуги)
    private Paint indicatorPaint;

    // Краска для текста (в центре)
    private Paint textPaint;

    // Прямоугольник, в котором рисуем дугу
    private RectF oval = new RectF();

    public CircularTimerView(Context context) {
        super(context);
        init();
    }

    public CircularTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Инициализация красок

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Style.STROKE);
        backgroundPaint.setColor(Color.GRAY);
        backgroundPaint.setStrokeWidth(strokeWidth);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Style.STROKE);
        progressPaint.setStrokeCap(Cap.ROUND);
        progressPaint.setStrokeWidth(strokeWidth);
        // Можно добавить свечение, например:
        // progressPaint.setShadowLayer(10f, 0f, 0f, Color.BLUE);

        indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorPaint.setStyle(Style.FILL);
        indicatorPaint.setColor(Color.WHITE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50f);
        textPaint.setTextAlign(Align.CENTER);
    }

    /**
     * Устанавливает максимальное время (в мс).
     */
    public void setMaxTimeMillis(long maxTimeMillis) {
        this.maxTimeMillis = maxTimeMillis;
        invalidate();
    }

    /**
     * Устанавливает текущее время (в мс) и перерисовывает View.
     */
    public void setCurrentTimeMillis(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
        invalidate();
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }

    /**
     * Пример метода установки толщины обводки (опционально).
     */
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        backgroundPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    /**
     * Устанавливаем градиент (пример со SweepGradient).
     */
    private void setupGradient(float width, float height) {
        float centerX = width / 2f;
        float centerY = height / 2f;

        // Пример SweepGradient (по окружности)
        SweepGradient shader = new SweepGradient(
                centerX, centerY,
                new int[] {
                        Color.parseColor("#4E9EFF"),
                        Color.parseColor("#8AC5FF")
                },
                null
        );
        progressPaint.setShader(shader);

        // Можно попробовать RadialGradient или LinearGradient, например:
        // RadialGradient radial = new RadialGradient(
        //         centerX, centerY, Math.min(centerX, centerY),
        //         new int[] { Color.BLUE, Color.CYAN },
        //         null,
        //         Shader.TileMode.CLAMP
        // );
        // progressPaint.setShader(radial);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Задаём прямоугольник для дуги с учётом толщины обводки
        float padding = strokeWidth / 2f;
        oval.set(padding, padding, w - padding, h - padding);

        // Инициализируем градиент с учётом реальных размеров
        setupGradient(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) - (strokeWidth / 2f);

        // 1) Рисуем фоновый круг (серый)
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);

        // 2) Вычисляем долю оставшегося времени
        float fraction = (float) currentTimeMillis / (float) maxTimeMillis;
        // Угол дуги (360° * fraction)
        float sweepAngle = 360f * fraction;

        // 3) Рисуем дугу прогресса (начинаем с -90°, чтобы "0°" был сверху)
        canvas.drawArc(oval, -90f, sweepAngle, false, progressPaint);

        // 4) Рисуем индикатор-кружок на конце дуги
        double endAngle = Math.toRadians(-90 + sweepAngle);
        float indicatorX = (float) (centerX + radius * Math.cos(endAngle));
        float indicatorY = (float) (centerY + radius * Math.sin(endAngle));
        canvas.drawCircle(indicatorX, indicatorY, strokeWidth / 2f, indicatorPaint);

        // 5) Рисуем текст по центру (формат "mm:ss")
        // Отображаем текст в формате "hh:mm:ss"
        long totalSeconds = currentTimeMillis / 1000;
        long hh = totalSeconds / 3600;
        long mm = (totalSeconds % 3600) / 60;
        long ss = totalSeconds % 60;

        String timeString = String.format("%02d:%02d:%02d", hh, mm, ss);

        float textY = centerY - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(timeString, centerX, textY, textPaint);

    }
}
