package com.neo4j.autoindex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        try{
            fileReader = new FileReader("index.properties");
            bufferedReader = new BufferedReader(fileReader);
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
