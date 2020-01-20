package ru.skillbox.blog.utils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class Captcha {

    public static byte[] getCaptcha(String randomText, String image_extension) throws java.io.IOException {
        BufferedImage bufferedImage;
        int width = 150;
        int height = 50;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        Font font = new Font("Arial", Font.BOLD, 18);
        g2d.setFont(font);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        GradientPaint gp = new GradientPaint(0, height / 2,
            new Color(218, 232, 215), 0, height, new Color(218, 232, 215), false);
        g2d.setPaint(new GradientPaint(0, height / 2, new Color(218, 232, 215), 0, height, new Color(254, 254, 254), true));
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(new Color(151, 100, 60));
        // -------------------------
        g2d.drawString(randomText, 60, 30);
        for (int i = 0; i < 100; i += 15) {
            g2d.drawLine((int) Math.round(Math.random() * width), (int) Math.round(Math.random() * height), (int) Math.round(Math.random() * width), (int) Math.round(Math.random() * height));
        }
        g2d.dispose();
        byte captchaByteStream[];
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ImageOutputStream imout = ImageIO.createImageOutputStream(bout);
        if (ImageIO.write(bufferedImage, image_extension, imout)) {
            captchaByteStream = bout.toByteArray();
            return captchaByteStream;
        } else {
            return null;
        }
    }
}
