const showAllBooksDiv = $("#showAllBooks");
const showAllBookstores = $("#showAllBookstores");
const showAllAuthorsDiv = $("#showAllAuthors");
const showAllGenresDiv = $("#showAllGenres");
const showAllUsersDiv = $("#showAllUsers");
const showAllBooksButton = $("#showAllBooksButton");
const showAllBookstoresButton = $("#showAllBookstoreButton");
const showAllAuthorsButton = $("#showAllAuthorsButton");
const showAllGenresButton = $("#showAllGenresButton");
const showAllUsersButton = $("#showAllUsersButton");

$(document).ready(function () {


    showAllBooksButton.on("click", function () {
        showAllBooksDiv.html("");
        showAllBooksDiv.toggleClass("showDetails");
        showBooksTable();

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
        showAllGenresDiv.html("");
        showAllGenresDiv.toggleClass("showDetails");
        showAllGenres();

    })
    showAllUsersButton.on("click", function () {
        showAllUsersDiv.html("");
        showAllUsersDiv.toggleClass("showDetails");
        showAllUsers();

    })


});


function showAllBookstoresFromDB() {
    $.ajax({
        url: "http://localhost:8080/ebm/bookstore/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        showAllBookstores.html("");
        showAllBookstores.append($("<h2>Bookstores</h2><table class=\"table table-dark\" id=\"table\" border=\"1\">" +
            "<tr><th>Name</th><th>Email</th><th>WEB</th><th colspan=\"2\">Actions</th>" +
            "</tr></table>"));

        for (const bookstore of json["data"]) {
            $("#table").append($(
                "<tr>" +
                "<td>" + bookstore.name + "</td>" +
                "<td>" + bookstore.email + "</td>" +
                "<td>" + "<button  class=\"btn btn-dark active btnIndex \" id=\"webLink" + bookstore.id + "\">Go to the bookstore's page </button></td>" +
                "<td>" + "<button  class=\"btn btn-dark active btnIndex \" id=\"deleteBookstore" + bookstore.id + "\">Delete Bookstore </button></td>" +
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
        showAllAuthorsDiv.html("<h2>Authors</h2><table class=\"table table-dark\" id=\"authorsTable\" border=\"1\"><tr><th>First Name</th><th>Last Name</th><th colspan=\"3\">Detail</th></tr></table>");
        for (let author of json["data"]) {
            $("#authorsTable").append($("<tr><td>" + author.firstName + "</td><td>" + author.lastName +
                "<td><button class=\"btn btn-dark active btnIndex \" id=\"deleteAuthorById" + author.id + "\">Delete Author </button></td>"
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
        showAllGenresDiv.html("<h2>Genres</h2><table class=\"table table-dark\" id=\"genrestable\" border=\"1\"><tr><th>Name</th><th>Description</th><th colspan=\"1\">Actions</th></tr></tr></table>");
        for (const genre of json["data"]) {
            $("#genrestable").append($("<tr><td>" + genre.name + "</td><td>" + genre.description + "</td>" +
                "<td><button class=\"btn btn-dark active btnIndex \" id=\"deleteGenreById" + genre.id + "\">Delete genre </button></td>" +
                "</tr>"
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

function showBooksTable() {

    $.ajax({
        url: "http://localhost:8080/ebm/book/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        showAllBooksDiv.html("");

        showAllBooksDiv.append($("<h2>Books</h2><table class=\"table table-dark\" id=\"booksTable\" border=\"1\">" +
            "<tr><th>Title</th><th>Authors</th><th>ISBN</th><th>Bookstore</th><th>Owner</th><th>Is Read?</th><th>Mark as Read</th>" +
            "<th>In Reader?</th><th>Is in Reader?</th>" +
            "<th colspan=\"3\">Actions</th>" +
            "</tr></table>"));

        for (const book of json["data"]) {
            $("#booksTable").append($(
                "<tr><td>" + book.title + "</td>" +
                "<td>" + getAuthors(book.authors) + "</td>" +
                "<td>" + book.isbn + "</td>" +
                "<td>" + book.bookstore.name + "</td>" +
                "<td>" + getName(book.owner)+"</td>" +
                "<td>" + isRead(book.isRead) + "</td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"markAsRead" + book.id + "\">" + getMarkAsReadButton(book.isRead) + "</button>" + "</td>" +
                "<td>" + inReader(book.inReader) + "</td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"markIsInReader" + book.id + "\">" + markIsInReader(book.inReader) + "</button>" + "</td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"deleteBook" + book.id + "\">Delete book </button></td>" +
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
            bookDetailsDiv.append($("<div><button class=\"btn btn-dark active btnIndex \" id=\"close\">Close </button></div>"));

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

function showAllUsers() {
    $.ajax({
        url: "http://localhost:8080/ebm/user/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        showAllUsersDiv.html("");
        showAllUsersDiv.html("<h2>Users</h2><table class=\"table table-dark\" id=\"usersTable\"  border=\"1\"><tr><th>First Name</th><th>Last Name</th><th>login</th>" +
            "<th>Email</th>" +
            "<th colspan=\"3\">Privileges</th>" +
            "<th>Actions</th>" +
            "</tr></table>");
        for (let user of json["data"]) {

            $("#usersTable").append($("<tr><td>" + user.firstName + "</td>" +
                "<td>" + user.lastName + "</td>" +
                "<td>" + user.login + "</td>" +
                "<td>" + user.email + "</td>" +
                "<td>" + user.role + "</td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"setRoleToUser" + user.id + "\">Set privileges to USER</button></td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"setRoleToAdmin" + user.id + "\">Set privileges to ADMIN</button></td>" +
                "<td>" + "<button class=\"btn btn-dark active btnIndex \" id=\"deleteUserById" + user.id + "\">Delete User</button></td></tr>"
            ));

            $("#setRoleToUser" + user.id).on("click", {
                id: user.id
            }, function (event) {
                $.ajax({
                    url: "http://localhost:8080/ebm/user/setPrivilagesToUSER/" + event.data.id,
                    type: "GET",
                    dataType: "json"
                }).done(function (json) {
                    if (json["code"] === "200") {
                        alert("User privilages has been changed");
                        showAllUsers();
                    } else {
                        alert("Cannot change the privilages. Only ADMIN is permitted to do it");
                    }
                }).fail(function () {
                    alert("Cannot connect to server");

                });

            });
            $("#setRoleToAdmin" + user.id).on("click", {
                id: user.id
            }, function (event) {
                $.ajax({
                    url: "http://localhost:8080/ebm/user/setPrivilagesToADMIN/" + event.data.id,
                    type: "GET",
                    dataType: "json"
                }).done(function (json) {

                    if (json["code"] === "200") {
                        alert("User privilages has been changed");
                        showAllUsers();
                    } else {
                        alert("Cannot change the privilages. Only ADMIN is permitted to do it");
                    }
                }).fail(function () {
                    alert("Cannot connect to server");

                });

            });


            $("#deleteUserById" + user.id).on("click", {
                id: user.id
            }, function (event) {
                $.ajax({
                    url: "http://localhost:8080/ebm/user/" + event.data.id,
                    type: "DELETE",
                    dataType: "json"
                }).done(function (json) {
                    if (json["code"] === "200") {
                        alert("User has been deleted");
                        showAllUsers();
                    } else {
                        alert("Cannot delete user");
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

function getName(owner) {
    if (owner === null || owner === undefined) {
        return "NONE";
    }
    return owner.firstName +" "+owner.lastName;
}