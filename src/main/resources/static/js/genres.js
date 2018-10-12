const genresTable = $("#genresTable");
const addNewGenresButton = $("#addNewGenresButton");
const bookTableDiv = $("#showBooksDiv");
const bookTable = $("#bookTable");
const newGenreForm = $("#addNewGenreForm");
const editGenreForm = $("#editGenreForm");

$(document).ready(function () {

    showAllGenres();

    addNewGenresButton.on("click", function () {
        newGenreForm.html("");
        showAddForm();
    })

});

function showAllGenres() {
    $.ajax({
        url: "http://localhost:8080/ebm/genre/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        genresTable.html("<tr><th>Name</th><th>Description</th><th colspan=\"3\">Detail</th></tr></tr>");
        for (const genre of json["data"]) {
            genresTable.append($("<tr><td>" + genre.name + "</td><td>" + genre.description + "</td>" +
                "<td><button id=\"showBooksByGenre" + genre.id + "\">Show books </button></td>" +
                "<td><button id=\"deleteGenreById" + genre.id + "\">Delete genre </button></td>" +
                "<td><button id=\"editGenreById" + genre.id + "\">Edit Author </button></td>" +

                "</tr>"
            ));
            $("#showBooksByGenre" + genre.id).on("click", {
                id: genre.id
            }, function (event) {
                getBooksByGenre(event.data.id);
                bookTableDiv.toggleClass("showDetails");
            });
            $("#deleteGenreById" + genre.id).on("click", {
                id: genre.id
            }, function (event) {
                deleteGenreById(event.data.id);
            });
            $("#editGenreById" + genre.id).on("click", {
                id: genre.id
            }, function (event) {
                editGenreForm.html("");
                showEditGenreForm(event.data.id);

            })

        };

    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function getBooksByGenre(genreId) {
    $.ajax({
        url: "http://localhost:8080/ebm/book/booksByGenresId/" + genreId,
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

function showAddForm() {
    newGenreForm.append($("<div id=\"newGenreErrors\"></div><h2>New genre</h2><form method=\"post\"><div><label for=\"genreName\">Name</label>" +
        "<input type=\"text\" name=\"genreName\" id=\"genreName\"></div><br>" +
        "<div><label for=\"genreDescription\">Description:</label><input type=\"text\" name=\"genreDescription\" id=\"genreDescription\"></div><br>" +
        "<div><input type=\"hidden\" name=\"id\" id=\"id\"><input type=\"submit\" id=\"subimtButton\" value=\"Register\"></div></form>"));
    newGenreForm.toggleClass("showDetails")


    const submitButton = $("#subimtButton");
    const genreName = $("#genreName");
    const genreDescription = $("#genreDescription");
    const errorsNewGenreDiv = $("#newGenreErrors");

    submitButton.on("click", function (event) {
        event.preventDefault();
        addNewGenre(genreName.val(), genreDescription.val());
    })

    function addNewGenre(genreName, genreDescription) {
        $.ajax({
            headers: {
                'Content-Type': 'application/json'
            },
            url: "http://localhost:8080/ebm/genre",
            type: "POST",
            data: JSON.stringify({
                id: null,
                name: genreName,
                description: genreDescription,
            }),
            dataType: "json"
        }).done(function (json) {
            if (json["message"] !== "OK") {
                let data = json["data"];
                let errorsFromServer = json["error"].split(";");
                errorsNewGenreDiv.html("");
                for (let error of errorsFromServer) {
                    errorsNewGenreDiv.append($('<h3>' + error + '</h3>'));
                }
            } else {
                errorsNewGenreDiv.html("");
                newGenreForm.html("");
                newGenreForm.toggleClass("showDetails");
                showAllGenres();
            }
        }).fail(function () {
            alert("Cannot connect to server");
        })
    }

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

function showEditGenreForm(id) {
    editGenreForm.append($("<div id=\"editGenreErrors\"></div><h2>Edit genre</h2><form method=\"post\"><div><label for=\"genreName\">Name</label>" +
        "<input type=\"text\" name=\"genreName\" id=\"genreName\"></div><br>" +
        "<div><label for=\"genreDescription\">Description:</label><input type=\"text\" name=\"genreDescription\" id=\"genreDescription\"></div><br>" +
        "<div><input type=\"hidden\" name=\"id\" id=\"genreId\"><input type=\"submit\" id=\"subimtButton\" value=\"Register\"></div></form>"));

    const submitButton = $("#subimtButton");
    const genreName = $("#genreName");
    const genreDescription = $("#genreDescription");
    const editGenreErrors = $("#editGenreErrors");
    const idInput = $("#genreId");

    editGenreForm.toggleClass("showDetails");
    getGenreById(id);

    submitButton.on("click", function (event) {
        event.preventDefault();
        editGenrePUT(idInput.val(), genreName.val(), genreDescription.val())
    })

    function getGenreById(genreId) {
        $.ajax({
            url: "http://localhost:8080/ebm/genre/" + genreId,
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            genreName.val(json["data"].name);
            genreDescription.val(json["data"].description);
            idInput.val(json["data"].id)
        }).fail(function () {
            alert("Cannot connect to server");
        });
    }

    function editGenrePUT(genreId, genreName, genreDescription) {
        $.ajax({
            headers: {
                'Content-Type': 'application/json'
            },
            url: "http://localhost:8080/ebm/genre",
            type: "PUT",
            data: JSON.stringify({
                id: genreId,
                name: genreName,
                description: genreDescription,
            }),
            dataType: "json"
        }).done(function (json) {
            if (json["message"] !== "OK") {
                let errorsFromServer = json["error"].split(";");
                editGenreErrors.html("");
                for (let error of errorsFromServer) {
                    editGenreErrors.append($('<h3>' + error + '</h3>'));
                }
            } else {
                editGenreErrors.html("");
                editGenreForm.html("");
                editGenreForm.toggleClass("showDetails");
                showAllGenres();
            }
        }).fail(function () {
            alert("Cannot connect to server");

        });
    }

}