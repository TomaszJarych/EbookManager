const booksTableDiv = $("#booksTable");
const addNewBookButton = $("#addNewBookButton");
const addNewBookForm = $("#addNewBookForm");
const editBookForm = $("#editBookForm");
const bookDetailsDiv = $("#bookDetailsDiv");

$(document).ready(function () {

    showBooksTable();

    addNewBookButton.on("click", function () {
        console.log("ADD new Button Click");
        addNewBookForm.toggleClass("showDetails")
        showAddNewBookForm();

    })

});

function showBooksTable() {
    $.ajax({
        url: "http://localhost:8080/ebm/book/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        booksTableDiv.html("");

        booksTableDiv.append($("<table id=\"table\" border=\"1\">" +
            "<tr><th>Title</th><th>Authors</th><th>ISBN</th><th>Bookstore</th><th>Owner</th><th>Is Read?</th><th>Mark as Read</th>" +
            "<th>In Reader?</th><th>Is in Reader?</th>" +
            "<th colspan=\"3\">Actions</th>" +
            "</tr></table>"));

        for (const book of json["data"]) {
            $("#table").append($(
                "<tr><td>" + book.title + "</td>" +
                "<td>" + getAuthors(book.authors) + "</td>" +
                "<td>" + book.isbn + "</td>" +
                "<td>" + book.bookstore.name + "</td>" +
                "<td>" + book.owner.firstName + " " + book.owner.lastName + "</td>" +
                "<td>" + isRead(book.isRead) + "</td>" +
                "<td>" + "<button id=\"markAsRead" + book.id + "\">" + getMarkAsReadButton(book.isRead) + "</button>" + "</td>" +
                "<td>" + inReader(book.inReader) + "</td>" +
                "<td>" + "<button id=\"markIsInReader" + book.id + "\">" + markIsInReader(book.inReader) + "</button>" + "</td>" +
                "<td>" + "<button id=\"showBookDetail" + book.id + "\">Show book details </button></td>" +
                "<td>" + "<button id=\"editBook" + book.id + "\">Edit book </button></td>" +
                "<td>" + "<button id=\"deleteBook" + book.id + "\">delete book </button></td>" +
                "</tr>"));

            $("#markAsRead" + book.id).on("click", {
                id: book.id
            }, function (event) {
                setBookIsRead(event.data.id);
            });

            $("#markIsInReader" + book.id).on("click", {
                id: book.id
            }, function (event) {
                setBookInReader(event.data.id);
            });
            $("#showBookDetail" + book.id).on("click", {
                id: book.id
            }, function (event) {
                console.log("showBookDetail" + event.data.id);
                showBookDetails(event.data.id);
            });
            $("#editBook" + book.id).on("click", {
                id: book.id
            }, function (event) {
                console.log("editBook" + event.data.id);
            });
            $("#deleteBook" + book.id).on("click", {
                id: book.id
            }, function (event) {
                console.log("deleteBook" + event.data.id);
            });

        }

    }).fail(function () {
        alert("Cannot connect to server");

    });

    function getAuthors(arrayOfAuthors) {
        if (arrayOfAuthors === null) {
            return "";
        }
        let authorsString = "";
        for (const author of arrayOfAuthors) {
            authorsString += author.firstName + " " + author.lastName + "; ";
        }
        return authorsString;

    }

    function isRead(isRead) {
        if (isRead) {
            return "Yes";
        } else {
            return "No";
        }
    }

    function getMarkAsReadButton(isRead) {
        if (isRead) {
            return "Mark as Unread";
        } else {
            return "Mark as Read";
        }
    }

    function inReader(inReader) {
        if (inReader) {
            return "Yes";
        } else {
            return "No";
        }
    }

    function markIsInReader(inReader) {
        if (inReader) {
            return "Removed from reader";
        } else {
            return "Send to reader";
        }
    }

    function setBookIsRead(bookId) {
        $.ajax({
            url: "http://localhost:8080/ebm/book/setIsRead/" + bookId,
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            if (json["message"] === "OK") {
                alert("Book IsRead status has been changed");
                showBooksTable();

            } else {
                alert("Book IsRead status can't be changed");
            }


        }).fail(function () {
            alert("Cannot connect to server");

        });

    }

    function setBookInReader(bookId) {
        $.ajax({
            url: "http://localhost:8080/ebm/book/setInReader/" + bookId,
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            if (json["message"] === "OK") {
                alert("Book InReader status has been changed");
                showBooksTable();
            } else {
                alert("Book InReader status can't be changed");
            }


        }).fail(function () {
            alert("Cannot connect to server");

        });

    }

    function showBookDetails(bookID) {
        $.ajax({
            url: "http://localhost:8080/ebm/book/" + bookID,
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            const bookDetail = json["data"];
            bookDetailsDiv.toggleClass("showDetails");
            bookDetailsDiv.html("");
            bookDetailsDiv.append($("<div><h3>Authors</h3><h4>" + getAuthors(bookDetail.authors) + "</h4></div>"));
            bookDetailsDiv.append($("<div><h3>Title</h3><h4>\"" + bookDetail.title + "\"</h4></div>"));
            bookDetailsDiv.append($("<div><h3>Bookstore</h3><h4>" + bookDetail.bookstore.name + "</h4></div>"));
            bookDetailsDiv.append($("<div><h3>ISBN</h3><h4>" + bookDetail.isbn + "</h4></div>"));
            bookDetailsDiv.append($("<div><h3>Genres</h3><h4>" + getGenres(bookDetail.genres) + "</h4></div>"));
            bookDetailsDiv.append($("<div><h3>Owner</h3><h4>" + bookDetail.owner.firstName + " " + bookDetail.owner.lastName + "</h4></div>"));
            bookDetailsDiv.append($("<div><h3>Created</h3><h4>" + new Date(bookDetail.created).toLocaleString() + "</h4></div>"));
            bookDetailsDiv.append($("<div><button id=\"close\">Close </button></div>"));

            $("#close").on("click", function () {
                bookDetailsDiv.toggleClass("showDetails");
                bookDetailsDiv.html("");
            })

        }).fail(function () {
            alert("Cannot connect to server");

        });

        function getGenres(arrayOfGenres) {
            let genresString = "";
            for (const genre of arrayOfGenres) {
                genresString += genre.name + " \n";
            }
            return genresString;
        }

    }
}

