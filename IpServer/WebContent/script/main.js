var myData = null;
var lastTimestamp = null;
var fifthTimestamp = null;

function fillBlogsArea(data){
	var blogsArea = document.getElementById("blogs_content_container");
	blogsArea.innerHTML = "";
	data.sort(function(a, b){
		return b.timestamp - a.timestamp;
	});
	for(i = 0 ; i < data.length ; i++){
		(function(i){
			var mainDiv = document.createElement("div");
			mainDiv.classList.add("post_container");
			var singleBlogDiv = document.createElement("div");
			singleBlogDiv.classList.add("post_container");
			singleBlogDiv.classList.add("container-fluid");
			var nameContainer = document.createElement("div");
			nameContainer.classList.add("row");
			nameContainer.classList.add("name_container");
			var profileImg = document.createElement("img");
			profileImg.src = "/images/" + data[i]["id"];
			profileImg.alt = "/images/" + data[i]["id"];
			profileImg.classList.add("rounded_image");
			profileImg.style.width = "40px";
			profileImg.style.height = "40px";
			profileImg.classList.add("pointer_image");
			profileImg.addEventListener("click", function() {
				window.location.href="/?action=view_profile&viewId=" + data[i]["id"];
			});
			nameContainer.appendChild(profileImg);
			var name = document.createElement("div");
			name.textContent = data[i]["author"];
			nameContainer.append(name);
			singleBlogDiv.append(nameContainer);
			var contentDiv = document.createElement("div");
			contentDiv.textContent = data[i]["content"];
			singleBlogDiv.append(contentDiv);
			var timestampDiv = document.createElement("div");
			timestampDiv.classList.add("small_text");
			timestampDiv.textContent = timeConverter(data[i]["timestamp"]);
			singleBlogDiv.append(timestampDiv);
			var commentArea = document.createElement("div");
			commentArea.classList.add("comment_area");
			var comments = data[i]["comments"];
			for(j = 0; j < data[i]["comments"].length; j++){
				(function(j){
					var commentDiv = document.createElement("div");
					var commentImage = document.createElement("img");
					commentImage.src = "/images/" + comments[j]["authorId"];
					commentImage.alt = "/images/" + comments[j]["authorId"];
					commentImage.classList.add("rounded_image");
					commentImage.style.width = "30px";
					commentImage.style.height = "30px";
					commentImage.classList.add("pointer_image");
					commentImage.addEventListener("click", function() {
						window.location.href="/?action=view_profile&viewId=" + comments[j]["authorId"];
					});
					var commentNameDiv = document.createElement("div");
					commentNameDiv.classList.add("row");
					commentNameDiv.classList.add("name_container");
					var commentName = document.createElement("div");
					commentName.textContent = comments[j]["author"];
					commentNameDiv.appendChild(commentImage);
					commentNameDiv.appendChild(commentName);
					commentDiv.appendChild(commentNameDiv);
					var commentContentDiv = document.createElement("div");
					commentContentDiv.textContent = comments[j]["content"];
					commentDiv.appendChild(commentContentDiv);
					var commentTimestampDiv = document.createElement("div");
					commentTimestampDiv.classList.add("small_text");
					commentTimestampDiv.textContent = timeConverter(comments[j]["timestamp"]);
					commentDiv.appendChild(commentTimestampDiv);
					commentArea.appendChild(commentDiv);
				}).call(this, j);
			}
			singleBlogDiv.appendChild(commentArea);
			var commentInput = document.createElement("input");
			commentInput.type = "text";
			commentInput.id = "commentInput" + data[i]["id"] + "#" + data[i]["timestamp"];
			commentInput.classList.add("blog_input");
			commentInput.idParam = data[i]["id"];
			commentInput.timestampParam = data[i]["timestamp"];
			commentInput.myParam = commentInput;
			commentInput.addEventListener("keyup", function(event){
				if(event.keyCode == 13){
					sendComment(event.target.idParam, event.target.timestampParam, event.target.myParam.value, event.target.myParam);
				}
			}, false);
			singleBlogDiv.appendChild(commentInput);
			blogsArea.appendChild(singleBlogDiv);
		}).call(this, i);
	}
}

