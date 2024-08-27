

const baseUrl1 = window.location.origin;

let searchUserUrl =  baseUrl1+"/librarian/home/searched-user-list";

let searchBookUrl =  baseUrl1+"/librarian/home/book-suggestions";

let serverUrl2 =  baseUrl1+"/librarian/home/";

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