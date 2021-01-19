package com.maritech.arterium.ui.widgets.avatar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


public class ImageViewCircularProgress extends AppCompatImageView implements CircularProgressContract {

    /**
     * Progress values
     */
    private float mMax      = 100;
    private float mProgress = 0;
    private float mCurrentProgress = 0;

    /**
     * Progress ring sizes
     */
    private float   mBackgroundRingSize  = 40;
    private float   mProgressRingSize    = mBackgroundRingSize;
    private boolean mProgressRingOutline = false;

    /**
     * Default progress colors
     */
    private int mBackgroundRingColor = DEFAULT_BG_COLOR;
    private int mProgressRingColor   = DEFAULT_RING_COLOR;
    private int[]   mProgressGradient;
    private boolean mIsJoinGradient;
    private float   mGradientFactor;

    /**
     * Default progress ring cap
     */
    private Paint.Cap mProgressRingCorner = Paint.Cap.BUTT;

    /*
     * Animator
     */
    private ObjectAnimator mAnimator;

    /*
     * Default interpolator
     */
    private Interpolator mDefaultInterpolator = new OvershootInterpolator();

    /*
     * Default sizes
     */
    private int mViewHeight = 0;
    private int mViewWidth  = 0;

    /*
     * Default padding
     */
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;

    /*
     * Paints
     */
    private Paint mProgressRingPaint;
    private Paint mBackgroundRingPaint;

    /*
     * Bounds of the ring
     */
    private RectF mRingBounds;
    private float mOffsetRingSize;

    /*
     * Masks for clipping the current drawable in a circle
     */
    private Paint  mMaskPaint;
    private Bitmap mOriginalBitmap;
    private Canvas mCacheCanvas;

    public ImageViewCircularProgress(Context context) {
        this(context, null);
    }

