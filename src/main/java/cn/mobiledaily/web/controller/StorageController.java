package cn.mobiledaily.web.controller;

import cn.mobiledaily.service.StorageService;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAllFiles() {
        return storageService.getFileNames();
    }


    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public void getById(@PathVariable(value = "id") String id, HttpServletResponse response) throws IOException {
        writeResponse(storageService.get(id), response);
    }

    @RequestMapping(value = "/{filename:.*}", method = RequestMethod.GET)
    public void getByFilename(@PathVariable(value = "filename") String filename, HttpServletResponse response) throws IOException {
        writeResponse(storageService.getByFilename(filename), response);
    }

    private void writeResponse(GridFSDBFile file, HttpServletResponse response) throws IOException {
        if (file != null) {
            byte[] data = IOUtils.toByteArray(file.getInputStream());
            response.setContentType(file.getContentType());
            response.setContentLength((int) file.getLength());
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

//    @RequestMapping(value = "/store", method = RequestMethod.POST)
//    public ResponseEntity<String> store(@RequestParam MultipartFile file) {
//        try {
//            String storedId = storageService.save(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
//            String storedURL = "/storage/id/" + storedId;
//            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.setLocation(new URI(storedURL));
//            return new ResponseEntity<String>(storedURL, responseHeaders, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String store(@RequestParam MultipartFile file) {
        try {
            String storedId = storageService.save(file.getInputStream(),file.getOriginalFilename(), file.getContentType());
        } catch (Exception e) {
//            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return "main";
    }
}

