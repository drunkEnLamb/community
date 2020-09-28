package life.beyond.community.provider;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.ProcessObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import life.beyond.community.exception.CustomizeErrorCode;
import life.beyond.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class OSSProvider {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accesskeyid}")
    private String accessKeyId;

    @Value("${aliyun.oss.accesskeysecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketname}")
    private  String bucketName;

    public String upload(InputStream inputStream,String fileName){
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        String generatedFileName = null;
        try {
            generatedFileName = null;
            String[] filePaths = fileName.split("\\.");
            if(filePaths.length > 1){
                generatedFileName = UUID.randomUUID().toString() + "." +filePaths[filePaths.length-1];
            }else {
                return null;
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,generatedFileName,inputStream);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            putObjectRequest.setMetadata(metadata);
            ossClient.putObject(putObjectRequest);
            //获取图片url
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 *365 *10);
            URL url = ossClient.generatePresignedUrl(bucketName, generatedFileName, expiration);
            return url.toString();
        } catch (OSSException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } finally {
            ossClient.shutdown();
        }
    }


    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }
}
