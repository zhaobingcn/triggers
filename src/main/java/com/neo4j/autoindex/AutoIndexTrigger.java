package com.neo4j.autoindex;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;

import java.util.concurrent.ExecutorService;

public class AutoIndexTrigger implements TransactionEventHandler {

    public static GraphDatabaseService db;
    private static ExecutorService ex;

    public AutoIndexTrigger(GraphDatabaseService graphDatabaseService, ExecutorService executor) {
        db = graphDatabaseService;
        ex = executor;
    }

    @Override
    public Object beforeCommit(TransactionData transactionData) throws Exception {
        return null;
    }

    @Override
    public void afterCommit(TransactionData transactionData, Object o) {
        ex.submit(new AddIndexRunnable(transactionData, db));
    }

    @Override
    public void afterRollback(TransactionData transactionData, Object o) {

    }
}
