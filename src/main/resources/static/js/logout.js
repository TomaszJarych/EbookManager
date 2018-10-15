$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/ebm/logout",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
           console.log(json);
           alert("User has been logged out");
           location.href = "../index.html";

    }).fail(function () {
        alert("Cannot connect to server");
        location.href = "../index.html";


    });
    
});