function sendComment(id, timestamp, content, myParam){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/addcomment";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"parentId" : id,
			"parentTimestamp" : timestamp,
			"content" : content,
			"author" : myData["firstName"] + " " + myData["lastName"],
			"authorId" : myData["idUser"]
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			myParam.value = "";
			updateBlogs();
		}else{
			return;
		}
	}
}

function updateBlogs(){
	var baseUrl = "/api/rest/blogposts/";
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){
			fillBlogsArea(data);
         }
	});
}

function postBlog(){
	var blogInput = document.getElementById("blog_input");
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/addblogpost";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"id" : myData["idUser"],
			"author" : myData["firstName"] + " " + myData["lastName"],
			"content" : blogInput.value
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			blogInput.value = "";
			updateBlogs();
		}else{
			return;
		}
	}
}

function fillConnectedUsersTable(data){
	table = document.getElementById("connected_users_table");
	table.innerHTML = "";
	for(i = 0; i < data.length; i++){
		(function(i){
			var tr = document.createElement("TR");
			var td1 = document.createElement("TD");
			var td2 = document.createElement("TD");
			var img = document.createElement("img");
			img.src = "/images/" + data[i]["idUser"];
			img.id = "img" + data[i]["idUser"];
			img.myParam = img;
			img.classList.add("pointer_image");
			img.addEventListener("click", function() {
				window.location.href="/?action=view_profile&viewId=" + data[i]["idUser"];
			});
			img.onerror = function(event){
				event.target.myParam.src = "/images/default_avatar.png";
			}
			img.style.width = '50px';
			img.style.height = '50px';
			img.classList.add("rounded_image");
			td2.appendChild(document.createTextNode(data[i]["firstName"] + " " + data[i]["lastName"]));
			td1.appendChild(img);
			tr.appendChild(td1);
			tr.appendChild(td2);
			table.appendChild(tr);
		}).call(this, i);
	}
}

