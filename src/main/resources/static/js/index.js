$(document).ready(function () {

    const button = $("#button");
    const selectMenu = $("#selectUser");
    const inputLogin = $("#inputLogin");
    const inputPassword = $("#inputPassword");
    const loginUserButton = $("#loginUser");
    const divForm = $("#divForm");



    selectMenu.on("load", showAllUsers())
    button.on("click", function () {
        inputLogin.val(selectMenu.val());
        divForm.removeClass("showDetails");

    })

    loginUserButton.on("click", function (event) {
        event.preventDefault();
        loginUser(inputLogin.val(), inputPassword.val());
    })


    function showAllUsers() {
        $.ajax({
            url: "http://localhost:8080/ebm/user/all",
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            for (let user of json["data"]) {
                let element = "";
                element += "<option value=\"" + user.login + "\" >" + user.firstName + " " + user.lastName + "</option>";
                selectMenu.append(element);
            }

        }).fail(function () {
            alert("Cannot connect to server");


        });
    }

    function loginUser(login, password) {
        $.ajax({
            headers: {
                'Content-Type': 'application/json'
            },
            url: "http://localhost:8080/ebm/user/login",
            type: "POST",
            data: JSON.stringify({
                login: login,
                password: password
            }),
            dataType: "json"
        }).done(function (json) {

            if (json["data"] === true && json["message"] === "OK") {
                location.href = "views/homePage.html";
            } else {
                alert("Invalid password");

            }

        }).fail(function () {
            alert("Cannot connect to server");
        })
    }




});