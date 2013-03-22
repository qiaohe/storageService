package cn.mobiledaily.service;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.BSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Johnson
 * Date: 3/21/13
 * Time: 11:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StorageService {
    List<String> getFileNames();

    List<GridFSDBFile> getFiles();

    String save(InputStream inputStream, String filename, String contentType);

    String save(InputStream inputStream, String filename, String contentType, DBObject metaData);

    GridFSDBFile get(String id);

    void delete(String filename);

    void clean();

}
