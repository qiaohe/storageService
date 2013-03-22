package cn.mobiledaily.service;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
        return gridFsTemplate.store(inputStream, contentType, filename).getFilename();
    }

    @Override
    public String save(InputStream inputStream, String filename, String contentType, DBObject metaData) {
        return gridFsTemplate.store(inputStream, filename, contentType, metaData).getFilename();
    }

    @Override
    public GridFSDBFile get(String id) {
        return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))));
    }

    public void delete(String filename) {
        gridFsTemplate.delete(null);
        gridFsTemplate.delete(new Query(Criteria.where("filename").is(filename)));
    }

    @Override
    public void clean() {
        gridFsTemplate.delete(null);
    }
}
