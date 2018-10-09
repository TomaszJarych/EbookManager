const submitButton = $("#subimtButton");
const firstNameInput = $("#firstName");
const lastNameInput = $("#lastName");
const idInput = $("#id");
const errorsDiv = $("#errors");

$(document).ready(function () {
    submitButton.on("click", function (event) {
        event.preventDefault();
        addNewAuthor(idInput.val(), firstNameInput.val(), lastNameInput.val());
    })


})

function addNewAuthor(authorId, authorFirstName, authorLastName) {
    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: "http://localhost:8080/ebm/author/",
        type: "POST",
        data: JSON.stringify({
            id: authorId,
            firstName: authorFirstName,
            lastName: authorLastName,
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
            location.href = "authors.html"
        }

    }).fail(function () {
        alert("Cannot connect to server");
    })

}