function fillPostArea(data){
	data.sort(function(a, b){
		return b.timestamp - a.timestamp;
	});
	if(lastTimestamp != null && data[0]["timestamp"] == lastTimestamp){
		console.log("avoided refresh");
		return;
	}
	lastTimestamp = data[0]["timestamp"];
	var postArea = document.getElementById("post_area");
	data.sort(function(a, b){
		return (b.likes.length - b.dislikes.length) - (a.likes.length - a.dislikes.length);
	});
	data = data.slice(0, 5).concat(data.slice(5, data.length).sort(function(a, b){
		return b.timestamp - a.timestamp;
	}));
	postArea.innerHTML = "";
	for(i = 0; i < data.length; i++){
		(function(i){
			var postContainer = document.createElement("div");
			postContainer.classList.add("post_container");
			postContainer.classList.add("container-fluid");
			var nameContainer = document.createElement("div");
			nameContainer.classList.add("row");
			nameContainer.classList.add("name_container");
			var contentContainer = document.createElement("div");
			var content = data[i]["content"];
			var prepared = null;
			if(isURL(content)){
				if(matchYoutubeUrl(content)){
					var iFrameDiv = document.createElement("div");
					iFrameDiv.classList.add("container-fluid");
					iFrameDiv.align = "center";
					var videoId = getId(content);
					var iFrame = document.createElement("iframe");
					iFrame.width = "520px";
					iFrame.height = "315px";
					iFrame.src = "//www.youtube.com/embed/" + videoId;
					iFrameDiv.appendChild(iFrame);
					contentContainer.appendChild(iFrameDiv);
				}else{
					var a = document.createElement("a");
					a.title = "Korisnik je podijelio link";
					a.href = content;
					var divForContent = document.createTextNode(content);
					a.appendChild(divForContent);
					contentContainer.appendChild(a);
				}
			}else{
				var divForContent = document.createElement("div");
				if(data[i].hasOwnProperty("idEvent")){
					divForContent.innerHTML = content;
				}else{
					divForContent.textContent = content;
				}
				contentContainer.appendChild(divForContent);
			}
			var likeDislikeContainer = document.createElement("div");
			likeDislikeContainer.classList.add("row");
			likeDislikeContainer.classList.add("container-fluid");
			var img = document.createElement("img");
			img.style.width = "55px";
			img.style.height = "55px";
			if(data[i].hasOwnProperty("userIdUser")){
				img.src = "/images/" + data[i]["userIdUser"];
				img.alt = "/images/" + data[i]["userIdUser"];
				img.classList.add("pointer_image");
				img.addEventListener("click", function() {
					window.location.href="/?action=view_profile&viewId=" + data[i]["userIdUser"];
				});
			}else if(data[i].hasOwnProperty("idEvent")){
				img.src = data[i]["image"];
				console.log(data[i]["image"]);
				img.alt = data[i]["image"];
				img.style.width = "150px";
				img.style.height = "150px";
			}else{
				img.src = "/images/news.png";
				img.alt = "/images/news.png";
				img.style.width = "150px";
				img.style.height = "150px";
			}
			img.myParam = img;
			img.onerror = function(event){
				event.target.myParam.src = "/images/default_avatar.png";
			}
			img.classList.add("rounded_image");
			var name = document.createElement("div");
			if(i < 5)
				name.textContent = "Izdvojeno: ";
			if(data[i].hasOwnProperty("userFirstName")){
				name.textContent += data[i]["userFirstName"] + " " + data[i]["userLastName"];
			}else if(data[i].hasOwnProperty("idNews")){
				name.textContent += "Sistemska vijest : " + data[i]["title"];
			}else if(data[i].hasOwnProperty("idEvent")){
				name.textContent += "Događaj: " + data[i]["category"] + " - " + data[i]["title"]
			}
			nameContainer.appendChild(img);
			nameContainer.appendChild(name);
			var likeImg = document.createElement("img");
			likeImg.src = "/images/like.png";
			likeImg.alt = "/images/like.png";
			likeImg.style.width = "35px";
			likeImg.style.height = "35px";
			if(data[i].hasOwnProperty("idPost")){
				likeImg.id = "like" + data[i]["idPost"];
				likeImg.style.opacity = "0.5";
				likeImg.setAttribute("onClick", "sendRating(\"" + data[i]["idPost"] + "\", \"" + getCookie("AUTHTOKEN") + "\", \"like\");");
			}else if(data[i].hasOwnProperty("idNews")){
				likeImg.id = "likeNews" + data[i]["idNews"];
				likeImg.style.opacity = "0.5";
				likeImg.setAttribute("onClick", "sendNewsRating(\"" + data[i]["idNews"] + "\", \"" + getCookie("AUTHTOKEN") + "\", \"like\");");
			}else if(data[i].hasOwnProperty("idEvent")){
				likeImg.id = "likeEvent" + data[i]["idEvent"];
				likeImg.style.opacity = "0.5";
				likeImg.setAttribute("onClick", "sendEventRating(\"" + data[i]["idEvent"] + "\", \"" + getCookie("AUTHTOKEN") + "\", \"like\");");
			}
			var dislikeImg = document.createElement("img");
			dislikeImg.src = "/images/dislike.png";
			dislikeImg.alt = "/images/dislike.png";
			dislikeImg.style.width = "35px";
			dislikeImg.style.height = "35px";
			if(data[i].hasOwnProperty("idPost")){
				dislikeImg.id = "dislike" + data[i]["idPost"];
				dislikeImg.style.opacity = "0.5";
				dislikeImg.setAttribute("onClick", "sendRating(\"" + data[i]["idPost"] + "\", \"" + getCookie("AUTHTOKEN") + "\", \"dislike\");");
			}else if(data[i].hasOwnProperty("idNews")){
				dislikeImg.id = "dislikeNews" + data[i]["idNews"];
				dislikeImg.style.opacity = "0.5";
				dislikeImg.setAttribute("onClick", "sendNewsRating(\"" + data[i]["idNews"] + "\", \"" + getCookie("AUTHTOKEN") + "\", \"dislike\");");
			}else if(data[i].hasOwnProperty("idEvent")){
				dislikeImg.id = "dislikeEvent" + data[i]["idEvent"];
				dislikeImg.style.opacity = "0.5";
				dislikeImg.setAttribute("onClick", "sendEventRating(\"" + data[i]["idEvent"] + "\", \"" + getCookie("AUTHTOKEN") + "\", \"dislike\");");
			}
			var timestamp = document.createElement("div");
			timestamp.classList.add("small_text");
			timestamp.textContent = timeConverter(data[i]["timestamp"]);
			likeDislikeContainer.appendChild(likeImg);
			var likeCount = document.createElement("div");
			if(data[i].hasOwnProperty("idPost")){
				likeCount.id = "likeCount" + data[i]["idPost"];
			}else if(data[i].hasOwnProperty("idNews")){
				likeCount.id = "likeCountNews" + data[i]["idNews"];
			}else if(data[i].hasOwnProperty("idEvent")){
				likeCount.id = "likeCountEvent" + data[i]["idEvent"];
			}
			var likes = data[i]["likes"];
			var dislikes = data[i]["dislikes"];
			likeCount.textContent = likes.length;
			likeDislikeContainer.appendChild(likeCount);
			likeDislikeContainer.appendChild(dislikeImg);
			var dislikeCount = document.createElement("div");
			if(data[i].hasOwnProperty("idPost")){
				dislikeCount.id = "dislikeCount" + data[i]["idPost"];
			}else if(data[i].hasOwnProperty("idNews")){
				dislikeCount.id = "dislikeCountNews" + data[i]["idNews"];
			}else if(data[i].hasOwnProperty("idEvent")){
				dislikeCount.id = "dislikeCountEvent" + data[i]["idEvent"];
			}
			dislikeCount.textContent = dislikes.length;
			likeDislikeContainer.appendChild(dislikeCount);
			postContainer.appendChild(nameContainer);
			postContainer.appendChild(contentContainer);
			postContainer.appendChild(likeDislikeContainer);
			postContainer.append(timestamp);
			postArea.appendChild(postContainer);
			
			if(data[i].hasOwnProperty("idPost")){
				for(j = 0; j < likes.length ; j++){
					if(likes[j] === window.myData["idUser"]){
						doRating(data[i]["idPost"], null, "like", true);
					}
				}
				for(k = 0 ; k < dislikes.length ; k++){
					if(dislikes[k] === window.myData["idUser"]){
						doRating(data[i]["idPost"], null, "dislike", true);
					}
				}
			}else if(data[i].hasOwnProperty("idNews")){
				for(j = 0; j < likes.length ; j++){
					if(likes[j] === window.myData["idUser"]){
						doNewsRating(data[i]["idNews"], null, "like", true);
					}
				}
				for(k = 0 ; k < dislikes.length ; k++){
					if(dislikes[k] === window.myData["idUser"]){
						doNewsRating(data[i]["idNews"], null, "dislike", true);
					}
				}
			}else if(data[i].hasOwnProperty("idEvent")){
				for(j = 0; j < likes.length ; j++){
					if(likes[j] === window.myData["idUser"]){
						doEventRating(data[i]["idEvent"], null, "like", true);
					}
				}
				for(k = 0 ; k < dislikes.length ; k++){
					if(dislikes[k] === window.myData["idUser"]){
						doEventRating(data[i]["idEvent"], null, "dislike", true);
					}
				}
			}
		}).call(this, i);
	}
}

