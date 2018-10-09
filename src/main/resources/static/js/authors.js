const button = $("#newAuthorButton");
const table = $("#authorsTable");
const bookTableDiv = $("#showBooksDiv");
const bookTable = $("#bookTable");
$(document).ready(function () {

    showAllAuthors();

    button.on("click", function () {
        location.href = "./addAuthorForm.html"
    });


});

function showAllAuthors() {
    $.ajax({
        url: "http://localhost:8080/ebm/author/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        for (let author of json["data"]) {
            table.append($("<tr><td>" + author.firstName + "</td><td>" + author.lastName +
                "</td><td><button id=\"authorID" + author.id + "\">Show books </button></td>" +
                "<td><button id=\"booksByAuthorId" + author.id + "\">Show books </button></td></tr>"));
            $("#authorID" + author.id).on("click", {
                id: author.id
            }, function (event) {
                bookTableDiv.removeClass("showDetails");
                getBooksByAuthor(event.data.id);

            });
            $("#booksByAuthorId" + author.id).on("click", {
                id: author.id
            }, function (event) {
                console.log("Pyk! Get Books");

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
        bookTable.append($("<tr><td>Title</td><td>ISBN</td><td>Bookstore</td><td>Owner:</td></tr>"))
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