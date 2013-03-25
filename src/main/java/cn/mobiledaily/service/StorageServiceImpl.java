package cn.mobiledaily.service;

import cn.mobiledaily.config.AppConfig;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import net.coobird.thumbnailator.Thumbnails;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service(value = "storageService")
public class StorageServiceImpl implements StorageService {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public List<String> getFileNames() {
        List<String> result = new ArrayList<>();
        for (GridFSDBFile file : gridFsTemplate.find(null)) {
//            if (file.getFilename().startsWith("T"))
                result.add(file.getFilename());
        }
        return result;
    }

    @Override
    public List<GridFSDBFile> getFiles() {
        List<GridFSDBFile> result = new ArrayList<>();
        for (GridFSDBFile file : gridFsTemplate.find(null)) {
            result.add(file);
        }
        return result;
    }

    @Override
    public String save(InputStream inputStream, String filename, String contentType) {
        BufferedInputStream buffer = new BufferedInputStream(inputStream);
        buffer.mark(Integer.MAX_VALUE);
        try {
            File f = new File(filename);
            Thumbnails.of(buffer).size(200, 200).toFile(f);
            gridFsTemplate.store(new FileInputStream(f), "T" + filename, contentType);
            buffer.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gridFsTemplate.store(buffer, filename, contentType).getFilename();
    }

    @Override
    public String save(InputStream inputStream, String filename, String contentType, DBObject metaData) {
        return gridFsTemplate.store(inputStream, filename, contentType, metaData).getFilename();
    }

    @Override
    public GridFSDBFile get(String id) {
        return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))));
    }

    @Override
    public GridFSDBFile getByFilename(String filename) {
        return gridFsTemplate.findOne(new Query(Criteria.where("filename").is(filename)));
    }

    public void delete(String filename) {
        gridFsTemplate.delete(new Query(Criteria.where("filename").is(filename)));
    }

    @Override
    public void clean() {
        gridFsTemplate.delete(null);
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        StorageService service = ctx.getBean("storageService", StorageService.class);
        service.clean();
//        for (int i = 0; i < 20; i++) {
//            InputStream is = new FileInputStream(new File("/Users/Johnson/Downloads/card.jpeg"));
//
//            service.save(is, "3101091878102100" + i + ".jpeg", ".jpeg");
//            is.close();
//        }
    }
}
