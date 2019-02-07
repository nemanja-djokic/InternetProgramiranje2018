package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.BlogPost;
import net.etfbl.dao.beans.BlogPostComment;

public interface BlogPostDAO {

	public ArrayList<BlogPost> getAll();
	public boolean addPost(BlogPost blogPost);
	public boolean addComment(BlogPostComment comment);
	
}
