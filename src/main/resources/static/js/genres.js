const genresTable = $("#genresTable");
const addNewGenresButton = $("#addNewGenresButton");
const bookTableDiv = $("#showBooksDiv");
const bookTable = $("#bookTable");
const newGenreForm = $("#addNewGenreForm");
const editGenreForm = $("#editGenreForm");
const errorsNewGenreDiv = $("#newGenreErrors");

$(document).ready(function () {

    showAllGenres();

    addNewGenresButton.on("click", function () {
        newGenreForm.html("");
        errorsNewGenreDiv.html("");
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
                "<td><button id=\"showBooksByGenre" + genre.id + "\">Show books </button></td>" + "</tr>"
            ));
            $("#showBooksByGenre" + genre.id).on("click", {
                id: genre.id
            }, function (event) {
                getBooksByGenre(event.data.id);
                bookTableDiv.toggleClass("showDetails");
            })

        };

        // for (let genre of json["data"]) {
        //     table.append($("<tr><td>" + author.firstName + "</td><td>" + author.lastName +
        //         "</td><td><button id=\"authorID" + author.id + "\">Show books </button></td>" +
        //         "<td><button id=\"deleteAuthorById" + author.id + "\">Delete Author </button></td>" +
        //         "<td><button id=\"editAuthorById" + author.id + "\">Edit Author </button></td></tr>"));
        //     $("#authorID" + author.id).on("click", {
        //         id: author.id
        //     }, function (event) {
        //         bookTableDiv.removeClass("showDetails");
        //         getBooksByAuthor(event.data.id);

        //     });
        //     $("#deleteAuthorById" + author.id).on("click", {
        //         id: author.id
        //     }, function (event) {
        //         deleteAuthorById(event.data.id);
        //     });
        //     $("#editAuthorById" + author.id).on("click", {
        //         id: author.id
        //     }, function (event) {
        //         editAuthorDiv.html("");
        //         showEditForm(event.data.id);

        //     });

        // }

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
        console.log(json);

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

function showAddForm() {
    newGenreForm.append($("<form method=\"post\"><div><label for=\"genreName\">Name</label>" +
        "<input type=\"text\" name=\"genreName\" id=\"genreName\"></div><br>" +
        "<div><label for=\"genreDescription\">Description:</label><input type=\"text\" name=\"genreDescription\" id=\"genreDescription\"></div><br>" +
        "<div><input type=\"hidden\" name=\"id\" id=\"id\"><input type=\"submit\" id=\"subimtButton\" value=\"Register\"></div></form>"));
    newGenreForm.toggleClass("showDetails")


    const submitButton = $("#subimtButton");
    const genreName = $("#genreName");
    const genreDescription = $("#genreDescription");
    const errorsNewGenreDiv = $("#errors");

    submitButton.on("click", function (event) {
        event.preventDefault();
        addNewGenre(genreName.val(), genreDescription.val());
    })

}