function doEventRating(idPost, token, rating, isInitial){
	var likeImg = document.getElementById("likeEvent" + idPost);
	var dislikeImg = document.getElementById("dislikeEvent" + idPost);
	var likeCount = document.getElementById("likeCountEvent" + idPost);
	var dislikeCount = document.getElementById("dislikeCountEvent" + idPost);
	if(rating === "like"){
		if(!likeImg.classList.contains("clicked_rating_button")){
			if(!isInitial){
				likeCount.textContent++;
				if(dislikeImg.classList.contains("clicked_rating_button"))
					dislikeCount.textContent--;
			}
			likeImg.opacity = "1.0";
			dislikeImg.opacity = "0.5";
			likeImg.classList.add("clicked_rating_button");
			likeImg.classList.add("clicked_rating_button_liked");
			dislikeImg.classList.remove("clicked_rating_button");
			dislikeImg.classList.remove("clicked_rating_button_disliked");
		}
	}else{
		if(!dislikeImg.classList.contains("clicked_rating_button")){
			if(!isInitial){
				dislikeCount.textContent++;
				if(likeImg.classList.contains("clicked_rating_button"))
					likeCount.textContent--;
			}
			likeImg.opacity = "0.5";
			dislikeImg.opacity = "1.0";
			dislikeImg.classList.add("clicked_rating_button");
			dislikeImg.classList.add("clicked_rating_button_disliked");
			likeImg.classList.remove("clicked_rating_button");
		}
	}
	likeImg.style.display = "none";
	likeImg.style.display = "block";
	dislikeImg.style.display = "none";
	dislikeImg.style.display = "block";
}

