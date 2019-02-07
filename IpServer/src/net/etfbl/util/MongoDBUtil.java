package net.etfbl.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {

	private static MongoClient mongoClient;
	
	public static MongoClient getClient() {
		if(mongoClient == null) {
			mongoClient = new MongoClient();
		}
		return mongoClient;
	}
	
	public static MongoDatabase getDatabase() {
		if(mongoClient == null) {
			mongoClient = new MongoClient();
		}
		return mongoClient.getDatabase("BlogPosts");
	}
	
}
