var courseData = null;
var firstRun = true;
var myData = null;

function changePassword(){
	var xhr = new XMLHttpRequest();
	var connectionUrl = "/api/rest/changepassword";
	xhr.open("POST", connectionUrl, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	params = {
			"token" : getCookie("AUTHTOKEN"),
			"oldPassword" : document.getElementById("oldPassword").value,
			"newPassword" : document.getElementById("newPassword").value,
			"newPasswordRepeat" : document.getElementById("newPasswordRepeat").value
	}
	xhr.send(JSON.stringify(params));
	xhr.onreadystatechange = function(){
		document.getElementById("oldPassword").value = "";
		document.getElementById("newPassword").value = "";
		document.getElementById("newPasswordRepeat").value = "";
		if(this.readyState != 4)
			return;
		var text = xhr.responseText;
		var notificationDiv = document.getElementById("alert_div");
		notificationDiv.innerHTML = "";
		if(this.status == 200){
			var alert = document.createElement("div");
			alert.classList.add("alert");
			alert.classList.add("alert-success");
			alert.classList.add("alert-dismissible");
			var a = document.createElement("a");
			a.href="#";
			a.classList.add("close");
			a.dataDismiss = "alert";
			a.ariaLabel = "close";
			a.textContent = "&times;";
			alert.appendChild(a);
			alert.textContent = text;
			notificationDiv.appendChild(alert);
		}else{
			var alert = document.createElement("div");
			alert.classList.add("alert");
			alert.classList.add("alert-danger");
			alert.classList.add("alert-dismissible");
			var a = document.createElement("a");
			a.href="#";
			a.classList.add("close");
			a.dataDismiss = "alert";
			a.ariaLabel = "close";
			a.textContent = "&times;";
			alert.appendChild(a);
			alert.textContent = text;
			notificationDiv.appendChild(alert);
			return;
		}
	}
}

function fillFacultyDropDown(data){
	var dropDown = document.getElementById("faculty_select");
	dropDown.innerHTML = "";
	for(i = 0; i < data.length; i++){
		var option = document.createElement("option");
		option.value = data[i]["name"];
		option.textContent = data[i]["name"];
		dropDown.appendChild(option);
	}
	dropDown.value = window.myData["courseFacultyName"];
	var descriptionBox = document.getElementById("description");
	descriptionBox.textContent = window.myData["description"];
	var imageDiv = document.getElementById("image_div");
	var img = document.createElement("img");
	img.src = "/images/" + window.myData["idUser"];
	img.style.width = '200px';
	img.style.height = '200px';
	img.classList.add("rounded_image");
	console.log("/images/" + window.myData["idUser"]);
	imageDiv.appendChild(img);
	updateCourseDropDown();
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

function fillCourseDropDown(data){
	window.courseData = data;
	var dropDown = document.getElementById("course_select");
	dropDown.innerHTML = "";
	for(i = 0; i < data.length; i++){
		var option = document.createElement("option");
		option.value = data[i]["name"];
		option.textContent = data[i]["name"];
		dropDown.appendChild(option);
	}
	dropDown.value = window.myData["courseName"];
	fillYearDropDown();
}

function updateCourseDropDown(){
	var baseUrl = "/api/rest/coursesforfaculty/";
	var facultyDropDown = document.getElementById("faculty_select");
	var selectedStr = facultyDropDown.options[facultyDropDown.selectedIndex].text;
	var token = getCookie("AUTHTOKEN");
	var restUrl = baseUrl + selectedStr + "&" + token;
	console.log(restUrl);
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){        
            fillCourseDropDown(data);
         }
	});
}

function fillYearDropDown(){
	var yearDropDown = document.getElementById("year_select");
	yearDropDown.innerHTML = "";
	var courseDropDown = document.getElementById("course_select");
	var selectedStr = courseDropDown.options[courseDropDown.selectedIndex].text;
	for(i = 0; i < window.courseData.length; i++){
		var courseName = window.courseData[i]["name"];
		if(courseName === selectedStr){
			var numOfYears = window.courseData[i]["durationYears"];
			for(j = 1 ; j <= numOfYears; j++){
				var option = document.createElement("option");
				option.value = j;
				option.textContent = j;
				yearDropDown.appendChild(option);
			}
			i = courseData.lenght;
		}
	}
	yearDropDown.value = window.myData["currentYear"];
}

function executeScripts(){
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
	updateFacultyDropDown();
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