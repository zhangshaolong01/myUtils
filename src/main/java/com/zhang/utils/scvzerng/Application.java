package com.zhang.utils.scvzerng;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.zhang.utils.scvzerng.entity.Coordinate;

/**
 *
 * Created by scvzerng on 2017/7/24.
 */
public class Application {
    public static void main(String[] args) throws IOException {
        ImageFinder finder = ScreenImageFinder.getFinder();
        BufferedImage search = ImageIO.read(Application.class.getClassLoader().getResourceAsStream("fish.png"));
        long start = System.currentTimeMillis();
        List<Coordinate> coordinateList = finder.match(search,0.99);
        System.out.println("耗时:"+(System.currentTimeMillis()-start));
        coordinateList.stream().findAny().ifPresent(coordinate -> System.out.println(String.format("find help image x:%d,y:%d",coordinate.getX(),coordinate.getY())));
    }
}
