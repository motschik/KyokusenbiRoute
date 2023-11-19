window.onload = function() {
	document.getElementById('submit').onclick = submit;;
}

function submit() {
	console.log('送信');


	const request = new XMLHttpRequest();
	const fd = new FormData();
	request.open('POST', '/search');
	request.onload = drawResult;

	fd.append('from', document.getElementById('from').value);
	fd.append('to', document.getElementById('to').value);

	request.send(fd);
}

function drawResult() {

	let route = document.getElementById('route');
	while(route.firstChild){
		route.removeChild(route.firstChild);
	}

	let jsonText = this.response;
	let data = JSON.parse(jsonText)
	console.log(data);

	data.forEach(routeBean => {
		if (routeBean.routeType === 'STATION') {
			console.log('station');
			let station = document.createElement('div');

			station.className = "station";

			if (routeBean.viaFlag) {
				console.log('via');
				let viaString = document.createElement('span');
				viaString.className = "via";
				viaString.innerText = "（直通）";
				station.appendChild(viaString);
			}

			let stationName = document.createElement('span');
			stationName.className = "station_name";
			stationName.innerText = routeBean.name;
			station.appendChild(stationName);

			if (routeBean.arriveNo !== null) {
				let arrive = document.createElement('span');
				arrive.className = "arrive";
				arrive.innerText = (routeBean.arriveNo + 1) + "番線着";
				station.appendChild(arrive);
			}

			if (routeBean.departureNo !== null) {
				let departure = document.createElement('span');
				departure.className = "departure";
				departure.innerText = (routeBean.departureNo + 1) + "番線発";
				station.appendChild(departure);
			}

			if (routeBean.cost !== null) {
				let cost = document.createElement('span');
				cost.className = "change";
				cost.innerText = "乗換" + routeBean.cost + "秒";
				station.appendChild(cost);
			}


			document.getElementById('route').appendChild(station);
		} else if (routeBean.routeType === 'WALK') {
			console.log('walk');
			let walk = document.createElement('div');
			walk.className = "walk";

			let walkBox = document.createElement('span');
			walkBox.className = "walk_box";
			walk.appendChild(walkBox);

			let cost = document.createElement('span');
			cost.className = "walk_cost";
			cost.innerText = routeBean.cost + "秒";
			walk.appendChild(cost);

			let name = document.createElement('span');
			name.className = "walk_name";
			name.innerText = "徒歩";
			walk.appendChild(name);

			document.getElementById('route').appendChild(walk);
		} else if (routeBean.routeType === 'LINE') {
			console.log('line');
			let line = document.createElement('div');
			line.className = "line";

			let count = document.createElement('span');
			count.className = "station_count";
			count.innerText = routeBean.stationCount + "駅";
			line.appendChild(count);

			let lineBox = document.createElement('span');
			lineBox.className = "line_box";
			lineBox.style.backgroundColor = routeBean.color;
			line.appendChild(lineBox);

			let cost = document.createElement('span');
			cost.className = "line_cost";
			cost.innerText = routeBean.cost + "秒";
			line.appendChild(cost);

			let name = document.createElement('span');
			name.className = "line_name";
			name.innerText = routeBean.name;
			line.appendChild(name);

			let forStation = document.createElement('span');
			forStation.className="for_station";
			forStation.innerText = routeBean.forStation + "行き";
			line.appendChild(forStation);

			document.getElementById('route').appendChild(line);
		}
	});
}