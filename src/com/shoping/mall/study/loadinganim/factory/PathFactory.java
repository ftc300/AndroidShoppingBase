package com.shoping.mall.study.loadinganim.factory;

import android.graphics.Path;
import android.graphics.Point;

import com.shoping.mall.study.loadinganim.factory.path.Circle;
import com.shoping.mall.study.loadinganim.factory.path.Diamond;
import com.shoping.mall.study.loadinganim.factory.path.Infinite;
import com.shoping.mall.study.loadinganim.factory.path.Square;
import com.shoping.mall.study.loadinganim.factory.path.Star;
import com.shoping.mall.study.loadinganim.factory.path.Triangle;

/**
 * @author Adrián García Lomas
 */
public class PathFactory {

  public static final String CIRCLE = "circle";
  public static final String SQUARE = "square";
  public static final String INFINITE = "infinite";
  public static final String STAR = "star";
  public static final String TRIANGLE = "triangle";
  public static final String DIAMOND = "diamond";

  public static Path makePath(String path, Point center, int pathWidth, int pathHeight,
      int maxBallSize) {

	if(CIRCLE.equals(path))
        return new Circle(center, pathWidth, pathHeight, maxBallSize).draw();
    	  if(SQUARE.equals(path))
        return new Square(center, pathWidth, pathHeight, maxBallSize).draw();
    	  if(INFINITE.equals(path))
        return new Infinite(center, pathWidth, pathHeight, maxBallSize).draw();
    	  if(STAR.equals(path))
        return new Star(center, pathWidth, pathHeight, maxBallSize).draw();
    	  if(TRIANGLE.equals(path))
        return new Triangle(center, pathWidth, pathHeight, maxBallSize).draw();
    	  if(DIAMOND.equals(path))
        return new Diamond(center, pathWidth, pathHeight, maxBallSize).draw();
    	  else
        return new Infinite(center, pathWidth, pathHeight, maxBallSize).draw();
  }
}

