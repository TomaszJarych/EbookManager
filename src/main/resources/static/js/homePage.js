const booksTableDiv = $("#booksTable");
const addNewBookButton = $("#addNewBookButton");
const addNewBookForm = $("#addNewBookForm");
const editBookForm = $("#editBookForm");
const bookDetailsDiv = $("#bookDetailsDiv");

$(document).ready(function () {

    showBooksTable();

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
            authorsString += author.firstName + " " + author.lastName + "\n";
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
            console.log(json);

        }).fail(function () {
            alert("Cannot connect to server");

        });

        
    }
}