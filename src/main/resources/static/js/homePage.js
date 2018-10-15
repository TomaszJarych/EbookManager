const booksTableDiv = $("#booksTable");
const addNewBookButton = $("#addNewBookButton");
const addNewBookForm = $("#addNewBookForm");
const editBookForm = $("#editBookForm");
const bookDetailsDiv = $("#bookDetailsDiv");
const sortByTitleButton = $("#sortByTitle");
const sortByCreatedDate = $("#sortByCreatedDate");
const findBooksByTitleButton = $("#findBooksByTitleButton");
const findBooksByTitleDiv = $("#findBooksByTitleDiv");

$(document).ready(function () {

    showBooksTable();

    addNewBookButton.on("click", function () {
        addNewBookForm.toggleClass("showDetails");
        showAddNewBookForm();
    });

    sortByTitleButton.on("click", function () {
        showBooksTable("http://localhost:8080/ebm/book/booksByTitle");

    });
    sortByCreatedDate.on("click", function () {
        showBooksTable("http://localhost:8080/ebm/book/booksByCreatedDate");

    });

    findBooksByTitleButton.on("click", function () {
        findBooksByTitleDiv.toggleClass("showDetails");
        showBooksByTitle();

    })
});

function showBooksTable(urlAddress) {

    if (urlAddress === undefined || urlAddress === null) {
        urlAddress = "http://localhost:8080/ebm/book/allByOwner";
    }

    $.ajax({
        url: urlAddress,
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
                "<td>" + "<button id=\"deleteBook" + book.id + "\">Delete book </button></td>" +
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
                showBookDetails(event.data.id);
            });
            $("#editBook" + book.id).on("click", {
                id: book.id
            }, function (event) {
                addNewBookForm.toggleClass("showDetails");
                showAddNewBookForm();
                insertBookDetailIntoForm(event.data.id);
            });
            $("#deleteBook" + book.id).on("click", {
                id: book.id
            }, function (event) {
                $.ajax({
                    url: "http://localhost:8080/ebm/book/" + event.data.id,
                    type: "DELETE",
                    dataType: "json"
                }).done(function (json) {
                    if (json["code"] === "200") {
                        alert("Book has been deleted");
                        showBooksTable();
                    } else {
                        alert("Cannot delete Book");
                    }
                }).fail(function () {
                    alert("Cannot connect to server");

                });
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
    addNewBookForm.append($("<div><h1>Add new Book</h1></div><div id=\"newBookErrors\"></div><form id=\"newBookForm\" method=\"post\"><div><label for=\"title\">Title:</label>" +
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
    const newBookErrorDiv = $("#newBookErrors");


    $("#subimtButton").on("click", function (event) {
        const idInput = $("#id").val();
        const titleInput = $("#title").val();
        const isbnInput = $("#isbn").val();
        const genresInput = $("#genres").val();
        const bookstoreInput = $("#bookstore").val();
        const authorsInput = $("#authors").val();
        let userId = sessionStorage.getItem("loggedUserId");
        event.preventDefault();

        addNewBook(idInput, titleInput, isbnInput, userId, bookstoreInput, getObjectsFromMultipleSelectInput(genresInput),
            getObjectsFromMultipleSelectInput(authorsInput));

    })

    function addNewBook(id, insertedTitle, insertedISBN, ownerID, bookstoreId, insertedGenres, insertedAuthors) {
        $.ajax({
            headers: {
                'Content-Type': 'application/json'
            },
            url: "http://localhost:8080/ebm/book",
            type: "POST",
            data: JSON.stringify({
                id: id,
                title: insertedTitle,
                isbn: insertedISBN,
                owner: {
                    id: ownerID
                },
                bookstore: {
                    id: bookstoreId
                },
                genres: insertedGenres,
                authors: insertedAuthors,
            }),
            dataType: "json"
        }).done(function (json) {
            if (json["message"] !== "OK") {
                let data = json["data"];
                let errorsFromServer = json["error"].split(";");
                newBookErrorDiv.html("");
                for (let error of errorsFromServer) {
                    newBookErrorDiv.append($('<h3>' + error + '</h3>'));
                }
            } else {
                newBookErrorDiv.html("");
                addNewBookForm.html("");
                addNewBookForm.toggleClass("showDetails");
                showBooksTable();
            }
        }).fail(function () {
            alert("Cannot connect to server");
        })
    }
}


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

function getObjectsFromMultipleSelectInput(input) {
    let genresId = [];
    for (const data of input) {
        genresId.push({
            id: data
        })
    }
    return genresId;
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

function insertBookDetailIntoForm(bookID) {
    $.ajax({
        url: "http://localhost:8080/ebm/book/" + bookID,
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        const bookDetail = json["data"];
        const idInput = $("#id").val(bookDetail.id);
        const titleInput = $("#title").val(bookDetail.title);
        const isbnInput = $("#isbn").val(bookDetail.isbn);
        const genresInput = $("#genres").val(bookDetail.genres);
        const bookstoreInput = $("#bookstore").val(bookDetail.bookstore.id);
        const authorsInput = $("#authors").val(bookDetail.authors);


    }).fail(function () {
        alert("Cannot connect to server");

    });

}

function showBooksByTitle() {
    findBooksByTitleDiv.append($("<form method=\"get\">" +
        "<label for=\"searchByTitle\">Enter text to search: </label>" +
        "<input type=\"text\" name=\"searchByTitle\" id=\"searchByTitle\">" +
        "<input type=\"submit\" value=\"Search\" id=\"searchByTitleSubmit\">" +
        "</form>"))

    $("#searchByTitleSubmit").on("click", function (event) {
        event.preventDefault();
        const searchInput = $("#searchByTitle").val();
        if (searchInput === null || searchInput === "") {
            alert("There is no input to check");
        } else {
            showBooksTable("http://localhost:8080/ebm/book/searchBooksByInput/" + searchInput);

        }


    })


}