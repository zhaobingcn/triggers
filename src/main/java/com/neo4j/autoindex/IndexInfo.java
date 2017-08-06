package com.neo4j.autoindex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhzy on 2017/8/6.
 */
public class IndexInfo {

    private String[] indexNames;
    private String[] propKeys;

    public IndexInfo(String[] indexNames, String[] propKeys) {
        this.indexNames = indexNames;
        this.propKeys = propKeys;
    }
    public IndexInfo(){}

    public String[] getIndexNames() {
        return indexNames;
    }

    public void setIndexNames(String[] indexNames) {
        this.indexNames = indexNames;
    }

    public String[] getPropKeys() {
        return propKeys;
    }

    public void setPropKeys(String[] propKeys) {
        this.propKeys = propKeys;
    }

    public List<IndexInfo> generateIndexInfo(){
        FileReader fileReader;
        BufferedReader bufferedReader;
        List<IndexInfo> indexInfoList = new ArrayList<>();
//        ClassLoader classLoader = getClass().getClassLoader();
        try{
//            fileReader = new FileReader(classLoader.getResource("index.properties").getFile());
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("index.properties");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String[] indexAndProp = line.split(":");
                String[] indexNames = indexAndProp[0].split(",");
                String[] propKeys = indexAndProp[0].split(",");
                indexInfoList.add(new IndexInfo(indexNames, propKeys));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("找不到索引配置文件");
        }
        return indexInfoList;
    }

    public boolean containsIndexName(String label){
        for(String indexname: indexNames){
            if(indexname.equals(label)){
                return true;
            }
        }
        return false;
    }
}
