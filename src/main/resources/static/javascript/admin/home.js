


let searchUserUrl = "http://localhost:9090/librarian/home/searched-user-list";

let searchBookUrl = "http://localhost:9090/librarian/home/book-suggestions";

let serverUrl2 = "http://localhost:9090/librarian/home/";

let searchedUserId;
let user;
// getter and setters for searchedBookId
let searchedBookId;
function setSearchedBookId(id) {
   this.searchedBookId = id;
}

function getSearchedBookId() {
    return searchedBookId;
}
// getter and setters for searchedBookId
 function setSearchedUserId(id) {
    this.searchedUserId = id;
}

function getSearchedUserId() {
    return searchedUserId;
}
function setUser(user) {
    this.user = user;
}

function getUser() {
    return user;
}





// add "active" class to the link whose id is "add-book-link" and remove the active class from the links present in unordered list whose id is "menuList"
const linkId = 'home-link'; // Change this to the desired link ID
window.onload = function() {
    // Call the setActiveLink function from main.js with a specific ID
    if (typeof window.setActiveMenuLink === 'function') {
        window.setActiveMenuLink(linkId);
    } else {
        console.error('setActiveMenuLink function is not available');
    }
};


function setHomeMenuLink(linkId) {
    // Remove 'active' class from all links within the menuList
    const homeMenu = document.getElementById('home_menu_list');
    const links = homeMenu.getElementsByTagName('a');
    for (let i = 0; i < links.length; i++) {
        links[i].classList.remove('active');
        console.log('links', links);
    }

    // Add 'active' class to the link with the specified id
    const activeLink = document.getElementById(linkId);
    if (activeLink) {
        activeLink.classList.add('active');
        console.log('activeLink', activeLink);
        // activeLink.classList.add('active-custom');
    } else {
        console.error(`No link found with id: ${linkId}`);
    }
}
// Export the function to make it available globally
// window.setHomeMenuLink = setHomeMenuLink;

// userDisplay('${JSON.stringify(user).replace(/'/g, "&apos;")}') "
// onmousedown="suggestionItemClick('${user.studentId}','userSuggestionItem')


const searchUser = () => {
    let query = $("#searchUser").val();
    let suggestions=$("#userSuggestionItem");


    if (query==="") {
        suggestions.hide();
    } else {
        let url = `${searchUserUrl}?search=${query}`;
       fetch(url)
            .then(response =>  response.json())
            .then(data => {
                let text="";
                data.forEach(user => {


                    text += `

<a href="${serverUrl2}user-prof/${user.studentId} " target="_blank" style="text-decoration: none; ">
<li class="list-group-item d-flex list-group-item-action suggestion-item  align-items-bottom"    >
                    <img class="userProfilePic rounded-circle border border-warning" src="${'/images/student/' + user.studentImage}" alt="">
                    <div class="d-flex flex-grow-1 flex-column">
                        <div class="ms-2 me-auto ">
                            <div class="fw-bold">${user.name}</div>
                        </div>
                        <div class="d-flex ms-2  justify-content-between ">
                            <span>${user.roll_no}</span>
                            <span class="badge text-bg-primary align-self-end  rounded-pill">${user.phone}</span>
                        </div>
                    </div>
                </li>
                </a>
                `

                } );
                suggestions.html(text).show();
                    suggestions.style.display = "block";
                // Add event listeners to the suggestion items


            })
           .catch(error => console.error('Error:', error));
    }
};

function  suggestionItemClick(id,itemId) {
    let suggestions=$("#"+ itemId);
    // const selectedText = ;
    console.log(id);
    if (itemId === "bookSuggestionItem") {
        setSearchedBookId(id)
    } else{
    setSearchedUserId(id);
    }
    suggestions.hide(); // Hide suggestions after selection
}
function userSuggestionHide() {


    let searchResult = $("#userSuggestionItem");
    // searchResult.hide();
}
function bookSuggestionHide() {


    let searchResult = $("#bookSuggestionItem");
    searchResult.hide();
}
/*
 function userDisplay(userJson) {
     let user = JSON.parse(userJson);
    let display = $("#displayUser");
    display.html("").hide()
    let text="";
    text = ` <div class="row g-0">
                    <div class="col-md-4">
                        <img src="${'/images/'+user.studentImage}"  class="img-fluid rounded-start userImage" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title"><strong>Name: &nbsp;</strong>${user.name}</h5>
                            <p class="card-text"><strong>Roll no.: &nbsp;</strong>${user.roll_no}</p>
                            <p class="card-text"><strong>Email: &nbsp;</strong>${user.email}</p>
                            <p class="card-text"><strong>Phone no.: &nbsp;</strong>${user.phone}</p>
                            <p class="card-text"><strong>Book Borowed: &nbsp;</strong><span class="text-danger">${user.borrowedBooks}</span></p>
                        </div>

                    </div>
                </div>`;
    display.html(text).show()


};
*/

// for suggestion books for issue
/*
const searchBook = () => {
    let query = $("#searchBook").val();
    let suggestions=$("#bookSuggestionItem");


    if (query==="") {
        suggestions.hide();
    } else {
        let url = `${searchBookUrl}?search=${query}`;
        fetch(url)
            .then(response =>  response.json())
            .then(data => {
                let text="";
                data.forEach(book =>

                    text +=`<li class="list-group-item d-flex list-group-item-action suggestion-book-item  align-items-bottom" data-user="${book}" onmousedown="suggestionItemClick('${book.bookId}','bookSuggestionItem') ">
                    <img class="userProfilePic rounded-circle border border-warning" src="${'/images/books'+book.coverPhoto}" alt="">
                    <div class="d-flex flex-grow-1 flex-column">
                        <div class="ms-2 me-auto ">
                            <div class="fw-bold">${book.title}&nbsp;<span class="badge text-bg-primary align-self-end  rounded-pill">${book.edition}</span></div>

                        </div>
                        <div class="d-flex ms-2  justify-content-between ">
                            <span><strong>Author:&nbsp;</strong>${book.author}</span>
                            <span><strong>&nbsp;Publisher:&nbsp;</strong>${book.publisher}</span>
                            <span class="badge text-bg-primary align-self-end  rounded-pill">&nbsp;${book.category}</span>
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
*/


const issueBook = () => {
    // Get the userId and bookId
    let userId = getSearchedUserId();

    fetch(`/librarian/home/can-borrow-more-books?studentId=${userId}`)
        .then(response => response.json())
        .then(canBorrow => {
            if (!canBorrow) {
                alert("Student has already borrowed the maximum number of books.");
            } else {
                // Proceed with the book issue process
            }
        })
        .catch(error => console.error('Error:', error));
};
