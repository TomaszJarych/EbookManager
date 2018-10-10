const button = $("#newAuthorButton");
const table = $("#authorsTable");
const bookTableDiv = $("#showBooksDiv");
const bookTable = $("#bookTable");
const editAuthorDiv = $("#editAuthorDiv");
const editAuthorErrorDiv = $("#editAuthorErrors");

$(document).ready(function () {

    showAllAuthors();

    button.on("click", function () {
        editAuthorDiv.html("");
        showAddForm();

    });
});

function showAllAuthors() {
    $.ajax({
        url: "http://localhost:8080/ebm/author/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        table.html("<tr><th>First Name</th><th>Last Name</th><th colspan=\"3\">Detail</th></tr>");
        for (let author of json["data"]) {
            table.append($("<tr><td>" + author.firstName + "</td><td>" + author.lastName +
                "</td><td><button id=\"authorID" + author.id + "\">Show books </button></td>" +
                "<td><button id=\"deleteAuthorById" + author.id + "\">Delete Author </button></td>" +
                "<td><button id=\"editAuthorById" + author.id + "\">Edit Author </button></td></tr>"));
            $("#authorID" + author.id).on("click", {
                id: author.id
            }, function (event) {
                bookTableDiv.removeClass("showDetails");
                getBooksByAuthor(event.data.id);

            });
            $("#deleteAuthorById" + author.id).on("click", {
                id: author.id
            }, function (event) {
                deleteAuthorById(event.data.id);
            });
            $("#editAuthorById" + author.id).on("click", {
                id: author.id
            }, function (event) {
                editAuthorDiv.html("");
                showEditForm(event.data.id);

            });

        }

    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function getBooksByAuthor(authorId) {
    $.ajax({
        url: "http://localhost:8080/ebm/book/booksByAuthorsId/" + authorId,
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        bookTable.html("");
        bookTable.append($("<tr><th>Title</th><th>ISBN</th><th>Bookstore</th><th>Owner</th></tr>"))
        for (const book of json["data"]) {
            bookTable.append($(
                "<tr><td>" + book.title + "</td>" +
                "<td>" + book.isbn + "</td>" +
                "<td>" + book.bookstore.name + "</td>" +
                "<td>" + book.owner.firstName + " " + book.owner.lastName + "</td>"))
        }
    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function deleteAuthorById(authorToDeleteID) {
    $.ajax({
        url: "http://localhost:8080/ebm/author/" + authorToDeleteID,
        type: "DELETE",
        dataType: "json"
    }).done(function (json) {
        if (json["code"] === "200") {
            alert("Author has been deleted");
            showAllAuthors();
        } else {
            alert("Cannot delete author");
        }
    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function editAuthor(authorId, authorFirstName, authorLastName) {
    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        url: "http://localhost:8080/ebm/author",
        type: "PUT",
        data: JSON.stringify({
            id: authorId,
            firstName: authorFirstName,
            lastName: authorLastName,
        }),
        dataType: "json"
    }).done(function (json) {
        if (json["message"] !== "OK") {
            let data = json["data"];
            let errorsDiv = $("#editAuthorErrors");
            let errorsFromServer = json["error"].split(";");
            errorsDiv.html("");
            for (let error of errorsFromServer) {
                errorsDiv.append($('<h3>' + error + '</h3>'));
            }
        } else {
            editAuthorDiv.html("");
            editAuthorDiv.toggleClass("showDetails");
            showAllAuthors();
        }
    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function showEditForm(authorId) {
    editAuthorDiv.append($("<form method=\"post\"><div><label for=\"firstName\">First name:</label>" +
        "<input type=\"text\" name=\"firstName\" id=\"firstName\"></div><br>" +
        "<div><label for=\"lastName\">Last name:</label><input type=\"text\" name=\"lastName\" id=\"lastName\"></div><br>" +
        "<div><input type=\"hidden\" name=\"id\" id=\"id\"><input type=\"submit\" id=\"subimtButton\" value=\"Register\"></div></form>"));
    editAuthorDiv.toggleClass("showDetails")
    getAuthorById(authorId);

    const submitButton = $("#subimtButton");
    const firstNameInput = $("#firstName");
    const lastNameInput = $("#lastName");
    const idInput = $("#id");
    const errorsDiv = $("#errors");

    submitButton.on("click", function (event) {
        event.preventDefault();
        editAuthor(idInput.val(), firstNameInput.val(), lastNameInput.val());
    })

    function getAuthorById(authorId) {
        $.ajax({
            url: "http://localhost:8080/ebm/author/" + authorId,
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            firstNameInput.val(json["data"].firstName);
            lastNameInput.val(json["data"].lastName);
            idInput.val(json["data"].id)
        }).fail(function () {
            alert("Cannot connect to server");

        });
    }
}

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

function showAddForm() {
    editAuthorDiv.append($("<form method=\"post\"><div><label for=\"firstName\">First name:</label>" +
        "<input type=\"text\" name=\"firstName\" id=\"firstName\"></div><br>" +
        "<div><label for=\"lastName\">Last name:</label><input type=\"text\" name=\"lastName\" id=\"lastName\"></div><br>" +
        "<div><input type=\"hidden\" name=\"id\" id=\"id\"><input type=\"submit\" id=\"subimtButton\" value=\"Register\"></div></form>"));
    editAuthorDiv.toggleClass("showDetails")


    const submitButton = $("#subimtButton");
    const firstNameInput = $("#firstName");
    const lastNameInput = $("#lastName");
    const idInput = $("#id");
    const errorsDiv = $("#errors");

    submitButton.on("click", function (event) {
        event.preventDefault();
        addNewAuthor(idInput.val(), firstNameInput.val(), lastNameInput.val());
        editAuthorDiv.html("");
        editAuthorDiv.toggleClass("showDetails");
        showAllAuthors();
    })

}