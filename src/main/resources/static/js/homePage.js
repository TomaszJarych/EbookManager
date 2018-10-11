const booksTableDiv = $("#booksTable");
const addNewBookButton = $("#addNewBookButton");
const addNewBookForm = $("#addNewBookForm");
const editBookForm = $("#editBookForm");

$(document).ready(function () {

    showBooksTable();



});

function showBooksTable() {
    let output = "";
    const tableHtmlBeginning = "<table id=\"table\" border=\"1\">";
    const tableHtmlEnd = "</table>";

    output += tableHtmlBeginning;

    output += "<tr><th>Title</th><th>ISBN</th><th>Bookstore</th><th>Owner</th><th>Is Read?</th><th>Mark as Read</th><th>In Reader?</th><th>Is in Reader?</th></tr>";

    $.ajax({
        url: "http://localhost:8080/ebm/book/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        for (const book of json["data"]) {
            output +=
                "<tr><td>" + book.title + "</td>" +
                "<td>" + book.isbn + "</td>" +
                "<td>" + book.bookstore.name + "</td>" +
                "<td>" + book.owner.firstName + " " + book.owner.lastName + "</td>" +
                "<td>" + isRead(book.isRead) + "</td>" +
                "<td>" + "<button id=\"markAsRead" + book.id + "\">" + getMarkAsReadButton(book.isRead) + "</button>" + "</td>" +
                "<td>" + inReader(book.inReader) + "</td>" +
                "<td>" + "<button id=\"markIsInReader" + book.id + "\">" + markIsInReader(book.inReader) + "</button>" + "</td>";
            $("#markIsInReader" + book.id).on("click", {
                id: book.id
            }, function (event) {
                console.log("MarkIsInReader" + event.data.id);
            })
        }


        output += tableHtmlEnd;
        booksTableDiv.append($(output));

    }).fail(function () {
        alert("Cannot connect to server");

    });

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
}