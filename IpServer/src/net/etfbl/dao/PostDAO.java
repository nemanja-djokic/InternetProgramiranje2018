package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.Post;

public interface PostDAO {

	public ArrayList<Post> getAllPosts();
	public boolean addPost(Post post);
	public ArrayList<Post> getFeedForUser(String idUser);
	public boolean giveRating(int idPost, String idUser, boolean isLike);
	
}
