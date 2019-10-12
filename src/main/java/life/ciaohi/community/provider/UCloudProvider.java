package life.ciaohi.community.provider;



import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import life.ciaohi.community.exceptin.CustomizeErrorCode;
import life.ciaohi.community.exceptin.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;
    private String bucketName="keeplearning";


    public String upload(InputStream fileStream,String mimeType,String fileName){
        String generateFileName;
        String[] filePaths=fileName.split("\\.");
        if(filePaths.length>1){
            generateFileName= UUID.randomUUID().toString()+"."+filePaths[filePaths.length-1];
        }else{
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
            ObjectConfig config = new ObjectConfig("cn-sh2", "ufileos.com");
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                .putObject(fileStream, mimeType)
                .nameAs(generateFileName)
                .toBucket(bucketName)
                .setOnProgressListener((bytesWritten,contentLength)->{
                    })
                .execute();
            if(response!=null &&response.getRetCode()==0){
                    String url = UfileClient.object(objectAuthorization, config)
                            .getDownloadUrlFromPrivateBucket(generateFileName, bucketName, 24*60*60*365*10)
                            .createUrl();
                    return url;
                }else{
                    throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
                }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }

}
