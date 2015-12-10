var rabbitMQHost = 'http://192.168.99.100:15674/stomp';
var ws = new SockJS(rabbitMQHost);
var client = Stomp.over(ws);
client.heartbeat.outgoing = 20000;
client.heartbeat.incoming = 0;

var onConnect = function () {
    console.log('connected');
    registerSubsribers()
};

var onError = function () {
    console.log('error');
};

client.connect('guest', 'guest', onConnect, onError, '/');

function registerSubsribers() {
    newCoolerEvent = client.subscribe("/queue/newCoolerEvent", newCoolerEventCallback);
    newHeaterEvent = client.subscribe("/queue/newHeaterEvent", newHeaterEventCallback);
    newSensorEvent = client.subscribe("/queue/newSensorEvent", newSensorEventCallback);
    temperatureChangedEvent = client.subscribe("/queue/temperatureChangedEvent", temperatureChangedEventCallback);
    coolerStateEvent = client.subscribe("/queue/coolerStateEvent", coolerStateEventCallback);
    averageTemperatureEvent = client.subscribe("/queue/averageTemperatureEvent", averageTemperatureEventCallback);
    heaterStateEvent = client.subscribe("/queue/heaterStateEvent", heaterStateEventCallback);
    sensorStateEvent = client.subscribe("/queue/sensorStateEvent", sensorStateEventCallback);
}

var temperatureChangedEventCallback = function (data) {
    var jsonData = jQuery.parseJSON(eval(data.body));
    var sensorTemperature = $("#" + jsonData.fromSensor + "Temperature");
    sensorTemperature.text(jsonData.currentTemperature)
}

var coolerStateEventCallback = function (data) {
    changeApplianceState(data);
}

var heaterStateEventCallback = function (data) {
    changeApplianceState(data);
}

var sensorStateEventCallback = function (data) {
    changeSensorState(data);
}

var averageTemperatureEventCallback = function (data) {
    var jsonData = jQuery.parseJSON(eval(data.body));
    var chart = $('#averageGraph').highcharts();

    if (!chart) {
        $('#averageGraph').highcharts({
            title: {
                text: '',
                style: {
                    display: 'none'
                }
            },
            yAxis: {
                title: {
                    text: 'Temperature (°C)'
                }
            },
            tooltip: {
                valueSuffix: '°C'
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            series: [{name: 'Average temperature', data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]}]
        });
    }
    if (chart)
        chart.series[0].addPoint(jsonData.averageTemperature, true, true);
}

var newCoolerEventCallback = function (data) {
    createNewAppliance(data, "coolers");
}

var newHeaterEventCallback = function (data) {
    createNewAppliance(data, "heaters");
}

var newSensorEventCallback = function (data) {
    createNewSensor(data);
}

function createNewSensor(data) {
    var jsonData = jQuery.parseJSON(eval(data.body));
    var element = $("#" + jsonData.name);
    var elementDiv = $("#sensors");

    if (element.length == 0) {
        elementDiv.append('<div class="col-md-3 col-xs-6" id=\"' + jsonData.name + '\">' +
            '<div id="' + jsonData.name + 'State" class="card temp tempOn">' +
            '<div class="inner">' +
            '<div class="icon"/>' +
            '<div class="title">' +
            '<span class="text" id="' + jsonData.name + 'Name\">' + jsonData.name + '</span>' +
            '</div> ' +
            '<div class="number" id="' + jsonData.name + 'Temperature\">' + '-' + '</div>' +
            '<div class="measure">CELCIUS</div></div></div></div></div>')
    }
}
function createNewAppliance(data, elementDiv) {
    var jsonData = jQuery.parseJSON(eval(data.body));
    var element = $("#" + jsonData.name);
    var elementDiv = $("#" + elementDiv);

    if (element.length == 0) {
        elementDiv.append('<li class="list__item" id=\"' + jsonData.name + '\"> </div > ' +
            '<span id=\"' + jsonData.name + 'Name' + '\">' + jsonData.name + '\t</span>' +
            '<label class="switch switch--list-item">' +
            '<input onclick="return false" type="checkbox" class="switch__input" id="' + jsonData.name + "State" + '"\>' +
            '<div class="switch__toggle"></div>' +
            '</label></li>')
    }
}

function changeSensorState(data) {
    var jsonData = jQuery.parseJSON(eval(data.body));
    var sensor = $("#" + jsonData.name);
    sensor.attr('onclick', jsonData.state ? 'disableSensor("' + jsonData.name + '")' : 'enableSensor("' + jsonData.name + '")')
    var sensorState = $("#" + jsonData.name + "State");
    sensorState.removeClass();
    sensorState.addClass(jsonData.state ? 'card temp tempOn' : 'card temp tempOff');
    sensor.find(".number").text("-")
}

function changeApplianceState(data) {
    var jsonData = jQuery.parseJSON(eval(data.body));
    var applicanceState = $("#" + jsonData.name + "State");
    applicanceState.prop('checked', jsonData.state);
}

function disableSensor(name) {
    $.post("/disable/sensor", {name: name});
}
function enableSensor(name) {
    $.post("/enable/sensor", {name: name});
}