function doNewsRating(idPost, token, rating, isInitial){
	var likeImg = document.getElementById("likeNews" + idPost);
	var dislikeImg = document.getElementById("dislikeNews" + idPost);
	var likeCount = document.getElementById("likeCountNews" + idPost);
	var dislikeCount = document.getElementById("dislikeCountNews" + idPost);
	if(rating === "like"){
		if(!likeImg.classList.contains("clicked_rating_button")){
			if(!isInitial){
				likeCount.textContent++;
				if(dislikeImg.classList.contains("clicked_rating_button"))
					dislikeCount.textContent--;
			}
			likeImg.opacity = "1.0";
			dislikeImg.opacity = "0.5";
			likeImg.classList.add("clicked_rating_button");
			likeImg.classList.add("clicked_rating_button_liked");
			dislikeImg.classList.remove("clicked_rating_button");
			dislikeImg.classList.remove("clicked_rating_button_disliked");
		}
	}else{
		if(!dislikeImg.classList.contains("clicked_rating_button")){
			if(!isInitial){
				dislikeCount.textContent++;
				if(likeImg.classList.contains("clicked_rating_button"))
					likeCount.textContent--;
			}
			likeImg.opacity = "0.5";
			dislikeImg.opacity = "1.0";
			dislikeImg.classList.add("clicked_rating_button");
			dislikeImg.classList.add("clicked_rating_button_disliked");
			likeImg.classList.remove("clicked_rating_button");
		}
	}
	likeImg.style.display = "none";
	likeImg.style.display = "block";
	dislikeImg.style.display = "none";
	dislikeImg.style.display = "block";
}

function doRating(idPost, token, rating, isInitial){
	var likeImg = document.getElementById("like" + idPost);
	var dislikeImg = document.getElementById("dislike" + idPost);
	var likeCount = document.getElementById("likeCount" + idPost);
	var dislikeCount = document.getElementById("dislikeCount" + idPost);
	if(rating === "like"){
		if(!likeImg.classList.contains("clicked_rating_button")){
			if(!isInitial){
				likeCount.textContent++;
				if(dislikeImg.classList.contains("clicked_rating_button"))
					dislikeCount.textContent--;
			}
			likeImg.opacity = "1.0";
			dislikeImg.opacity = "0.5";
			likeImg.classList.add("clicked_rating_button");
			likeImg.classList.add("clicked_rating_button_liked")
			dislikeImg.classList.remove("clicked_rating_button");
		}
	}else{
		if(!dislikeImg.classList.contains("clicked_rating_button")){
			if(!isInitial){
				dislikeCount.textContent++;
				if(likeImg.classList.contains("clicked_rating_button"))
					likeCount.textContent--;
			}
			likeImg.opacity = "0.5";
			dislikeImg.opacity = "1.0";
			dislikeImg.classList.add("clicked_rating_button");
			dislikeImg.classList.add("clicked_rating_button_disliked");
			likeImg.classList.remove("clicked_rating_button");
		}
	}
	likeImg.style.display = "none";
	likeImg.style.display = "block";
	dislikeImg.style.display = "none";
	dislikeImg.style.display = "block";
}

