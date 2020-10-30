package com.zk.cloudweb.util.export;




import com.jsevy.jdxf.DXFDocument;
import com.jsevy.jdxf.DXFGraphics;
import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import com.zk.cloudweb.util.dxfType.DxfLwPolyLine;
import com.zk.cloudweb.util.dxfType.LineWidth;
import com.zk.cloudweb.util.dxfUtil.DxfDocWriter;
import com.zk.cloudweb.util.dxfUtil.Vector2;
import com.zk.cloudweb.util.wgs84_px;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xf
 * @version 1.0
 * @date 2020/4/1 11:00
 */
public class exportCAD {

    /**
     * 导出单个CAD文件
     * @param measureds
     * @param fileName
     * @param response
     * @param basePath
     * @throws IOException
     */
    public static void AutoCADExport(List<SocketGPSDataPackage> measureds, String fileName, HttpServletResponse response, String basePath) throws IOException {
        DXFDocument dxfDocument = new
                DXFDocument("Example");
        DXFGraphics dxfGraphics =
                dxfDocument.getGraphics();
        fileName = UUID.randomUUID().toString().replace("-", "");
        String newfilePathOnly = basePath+"/webCAD/";
        //判断地址是否存在
        File fileUIS = new File(newfilePathOnly);
        //如果不存在文件夹创建文件夹
        if (!fileUIS.exists()) {
            fileUIS.mkdirs();
        }
        String generateNewDxf = newfilePathOnly+fileName+".dxf";
        Graphics2D graphics2D = dxfGraphics;

        graphics2D.setColor(Color.RED);
        graphics2D.setStroke(new BasicStroke(0.3f));
        List<Double> longitude = new ArrayList<>();
        List<Double> latitude = new ArrayList<>();

        for (int i = 0;i<measureds.size();i++ ){
            longitude.add(Double.parseDouble(""+measureds.get(i).getLongitude()));
            latitude.add(Double.parseDouble(""+measureds.get(i).getLatitude()));
        }

        /**
         * 将经纬度坐标转换为像素坐标
         */
        List<List<Integer>> Lxy = wgs84_px.returnXY(longitude,latitude);
        //通过Graphics2D写入图形
        for (int i = 0; i < Lxy.get(0).size()-1; i++) {
            graphics2D.drawLine(Lxy.get(0).get(i),Lxy.get(1).get(i),Lxy.get(0).get(i+1),Lxy.get(1).get(i+1));
        }
        //生成dxf文本
        String dxfText = dxfDocument.toDXFString();

        FileWriter fileWriter = new FileWriter(generateNewDxf);
        fileWriter.write(dxfText);
        fileWriter.flush();
        fileWriter.close();
        //导出
        try{
            OutputStream out = response.getOutputStream();
            // inputStream：读文件，前提是这个文件必须存在，要不就会报错
            String filename =new String(fileName.getBytes("GBK"), "iso8859-1");
            response.addHeader("content-disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(filename+".dxf", "utf-8"));
            response.setContentType("application/x-cmx");
            response.setHeader("Content-disposition", "attachment;filename="+filename+".dxf");
            InputStream is = new FileInputStream(generateNewDxf);
//            ServletOutputStream out = response.getOutputStream();
            byte[] b = new byte[4096];
            int size = is.read(b);
            while (size > 0) {
                out.write(b, 0, size);
                size = is.read(b);
            }
            out.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 批量导出CAD文件
     * @param measureds
     * @param fileName
     * @param response
     * @return 返回文件地址
     */
    public static String  batchAutoCAD(List<SocketGPSDataPackage> measureds, String fileName, HttpServletResponse response, String basePath) throws IOException {
        DXFDocument dxfDocument = new
                DXFDocument("Example");
        DXFGraphics dxfGraphics =
                dxfDocument.getGraphics();
        fileName = UUID.randomUUID().toString().replace("-", "");
        String newfilePathOnly = basePath+"/webCAD/";
        //判断地址是否存在
        File fileUIS = new File(newfilePathOnly);
        //如果不存在文件夹创建文件夹
        if (!fileUIS.exists()) {
            fileUIS.mkdirs();
        }
        String generateNewDxf = newfilePathOnly+fileName+".dxf";
        Graphics2D graphics2D = dxfGraphics;

        graphics2D.setColor(Color.RED);
        graphics2D.setStroke(new BasicStroke(0.3f));
        List<Double> longitude = new ArrayList<>();
        List<Double> latitude = new ArrayList<>();

        for (int i = 0;i<measureds.size();i++ ){
            longitude.add(Double.parseDouble(""+measureds.get(i).getLongitude()));
            latitude.add(Double.parseDouble(""+measureds.get(i).getLatitude()));
        }

        /**
         * 将经纬度坐标转换为像素坐标
         */
        List<List<Integer>> Lxy = wgs84_px.returnXY(longitude,latitude);
        //通过Graphics2D写入图形
        for (int i = 0; i < Lxy.get(0).size()-1; i++) {
            graphics2D.drawLine(Lxy.get(0).get(i),Lxy.get(1).get(i),Lxy.get(0).get(i+1),Lxy.get(1).get(i+1));
        }
        //生成dxf文本
        String dxfText = dxfDocument.toDXFString();

        FileWriter fileWriter = new FileWriter(generateNewDxf);
        fileWriter.write(dxfText);
        fileWriter.flush();
        fileWriter.close();

        return generateNewDxf;
    }


    public static void generateCAD(List<SocketGPSDataPackage> measureds, String fileName, HttpServletRequest request, HttpServletResponse response, String basePath){

        fileName = UUID.randomUUID().toString().replace("-", "");
        String newfilePathOnly = basePath+"/webCAD/";
        //判断地址是否存在
        File fileUIS = new File(newfilePathOnly);
        //如果不存在文件夹创建文件夹
        if (!fileUIS.exists()) {
            fileUIS.mkdirs();
        }
        String generateNewDxf = newfilePathOnly+fileName+".dxf";
//        String entitiesFilePath = "d:\\web\\"+fileName+"_entities.dxf";
        List<Double> longitude = new ArrayList<>();
        List<Double> latitude = new ArrayList<>();

        for (int i = 0;i<measureds.size();i++ ){
            longitude.add(Double.parseDouble(""+measureds.get(i).getLongitude()));
            latitude.add(Double.parseDouble(""+measureds.get(i).getLatitude()));
        }

        /**
         * 将经纬度坐标转换为像素坐标
         */
        List<List<Integer>> Lxy = wgs84_px.returnXY(longitude,latitude);

        try (DxfDocWriter dxfDocWriter = new DxfDocWriter()) {
//            for (int i=0;i<yd.length;i++){
//                test.xyTrans(xd[i],yd[i],0d);
//            }
            //设置颜色
            Color randomColor = new Color(255, 0, 50);
            //设置线宽
            LineWidth lineWidth = LineWidth.values()[0];
            for (int i = 0; i < Lxy.get(0).size()-1; i++) {
//                Vector3 center = new Vector3(xd[i],yd[i], 100);
//                // 添加圆形
//                DxfCircle dxfCircle = new DxfCircle(center, 50);
//                // 设置线宽
//                dxfCircle.setLineWidth(lineWidth);
//                // 设置颜色
//                dxfCircle.setColor(randomColor);
//                // 设置是否进行填充
//                dxfCircle.setSolid(i % 3 == 0);
//                // 设置填充的颜色
//                dxfCircle.setSolidColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
//                // 添加图元
//                dxfDocWriter.addEntity(dxfCircle);

//                 添加线段
//                DxfLine dxfLine = new DxfLine(new Vector3(xd[i+1], yd[i+1], 100), center);
//
//                dxfLine.setColor(randomColor);
//                dxfLine.setLineWidth(lineWidth);
//                dxfDocWriter.addEntity(dxfLine);

                // 添加多线段
                DxfLwPolyLine dxfLwPolyLine = new DxfLwPolyLine();
                // 添加多线段各个顶点
                //----生成的图形跟原图相反，反转为原来的图形
                dxfLwPolyLine.addPoint(new Vector2(Lxy.get(0).get(i),Lxy.get(1).get(i)-Lxy.get(1).get(i)*2));
                dxfLwPolyLine.addPoint(new Vector2(Lxy.get(0).get(i+1),Lxy.get(1).get(i+1)-Lxy.get(1).get(i+1)*2));
//                dxfLwPolyLine.addPoint(new Vector2(60 + i * 100, 1200 - 50));
//                dxfLwPolyLine.addPoint(new Vector2(10 + i * 100, 1200 - 50));
                // 设置是否是闭合图形
//                dxfLwPolyLine.setClose(true);
                dxfLwPolyLine.setLineWidth(lineWidth);
                dxfLwPolyLine.setColor(randomColor);
                dxfDocWriter.addEntity(dxfLwPolyLine);

                // 添加文字
//                DxfText dxfText = new DxfText();
//                dxfText.setText((char) (65 + random.nextInt(26)) + "哈哈");
//                // 设置旋转角度
//                dxfText.setAngle(random.nextInt(360));
//                // 设置高度
//                dxfText.setHigh(10 + random.nextInt(10));
//                // 设置倾斜角度
//                dxfText.setInclination(random.nextInt(45));
//                // 设置宽度
//                dxfText.setWidth(1 + random.nextInt(2));
//                dxfText.setColor(randomColor);
//                // 设置绘制点位置
//                dxfText.setStartPoint(new Vector3(i * 100, 1400, 100));
//                dxfDocWriter.addEntity(dxfText);

                // 添加弧形
//                DxfArc dxfArc = new DxfArc();
//                dxfArc.setCenter(new Vector3(200 + i * 100, 1600, 100));
//                dxfArc.setRadius(100);
//                dxfArc.setStartAngle(random.nextInt(90));
//                dxfArc.setEndAngle(180 + random.nextInt(180));
//                dxfArc.setColor(randomColor);
//                dxfArc.setLineWidth(lineWidth);
//                dxfArc.setSolid(i % 5 == 0);
//                dxfDocWriter.addEntity(dxfArc);
            }
            // 保存dxf文件
            dxfDocWriter.save(generateNewDxf, true);
            // 仅仅保存图元信息
//            dxfDocWriter.saveEntities(entitiesFilePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //导出
        try{
            OutputStream out = response.getOutputStream();
            // inputStream：读文件，前提是这个文件必须存在，要不就会报错
            String filename =new String(fileName.getBytes("GBK"), "iso8859-1");
            response.addHeader("content-disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(filename+".dxf", "utf-8"));
            response.setContentType("application/x-cmx");
            response.setHeader("Content-disposition", "attachment;filename="+filename+".dxf");
            InputStream is = new FileInputStream(generateNewDxf);
//            ServletOutputStream out = response.getOutputStream();
            byte[] b = new byte[4096];
            int size = is.read(b);
            while (size > 0) {
                out.write(b, 0, size);
                size = is.read(b);
            }
            out.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try (DxfDocWriter dxfDocWriter = new DxfDocWriter()) {
//            for (int i = 0; i < 50; i++) {
//                Color randomColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
//                LineWidth lineWidth = LineWidth.values()[random.nextInt(LineWidth.values().length)];
//                Vector3 center = new Vector3(200 + i * 100, random.nextInt(200), 100);
//                // 添加圆形
//                DxfCircle dxfCircle = new DxfCircle(center, 50);
//                // 设置线宽
//                dxfCircle.setLineWidth(lineWidth);
//                // 设置颜色
//                dxfCircle.setColor(randomColor);
//                // 设置是否进行填充
//                dxfCircle.setSolid(i % 3 == 0);
//                // 设置填充的颜色
//                dxfCircle.setSolidColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
//                // 添加图元
//                dxfDocWriter.addEntity(dxfCircle);

        // 添加线段
//                DxfLine dxfLine = new DxfLine(new Vector3(2600, 1000, 100), center);
//
//                dxfLine.setColor(randomColor);
//                dxfLine.setLineWidth(lineWidth);
//                dxfDocWriter.addEntity(dxfLine);
//
//                // 添加多线段
//                DxfLwPolyLine dxfLwPolyLine = new DxfLwPolyLine();
//                // 添加多线段各个顶点
//                dxfLwPolyLine.addPoint(new Vector2(i * 100, 1200));
//                dxfLwPolyLine.addPoint(new Vector2(50 + i * 100, 1200));
//                dxfLwPolyLine.addPoint(new Vector2(60 + i * 100, 1200 - 50));
//                dxfLwPolyLine.addPoint(new Vector2(10 + i * 100, 1200 - 50));
//                // 设置是否是闭合图形
//                dxfLwPolyLine.setClose(i % 5 != 0);
//                dxfLwPolyLine.setLineWidth(lineWidth);
//                dxfLwPolyLine.setColor(randomColor);
//                dxfDocWriter.addEntity(dxfLwPolyLine);

        // 添加文字
//                DxfText dxfText = new DxfText();
//                dxfText.setText((char) (65 + random.nextInt(26)) + "哈哈");
//                // 设置旋转角度
//                dxfText.setAngle(random.nextInt(360));
//                // 设置高度
//                dxfText.setHigh(10 + random.nextInt(10));
//                // 设置倾斜角度
//                dxfText.setInclination(random.nextInt(45));
//                // 设置宽度
//                dxfText.setWidth(1 + random.nextInt(2));
//                dxfText.setColor(randomColor);
//                // 设置绘制点位置
//                dxfText.setStartPoint(new Vector3(i * 100, 1400, 100));
//                dxfDocWriter.addEntity(dxfText);

        // 添加弧形
//                DxfArc dxfArc = new DxfArc();
//                dxfArc.setCenter(new Vector3(200 + i * 100, 1600, 100));
//                dxfArc.setRadius(100);
//                dxfArc.setStartAngle(random.nextInt(90));
//                dxfArc.setEndAngle(180 + random.nextInt(180));
//                dxfArc.setColor(randomColor);
//                dxfArc.setLineWidth(lineWidth);
//                dxfArc.setSolid(i % 5 == 0);
//                dxfDocWriter.addEntity(dxfArc);
//            }
//            String generateNewDxf = "d:\\web\\test_empty_generate.dxf";
//            String entitiesFilePath = "d:\\web\\test_empty_generate_entities.dxf";
//            // 保存dxf文件
//            dxfDocWriter.save(generateNewDxf, true);
//            // 仅仅保存图元信息
//            dxfDocWriter.saveEntities(entitiesFilePath, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 批量导出CAD文件
     * @param measureds
     * @param fileName
     * @param request
     * @param response
     * @return 返回文件地址
     */
    public static String  batchGenerateCAD(List<SocketGPSDataPackage> measureds, String fileName, HttpServletRequest request, HttpServletResponse response, String basePath){

        fileName = UUID.randomUUID().toString().replace("-", "");
        String newfilePathOnly = basePath+"/webCAD/";
        //判断地址是否存在
        File fileUIS = new File(newfilePathOnly);
        //如果不存在文件夹创建文件夹
        if (!fileUIS.exists()) {
            fileUIS.mkdirs();
        }
        String generateNewDxf = newfilePathOnly+fileName+".dxf";
//        String entitiesFilePath = "d:\\web\\"+fileName+"_entities.dxf";
        List<Double> longitude = new ArrayList<>();
        List<Double> latitude = new ArrayList<>();

        for (int i = 0;i<measureds.size();i++ ){
            longitude.add(Double.parseDouble(""+measureds.get(i).getLongitude()));
            latitude.add(Double.parseDouble(""+measureds.get(i).getLatitude()));
        }

        /**
         * 将经纬度坐标转换为像素坐标
         */
        List<List<Integer>> Lxy = wgs84_px.returnXY(longitude,latitude);

        try (DxfDocWriter dxfDocWriter = new DxfDocWriter()) {
//            for (int i=0;i<yd.length;i++){
//                test.xyTrans(xd[i],yd[i],0d);
//            }
            //设置颜色
            Color randomColor = new Color(255, 0, 50);
            //设置线宽
            LineWidth lineWidth = LineWidth.values()[0];
            for (int i = 0; i < Lxy.get(0).size()-1; i++) {
//                Vector3 center = new Vector3(xd[i],yd[i], 100);
//                // 添加圆形
//                DxfCircle dxfCircle = new DxfCircle(center, 50);
//                // 设置线宽
//                dxfCircle.setLineWidth(lineWidth);
//                // 设置颜色
//                dxfCircle.setColor(randomColor);
//                // 设置是否进行填充
//                dxfCircle.setSolid(i % 3 == 0);
//                // 设置填充的颜色
//                dxfCircle.setSolidColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
//                // 添加图元
//                dxfDocWriter.addEntity(dxfCircle);

//                 添加线段
//                DxfLine dxfLine = new DxfLine(new Vector3(xd[i+1], yd[i+1], 100), center);
//
//                dxfLine.setColor(randomColor);
//                dxfLine.setLineWidth(lineWidth);
//                dxfDocWriter.addEntity(dxfLine);

                // 添加多线段
                DxfLwPolyLine dxfLwPolyLine = new DxfLwPolyLine();
                // 添加多线段各个顶点
                //----生成的图形跟原图相反，反转为原来的图形
                dxfLwPolyLine.addPoint(new Vector2(Lxy.get(0).get(i),Lxy.get(1).get(i)-Lxy.get(1).get(i)*2));
                dxfLwPolyLine.addPoint(new Vector2(Lxy.get(0).get(i+1),Lxy.get(1).get(i+1)-Lxy.get(1).get(i+1)*2));
//                dxfLwPolyLine.addPoint(new Vector2(60 + i * 100, 1200 - 50));
//                dxfLwPolyLine.addPoint(new Vector2(10 + i * 100, 1200 - 50));
                // 设置是否是闭合图形
//                dxfLwPolyLine.setClose(true);
                dxfLwPolyLine.setLineWidth(lineWidth);
                dxfLwPolyLine.setColor(randomColor);
                dxfDocWriter.addEntity(dxfLwPolyLine);

                // 添加文字
//                DxfText dxfText = new DxfText();
//                dxfText.setText((char) (65 + random.nextInt(26)) + "哈哈");
//                // 设置旋转角度
//                dxfText.setAngle(random.nextInt(360));
//                // 设置高度
//                dxfText.setHigh(10 + random.nextInt(10));
//                // 设置倾斜角度
//                dxfText.setInclination(random.nextInt(45));
//                // 设置宽度
//                dxfText.setWidth(1 + random.nextInt(2));
//                dxfText.setColor(randomColor);
//                // 设置绘制点位置
//                dxfText.setStartPoint(new Vector3(i * 100, 1400, 100));
//                dxfDocWriter.addEntity(dxfText);

                // 添加弧形
//                DxfArc dxfArc = new DxfArc();
//                dxfArc.setCenter(new Vector3(200 + i * 100, 1600, 100));
//                dxfArc.setRadius(100);
//                dxfArc.setStartAngle(random.nextInt(90));
//                dxfArc.setEndAngle(180 + random.nextInt(180));
//                dxfArc.setColor(randomColor);
//                dxfArc.setLineWidth(lineWidth);
//                dxfArc.setSolid(i % 5 == 0);
//                dxfDocWriter.addEntity(dxfArc);
            }
            // 保存dxf文件
            dxfDocWriter.save(generateNewDxf, true);
            // 仅仅保存图元信息
//            dxfDocWriter.saveEntities(entitiesFilePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return generateNewDxf;
    }



}
