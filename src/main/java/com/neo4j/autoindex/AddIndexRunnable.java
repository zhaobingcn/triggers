package com.neo4j.autoindex;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.index.Index;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.neo4j.graphdb.index.IndexManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.neo4j.helpers.collection.MapUtil.stringMap;

public class AddIndexRunnable implements Runnable {

    private static TransactionData td;
    private static GraphDatabaseService db;
    private static final Map<String,String> STANDARD_ANALYZER =
            stringMap( IndexManager.PROVIDER, "lucene", "type", "fulltext", "analyzer", "org.apache.lucene.analysis.standard.StandardAnalyzer");


    public AddIndexRunnable(TransactionData transactionData, GraphDatabaseService graphDatabaseService) {
        td = transactionData;
        db = graphDatabaseService;
    }

    @Override
    public void run() {
        try (Transaction tx = db.beginTx()) {
            List<IndexInfo> indexInfoList = new IndexInfo().generateIndexInfo();
            for (Node node : td.createdNodes()) {

                IndexInfo infos = toBeIndexInfo(indexInfoList, node.getLabels());

                Set<Map.Entry<String, Object>> properties =
                        node.getProperties(infos.getPropKeys()).entrySet();

                for (String indexName : infos.getIndexNames()) {
                    Index<Node> index = db.index().forNodes(indexName, STANDARD_ANALYZER);

                    index.remove(node);

                    // And then index all the properties
                    for (Map.Entry<String, Object> property : properties) {
                        index.add(node, property.getKey(), property.getValue());
                    }
                }

//                tx.acquireWriteLock(node);
//
//                node.setProperty("age", "30");
                System.out.println("has add property");
                tx.success();

            }
        }
    }

    public IndexInfo toBeIndexInfo(List<IndexInfo> indexInfos, Iterable<Label> labels){
        for(IndexInfo indexInfo: indexInfos){
            for(Label label: labels){
                if(indexInfo.containsIndexName(label.toString())){
                    return indexInfo;
                }
            }
        }
        return null;
    }
}
