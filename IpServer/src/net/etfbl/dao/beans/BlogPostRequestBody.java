package net.etfbl.dao.beans;

import javax.xml.bind.annotation.XmlElement;

public class BlogPostRequestBody {

	public BlogPostRequestBody() {
		this.id = "";
		this.author = "";
		this.content = "";
	}
	
	@XmlElement public String id;
	@XmlElement public String author;
	@XmlElement public String content;
	
}
