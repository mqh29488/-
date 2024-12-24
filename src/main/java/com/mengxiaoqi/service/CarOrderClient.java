package com.mengxiaoqi.service;

import java.io.*;
import java.net.*;
import java.util.Random;

public class CarOrderClient {
    // 模拟生成车辆订单数据
    public static String generateCarOrderData() {
        String[] carBrands = {"Toyota", "Honda", "BMW", "Audi", "Tesla"};
        String[] carColors = {"Red", "Blue", "Black", "White", "Green"};
        Random random = new Random();

        String brand = carBrands[random.nextInt(carBrands.length)];
        String color = carColors[random.nextInt(carColors.length)];
        double price = 20000 + random.nextDouble() * 30000;  // 模拟落地价格

        return brand + "," + color + "," + String.format("%.2f", price);  // 返回 CSV 格式的字符串
    }

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // 模拟发送多个订单数据
            for (int i = 0; i < 5; i++) {  // 模拟生成 5 个订单
                String carOrderData = generateCarOrderData();
                out.println(carOrderData);
                System.out.println("发送订单数据: " + carOrderData);
                Thread.sleep(1000);  // 每秒发送一个订单
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}