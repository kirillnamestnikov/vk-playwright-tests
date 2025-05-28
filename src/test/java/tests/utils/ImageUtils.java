package tests.utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageUtils {
    public static boolean compareImages(String expected, String actual, String diffPath) throws Exception {
        BufferedImage expectedImage = ImageIO.read(new File(expected));
        BufferedImage actualImage = ImageIO.read(new File(actual));

        ImageComparison comparison = new ImageComparison(expectedImage, actualImage);
        ImageComparisonResult result = comparison.compareImages();

        if (result.getDifferencePercent() > 0) {
            ImageIO.write(result.getResult(), "png", new File(diffPath));
            return false;
        }
        return true;
    }
}