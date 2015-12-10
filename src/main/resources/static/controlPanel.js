$(document).ready(function () {
    $("#addSensorButton").on('click', function () {
        $.post("/add/sensor", {name: randomName("Sensor")});
    });

    $("#addHeaterButton").on('click', function () {
        $.post("/add/heater", {name: randomName("Heater")});
    });

    $("#addCoolerButton").on('click', function () {
        $.post("/add/cooler", {name: randomName("Cooler")});
    });

})

function randomName(elementType) {
    var words = ["Java", "AspectJ", "JavaScript", "Groovy", "Scala", "Clojure", "Rhino",
        "DynJS", "Ruby", "JRuby", "Jython", "PHP", "Perl", "C",
        "RxJava", "RxJS", "Nodejs", "OSGI", "Android", "Titanium", "PhoneGap",
        "Sencha", "AWS", "OpenShift", "Heroku", "OpenStack", "Tomcat", "Jetty",
        "TomEE", "Apache", "Geronimo", "JBoss", "WebLogic", "JUnit", "TestNG",
        "Mockito", "EasyMock"];
    return words[Math.floor(Math.random() * words.length)] + elementType;
}
