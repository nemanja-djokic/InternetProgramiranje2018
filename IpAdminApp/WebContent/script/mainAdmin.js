var lastChart = null;

function executeScripts(){
	getChartInfo();
	setInterval(getChartInfo, 30000);
}

function getChartInfo(){
	var restUrl = "http://localhost:8080/IpAdminApp/api/rest/loginstatistics";
	$.ajax({
		type: "GET",
		datatype: "json",
		url: restUrl,
		success: function(data){
			console.log("succ");
			drawChart(data);
         }
	});
}

function drawChart(data){
	console.log(data);
	if(lastChart != null)
		lastChart.destroy();
	var canvas = document.getElementById("chart");
	var ctx = document.getElementById("chart").getContext('2d');
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	lastChart = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: data[1],
	        datasets: [{
	            label: 'Broj korisnika po satu',
	            data: data[0],
	            backgroundColor: [
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)'
	            ],
	            borderColor: [
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)',
	                'rgba(53, 122, 56, 0.3)'
	            ],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero:true
	                }
	            }]
	        }
	    }
	});
	/*var myChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: data[1],
	        datasets: [{
	            label: 'Broj korisnika po satu',
	            data: data[0],
	            backgroundColor: [
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)'
	            ],
	            borderColor: [
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)',
	                'rgba(76, 175, 80, 0.3)'
	            ],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        title: {
	            display: true,
	            text: 'Broj korisnika po satu'
	          }
	        }
	});*/
}