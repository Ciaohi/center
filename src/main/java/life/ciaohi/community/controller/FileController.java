package life.ciaohi.community.controller;

import life.ciaohi.community.dto.FileDTO;
import life.ciaohi.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private UCloudProvider uCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest) request;
        MultipartFile file=multiRequest.getFile("editormd-image-file");
        try {
            String filename=uCloudProvider.upload(file.getInputStream(),file.getContentType(),file.getOriginalFilename());
            FileDTO fileDTO=new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(filename);
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileDTO fileDTO=new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/wechat.jpg");
        return fileDTO;
    }

}
