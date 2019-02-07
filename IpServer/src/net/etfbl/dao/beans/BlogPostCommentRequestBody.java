package net.etfbl.dao.beans;

public class BlogPostCommentRequestBody {

	public BlogPostCommentRequestBody() {
		this.parentId = "";
		this.parentTimestamp = 0;
		this.authorId = "";
		this.author = "";
		this.content = "";
	}
	
	public String parentId;
	public long parentTimestamp;
	public String authorId;
	public String author;
	public String content;
	
}
