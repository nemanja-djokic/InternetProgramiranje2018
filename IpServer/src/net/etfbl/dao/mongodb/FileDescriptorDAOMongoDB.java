package net.etfbl.dao.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.etfbl.dao.FileDescriptorDAO;
import net.etfbl.dao.beans.FileDescriptor;
import net.etfbl.util.MongoDBUtil;

public class FileDescriptorDAOMongoDB implements FileDescriptorDAO {

	@Override
	public ArrayList<FileDescriptor> getAll() {
		ArrayList<FileDescriptor> toReturn = new ArrayList<>();
		try {
			MongoDatabase database = MongoDBUtil.getDatabase();
			MongoCollection<Document> fileDescriptors = database.getCollection("FileDescriptor");
			FindIterable<Document> fileDescriptorsCollection = fileDescriptors.find();
			for(Document fileDescriptor : fileDescriptorsCollection) {
				FileDescriptor toAdd = new FileDescriptor();
				toAdd.setUserId(fileDescriptor.getString("userId"));
				toAdd.setFileName(fileDescriptor.getString("fileName"));
				toAdd.setDescription(fileDescriptor.getString("description"));
				toAdd.setTimestamp(fileDescriptor.getLong("timestamp"));
				toAdd.setAuthor(fileDescriptor.getString("author"));
				toReturn.add(toAdd);
			}
			return toReturn;
		}catch(Exception ex) {
			ex.printStackTrace();
			return toReturn;
		}
	}

	@Override
	public boolean addFile(FileDescriptor fileDescriptor) {
		try {
			MongoDatabase database = MongoDBUtil.getDatabase();
			MongoCollection<Document> blogPostComments = database.getCollection("FileDescriptor");
			blogPostComments.insertOne(fileDescriptor.toDocument());
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
