package common.file;

import com.alibaba.fastjson.JSON;
import common.log.CLogger;
import common.qconfig.QConfigHelper;
import javafx.util.Pair;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public  class ImageUpload {

    /**
     * 上传图片
     * @return
     */
    public static ImageRespose upload(MultipartFile file){
        ImageRespose imageRespose =new  ImageRespose();
        imageRespose.setSuccess(false);
        if(file.isEmpty()){
            imageRespose.setMessage("文件不可为空");
            return imageRespose;
        }

        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        if (size>5242880){
            imageRespose.setMessage("文件大小不可超过2M");
            return imageRespose;
        }

        //文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);

        if (!prefix.trim().toLowerCase().contains("doc")
                &&!prefix.trim().toLowerCase().contains("docx")
                &&!prefix.trim().toLowerCase().contains("pdf")
                &&!prefix.trim().toLowerCase().contains("txt")
                &&!prefix.trim().toLowerCase().contains("png")
                &&!prefix.trim().toLowerCase().contains("jpg")
                &&!prefix.trim().toLowerCase().contains("jpeg")) {
            imageRespose.setMessage("不支持上传文件格式");
            return imageRespose;
        }

        //配置中心读取url
        String url= QConfigHelper.getAppSetting("imageUpload");
        String image=inputImage(file);
        String  contents=String.format("{\"base64Str\":\"data:image/jpeg;base64,%s\"}",image);
        try {
            //调用公共上传接口
            String respose=  doPost(url,contents);
            imageRespose=  JSON.parseObject(respose,ImageRespose.class);
            if (imageRespose!=null&&imageRespose.getUrl()!=null){
                imageRespose.setSuccess(true);
                imageRespose.setMessage("上传成功");
                return imageRespose;
            }
        } catch (IllegalStateException e) {
            CLogger.error("fileUpload",e);
        }

        assert imageRespose != null;
        imageRespose.setMessage("上传失败");
        return imageRespose;

    }

    /**
     * file转成base64 二进制
     */
    private static  String  inputImage(MultipartFile file) {
        try {
            FileInputStream in = (FileInputStream) file.getInputStream();
            BufferedImage srcImage = javax.imageio.ImageIO.read(in);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(srcImage, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return Base64.encodeBase64String(bytes);

        } catch (IOException e) {
            System.out.println("读取图片文件出错！" + e.getMessage());
        }
        return null;
    }


    /**
     * 框架组提供的接口比较特殊，所以单独写了一个post请求
     * post 请求
     */
    private static String doPost(String url,  String contents) {
        CloseableHttpClient httpClient;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json");

        try {
            // 为httpPost设置封装好的请求参数
            httpPost.setEntity(new StringEntity(contents, "UTF-8"));
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            CLogger.error("HttpClientUtils",e);
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    CLogger.error("HttpClientUtils",e);
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    CLogger.error("HttpClientUtils",e);
                }
            }
        }
        return result;
    }
}
