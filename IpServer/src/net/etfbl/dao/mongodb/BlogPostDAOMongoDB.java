package net.etfbl.dao.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.etfbl.dao.BlogPostDAO;
import net.etfbl.dao.beans.BlogPost;
import net.etfbl.dao.beans.BlogPostComment;
import net.etfbl.util.MongoDBUtil;

public class BlogPostDAOMongoDB implements BlogPostDAO {

	@Override
	public ArrayList<BlogPost> getAll() {
		ArrayList<BlogPost> toReturn = new ArrayList<>();
		try {
			MongoDatabase database = MongoDBUtil.getDatabase();
			MongoCollection<Document> blogPosts = database.getCollection("BlogPost");
			MongoCollection<Document> blogPostComments = database.getCollection("BlogPostComment");
			FindIterable<Document> blogPostCollection = blogPosts.find();
			FindIterable<Document> blogPostCommentCollection = blogPostComments.find();
			for(Document blogPost : blogPostCollection) {
				BlogPost toAdd = new BlogPost();
				String id = blogPost.getString("id");
				long timestamp = blogPost.getLong("timestamp");
				toAdd.setId(id);
				toAdd.setTimestamp(blogPost.getLong("timestamp"));
				toAdd.setAuthor(blogPost.getString("author"));
				toAdd.setContent(blogPost.getString("content"));
				ArrayList<BlogPostComment> commentsToAdd = new ArrayList<>();
				for(Document blogPostComment : blogPostCommentCollection) {
					String parentId = blogPostComment.getString("parentId");
					long parentTimestamp = blogPostComment.getLong("parentTimestamp");
					if(parentId.equals(id) && timestamp == parentTimestamp) {
						BlogPostComment commentToAdd = new BlogPostComment();
						commentToAdd.setParentId(parentId);
						commentToAdd.setTimestamp(blogPostComment.getLong("timestamp"));
						commentToAdd.setAuthor(blogPostComment.getString("author"));
						commentToAdd.setAuthorId(blogPostComment.getString("authorId"));
						commentToAdd.setContent(blogPostComment.getString("content"));
						commentToAdd.setParentTimestamp(parentTimestamp);
						commentsToAdd.add(commentToAdd);
					}
				}
				toAdd.setComments(commentsToAdd);
				toReturn.add(toAdd);
			}
			return toReturn;
		}catch(Exception ex) {
			ex.printStackTrace();
			return toReturn;
		}
	}

	@Override
	public boolean addPost(BlogPost blogPost) {
		try {
			MongoDatabase database = MongoDBUtil.getDatabase();
			MongoCollection<Document> blogPosts = database.getCollection("BlogPost");
			MongoCollection<Document> blogPostComments = database.getCollection("BlogPostComment");
			blogPosts.insertOne(blogPost.toDocument());
			for(BlogPostComment comment : blogPost.getComments()) {
				blogPostComments.insertOne(comment.toDocument());
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addComment(BlogPostComment comment) {
		try {
			MongoDatabase database = MongoDBUtil.getDatabase();
			MongoCollection<Document> blogPostComments = database.getCollection("BlogPostComment");
			blogPostComments.insertOne(comment.toDocument());
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	

}
