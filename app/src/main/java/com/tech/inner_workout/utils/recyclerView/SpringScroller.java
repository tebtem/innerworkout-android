package com.tech.inner_workout.utils.recyclerView;



public class SpringScroller extends SimpleSpringListener {
    private static final SpringConfig DEFAULT_CONFIG = new SpringConfig(1000, 200);

    private final Spring mSpringX;
    private final Spring mSpringY;

    private SpringScrollerListener mListener;

    public interface SpringScrollerListener {
        void onSpringUpdate(int currX, int currY);
        void onSpringAtRest();
    }

    public SpringScroller(double tension, double friction, SpringScrollerListener listener) {
        final SpringSystem mSpringSystem = SpringSystem.create();

        SpringConfig config;
        if (tension < 0 || friction < 0) {
            config = DEFAULT_CONFIG;
        } else {
            config = new SpringConfig(tension, friction);
        }

        mSpringX = mSpringSystem
                .createSpring()
                .setSpringConfig(config);

        mSpringY = mSpringSystem
                .createSpring()
                .setSpringConfig(config);

        mSpringX.addListener(this);
        mSpringY.addListener(this);

        mListener = listener;
    }

    /**
     * Set vertical and horizontal distances. It will scroll back to 0.
     * @param distanceX vertical distance.
     * @param distanceY horizontal distance.
     */
    public void startScroll(int distanceX, int distanceY) {
        mSpringX.setCurrentValue(distanceX);
        mSpringY.setCurrentValue(distanceY);
        mSpringX.setEndValue(0);
        mSpringY.setEndValue(0);
    }

    public void stopScroll() {
        if (!mSpringX.isAtRest())
            mSpringX.setAtRest();
        if (!mSpringY.isAtRest())
            mSpringY.setAtRest();
    }

    public boolean isAtRest() {
        return mSpringX.isAtRest() && mSpringY.isAtRest();
    }

    public int getCurrX() {
        return (int) Math.round(mSpringX.getCurrentValue());
    }

    public int getCurrY() {
        return (int) Math.round(mSpringY.getCurrentValue());
    }

    public void setCurrX(int distanceX) {
        mSpringX.setCurrentValue(distanceX, false);
        mSpringX.setEndValue(0);
    }

    public void setCurrY(int distanceY) {
        mSpringY.setCurrentValue(distanceY, false);
        mSpringY.setEndValue(0);
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        if (mListener != null) {
            mListener.onSpringUpdate(getCurrX(), getCurrY());
        }
    }

    @Override
    public void onSpringAtRest(Spring spring) {
        if (mListener != null && isAtRest()) {
            mListener.onSpringAtRest();
        }
    }
}
