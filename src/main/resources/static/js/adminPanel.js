const showAllBooks = $("#showAllBooks");
const showAllBookstores = $("#showAllBookstores");
const showAllAuthorsDiv = $("#showAllAuthors");
const showAllGenresDiv = $("#showAllGenres");
const showAllUsers = $("#showAllUsers");
const showAllBooksButton = $("#showAllBooksButton");
const showAllBookstoresButton = $("#showAllBookstoreButton");
const showAllAuthorsButton = $("#showAllAuthorsButton");
const showAllGenresButton = $("#showAllGenresButton");
const showAllUsersButton = $("#showAllUsersButton");

$(document).ready(function () {


    showAllBooksButton.on("click", function () {
        console.log("SHOW ALL BOOKS");

    })
    showAllBookstoresButton.on("click", function () {
        showAllBookstores.html("");
        showAllBookstores.toggleClass("showDetails");
        showAllBookstoresFromDB();

    })
    showAllAuthorsButton.on("click", function () {
        showAllAuthorsDiv.html("");
        showAllAuthorsDiv.toggleClass("showDetails");
        showAllAuthors();

    })
    showAllGenresButton.on("click", function () {
        console.log("SHOW ALL GENRES");
        showAllGenresDiv.html("");
        showAllGenresDiv.toggleClass("showDetails");
        showAllGenres();

    })
    showAllUsersButton.on("click", function () {
        console.log("SHOW ALL USERS");

    })


});


function showAllBookstoresFromDB() {
    $.ajax({
        url: "http://localhost:8080/ebm/bookstore/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        showAllBookstores.html("");
        showAllBookstores.append($("<h2>Bookstores</h2><table id=\"table\" border=\"1\">" +
            "<tr><th>Name</th><th>Email</th><th>WEB</th><th colspan=\"2\">Actions</th>" +
            "</tr></table>"));

        for (const bookstore of json["data"]) {
            $("#table").append($(
                "<tr>" +
                "<td>" + bookstore.name + "</td>" +
                "<td>" + bookstore.email + "</td>" +
                // "<td>" + bookstore.web + "</td>" +
                "<td>" + "<button id=\"webLink" + bookstore.id + "\">Go to the bookstore's page </button></td>" +
                "<td>" + "<button id=\"deleteBookstore" + bookstore.id + "\">Delete Bookstore </button></td>" +
                "</tr>"));


            $("#webLink" + bookstore.id).on("click", {
                id: bookstore.id,
                web: bookstore.web
            }, function (event) {
                window.open(event.data.web, '_blank');
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

function showAllAuthors() {
    $.ajax({
        url: "http://localhost:8080/ebm/author/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        showAllAuthorsDiv.html("");
        showAllAuthorsDiv.html("<h2>Authors</h2><table id=\"authorsTable\" border=\"1\"><tr><th>First Name</th><th>Last Name</th><th colspan=\"3\">Detail</th></tr>");
        for (let author of json["data"]) {
            $("#authorsTable").append($("<tr><td>" + author.firstName + "</td><td>" + author.lastName +
                "<td><button id=\"deleteAuthorById" + author.id + "\">Delete Author </button></td>"
            ));
            $("#deleteAuthorById" + author.id).on("click", {
                id: author.id
            }, function (event) {
                deleteAuthorById(event.data.id);
            });
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


function showAllGenres() {
    $.ajax({
        url: "http://localhost:8080/ebm/genre/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        showAllGenresDiv.html("<h2>Genres</h2><table id=\"genrestable\" border=\"1\"><tr><th>Name</th><th>Description</th><th colspan=\"1\">Actions</th></tr></tr>");
        for (const genre of json["data"]) {
            $("#genrestable").append($("<tr><td>" + genre.name + "</td><td>" + genre.description + "</td>" +
                "<td><button id=\"deleteGenreById" + genre.id + "\">Delete genre </button></td>" +
                "</tr></table>"
            ));
            $("#deleteGenreById" + genre.id).on("click", {
                id: genre.id
            }, function (event) {
                deleteGenreById(event.data.id);
            });
        };

    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function deleteGenreById(genreToDeleteID) {
    $.ajax({
        url: "http://localhost:8080/ebm/genre/" + genreToDeleteID,
        type: "DELETE",
        dataType: "json"
    }).done(function (json) {
        if (json["code"] === "200") {
            alert("Genre has been deleted");
            showAllGenres();
        } else {
            alert("Cannot delete genre");
        }
    }).fail(function () {
        alert("Cannot connect to server");

    });
}