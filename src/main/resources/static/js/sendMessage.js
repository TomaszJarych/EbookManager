const messageFormDiv = $("#messageFormDiv");

$(document).ready(function () {

    showMessageForm();


});

function showMessageForm() {
    messageFormDiv.html("");
    messageFormDiv.toggleClass("showDetails");
    messageFormDiv.append($("<form  method=\"post\"><div><div id=\"errors\"></div>" +
        "<label for=\"subject\">Subject </label><input type=\"text\" name=\"subject\" id=\"subject\"></div><br></div>" +
        "<div><label for=\"content\">Content </label><textarea rows=\"8\" cols=\"70\" name=\"content\" id=\"content\"/></div><br></div><br>" +
        "<div><label for=\"reciever\">Reciever </label><select rows=\"8\" cols=\"70\" name=\"reciever\" id=\"reciever\"><option value=\"\">Select reciever from the list</option></select></div><br></div>" +
        "<div><input type=\"submit\" id=\"subimtButton\" value=\"Send\"></div>" +
        "</form><br>"));


    $.ajax({
        url: "http://localhost:8080/ebm/user/getRecieversList",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        for (const user of json["data"]) {
            $("#reciever").append($("<option value=" + user.email + ">" + user.firstName + " " + user.lastName + "</option>"))
        }
    }).fail(function () {
        alert("Cannot connect to server");

    });

    $("#subimtButton").on("click", function name(event) {
        event.preventDefault();
        console.log("SUBMIT BUTTON");
        const subjectInput = $("#subject").val();
        const contentInput = $("#content").val();
        const recieverInput = $("#reciever").val();

        console.log("Subject: " + subjectInput);
        console.log("Reciever " + recieverInput);
        console.log("Content " + contentInput);
        if (recieverInput === null || recieverInput === "") {
            alert("Please select the Reciever");

        } else {
            $.ajax({
                headers: {
                    'Content-Type': 'application/json'
                },
                url: "http://localhost:8080/ebm/message/sendMail",
                type: "POST",
                data: JSON.stringify({
                    subject: subjectInput,
                    reciever: recieverInput,
                    content: contentInput,
                }),
                dataType: "json"
            }).done(function (json) {
                console.log(json);

                if (json["message"] !== "OK") {
                    let data = json["data"];
                    let errorsDiv = $("#errors");
                    let errorsFromServer = json["error"].split(";");
                    errorsDiv.html("");
                    for (let error of errorsFromServer) {
                        errorsDiv.append($('<h3>' + error + '</h3>'));
                    }
                }
                if (json["message"] === "You're not logged. Please logg in") {
                    alert("There is no logged User, please log in")
                } else {
                    alert("Message has been sent");
                    location.href = "sendMessage.html"
                }
            }).fail(function () {
                alert("Cannot connect to server");
            })
        }
    })
}