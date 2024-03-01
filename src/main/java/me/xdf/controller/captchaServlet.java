package me.xdf.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
@WebServlet("/captchaServlet")
public class captchaServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        int width = 100;
        int height = 40;
        char[] codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        Font font = new Font("Arial", Font.BOLD, 20);  // 字体、风格、大小
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setColor(Color.BLACK);
        Random random = new Random();
        StringBuilder captchaCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char c = codeSequence[random.nextInt(codeSequence.length)];
            captchaCode.append(c);
            g.drawString(String.valueOf(c), 15 * i + 15, 25);
        }
        g.setColor(Color.red);
        for (int i = 0; i < 8; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        request.getSession().setAttribute("captchaCode", captchaCode.toString());
        g.dispose();
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }
}