    public ImageViewCircularProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewCircularProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        new AttributesHelper(this).loadFromAttributes(attrs, defStyleAttr, 0);
        setupAnimator();
    }

    /**
     * Measure to square the view
     *
     * @param widthMeasureSpec  int
     * @param heightMeasureSpec int
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Process complexity measurements
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Squared size
        int size;

        // Get getMeasuredWidth() and getMeasuredHeight().
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // Remove padding to avoid bad size ratio calculation
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        // Depending on the size ratio, calculate the final size without padding
        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        // Report back the measured size.
        // Add pending padding
        setMeasuredDimension(
                size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom());
    }

    /**
     * This method is called after measuring the dimensions of MATCH_PARENT and WRAP_CONTENT Save
     * these dimensions to setup the bounds and paints
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Save current view dimensions
        mViewWidth = w;
        mViewHeight = h;

        // Apply ring as outline
        if (isProgressRingOutline()) {
            setPadding(
                    Float.valueOf(mBackgroundRingSize + getPaddingLeft()).intValue(),
                    Float.valueOf(mBackgroundRingSize + getPaddingTop()).intValue(),
                    Float.valueOf(mBackgroundRingSize + getPaddingRight()).intValue(),
                    Float.valueOf(mBackgroundRingSize + getPaddingBottom()).intValue());
        }

        setupBounds();
        setupBackgroundRingPaint();
        setupProgressRingPaint();

        requestLayout();
        invalidate();
    }

    /**
     * Set the common bounds of the rings
     */
    private void setupBounds() {
        // Min value for squared size
        int minValue = Math.min(mViewWidth, mViewHeight);

        // Calculate the Offset if needed
        int xOffset = mViewWidth - minValue;
        int yOffset = mViewHeight - minValue;

        // Apply ring as outline
        int outline = 0;
        if (isProgressRingOutline()) {
            outline = Float.valueOf(-mBackgroundRingSize).intValue();
        }

        // Save padding plus offset
        mPaddingTop = outline + this.getPaddingTop() + (yOffset / 2);
        mPaddingBottom = outline + this.getPaddingBottom() + (yOffset / 2);
        mPaddingLeft = outline + this.getPaddingLeft() + (xOffset / 2);
        mPaddingRight = outline + this.getPaddingRight() + (xOffset / 2);

        // Bigger ring size
        float biggerRingSize = mBackgroundRingSize > mProgressRingSize
                ? mBackgroundRingSize
                : mProgressRingSize;

        // Save the half of the progress ring
        mOffsetRingSize = biggerRingSize / 2;

        int width = getWidth();
        int height = getHeight();

        // Create the ring bounds Rect
        mRingBounds = new RectF(
                mPaddingLeft + mOffsetRingSize,
                mPaddingTop + mOffsetRingSize,
                width - mPaddingRight - mOffsetRingSize,
                height - mPaddingBottom - mOffsetRingSize);
    }

    private void setupMask() {
        mOriginalBitmap = Bitmap.createBitmap(
                getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Shader shader = new BitmapShader(mOriginalBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);
        mMaskPaint.setShader(shader);
    }

    private void setupProgressRingPaint() {
        mProgressRingPaint = new Paint();
        mProgressRingPaint.setAntiAlias(true);
        mProgressRingPaint.setStrokeCap(mProgressRingCorner);
        mProgressRingPaint.setStyle(Paint.Style.STROKE);
        mProgressRingPaint.setStrokeWidth(mProgressRingSize);
        mProgressRingPaint.setColor(mProgressRingColor);

        if (mProgressGradient != null) {
            int[] colors = mProgressGradient;
            float[] positions;
            if (isJoinGradient()) {
                colors = new int[mProgressGradient.length + 1];
                positions = new float[colors.length];
                int i = 0;
                positions[i] = i;
                for (int color : mProgressGradient) {
                    colors[i] = color;
                    if (i == mProgressGradient.length - 1) {
                        positions[i] = (ANGLE_360 - mProgressRingSize * getGradientFactor())
                                / ANGLE_360;
                    } else if (i > 0) {
                        positions[i] = ((float) i / (float) colors.length);
                    }
                    i++;
                }
                colors[i] = colors[0];
                positions[i] = 1;
            }

            SweepGradient gradient = new SweepGradient(mRingBounds.centerX(),
                    mRingBounds.centerY(),
                    colors, null);

            mProgressRingPaint.setShader(gradient);
            Matrix matrix = new Matrix();
            mProgressRingPaint.getShader().setLocalMatrix(matrix);
            matrix.postTranslate(-mRingBounds.centerX(), -mRingBounds.centerY());
            matrix.postRotate(-ANGLE_90);
            matrix.postTranslate(mRingBounds.centerX(), mRingBounds.centerY());
            mProgressRingPaint.getShader().setLocalMatrix(matrix);
            mProgressRingPaint.setColor(mProgressGradient[0]);
        }
    }

    private void setupBackgroundRingPaint() {
        mBackgroundRingPaint = new Paint();
        mBackgroundRingPaint.setColor(mBackgroundRingColor);
        mBackgroundRingPaint.setAntiAlias(true);
        mBackgroundRingPaint.setStyle(Paint.Style.STROKE);
        mBackgroundRingPaint.setStrokeWidth(mBackgroundRingSize);
    }

    private void setupAnimator() {
        mAnimator = ObjectAnimator.ofFloat(
                this, "progress", this.getProgress(), this.getProgress());
        mAnimator.setDuration(ANIMATION_DURATION);
        mAnimator.setInterpolator(mDefaultInterpolator);
        mAnimator.setStartDelay(ANIMATION_DELAY);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setCurrentProgress((float) animation.getAnimatedValue());
                setProgress(getCurrentProgress());
            }
        });
    }

    /**
     * It will start animating the progress ring to the progress value set
     * <br>Default animation duration is 1200 milliseconds
     * <br/>It starts with a default delay of 500 milliseconds
     * <br/>You can get an instance of the animator with the method {@link
     * ImageViewCircularProgress#getAnimator()} and Override these values
     *
     * @see ObjectAnimator
     */
    @SuppressWarnings("unused")
    public void startAnimation() {
        float finalProgress = this.getProgress();
        this.setProgress(this.getCurrentProgress());
        mAnimator.setFloatValues(this.getCurrentProgress(), finalProgress);
        mAnimator.start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        // Setup the mask at first
        if (mMaskPaint == null) {
            setupMask();
        }

        // Cache the canvas
        if (mCacheCanvas == null) {
            mCacheCanvas = new Canvas(mOriginalBitmap);
        }

        // ImageView
        super.onDraw(mCacheCanvas);

        // Crop ImageView resource to a circle
        canvas.drawCircle(
                mRingBounds.centerX(),
                mRingBounds.centerY(),
                (mRingBounds.width() / 2) - (mBackgroundRingSize / 2),
                mMaskPaint);

        // Draw the background ring
        if (mBackgroundRingSize > 0) {
            canvas.drawArc(mRingBounds, ANGLE_360, ANGLE_360, false, mBackgroundRingPaint);
        }
        // Draw the progress ring
        if (mProgressRingSize > 0) {
            canvas.drawArc(mRingBounds, -ANGLE_90, getSweepAngle(), false, mProgressRingPaint);
        }
    }

    private float getSweepAngle() {
        return (360f / mMax * mProgress);
    }

    /* *************************
     * GETTERS & SETTERS
     * *************************/

    /**
     * Get an instance of the current {@link ObjectAnimator}
     * <br/>You can e.g. add Listeners to it
     *
     * @return {@link ObjectAnimator}
     */
    public ObjectAnimator getAnimator() {
        return mAnimator;
    }

    @Override
    public float getMax() {
        return mMax;
    }

    @Override
    public void setMax(float max) {
        mMax = max;
    }

    @Override
    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    @Override
    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
    }

    @Override
    public float getProgress() {
        return mProgress;
    }

    @Override
    public void setProgress(float progress) {
        if (progress < 0) {
            this.mProgress = 0;
        } else if (progress > 100) {
            this.mProgress = 100;
        } else {
            this.mProgress = progress;
        }
        invalidate();
    }

    @Override
    public float getProgressRingSize() {
        return mProgressRingSize;
    }

    @Override
    public void setProgressRingSize(float progressRingSize) {
        mProgressRingSize = progressRingSize;
    }

    @Override
    public float getBackgroundRingSize() {
        return mBackgroundRingSize;
    }

    @Override
    public void setBackgroundRingSize(float backgroundRingSize) {
        mBackgroundRingSize = backgroundRingSize;
    }

    @Override
    public boolean isProgressRingOutline() {
        return mProgressRingOutline;
    }

    @Override
    public void setProgressRingOutline(boolean progressRingOutline) {
        mProgressRingOutline = progressRingOutline;
    }

    @Override
    public int getBackgroundRingColor() {
        return mBackgroundRingColor;
    }

    @Override
    public void setBackgroundRingColor(int backgroundRingColor) {
        mBackgroundRingColor = backgroundRingColor;
    }

    @Override
    public int getProgressRingColor() {
        return mProgressRingColor;
    }

    @Override
    public void setProgressRingColor(int progressRingColor) {
        mProgressRingColor = progressRingColor;
    }

    @Override
    public int[] getProgressGradient() {
        return mProgressGradient;
    }

    @Override
    public void setProgressGradient(int[] progressGradient) {
        this.mProgressGradient = progressGradient;
    }

    @Override
    public boolean isJoinGradient() {
        return mIsJoinGradient;
    }

    @Override
    public void setJoinGradient(boolean isJoinGradient) {
        this.mIsJoinGradient = isJoinGradient;
    }

    @Override
    public float getGradientFactor() {
        return mGradientFactor;
    }

    @Override
    public void setGradientFactor(float gradientFactor) {
        this.mGradientFactor = gradientFactor;
    }

    @Override
    public Paint.Cap getProgressRingCorner() {
        return mProgressRingCorner;
    }

    @Override
    public void setProgressRingCorner(int progressRingCorner) {
        mProgressRingCorner = getCap(progressRingCorner);
    }

    @Override
    public Paint.Cap getCap(int id) {
        for (Paint.Cap value : Paint.Cap.values()) {
            if (id == value.ordinal()) {
                return value;
            }
        }
        return Paint.Cap.BUTT;
    }
}
