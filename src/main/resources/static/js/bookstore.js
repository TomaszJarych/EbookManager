const bookstoreList = $("#bookstoreList");
const addNewBookstoreButton = $("#addNewBookstoreButton");
const bookstoreDetail = $("#bookstoreDetail");
const bookstoreForm = $("#bookstoreForm");


$(document).ready(function () {
    showAllBookstores();

    addNewBookstoreButton.on("click", function name() {
        console.log("Add new bookstore button HERE!");
        showBookstoreForm()
    })


});

function showAllBookstores() {
    $.ajax({
        url: "http://localhost:8080/ebm/bookstore/all",
        type: "GET",
        dataType: "json"
    }).done(function (json) {
        bookstoreList.html("");
        bookstoreList.append($("<table id=\"table\" border=\"1\">" +
            "<tr><th>Name</th><th>Email</th><th colspan=\"2\">WEB</th><th colspan=\"3\">Actions</th>" +
            "</tr></table>"));

        for (const bookstore of json["data"]) {
            $("#table").append($(
                "<tr>" +
                "<td>" + bookstore.name + "</td>" +
                "<td>" + bookstore.email + "</td>" +
                "<td>" + bookstore.web + "</td>" +
                "<td>" + "<button id=\"webLink" + bookstore.id + "\">Go to the bookstore's page </button></td>" +
                "<td>" + "<button id=\"showBookstoreDetail" + bookstore.id + "\">Show Bookstore details </button></td>" +
                "<td>" + "<button id=\"editBookstore" + bookstore.id + "\">Edit Bookstore </button></td>" +
                "<td>" + "<button id=\"deleteBookstore" + bookstore.id + "\">Delete Bookstore </button></td>" +
                "</tr>"));


            $("#webLink" + bookstore.id).on("click", {
                id: bookstore.id,
                web: bookstore.web
            }, function (event) {
                window.open(event.data.web,'_blank');
            });
            $("#showBookstoreDetail" + bookstore.id).on("click", {
                id: bookstore.id
            }, function (event) {
                console.log("Show detail " + event.data.id);
            });
            $("#editBookstore" + bookstore.id).on("click", {
                id: bookstore.id
            }, function (event) {
                console.log("editBookstore " + event.data.id);
            });
            $("#deleteBookstore" + bookstore.id).on("click", {
                id: bookstore.id

            }, function (event) {
                console.log("deleteBookstore " + event.data.id);

            });

        }
    }).fail(function () {
        alert("Cannot connect to server");

    });
}

function showBookstoreForm() {
    bookstoreForm.toggleClass("showDetails");
    bookstoreForm.html("");
    bookstoreForm.append($("<div><h1>Add new Bookstore</h1></div><div id=\"newBookstoreErrors\"></div><form id=\"newBookstoreForm\" method=\"post\">"+
    "<div><label for=\"name\">Name: </label>" +
    "<input type=\"text\" name=\"name\" id=\"name\"></div><br><div>" +
    "<label for=\"email\">EMAIL: </label><input type=\"email\" name=\"email\" id=\"email\"></div><br>" +
    "<div><label for=\"web\">WEB: </label><input type=\"url\" name=\"web\" id=\"web\">" +
    "</div><br><input type=\"hidden\" name=\"id\" id=\"id\">" +
    "<div><input type=\"submit\" id=\"subimtButton\" value=\"Add\"></div></form>"));
    
}