function sendRating(idPost, token, rating, caller){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/giverating";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"postId" : idPost,
			"token" : token,
			"rating" : rating
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			doRating(idPost, token, rating, false);
		}else{
			return;
		}
	}
}

function sendNewsRating(idNews, token, rating, caller){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "http://localhost:9080/api/rest/givenewsrating";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"idNews" : idNews,
			"idUser" : window.myData["idUser"],
			"rating" : rating
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			doNewsRating(idNews, token, rating, false);
		}else{
			return;
		}
	}
}

function sendEventRating(idEvent, token, rating, caller){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "http://localhost:9080/api/rest/giveeventrating";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"idEvent" : idEvent,
			"idUser" : window.myData["idUser"],
			"rating" : rating
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			doEventRating(idEvent, token, rating, false);
		}else{
			return;
		}
	}
}

function updateAllPosts(){
	var baseUrl = "/api/rest/posts/";
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){        
            //fillPostArea(data);
			updateNews(data);
         }
	});
}

function updateNews(inputData){
	var restUrl = "http://localhost:9080/api/rest/allnews";
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){
			updateEvents(data.concat(inputData));
		}
	});
}

function updateEvents(inputData){
	var url = "http://localhost:9080/rss";
	$.get(url, function(data) {
	    var $xml = $($.parseXML(data));
	    var jsonData = {};
	    $xml.find("item").each(function() {
	        var $this = $(this),
	            item = {
	                title: $this.find("title").text(),
	                image: $this.find("link").text(),
	                content: $this.find("description").text(),
	                category: $this.find("category").text(),
	                idEvent: $this.find("dc\\:creator").text(),
	                timestamp: new Date($this.find("pubDate").text()).getTime()
	        }
	        var ratings = $this.find("content\\:encoded").text();
	        var likes = ratings.split("#SPLITTER#")[0];
	        var dislikes = ratings.split("#SPLITTER#")[1];
	        item.likes = JSON.parse(likes);
	        item.dislikes = JSON.parse(dislikes);
	        inputData.push(item);
	    });
		fillPostArea(inputData);
	});
}

function updateConnectedUsers(){
	var baseUrl = "/api/rest/connected/";
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + token;
	
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){        
            fillConnectedUsersTable(data);
         }
	});
	
	var myInfoBaseUrl = "/api/rest/myinfo/";
	var token = getCookie("AUTHTOKEN");
	var myInfoCall = myInfoBaseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: myInfoCall,
		success: function(data){
			window.myData = data;
         }
	});
	
}

function fillSharedFiles(data){
	var filesContainer = document.getElementById("files_div");
	data.sort(function(a, b){
		return b.timestamp - a.timestamp;
	});
	for(i = 0; i < data.length; i++){
		(function(i){
			var singleFileDiv = document.createElement("div");
			singleFileDiv.classList.add("post_container");
			singleFileDiv.classList.add("container-fluid");
			var form = document.createElement("form");
			form.classList.add("container_fluid");
			form.action = "?action=download_file";
			form.method = "post";
			var img = document.createElement("img");
			var nameContainer = document.createElement("div");
			var name = document.createElement("div");
			name.textContent = data[i]["author"];
			nameContainer.classList.add("row");
			nameContainer.classList.add("name_container");
			img.style.width = "40px";
			img.style.height = "40px";
			img.classList.add("rounded_image");
			img.src = "/images/" + data[i]["userId"];
			img.id = "imgFile" + data[i]["userId"];
			img.classList.add("pointer_image");
			img.addEventListener("click", function() {
				window.location.href="/?action=view_profile&viewId=" + data[i]["userId"];
			});
			img.myParam = img;
			img.onerror = function(event){
				event.target.myParam.src = "/images/default_avatar.png";
			}
			nameContainer.appendChild(img);
			nameContainer.appendChild(name);
			form.appendChild(nameContainer);
			var hiddenInput = document.createElement("input");
			hiddenInput.type = "hidden";
			hiddenInput.name = "filename";
			hiddenInput.id = "filename";
			hiddenInput.value = data[i]["fileName"];
			var submit = document.createElement("input");
			submit.classList.add("file_button");
			submit.type = "submit";
			submit.name = "filename";
			submit.id = "filename";
			submit.value = data[i]["fileName"];
			form.appendChild(hiddenInput);
			var descriptionDiv = document.createElement("div");
			descriptionDiv.textContent = data[i]["description"];
			form.appendChild(submit);
			form.appendChild(descriptionDiv);
			var timestampDiv = document.createElement("div");
			timestampDiv.classList.add("small_text");
			timestampDiv.textContent = timeConverter(data[i]["timestamp"]);
			form.appendChild(timestampDiv);
			singleFileDiv.appendChild(form);
			filesContainer.appendChild(singleFileDiv);
		}).call(this, i);
	}
}

