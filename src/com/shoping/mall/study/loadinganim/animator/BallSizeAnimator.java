package com.shoping.mall.study.loadinganim.animator;

import java.util.ArrayList;
import java.util.List;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.shoping.mall.study.loadinganim.Ball;

/**
 * @author Adrián García Lomas
 */
@SuppressLint("NewApi")
public class BallSizeAnimator extends AbstractBallAnimator {

  private int duration;
  private float offset;
  private Path path;
  private float minBallSize;
  private float maxBallSize;
  private List<ValueAnimator> animators;

  public BallSizeAnimator(Path path, int duration, float minBallSize, float maxBallSize) {
    this.duration = duration;
    this.path = path;
    this.minBallSize = minBallSize;
    this.maxBallSize = maxBallSize;
    init();
  }

  private void init() {
    animators = new ArrayList<ValueAnimator>();
  }

  public void start() {
    animators.clear();
    for (int i = 0; i < balls.size(); i++) {
      createBallAnimatorAndStart(balls.get(i), 300 * i);
    }
  }

  @Override
  public void stop() {
    for (ValueAnimator animator : animators) {
      animator.cancel();
    }
  }

  @Override
  public void restart() {
    stop();
    start();
  }

  private void createBallAnimatorAndStart(Ball ball, int startDelay) {
    ValueAnimator valueAnimator = new ValueAnimator();
    valueAnimator.setFloatValues(minBallSize, maxBallSize);
    valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    valueAnimator.setDuration(duration);
    valueAnimator.addUpdateListener(new BallUpdateListener(ball));
    valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
    valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    valueAnimator.setStartDelay(startDelay);
    animators.add(valueAnimator);
    valueAnimator.start();
  }

  private class BallUpdateListener implements ValueAnimator.AnimatorUpdateListener {

    private Ball ball;

    public BallUpdateListener(Ball ball) {
      this.ball = ball;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
      float value = (Float) animation.getAnimatedValue();
      ball.setSize((int) value);
    }
  }
}
