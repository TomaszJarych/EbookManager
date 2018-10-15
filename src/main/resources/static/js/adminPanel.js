const showAllBooks = $("#showAllBooks");
const showAllAuthors = $("#showAllAuthors");
const showAllGenres = $("#showAllGenres");
const showAllUsers = $("#showAllUsers");
const showAllBooksButton = $("#showAllBooksButton");
const showAllAuthorsButton = $("#showAllAuthorsButton");
const showAllGenresButton = $("#showAllGenresButton");
const showAllUsersButton = $("#showAllUsersButton");

$(document).ready(function () {


    showAllBooksButton.on("click", function () {
        console.log("SHOW ALL BOOKS");

    })
    showAllAuthorsButton.on("click", function () {
        console.log("SHOW ALL AUTHORS");

    })
    showAllGenresButton.on("click", function () {
        console.log("SHOW ALL GENRES");

    })
    showAllUsersButton.on("click", function () {
        console.log("SHOW ALL USERS");

    })


});