function updateSharedFiles(){
	var restUrl = "/api/rest/files/" + getCookie("AUTHTOKEN");
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){
			fillSharedFiles(data);
		}
	});
}

function executeScripts(){
	readNotificationCount();
	updateConnectedUsers();
	updateBlogs();
	updateAllPosts();
	updateSharedFiles();
	var connectedInterval = setInterval(updateConnectedUsers, 60000);
	var postsInterval = setInterval(updateAllPosts, 30000);
	var notificationInterval = setInterval(readNotificationCount, 30000);
}

function getCookie(cname) {
	  var name = cname + "=";
	  var decodedCookie = decodeURIComponent(document.cookie);
	  var ca = decodedCookie.split(';');
	  for(var i = 0; i <ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0) == ' ') {
	      c = c.substring(1);
	    }
	    if (c.indexOf(name) == 0) {
	      return c.substring(name.length, c.length);
	    }
	  }
	  return "";
	}

function timeConverter(timestamp){
	  var a = new Date(timestamp);
	  var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
	  var year = a.getFullYear();
	  var month = months[a.getMonth()];
	  var date = a.getDate();
	  var hour = a.getHours();
	  var min = a.getMinutes();
	  var sec = a.getSeconds();
	  var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ;
	  return time;
	}

function ValidURL(str) {
	  try{
		  var pattern = new RegExp('^(https?:\/\/)?'+ // protocol
				    '((([a-z\d]([a-z\d-]*[a-z\d])*)\.)+[a-z]{2,}|'+ // domain name
				    '((\d{1,3}\.){3}\d{1,3}))'+ // OR ip (v4) address
				    '(\:\d+)?(\/[-a-z\d%_.~+]*)*'+ // port and path
				    '(\?[;&a-z\d%_.~+=-]*)?'+ // query string
				    '(\#[-a-z\d_]*)?$','i'); // fragment locater
				  if(!pattern.test(str)) {
				    alert("Please enter a valid URL.");
				    return false;
				  } else {
				    return true;
				  }
	  }catch(err){
		  return false;
	  }
	}

function isURL(str) {
	  var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
	  '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name and extension
	  '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
	  '(\\:\\d+)?'+ // port
	  '(\\/[-a-z\\d%@_.~+&:]*)*'+ // path
	  '(\\?[;&a-z\\d%@_.,~+&:=-]*)?'+ // query string
	  '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
	  var status = pattern.test(str);
	  return status;
	}

function matchYoutubeUrl(url){
	var p = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
	return (url.match(p)) ? true : false ;
}

function readNotificationCount(){
	var baseUrl = "/api/rest/pending/";
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){
			if(data.length > 0){
				var connectHeaderLink = document.getElementById("connect_header_link");
				connectHeaderLink.innerHTML = "Povezivanje";
				var span = document.createElement("span");
				span.style.backgroundColor = "#8b0000";
				span.style.marginLeft = "10px";
				span.classList.add("badge");
				span.textContent = data.length;
				connectHeaderLink.appendChild(span);
			}
         }
	});
}

function getId(url) {
    var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    var match = url.match(regExp);

    if (match && match[2].length == 11) {
        return match[2];
    } else {
        return 'error';
    }
}
