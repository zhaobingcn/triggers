package com.neo4j.autoindex;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.event.TransactionData;

import java.util.List;

public class AddIndexRunnable implements Runnable {

    private static TransactionData td;
    private static GraphDatabaseService db;

    public AddIndexRunnable(TransactionData transactionData, GraphDatabaseService graphDatabaseService) {
        td = transactionData;
        db = graphDatabaseService;
    }

    @Override
    public void run() {
        try (Transaction tx = db.beginTx()) {
            List<IndexInfo> indexInfoList = new IndexInfo().generateIndexInfo();
            for (Node node : td.createdNodes()) {
//                if (node.hasLabel(Labels.Suspect)) {
//                    suspects.add(node);
//                GmailSender.sendEmail("maxdemarzi@gmail.com", "A new Suspect has been created in the System!", "boo-yeah");

                for(IndexInfo indexInfo: indexInfoList){
                    for(Label label: node.getLabels()){
                        if(indexInfo.containsIndexName(label.toString())){

                        }
                    }
                }

                tx.acquireWriteLock(node);

                node.setProperty("age", "30");
                System.out.println("has add property");
//                System.out.println("A new Suspect has been created!");
//                }
            }
            tx.success();
            
        }
    }
}
