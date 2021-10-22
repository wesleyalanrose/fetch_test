var index = 0;

const FETCH_API = "/fetchtest";
const CONTROLLER_API = "/fetch"
const ADD_ENDPOINT = "/add_points";
const SPEND_ENDPOINT = "/spend_points";
const BALANCE_ENDPOINT = "/check_balance";


const transactions = [{ "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" },
{ "payer": "UNILEVER", "points": 200, "timestamp": "2020-10-31T11:00:00Z" },
//{ "payer": "DANNON", "points": -200, "timestamp": "2020-10-31T15:00:00Z" },
{ "payer": "MILLER COORS", "points": 10000, "timestamp": "2020-11-01T14:00:00Z" },
{ "payer": "DANNON", "points": 300, "timestamp": "2020-11-03T10:00:00Z" },
{ "payer": "BAR", "points": 300, "timestamp": "2020-11-04T10:00:00Z" },
{ "payer": "FOO", "points": 1100, "timestamp": "2020-11-05T10:00:00Z" },
{ "payer": "BAR", "points": 100, "timestamp": "2020-11-05T11:00:00Z" },
{ "payer": "FOO", "points": 200, "timestamp": "2020-11-06T10:00:00Z" }];

function pop_transactions() {
	$.each(transactions, function(i, e) {
		$d = `<li id="${i}">${e.payer}, ${e.points}, ${e.timestamp}</li>`;
		$('#transactions').append($d);
	});
}

function add_all() {
	var data = [];
	$.each(transactions, function(i, e){
		data.push({
			payer: e.payer, 
			points: e.points, 
			timestamp: e.timestamp, 
			pointsAvailable: e.points
		});
	});

	var dataJson = JSON.stringify(data);
	console.log(dataJson);

	$.ajax({
		url: `${FETCH_API}${CONTROLLER_API}${ADD_ENDPOINT}`,
		contentType: 'application/json',
		type: 'POST',
		data: dataJson,
		success: function(data, textStatus, jsXHR) {
			$(`#${index++}`).append(" added");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
			console.log(XMLHttpRequest);
		}
	});
}


function add_transaction() {
	var data = [];
	data.push({
		payer: $('#payer').val(), 
		points: $('#points').val(), 
		timestamp: `${$('#timestamp').val()}:00Z`, 
		pointsAvailable: $('#points').val()
	});


	var dataJson = JSON.stringify(data);
	console.log(dataJson);

	$.ajax({
		url: `${FETCH_API}${CONTROLLER_API}${ADD_ENDPOINT}`,
		contentType: 'application/json',
		type: 'POST',
		data: dataJson,
		success: function(data, textStatus, jsXHR) {
			$(`#${index++}`).append(" added");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
			console.log(XMLHttpRequest);
		}
	});
}

function spend_points(points) {
	var points = $('#spend_points').val();
	$('#output').empty();
	$.ajax({
		url: `${FETCH_API}${CONTROLLER_API}${SPEND_ENDPOINT}?points=${points}`,
		type: 'GET',
		dataType: 'json',
		success: function(data, textStatus, jsXHR) {
			console.log(data);
			$.each(data, function(i,e) {
				$('#output').append(`<li>${e.payer}: ${e.points}</li>`);
			});
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
			console.log(XMLHttpRequest);
		}
	});
}

function get_balance() {
	$.ajax({
		url: `${FETCH_API}${CONTROLLER_API}${BALANCE_ENDPOINT}`,
		type: 'GET',
		dataType: 'json',
		success: function(data, textStatus, jsXHR) {
			console.log(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorThrown);
			console.log(XMLHttpRequest);
		}
	});
}

$(document).ready(function(){
	pop_transactions();
});