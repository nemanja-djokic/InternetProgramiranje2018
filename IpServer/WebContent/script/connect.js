myData = null;

function fillFacultyDropDown(data){
	var dropDown = document.getElementById("faculty_select");
	dropDown.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "Svi";
	defOption.textContent = "Svi";
	dropDown.appendChild(defOption);
	for(i = 0; i < data.length; i++){
		var option = document.createElement("option");
		option.value = data[i]["name"];
		option.textContent = data[i]["name"];
		dropDown.appendChild(option);
	}
	if(window.myData.hasOwnProperty("courseFacultyName") && window.myData["courseFacultyName"] != null){
		dropDown.value = window.myData["courseFacultyName"];
	}else{
		dropDown.value = "Svi";
	}
	updateRequestListTable();
	updateUserListTable();
	updateSentListTable();
	updateConnectedUsers();
}

function updateFacultyDropDown(){
	var baseUrl = "/api/rest/allfaculties/";
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){        
            fillFacultyDropDown(data);
         }
	});
}

function getMyData(){
	var myInfoBaseUrl = "/api/rest/myinfo/";
	var token = getCookie("AUTHTOKEN");
	var myInfoCall = myInfoBaseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: myInfoCall,
		success: function(data){
			window.myData = data;
			updateFacultyDropDown();
         }
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
            fillConnectedListTable(data);
         }
	});
}

function fillConnectedListTable(data){
	var table = document.getElementById("connected_list_table");
	table.innerHTML = "";
	for(i = 0; i < data.length; i++){
		(function(i){
			var tr = document.createElement("TR");
			var td1 = document.createElement("TD");
			var td2 = document.createElement("TD");
			var td3 = document.createElement("TD");
			var img = document.createElement("img");
			var button = document.createElement("button");
			button.classList.add("connect_button");
			var textNode = document.createTextNode("Poništi");
			button.appendChild(textNode);
			button.setAttribute("onClick", "cancelActiveConnection(\"" + data[i]["idUser"] + "\");");
			td3.appendChild(button);
			img.src = "/images/" + data[i]["idUser"];
			img.onerror = function(){
				img.src = "/images/default_avatar.png";
			}
			img.style.width = '50px';
			img.style.height = '50px';
			img.classList.add("rounded_image");
			td2.appendChild(document.createTextNode(data[i]["firstName"] + " " + data[i]["lastName"]));
			td1.appendChild(img);
			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			table.appendChild(tr);
		}).call(this, i);
	}
}

function fillSentListTable(data){
	var table = document.getElementById("sent_list_table");
	table.innerHTML = "";
	for(i = 0; i < data.length; i++){
		(function(i){
			var tr = document.createElement("TR");
			var td1 = document.createElement("TD");
			var td2 = document.createElement("TD");
			var td3 = document.createElement("TD");
			var img = document.createElement("img");
			var button = document.createElement("button");
			button.classList.add("connect_button");
			var textNode = document.createTextNode("Poništi");
			button.appendChild(textNode);
			button.setAttribute("onClick", "cancelConnection(\"" + data[i]["idUser"] + "\");");
			td3.appendChild(button);
			img.src = "/images/" + data[i]["idUser"];
			img.onerror = function(){
				img.src = "/images/default_avatar.png";
			}
			img.style.width = '50px';
			img.style.height = '50px';
			img.classList.add("rounded_image");
			td2.appendChild(document.createTextNode(data[i]["firstName"] + " " + data[i]["lastName"]));
			td1.appendChild(img);
			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			table.appendChild(tr);
		}).call(this, i);
	}
}

function updateSentListTable(){
	var baseUrl = "/api/rest/sent/";
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){        
            fillSentListTable(data);
         }
	});
}

function fillRequestListTable(data){
	var table = document.getElementById("request_list_table");
	table.innerHTML = "";
	for(i = 0; i < data.length; i++){
		(function(i){
			var tr = document.createElement("TR");
			var td1 = document.createElement("TD");
			var td2 = document.createElement("TD");
			var td3 = document.createElement("TD");
			var img = document.createElement("img");
			var button = document.createElement("button");
			button.classList.add("connect_button");
			var textNode = document.createTextNode("Prihvati");
			button.appendChild(textNode);
			button.setAttribute("onClick", "acceptConnection(\"" + data[i]["idUser"] + "\");");
			td3.appendChild(button);
			img.src = "/images/" + data[i]["idUser"];
			img.onerror = function(){
				img.src = "/images/default_avatar.png";
			}
			img.style.width = '50px';
			img.style.height = '50px';
			img.classList.add("rounded_image");
			td2.appendChild(document.createTextNode(data[i]["firstName"] + " " + data[i]["lastName"]));
			td1.appendChild(img);
			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			table.appendChild(tr);
		}).call(this, i);
	}
}

function updateRequestListTable(){
	var baseUrl = "/api/rest/pending/";
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + token;
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){        
            fillRequestListTable(data);
         }
	});
}

function fillUserListTable(data){
	var table = document.getElementById("user_list_table");
	table.innerHTML = "";
	for(i = 0; i < data.length; i++){
		(function(i){
			var tr = document.createElement("TR");
			var td1 = document.createElement("TD");
			var td2 = document.createElement("TD");
			var td3 = document.createElement("TD");
			var img = document.createElement("img");
			var button = document.createElement("button");
			button.classList.add("connect_button");
			var textNode = document.createTextNode("Poveži se");
			button.appendChild(textNode);
			button.setAttribute("onClick", "connectWith(\"" + data[i]["idUser"] + "\");");
			td3.appendChild(button);
			img.src = "/images/" + data[i]["idUser"];
			console.log(img.src);
			img.onerror = function(){
				img.src = "/images/default_avatar.png";
			}
			img.style.width = '50px';
			img.style.height = '50px';
			img.classList.add("rounded_image");
			td2.appendChild(document.createTextNode(data[i]["firstName"] + " " + data[i]["lastName"]));
			td1.appendChild(img);
			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			table.appendChild(tr);
		}).call(this, i);
	}
}

function connectWith(idToConnect){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/sendrequest";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"userid" : idToConnect,
			"sessiontoken" : getCookie("AUTHTOKEN")
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			updateUserListTable();
			updateSentListTable();
		}else{
			return;
		}
	}
}


function acceptConnection(idToAccept){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/acceptrequest";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"userid" : idToAccept,
			"sessiontoken" : getCookie("AUTHTOKEN")
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			updateUserListTable();
			updateRequestListTable();
			updateConnectedUsers();
		}else{
			return;
		}
	}
}

function cancelConnection(idToCancel){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/cancelrequest";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"userid" : idToCancel,
			"sessiontoken" : getCookie("AUTHTOKEN")
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			updateUserListTable();
			updateSentListTable();
		}else{
			return;
		}
	}
}

function cancelActiveConnection(idToCancel){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/cancelactiveconnection";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"userid" : idToCancel,
			"sessiontoken" : getCookie("AUTHTOKEN")
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		if(this.readyState != 4)
			return;
		if(this.status == 200){
			updateUserListTable();
			updateSentListTable();
			updateConnectedUsers();
		}else{
			return;
		}
	}
}

function updateUserListTable(){
	var baseUrl = "/api/rest/allusersforfaculty/";
	var dropDown = document.getElementById("faculty_select");
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + dropDown.value + "&" + token;
	console.log(restUrl);
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){        
            fillUserListTable(data);
         }
	});
}

function executeScripts(){
	getMyData();
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