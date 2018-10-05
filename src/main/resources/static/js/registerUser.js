$(document).ready(function () {

    const submitButton = $("#subimtButton");
    const idInput = $("#id");
    const loginInput = $("#login");
    const passwordInput = $("#password");
    const firstNameInput = $("#firstName");
    const lastNameInput = $("#lastName");
    const emailInput = $("#email");
    const userRoleInput = $("#userRole");
    const errorsDiv = $("#errors");

    submitButton.on("click", function (event) {
        event.preventDefault();
        registerUser(idInput.val(), loginInput.val(), firstNameInput.val(),
            lastNameInput.val(), emailInput.val(), userRoleInput.val(), passwordInput.val())

    })
});

function registerUser(userId, userLogin, userFirstName, userLastName, userEmail, userRole, userPassword) {
    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: "http://localhost:8080/ebm/user/",
        type: "POST",
        data: JSON.stringify({
            id: userId,
            login: userLogin,
            firstName: userFirstName,
            lastName: userLastName,
            email: userEmail,
            role: userRole,
            password: userPassword
        }),
        dataType: "json"
    }).done(function (json) {
        if (json["message"] !== "OK") {
            let data = json["data"];
            let errorsDiv = $("#errors");
            let errorsFromServer = json["error"].split(";");
            errorsDiv.html("");
            for (let error of errorsFromServer) {
                errorsDiv.append($('<h3>' + error + '</h3>'));
            }
        } else {
            location.href = "../index.html"
        }

    }).fail(function () {
        alert("Cannot connect to server");
    })
}