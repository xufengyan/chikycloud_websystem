package com.zk.cloudweb.util.export;

import com.zk.cloudweb.entity.socketLink.SocketGPSDataPackage;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author xf
 * @version 1.0
 * @date 2020/4/1 11:00
 */
public class exportKML {


    /**
     * 单个kml文件导出
     * @param measureds
     * @param fileName
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    public static boolean setTravelsKml(List<SocketGPSDataPackage> measureds, String fileName, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Element root = DocumentHelper.createElement("kml");

        Document document = DocumentHelper.createDocument(root);
        //根节点添加属性
        root.addAttribute("xmlns", "http://earth.google.com/kml/2.1");
//                .addAttribute("xmlns:gx", "http://www.google.com/kml/ext/2.2")
//                .addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
//                .addAttribute("xsi:schemaLocation",
//                        "http://www.opengis.net/kml/2.2 http://schemas.opengis.net/kml/2.2.0/ogckml22.xsd http://www.google.com/kml/ext/2.2 http://code.google.com/apis/kml/schema/kml22gx.xsd");
        Element documentElement = root.addElement("Document");
        documentElement.addElement("name").addText(fileName);
        documentElement.addElement("description").addText("");
//        documentElement.addElement("Snippet").addText("");
//        Element folderElement = documentElement.addElement("Folder");//添加一个目录
//        folderElement.addAttribute("id", "FeatureLayer0");
//        folderElement.addElement("name").addText("轨迹点位"); //名称
//        folderElement.addElement("Snippet").addText(""); //显示在Google Earth之中的对description的简短概要.
        //生成点位图标数据
//        for (int i=0;i<measureds.size();i++) {
//            Element placeMarkElement = folderElement.addElement("Placemark");//在文件夹中添加一个地标
//            placeMarkElement.addAttribute("id", String.valueOf(i));
//            placeMarkElement.addElement("name").addText("点位"+String.valueOf(i));
//            placeMarkElement.addElement("Snippet").addText("");
//            placeMarkElement.addElement("description").addText("");//简介
//            placeMarkElement.addElement("styleUrl").addText("#MyStyle");//风格
//            Element pointElement = placeMarkElement.addElement("Point");
//            pointElement.addElement("altitudeMode").addText("clampToGround");
//            //添加点位的经纬度坐标以及高度(显示时绘制高度m)
//            pointElement.addElement("coordinates").addText(measureds.get(i).getLongitude()+","+measureds.get(i).getLatitude()+",0");//可以是是任何几何形状的子元素，定义每一个点的经度、纬度和高度(按照严格的顺序). 多个点使用空格隔开，经纬度按照WGS84标准.
//        }

        //设置出发点图标样式
        Element startStyleMap = documentElement.addElement("StyleMap");
        startStyleMap.addAttribute("id","msn_checkpoint_style0");
        Element startMapPair = startStyleMap.addElement("Pair");
        startMapPair.addElement("key").addText("normal");
        startMapPair.addElement("styleUrl").addText("#sn_checkpoint_style0");
        Element startMapPair2 = startStyleMap.addElement("Pair");
        startMapPair2.addElement("key").addText("highlight");
        startMapPair2.addElement("styleUrl").addText("#sh_checkpoint_style0");

        Element startStyle = documentElement.addElement("Style");
        startStyle.addAttribute("id","sh_checkpoint_style0");
        Element startIconStyle = startStyle.addElement("IconStyle");
        startIconStyle.addElement("scale").addText("1.4");
        startIconStyle.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/go.png");
        Element startHotSpotElement = startIconStyle.addElement("hotSpot");
        startHotSpotElement.addAttribute("x","0.5");
        startHotSpotElement.addAttribute("y","0");
        startHotSpotElement.addAttribute("xunits","fraction");
        startHotSpotElement.addAttribute("yunits","fraction");

        Element startStyle2 = documentElement.addElement("Style");
        startStyle2.addAttribute("id","sn_checkpoint_style0");
        Element startIconStyle2 = startStyle.addElement("IconStyle");
        startIconStyle2.addElement("scale").addText("1.2");
        startIconStyle2.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/go.png");
        Element startHotSpotElement2 = startIconStyle.addElement("hotSpot");
        startHotSpotElement2.addAttribute("x","0.5");
        startHotSpotElement2.addAttribute("y","0");
        startHotSpotElement2.addAttribute("xunits","fraction");
        startHotSpotElement2.addAttribute("yunits","fraction");


        //设置目的地点图标样式
        Element destStyleMap = documentElement.addElement("StyleMap");
        destStyleMap.addAttribute("id","msn_checkpoint_style1");
        Element desMapPair = destStyleMap.addElement("Pair");
        desMapPair.addElement("key").addText("normal");
        desMapPair.addElement("styleUrl").addText("#sn_checkpoint_style1");
        Element desMapPair2 = destStyleMap.addElement("Pair");
        desMapPair2.addElement("key").addText("highlight");
        desMapPair2.addElement("styleUrl").addText("#sh_checkpoint_style1");

        Element desStyle = documentElement.addElement("Style");
        desStyle.addAttribute("id","sh_checkpoint_style1");
        Element desIconStyle = desStyle.addElement("IconStyle");
        desIconStyle.addElement("scale").addText("1.4");
        desIconStyle.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/stop.png");
        Element desHotSpotElement = desIconStyle.addElement("hotSpot");
        desHotSpotElement.addAttribute("x","0.5");
        desHotSpotElement.addAttribute("y","0");
        desHotSpotElement.addAttribute("xunits","fraction");
        desHotSpotElement.addAttribute("yunits","fraction");

        Element desStyle2 = documentElement.addElement("Style");
        desStyle2.addAttribute("id","sn_checkpoint_style1");
        Element desIconStyle2 = desStyle2.addElement("IconStyle");
        desIconStyle2.addElement("scale").addText("1.2");
        desIconStyle2.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/stop.png");
        Element desHotSpotElement2 = desIconStyle2.addElement("hotSpot");
        desHotSpotElement2.addAttribute("x","0.5");
        desHotSpotElement2.addAttribute("y","0");
        desHotSpotElement2.addAttribute("xunits","fraction");
        desHotSpotElement2.addAttribute("yunits","fraction");



        //出发点
        Element startPlacemark = documentElement.addElement("Placemark");
        startPlacemark.addElement("name").addText("出发点");
        startPlacemark.addElement("description").addText("");
        startPlacemark.addElement("styleUrl").addText("#msn_checkpoint_style0");
        Element startPoint = startPlacemark.addElement("Point");
        startPoint.addElement("coordinates").addText(measureds.get(0).getLongitude()+","+measureds.get(0).getLatitude()+",0");

        //目的地
        Element desPlacemark = documentElement.addElement("Placemark");
        desPlacemark.addElement("name").addText("目的地");
        desPlacemark.addElement("description").addText("");
        desPlacemark.addElement("styleUrl").addText("#msn_checkpoint_style1");
        Element desPoint = desPlacemark.addElement("Point");
        desPoint.addElement("coordinates").addText(measureds.get(measureds.size()-1).getLongitude()+","+measureds.get(measureds.size()-1).getLatitude());


//        Element pointElement = lineElement.addElement("LineString");
//        pointElement.addElement("extrude").addText("0");
//        pointElement.addElement("tessellate").addText("1");
//        pointElement.addElement("altitudeMode").addText("clampedToGround");


        //生成显示风格
        Element styleElement = documentElement.addElement("Style");//Style节点
        styleElement.addAttribute("id", "MyStyle");
        // IconStyle 图标风格
        Element iconStyleElement = styleElement.addElement("IconStyle");
        iconStyleElement.addElement("scale").addText("1.1");
        Element iconElement = iconStyleElement.addElement("Icon");
        iconElement.addElement("href").addText("http://maps.google.com/mapfiles/kml/paddle/9.png");//在线图标
        Element hotSpotElement = iconStyleElement.addElement("hotSpot");
        hotSpotElement.addAttribute("x","0.5");
        hotSpotElement.addAttribute("y","0");
        hotSpotElement.addAttribute("xunits","fraction");
        hotSpotElement.addAttribute("yunits","fraction");
        // LabelStyle  标签风格
//        Element labelStyleElement = styleElement.addElement("LabelStyle");
//        labelStyleElement.addElement("color").addText("ff0000FF");
//        labelStyleElement.addElement("scale").addText("0.000000");
//         PolyStyle 图形风格
        Element polyStyleElement = styleElement.addElement("PolyStyle");
        polyStyleElement.addElement("color").addText("ff0000FF");
        polyStyleElement.addElement("outline").addText("0");

        // LineStyle  路径线风格

        Element styleElement2 = documentElement.addElement("Style");
        styleElement2.addAttribute("id","style10");
        Element lineStyleElement = styleElement2.addElement("LineStyle");
        lineStyleElement.addElement("color").addText("ff0000FF");
        lineStyleElement.addElement("width").addText("5");

        //生成轨迹线路径数据
        Element lineElement = documentElement.addElement("Placemark");//在文件夹外添加一个地标
        lineElement.addElement("name").addText("轨迹线");
        lineElement.addElement("description").addText("");
        lineElement.addElement("styleUrl").addText("#style10");
        Element pointElement = lineElement.addElement("LineString");
        pointElement.addElement("extrude").addText("0");
        pointElement.addElement("tessellate").addText("1");
        pointElement.addElement("altitudeMode").addText("clampedToGround");
        String linedata="";
        //每个坐标以及高度用换行符或空格分开
        for (int i=0;i<measureds.size();i++) {
            linedata =linedata+"\n"+ measureds.get(i).getLongitude()+","+measureds.get(i).getLatitude()+",0";
        }
        pointElement.addElement("coordinates").addText(linedata);

        //将文件写入到缓冲区然后导出
        fileName = UUID.randomUUID().toString().replace("-", "")+fileName+".kml";;
        fileName = new String(fileName.getBytes("GBK"), "iso8859-1");
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename="
                + fileName);// 指定下载的文件名
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        OutputStream output = response.getOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
        //创建kml到本地
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter xmlWriter = new XMLWriter(bufferedOutPut,format);

        xmlWriter.write(document);
        xmlWriter.close();

        try {
            bufferedOutPut.flush();
//            xmlWriter.write(bufferedOutPut);
            bufferedOutPut.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }

//        try{
//            OutputStream out = response.getOutputStream();
//            String filename =new String(fileName.getBytes("GBK"), "iso8859-1");
//            // inputStream：读文件，前提是这个文件必须存在，要不就会报错
//            response.addHeader("content-disposition", "attachment;filename="
//                    + java.net.URLEncoder.encode(filename+".kml", "utf-8"));
//            response.setContentType("application/x-cmx");
//            response.setHeader("Content-disposition", "attachment;filename="+filename+".kml");
//            InputStream is = new FileInputStream("D:/web/airportData/"+fileName+".kml");
//
//            byte[] b = new byte[4096];
//            int size = is.read(b);
//            while (size > 0) {
//                out.write(b, 0, size);
//                size = is.read(b);
//            }
//            out.close();
//            is.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

        //开始对文件进行压缩，一个kml文件其实是一个压缩文件，里面包含一个kml文件和一个png图标
//        String[] strs = new String[2];
//        strs[0]="D:/web/Travels/"+fileName+"_1.kml";
////        strs[1]="D:/web/Travels/images/img_mark.png";//这里写图片的路径  如果使用在线图标这段代码屏蔽
//        writeKml(strs,"D:/web/Travels/"+ fileName);//压缩

        return true;

    }


    /**
     * 批量文件导出
     * @param measureds
     * @param fileName
     * @param response
     * @param request
     */
    public static String bacthKml(List<SocketGPSDataPackage> measureds, String fileName,  HttpServletRequest request,HttpServletResponse response, String basePath) throws IOException{

        Element root = DocumentHelper.createElement("kml");
        Document document = DocumentHelper.createDocument(root);
        //根节点添加属性
        root.addAttribute("xmlns", "http://earth.google.com/kml/2.1");
//                .addAttribute("xmlns:gx", "http://www.google.com/kml/ext/2.2")
//                .addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
//                .addAttribute("xsi:schemaLocation",
//                        "http://www.opengis.net/kml/2.2 http://schemas.opengis.net/kml/2.2.0/ogckml22.xsd http://www.google.com/kml/ext/2.2 http://code.google.com/apis/kml/schema/kml22gx.xsd");
        Element documentElement = root.addElement("Document");
        documentElement.addElement("name").addText(fileName);
        documentElement.addElement("description").addText("");
//        documentElement.addElement("Snippet").addText("");
//        Element folderElement = documentElement.addElement("Folder");//添加一个目录
//        folderElement.addAttribute("id", "FeatureLayer0");
//        folderElement.addElement("name").addText("轨迹点位"); //名称
//        folderElement.addElement("Snippet").addText(""); //显示在Google Earth之中的对description的简短概要.
        //生成点位图标数据
//        for (int i=0;i<measureds.size();i++) {
//            Element placeMarkElement = folderElement.addElement("Placemark");//在文件夹中添加一个地标
//            placeMarkElement.addAttribute("id", String.valueOf(i));
//            placeMarkElement.addElement("name").addText("点位"+String.valueOf(i));
//            placeMarkElement.addElement("Snippet").addText("");
//            placeMarkElement.addElement("description").addText("");//简介
//            placeMarkElement.addElement("styleUrl").addText("#MyStyle");//风格
//            Element pointElement = placeMarkElement.addElement("Point");
//            pointElement.addElement("altitudeMode").addText("clampToGround");
//            //添加点位的经纬度坐标以及高度(显示时绘制高度m)
//            pointElement.addElement("coordinates").addText(measureds.get(i).getLongitude()+","+measureds.get(i).getLatitude()+",0");//可以是是任何几何形状的子元素，定义每一个点的经度、纬度和高度(按照严格的顺序). 多个点使用空格隔开，经纬度按照WGS84标准.
//        }

        //设置出发点图标样式
        Element startStyleMap = documentElement.addElement("StyleMap");
        startStyleMap.addAttribute("id","msn_checkpoint_style0");
        Element startMapPair = startStyleMap.addElement("Pair");
        startMapPair.addElement("key").addText("normal");
        startMapPair.addElement("styleUrl").addText("#sn_checkpoint_style0");
        Element startMapPair2 = startStyleMap.addElement("Pair");
        startMapPair2.addElement("key").addText("highlight");
        startMapPair2.addElement("styleUrl").addText("#sh_checkpoint_style0");

        Element startStyle = documentElement.addElement("Style");
        startStyle.addAttribute("id","sh_checkpoint_style0");
        Element startIconStyle = startStyle.addElement("IconStyle");
        startIconStyle.addElement("scale").addText("1.4");
        startIconStyle.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/go.png");
        Element startHotSpotElement = startIconStyle.addElement("hotSpot");
        startHotSpotElement.addAttribute("x","0.5");
        startHotSpotElement.addAttribute("y","0");
        startHotSpotElement.addAttribute("xunits","fraction");
        startHotSpotElement.addAttribute("yunits","fraction");

        Element startStyle2 = documentElement.addElement("Style");
        startStyle2.addAttribute("id","sn_checkpoint_style0");
        Element startIconStyle2 = startStyle.addElement("IconStyle");
        startIconStyle2.addElement("scale").addText("1.2");
        startIconStyle2.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/go.png");
        Element startHotSpotElement2 = startIconStyle.addElement("hotSpot");
        startHotSpotElement2.addAttribute("x","0.5");
        startHotSpotElement2.addAttribute("y","0");
        startHotSpotElement2.addAttribute("xunits","fraction");
        startHotSpotElement2.addAttribute("yunits","fraction");


        //设置目的地点图标样式
        Element destStyleMap = documentElement.addElement("StyleMap");
        destStyleMap.addAttribute("id","msn_checkpoint_style1");
        Element desMapPair = destStyleMap.addElement("Pair");
        desMapPair.addElement("key").addText("normal");
        desMapPair.addElement("styleUrl").addText("#sn_checkpoint_style1");
        Element desMapPair2 = destStyleMap.addElement("Pair");
        desMapPair2.addElement("key").addText("highlight");
        desMapPair2.addElement("styleUrl").addText("#sh_checkpoint_style1");

        Element desStyle = documentElement.addElement("Style");
        desStyle.addAttribute("id","sh_checkpoint_style1");
        Element desIconStyle = desStyle.addElement("IconStyle");
        desIconStyle.addElement("scale").addText("1.4");
        desIconStyle.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/stop.png");
        Element desHotSpotElement = desIconStyle.addElement("hotSpot");
        desHotSpotElement.addAttribute("x","0.5");
        desHotSpotElement.addAttribute("y","0");
        desHotSpotElement.addAttribute("xunits","fraction");
        desHotSpotElement.addAttribute("yunits","fraction");

        Element desStyle2 = documentElement.addElement("Style");
        desStyle2.addAttribute("id","sn_checkpoint_style1");
        Element desIconStyle2 = desStyle2.addElement("IconStyle");
        desIconStyle2.addElement("scale").addText("1.2");
        desIconStyle2.addElement("Icon").addElement("href").addText("http://maps.gstatic.com/intl/zh-CN_ALL/mapfiles/kml/paddle/stop.png");
        Element desHotSpotElement2 = desIconStyle2.addElement("hotSpot");
        desHotSpotElement2.addAttribute("x","0.5");
        desHotSpotElement2.addAttribute("y","0");
        desHotSpotElement2.addAttribute("xunits","fraction");
        desHotSpotElement2.addAttribute("yunits","fraction");



        //出发点
        Element startPlacemark = documentElement.addElement("Placemark");
        startPlacemark.addElement("name").addText("出发点");
        startPlacemark.addElement("description").addText("");
        startPlacemark.addElement("styleUrl").addText("#msn_checkpoint_style0");
        Element startPoint = startPlacemark.addElement("Point");
        startPoint.addElement("coordinates").addText(measureds.get(0).getLongitude()+","+measureds.get(0).getLatitude()+",0");

        //目的地
        Element desPlacemark = documentElement.addElement("Placemark");
        desPlacemark.addElement("name").addText("目的地");
        desPlacemark.addElement("description").addText("");
        desPlacemark.addElement("styleUrl").addText("#msn_checkpoint_style1");
        Element desPoint = desPlacemark.addElement("Point");
        desPoint.addElement("coordinates").addText(measureds.get(measureds.size()-1).getLongitude()+","+measureds.get(measureds.size()-1).getLatitude());


//        Element pointElement = lineElement.addElement("LineString");
//        pointElement.addElement("extrude").addText("0");
//        pointElement.addElement("tessellate").addText("1");
//        pointElement.addElement("altitudeMode").addText("clampedToGround");


        //生成显示风格
        Element styleElement = documentElement.addElement("Style");//Style节点
        styleElement.addAttribute("id", "MyStyle");
        // IconStyle 图标风格
        Element iconStyleElement = styleElement.addElement("IconStyle");
        iconStyleElement.addElement("scale").addText("1.1");
        Element iconElement = iconStyleElement.addElement("Icon");
        iconElement.addElement("href").addText("http://maps.google.com/mapfiles/kml/paddle/9.png");//在线图标
        Element hotSpotElement = iconStyleElement.addElement("hotSpot");
        hotSpotElement.addAttribute("x","0.5");
        hotSpotElement.addAttribute("y","0");
        hotSpotElement.addAttribute("xunits","fraction");
        hotSpotElement.addAttribute("yunits","fraction");
        // LabelStyle  标签风格
//        Element labelStyleElement = styleElement.addElement("LabelStyle");
//        labelStyleElement.addElement("color").addText("ff0000FF");
//        labelStyleElement.addElement("scale").addText("0.000000");
//         PolyStyle 图形风格
        Element polyStyleElement = styleElement.addElement("PolyStyle");
        polyStyleElement.addElement("color").addText("ff0000FF");
        polyStyleElement.addElement("outline").addText("0");

        // LineStyle  路径线风格

        Element styleElement2 = documentElement.addElement("Style");
        styleElement2.addAttribute("id","style10");
        Element lineStyleElement = styleElement2.addElement("LineStyle");
        lineStyleElement.addElement("color").addText("ff0000FF");
        lineStyleElement.addElement("width").addText("5");

        //生成轨迹线路径数据
        Element lineElement = documentElement.addElement("Placemark");//在文件夹外添加一个地标
        lineElement.addElement("name").addText("轨迹线");
        lineElement.addElement("description").addText("");
        lineElement.addElement("styleUrl").addText("#style10");
        Element pointElement = lineElement.addElement("LineString");
        pointElement.addElement("extrude").addText("0");
        pointElement.addElement("tessellate").addText("1");
        pointElement.addElement("altitudeMode").addText("clampedToGround");
        String linedata="";
//        //每个坐标以及高度用换行符或空格分开
        for (int i=0;i<measureds.size();i++) {
            linedata =linedata+"\n"+ measureds.get(i).getLongitude()+","+measureds.get(i).getLatitude()+",0";
        }
        pointElement.addElement("coordinates").addText(linedata);



        String newfilePathOnly = basePath + "/webKml/";
        //判断地址是否存在
        File fileUIS = new File(newfilePathOnly);
        //如果不存在文件夹创建文件夹
        if (!fileUIS.exists()) {
            fileUIS.mkdirs();
        }
        //创建kml到本地
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        fileName = UUID.randomUUID().toString().replace("-", "")+fileName;
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(newfilePathOnly+fileName+".kml"),format);
        xmlWriter.write(document);
        xmlWriter.close();

        return newfilePathOnly+fileName+".kml";
    }




    public static void writeKml(String[] strs, String kmlName) throws IOException
    {
         String[] files = strs;
         //File kmlFile = new File("person.kml");
        OutputStream os = new BufferedOutputStream( new FileOutputStream(kmlName + ".kml"));
         ZipOutputStream zos = new ZipOutputStream(os);
        byte[] buf = new byte[8192];
        int len;
        for (int i=0;i < files.length;i++) {  
            File file = new File(files[i]);
            if ( !file.isFile() )
                continue;
            ZipEntry ze = new ZipEntry( file.getName() );
            zos.putNextEntry( ze );
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
            while ( ( len = bis.read( buf ) ) > 0 ) {
                zos.write( buf, 0, len );
            }
            zos.closeEntry();
        }

        zos.closeEntry();

        zos.close();

    }

}
