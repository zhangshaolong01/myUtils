package com.zhang.utils.scvzerng;

import java.awt.image.BufferedImage;
import java.util.List;

import com.zhang.utils.scvzerng.entity.Coordinate;

/**
 *
 * Created by scvzerng on 2017/7/24.
 */
public interface ImageFinder {
    /**
     * 查询到匹配的图片
     * @param image 需要查找的图片
     * @return
     */
   List<Coordinate> match(BufferedImage image,double percent);
}
