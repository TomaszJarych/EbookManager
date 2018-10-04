$(document).ready(function () {

$.ajax({
        url: "http://localhost:8080/ebm/hello",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        $("#test").after("<h1>"+json["content"]+"</h1>")
    });

});