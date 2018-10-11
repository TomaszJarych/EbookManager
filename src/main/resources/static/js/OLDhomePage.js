$(document).ready(function () {

    let dataFromDb = new BookFromDb("http://localhost:8080/ebm/book/all", "Get", "json");

    dataFromDb.fetch();

    let testBooks = dataFromDb.books;


    for (let index = 0; index < testBooks.length; index++) {
        console.log(testBooks[index]);

    }


});

function BookFromDb(url, type, dataType) {
    this.books = [];
    this.url = url;
    this.type = type;
    this.dataType = dataType;
}


BookFromDb.prototype.fetch = function () {
    var that = this;

    $.ajax({
        url: that.url,
        type: that.type,
        dataType: that.dataType
    }).done(function (json) {
            if (json["message"] === "OK") {

                let booksFromJSON = json["data"];
                for (let book of booksFromJSON) {

                    that.books.push(book);
                }
            }
        }

    ).fail(function () {
        alert("Cannot connect to server");
    })
}