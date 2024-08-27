document.addEventListener("DOMContentLoaded", function () {



});
let searchBookUrl = "http://localhost:9090/librarian/home/book-suggestions";
let searchedBookId;
let serverUrl = "http://localhost:9090/librarian/home/";
let userId=window.location.href.split("user-prof/")[1];
function setSearchedBookId(id) {
    this.searchedBookId = id;
}

function getSearchedBookId() {
    return this.searchedBookId;
}
// for suggestion books for issue
const searchBook = () => {
    let query = $("#searchBook").val();
    let suggestions=$("#bookSuggestionItem");


    if (query==="") {
        suggestions.hide();
    } else {
        let url = `${searchBookUrl}?search=${query}&flag=true`;
        fetch(url)
            .then(response =>  response.json())
            .then(data => {
                let text="";
                data.forEach(book =>

                    text +=`<li class="list-group-item  d-flex list-group-item-action suggestion-book-item  align-items-bottom" data-user="${book}" 
onmousedown="suggestionItemClick('${book.bookId}','bookSuggestionItem'); bookDisplay('${book.coverPhoto}', '${book.title}' ,'${book.edition}' ,'${book.author}' ,'${book.publisher}' ,'${book.description}' )  ">
                    <img class="userProfilePic rounded-circle border border-warning" src="${'/images/books/'+book.coverPhoto}" alt="">
                    <div class="d-flex flex-column flex-grow-1  ">
                        <div class="ms-2   me-auto ">
                            <div class="  fw-bold">${book.title}&nbsp;<span class="badge text-bg-primary align-self-end  rounded-pill">${book.edition}</span></div>
                            
                        </div>
                        <div class=" ms-2  d-flex book-details justify-content-between ">
                            <span class="d-flex nowrap"><strong>Author:&nbsp;</strong><span>${book.author}&nbsp;</span></span>
                            <span class="d-flex nowrap"><strong>Publisher:&nbsp;</strong><span>${book.publisher}&nbsp;</span></span>
                            <span class="badge text-bg-primary   rounded-pill">${book.category}</span>
                        </div>
                    </div>
                </li>
                `
                );
                suggestions.html(text).show();
                suggestions.style.display = "block";
                // Add event listeners to the suggestion items


            })
            .catch(error => console.error('Error:', error));
    }
};
function bookDisplay(bookCover,bookTitle,bookEdition,author,publisher,description) {

    let display = $("#displayUser");
    display.html("").hide()
    let text="";
    text = ` <div class="row g-0">
                    <div class="col-md-4">
                        <img src="${'/images/books/'+bookCover}"  class="img-fluid rounded-start userImage" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title"><strong>Title: &nbsp;</strong>${bookTitle}</h5>
                            <p class="card-text"><strong>Edition: &nbsp;</strong>${bookEdition}</p>
                            <p class="card-text"><strong>Author: &nbsp;</strong>${author}</p>
                            <p class="card-text"><strong>Publisher: &nbsp;</strong>${publisher}</p>
                            <p class="card-text"><strong>Description: &nbsp;</strong><span class="text-danger">${description}</span></p>
                        </div>

                    </div>
                </div>`;
    display.html(text).show()


}
function  suggestionItemClick(id,itemId) {
    let suggestions=$("#"+ itemId);

    // const selectedText = ;
    console.log(id);
    if (itemId === "bookSuggestionItem") {
        setSearchedBookId(id)
    }
    console.log("Get Id by " +getSearchedBookId())
    suggestions.hide(); // Hide suggestions after selection
    checkValue()
}
function bookSuggestionHide() {


    let searchResult = $("#bookSuggestionItem");
    searchResult.hide();
}
function checkValue() {
    const inputField = getSearchedBookId();
    const addButton = document.getElementById("addButton");

    if (inputField !== "") { // Checks if the input is not empty or just spaces
        addButton.disabled = false; // Enable the button
    } else {
        addButton.disabled = true; // Disable the button
    }
}

// Call checkValue initially to ensure the button state is correct based on initial input value
document.addEventListener("DOMContentLoaded", checkValue);

function addBookDb(){

    console.log(getSearchedBookId())

}
// 0 =Success
// 1 = Book limit full
// 2 = Server Error
// 3 = Book not found
// 4=Something went wrong
// 5=book renewal successfully
function showToast(toast_type){
let parent_toast = document.getElementById("toast-container");
    parent_toast.style.display = "block";
    let mainStatusParent=parent_toast.children[0];
            mainStatusParent.children[toast_type].style.display = "block";



}

function refreshPage() {
    // Reload the page
    location.reload();
}
function issueBookToStudent() {
    // Ensure studentId and bookId are not empty

    studentId=userId
    bookId= getSearchedBookId()
    if (!studentId || !bookId) {
        console.log("Student ID and Book ID cannot be empty.");
        return;
    }

    // Make a POST request to issue the book
    fetch(serverUrl+'user-issue-book', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            studentId: studentId,
            bookId: bookId
        })
    })
        .then(response => response.text())
        .then(data => {
            if (data) {
                console.log(data);
                showToast(data)// Log the response message
                // refreshPage();
            }
        })
        .catch(error => {
            console.error('Error issuing book:', error);
            showToast(4)
        });
}
function renewBook(bookId) {
    // Ensure studentId and bookId are not empty
    if (!bookId) {
        console.error('Book ID is required');
        return;
    }
    // Make a POST request to issue the book
    console.log(bookId)
    fetch(serverUrl+'books_renew', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            issuedBookId: bookId

        })
    })
        .then(response => response.text())
        .then(data => {
            if (data) {
                console.log(data);
                showToast(data)// Log the response message
                // refreshPage();
            }
        })
        .catch(error => {
            console.error('Error issuing book:', error);
            showToast(4)
        });
}
function submitBook(bookId,userId){
    showToast(6)
    document.getElementById("submitBookId").value= bookId;
    document.getElementById("submitUserId").value = userId;
}