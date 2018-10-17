const bookstoreList = $("#bookstoreList");
const addNewBookstoreButton = $("#addNewBookstoreButton");
const bookstoreDetail = $("#bookstoreDetail");
const bookstoreForm = $("#bookstoreForm");


$(document).ready(function () {
    showAllBookstores();

    addNewBookstoreButton.on("click", function name() {
        showBookstoreForm();
    })
});

function showAllBookstores() {
    $.ajax({
        url: "http://localhost:8080/ebm/bookstore/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        bookstoreList.html("");
        bookstoreList.append($("<table class=\"table table-dark\" id=\"table\" border=\"1\">" +
            "<tr><th>Name</th><th>Email</th><th colspan=\"2\">WEB</th><th colspan=\"2\">Actions</th>" +
            "</tr></table>"));

        for (const bookstore of json["data"]) {
            $("#table").append($(
                "<tr>" +
                "<td>" + bookstore.name + "</td>" +
                "<td>" + bookstore.email + "</td>" +
                "<td>" + bookstore.web + "</td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"webLink" + bookstore.id + "\">Go to the bookstore's page </button></td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"editBookstore" + bookstore.id + "\">Edit Bookstore </button></td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"deleteBookstore" + bookstore.id + "\">Delete Bookstore </button></td>" +
                "</tr>"));


            $("#webLink" + bookstore.id).on("click", {
                id: bookstore.id,
                web: bookstore.web
            }, function (event) {
                window.open(event.data.web, '_blank');
            });
            $("#editBookstore" + bookstore.id).on("click", {
                id: bookstore.id
            }, function (event) {
                showBookstoreForm();
                getBookstorByIDToEdit(event.data.id);
            });
            $("#deleteBookstore" + bookstore.id).on("click", {
                id: bookstore.id

            }, function (event) {
                $.ajax({
                    url: "http://localhost:8080/ebm/bookstore/" + event.data.id,
                    type: "DELETE",
                    dataType: "json"
                }).done(function (json) {
                    if (json["code"] === "200") {
                        alert("Bookstore has been deleted");
                        showAllBookstores();
                    } else {
                        alert("Cannot delete Bookstore");
                    }
                }).fail(function () {
                    alert("Cannot connect to server");

                });

            });

        }
    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function showBookstoreForm() {
    bookstoreForm.toggleClass("showDetails");
    bookstoreForm.html("");
    bookstoreForm.append($("<div><h1>Add new Bookstore</h1></div><div id=\"newBookstoreErrors\"></div><form id=\"newBookstoreForm\" method=\"post\">" +
        "<div><label for=\"name\">Name: </label>" +
        "<input type=\"text\" name=\"name\" id=\"name\"></div><br><div>" +
        "<label for=\"email\">EMAIL: </label><input type=\"email\" name=\"email\" id=\"email\"></div><br>" +
        "<div><label for=\"web\">WEB: </label><input type=\"url\" name=\"web\" id=\"web\">" +
        "</div><br><input type=\"hidden\" name=\"id\" id=\"id\">" +
        "<div><input type=\"submit\" class=\"btn btn-dark active btnIndex \" id=\"subimtButton\" value=\"Add\"> <button class=\"btn btn-dark active btnIndex \" id=\"close\">Close </button></div></form>"));


    $("#subimtButton").on("click", function (event) {
        event.preventDefault();


        const idInput = $("#id").val();
        const nameInput = $("#name").val();
        const emailInput = $("#email").val();
        const webInput = $("#web").val();

        addNewBookstore(idInput, nameInput, emailInput, webInput);

    });

    $("#close").on("click", function () {
        newBookstoreErrors.html("");
        bookstoreForm.html("");
        bookstoreForm.toggleClass("showDetails");
    })

    function addNewBookstore(bookstoreID, bookstoreName, bookstoreEMAIL, bookstoreWEB) {
        const newBookstoreErrors = $("#newBookstoreErrors");
        $.ajax({
            headers: {
                'Content-Type': 'application/json'
            },
            url: "http://localhost:8080/ebm/bookstore",
            type: "POST",
            data: JSON.stringify({
                id: bookstoreID,
                name: bookstoreName,
                email: bookstoreEMAIL,
                web: bookstoreWEB,
            }),
            dataType: "json"
        }).done(function (json) {
            if (json["message"] !== "OK") {
                let data = json["data"];
                let errorsFromServer = json["error"].split(";");
                newBookstoreErrors.html("");
                for (let error of errorsFromServer) {
                    newBookstoreErrors.append($('<h3>' + error + '</h3>'));
                }
            } else {
                newBookstoreErrors.html("");
                bookstoreForm.html("");
                bookstoreForm.toggleClass("showDetails");
                showAllBookstores();
            }
        }).fail(function () {
            alert("Cannot connect to server");
        })
    }

}

function getBookstorByIDToEdit(bookstoreID) {
    $.ajax({
        url: "http://localhost:8080/ebm/bookstore/" + bookstoreID,
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        let bookstoreDetail = json["data"];

        $("#id").val(bookstoreDetail.id);
        $("#name").val(bookstoreDetail.name);
        $("#email").val(bookstoreDetail.email);
        $("#web").val(bookstoreDetail.web);

    }).fail(function () {
        alert("Cannot connect to server");
    });
}