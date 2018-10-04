$(document).ready(function () {

    var users = [];

    $.ajax({
        url: "http://localhost:8080/ebm/hello",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        $("#test").after("<h1>" + json["content"] + "</h1>")


    });

    $.ajax({
        url: "http://localhost:8080/ebm/user/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        for (user of json["data"]) {
            $("#test").after("<h1>" + user.firstName + " " + user.lastName + "</h1>")
            users.push(user);
        }
        users.forEach(function (element, index, array) {
            console.log(element.id + " " + element.firstName + " " + element.lastName);

        })

    });
    console.log(users.length);


    users.forEach(function (element, index, array) {
        console.log(element);

    })

    $.ajax({
        headers: { 
            'Content-Type': 'application/json' 
        },
        url: "http://localhost:8080/ebm/user",
        type: "POST",
        data: JSON.stringify({
            login: "cay",
            firstName: "Cay",
            lastName: "Hortsmann",
            email: "cay@gmail.com",
            role: "USER",
            password: "cay"
        }),
        dataType: "json"
    }).done(function(json){
        console.log("It works!");
        
        console.log(json);
        

    })

    

});