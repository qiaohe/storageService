package cn.mobiledaily.web.controller;

import cn.mobiledaily.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Johnson
 * Date: 3/22/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public void getById (@PathVariable(value="id") String id, HttpServletResponse response) throws IOException {

    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public ResponseEntity<String> store (@RequestParam MultipartFile file, WebRequest webRequest) {
        try {
            String storedId = storageService.save(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            String storedURL = "/storage/id/" + storedId;
            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.setLocation(file.getName());
            return new ResponseEntity<String>(storedURL, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