function showAddNewBookForm() {
    addNewBookForm.html("");
    addNewBookForm.append($("<div><h1>Add new Book</h1></div><form id=\"newBookForm\" method=\"post\"><div><label for=\"title\">Title:</label>" +
        "<input type=\"text\" name=\"title\" id=\"title\"></div><br><div>" +
        "<label for=\"isbn\">ISBN:</label><input type=\"text\" name=\"isbn\" id=\"isbn\">" +
        "</div><br><input type=\"hidden\" name=\"id\" id=\"id\">" +
        "<div><label for=\"genres\">Genres: </label><select name=\"genres\" multiple=\"multiple\" id=\"genres\"></select></div><br>" +
        "<div><label for=\"bookstore\">Bookstore </label><select name=\"bookstore\" id=\"bookstore\"></select></div><br></br>" +
        "<div><label for=\"authors\">Authors: </label><select name=\"authors\" multiple=\"multiple\" id=\"authors\"></select></div><br>" +
        "<input type=\"submit\" id=\"subimtButton\" value=\"Add\"></div></form>"));

    getGenresFromDB();
    getBookstoreFromDB();
    getAuthorsFromDB();



    function getGenresFromDB() {
        $.ajax({
            url: "http://localhost:8080/ebm/genre/all",
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            for (const genre of json["data"]) {
                $("#genres").append($("<option value=\"" + genre.id + "\">" + genre.name + "</option>"))

            };
        }).fail(function () {
            alert("Cannot connect to server");

        });
    }

    function getBookstoreFromDB() {
        $.ajax({
            url: "http://localhost:8080/ebm/bookstore/all",
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            console.log(json["data"]);

            for (const bookstore of json["data"]) {
                $("#bookstore").append($("<option value=\"" + bookstore.id + "\">" + bookstore.name + "</option>"))
            };
        }).fail(function () {
            alert("Cannot connect to server");

        });
    }

    function getAuthorsFromDB() {
        $.ajax({
            url: "http://localhost:8080/ebm/author/all",
            type: "GET",
            dataType: "json"
        }).done(function (json) {
            for (const author of json["data"]) {
                $("#authors").append($("<option value=\"" + author.id + "\">" + author.firstName + " " + author.lastName + "</option>"))

            };
        }).fail(function () {
            alert("Cannot connect to server");

        });
    }


}