
let listingBooksUrl = "";
//     getter and setter of the listingBooksUrl

let serverUrl= baseUrl+"/librarian/";
let deleteBookUrl =  baseUrl+"/librarian/home/delete-book/";
let bookEditUrl=  baseUrl+"/librarian/update-book/";

// let listingSearchedBooksUrl=baseUrl+"/librarian/home/searched-books-list";
let currentPage = 0;
const pageSize = 8;




function suggestionsHide() {
    let searchResult = $(".search-result");
    searchResult.text("");
}




document.addEventListener('DOMContentLoaded', function () {
    console.log("book list js is loaded ")
    // Handle the filter selection
    document.querySelectorAll('.dropdown-item').forEach(function (item) {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent the default anchor behavior
            const selectedFilter = this.getAttribute('data-filter');

            // Set the selected filter text in the span
            document.getElementById('filter-selected').textContent = selectedFilter;

            // Store the selected filter value
            document.getElementById('filter-selected').dataset.value = selectedFilter;
        });
    });

    // Handle the clear filter button
    document.getElementById('clear-filter').addEventListener('click', function (event) {
        event.preventDefault(); // Prevent the default anchor behavior

        // Clear the selected filter text
        document.getElementById('filter-selected').textContent = '';

        // Clear the stored filter value
        document.getElementById('filter-selected').dataset.value = null;
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const filterDisplayDiv = document.getElementById('filter-display');
    const filterSelectedSpan = document.getElementById('filter-selected');

    // Handle the filter selection
    document.querySelectorAll('.dropdown-item').forEach(function (item) {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent the default anchor behavior
            const selectedFilter = this.getAttribute('data-filter');

            // Set the selected filter text in the span
            filterSelectedSpan.textContent = selectedFilter;

            // Store the selected filter value
            filterSelectedSpan.dataset.value = selectedFilter;

            // Show the div containing the selected filter
            filterDisplayDiv.style.display = 'block';
        });
    });

    // Handle the clear filter button
    document.getElementById('clear-filter').addEventListener('click', function (event) {
        event.preventDefault(); // Prevent the default anchor behavior

        // Clear the selected filter text
        filterSelectedSpan.textContent = '';

        // Clear the stored filter value
        filterSelectedSpan.dataset.value = "";

        // Hide the div containing the selected filter

        filterDisplayDiv.setAttribute("style", "display: none !important;");
    });
});

// Function to get the current selected filter
function getSelectedFilter() {
    return document.getElementById('filter-selected').dataset.value || null;
}


// Search handler

const search = () => {
    let query = $("#book-search-input").val();
    let filter = getSelectedFilter() || "title";

    if (query === "") {
        $(".search-result").hide();
    } else {
        let url = `${baseUrl}/librarian/home/book-suggestions?search=${query}&filter=${filter}&flag=false`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                let text = `<div class='list-group'>`;

                data.forEach(book => {
                    text += `<a  class="list-group-item suggestion-item" data-value="${book[filter.toLowerCase()]}">${book[filter.toLowerCase()]}</a>`;
                });
                text += `</div>`;
                $(".search-result").html(text).show();

                // Add event listeners to the suggestion items
                $(".suggestion-item").on("mousedown", function () {
                    const selectedText = $(this).data("value");
                    $("#book-search-input").val(selectedText);
                    $(".search-result").hide(); // Hide suggestions after selection
                });
            });
    }
};






// Code for showing all books in list

function fetchSearchedBooks() {
    let query = $("#book-search-input").val();

    let filter = getSelectedFilter() || "title";

    listingBooksUrl = `${baseUrl}/librarian/home/searched-books-list?search=${query}&filter=${filter}&`;
    fetchBooks(currentPage, pageSize);

    let searchResult = $(".search-result");
    searchResult.text("");


}




document.addEventListener("DOMContentLoaded", function () {

    listingBooksUrl =  baseUrl+"/librarian/home/books-list?";
    fetchBooks(currentPage, pageSize);

    document.querySelector('.pagination1').addEventListener('click', function (e) {
        if (e.target.tagName === 'A') {
            const pageNumber = parseInt(e.target.dataset.page);
            fetchBooks(pageNumber, pageSize);
        }
    });
    document.querySelector('.pagination2').addEventListener('click', function (e) {
        if (e.target.tagName === 'A') {
            const pageNumber = parseInt(e.target.dataset.page);
            fetchBooks(pageNumber, pageSize);
        }
    });

});

function fetchBooks(page, size) {

    fetch(`${listingBooksUrl}page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            const bookListCard = document.querySelector('.book-list-card');
            bookListCard.innerHTML = ''; // Clear any existing content
            data.content.forEach(book => {
                const bookCard = createBookCard(book);
                bookListCard.appendChild(bookCard);
            });

            // Update pagination controls
            updatePaginationControls1(data);
            updatePaginationControls2(data);
        })
        .catch(error => {
            console.error("Error fetching books:", error);
        });
}

function createBookCard(book) {
    const cardDiv = document.createElement('div');
    cardDiv.className = 'm-3 book-card';
    cardDiv.setAttribute("id", book.bookId);

    cardDiv.innerHTML = `
        <div class="image-div">
            <img src="${imagePrefix}${book.coverPhoto || 'contact_profile_default.png'}" class="book-image" alt="Book Photo">
        </div>
        <div class="card-body1 p-2">
            <div class="title-div">
                <div class="book-title">
                    <h5 class="book-title-span">${book.title}</h5>
                </div>
                <span class="badge bg-info text-dark">${book.edition}</span>
            </div>
            <div class="publisher-author">
                <div>
                    <span class="publisher">Publisher: <span class="badge bg-warning text-dark">${book.publisher}</span></span><br>
                    <span class="author">Author: <span class="badge bg-warning text-dark">${book.author}</span></span>
                </div>

                <div class="book-price"><h4>${book.price}<span>&nbsp;Rs</span></h4><span>Price</span></div>
                <div class="d-flex">
                
                
                 <a class="btn me-3 btn-primary" href="${bookEditUrl}${book.bookId}">Edit</a>
                <!-- Button trigger modal -->
                 <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#staticBackdrop${book.bookId}">
  Delete
</button>

<!-- Modal -->

                    <div class="modal fade" id="staticBackdrop${book.bookId}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title  fs-5" id="staticBackdropLabel">Delete <span class="text-danger">${book.title}</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
                                            Are you sure to delete this Book!

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" onclick="deleteBook('${book.bookId}')"  data-bs-dismiss="modal" class="btn btn-danger">Delete</button>
      </div>
    </div>
  </div>
</div>
                   
                    
                </div>
            </div>
            <div class="me-2">
                <div class="description-container">
                    <span class="fw-bold">Description:</span>
                    <span>${book.description || "No description available."}</span>
                </div>
                <a class="more-link read-more-link">Read more</a>
            </div>
        </div>
    `;

    return cardDiv;
}

function updatePaginationControls1(data) {
    const pagination = document.querySelector('.pagination1');
    paginationController(pagination, data);

}
function updatePaginationControls2(data) {
    const pagination = document.querySelector('.pagination2');
    paginationController(pagination, data);
}

function paginationController(pagination, data) {
    pagination.innerHTML = '';

    const firstPage = 0;
    const lastPage = data.totalPages - 1;
    const currentPage = data.number;
    const maxPagesToShow = 1; // Show 4 pages before and after the current page

    // Previous button
    const prevPage = currentPage > 0 ? currentPage - 1 : 0;
    pagination.appendChild(createPaginationItem(prevPage, '&laquo;'));

    // Calculate the start and end page indexes
    const startPage = Math.max(firstPage, currentPage - maxPagesToShow);
    const endPage = Math.min(lastPage, currentPage + maxPagesToShow);

    // First page
    if (startPage > firstPage) {
        pagination.appendChild(createPaginationItem(firstPage, '1'));
        if (startPage > firstPage + 1) {
            pagination.appendChild(createPaginationEllipsis());
        }
    }

    // Pages around the current page
    for (let i = startPage; i <= endPage; i++) {
        pagination.appendChild(createPaginationItem(i, i + 1, i === currentPage));
    }

    // Last page
    if (endPage < lastPage) {
        if (endPage < lastPage - 1) {
            pagination.appendChild(createPaginationEllipsis());
        }
        pagination.appendChild(createPaginationItem(lastPage, lastPage + 1));
    }

    // Next button
    const nextPage = currentPage < lastPage ? currentPage + 1 : lastPage;
    pagination.appendChild(createPaginationItem(nextPage, '&raquo;'));
}
function createPaginationItem(page, text, active = false) {
    const li = document.createElement('li');
    li.className = `page-item ${active ? 'active' : ''}`;

    const a = document.createElement('a');
    a.className = 'page-link';
    a.href = '#';
    a.dataset.page = page;
    a.innerHTML = text;

    li.appendChild(a);
    return li;
}

function createPaginationEllipsis() {
    const li = document.createElement('li');
    li.className = 'page-item disabled';

    const span = document.createElement('span');
    span.className = 'page-link';
    span.innerHTML = '...';

    li.appendChild(span);
    return li;
}

function deleteBook(bookId) {
    console.log(bookId + " is trying to delete");

    fetch(deleteBookUrl + bookId, {
        method: 'GET' // Explicitly state the method, though GET is default
    })
        .then(response => {
            if (response.ok) {
                // Deletion was successful
                let mainCard = document.getElementById(bookId);
                if (mainCard) {
                    mainCard.remove(); // Remove the book card from the DOM
                    console.log('Book deleted successfully');
                } else {
                    console.log('Failed to find the book element in the DOM');
                }
            } else {
                // Handle the case where deletion was not successful
                console.log('Failed to delete book');
            }
        })
        .catch(error => {
            // Handle any network errors
            console.error('Error deleting the book:', error);
            alert('Failed to delete book');
        });
}




/*
*
* let listingBooksUrl = baseUrl+"/librarian/home/searched-books-list"; // Updated URL for search

document.addEventListener('DOMContentLoaded', function () {
    // Handle the filter selection
    document.querySelectorAll('.dropdown-item').forEach(function (item) {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent the default anchor behavior
            const selectedFilter = this.getAttribute('data-filter');

            // Set the selected filter text in the span
            document.getElementById('filter-selected').textContent = selectedFilter;

            // Store the selected filter value
            document.getElementById('filter-selected').dataset.value = selectedFilter;
        });
    });

    // Handle the clear filter button
    document.getElementById('clear-filter').addEventListener('click', function (event) {
        event.preventDefault(); // Prevent the default anchor behavior

        // Clear the selected filter text
        document.getElementById('filter-selected').textContent = '';

        // Clear the stored filter value
        document.getElementById('filter-selected').dataset.value = null;
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const filterDisplayDiv = document.getElementById('filter-display');
    const filterSelectedSpan = document.getElementById('filter-selected');

    // Handle the filter selection
    document.querySelectorAll('.dropdown-item').forEach(function (item) {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent the default anchor behavior
            const selectedFilter = this.getAttribute('data-filter');

            // Set the selected filter text in the span
            filterSelectedSpan.textContent = selectedFilter;

            // Store the selected filter value
            filterSelectedSpan.dataset.value = selectedFilter;

            // Show the div containing the selected filter
            filterDisplayDiv.style.display = 'block';
        });
    });

    // Handle the clear filter button
    document.getElementById('clear-filter').addEventListener('click', function (event) {
        event.preventDefault(); // Prevent the default anchor behavior

        // Clear the selected filter text
        filterSelectedSpan.textContent = '';

        // Clear the stored filter value
        filterSelectedSpan.dataset.value = "";

        // Hide the div containing the selected filter
        filterDisplayDiv.style.display = 'none';
    });
});

// Function to get the current selected filter
function getSelectedFilter() {
    return document.getElementById('filter-selected').dataset.value || null;
}

// Search handler
const search = () => {
    let query = $("#book-search-input").val();
    let filter = getSelectedFilter() || "title";

    if (query === "") {
        $(".search-result").hide();
    } else {
        fetchBooks(0, 8, query, filter); // Start with the first page
    }
};

// Fetch books with pagination
function fetchBooks(page, size, search = '', filter = '') {
    let url = `${listingBooksUrl}?page=${page}&size=${size}&search=${search}&filter=${filter}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const bookListCard = document.querySelector('.book-list-card');
            bookListCard.innerHTML = ''; // Clear any existing content
            data.content.forEach(book => {
                const bookCard = createBookCard(book);
                bookListCard.appendChild(bookCard);
            });

            // Update pagination controls
            updatePaginationControls1(data);
            updatePaginationControls2(data);
        })
        .catch(error => {
            console.error("Error fetching books:", error);
        });
}

function createBookCard(book) {
    const cardDiv = document.createElement('div');
    cardDiv.className = 'm-3 book-card';

    cardDiv.innerHTML = `
        <div class="image-div">
            <img src="${'/images/books/'+book.coverPhoto || '/images/contact_profile_default.png'}" class="book-image" alt="Book Photo">
        </div>
        <div class="card-body1 p-2">
            <div class="title-div">
                <div class="book-title">
                    <h5 class="book-title-span">${book.title}</h5>
                </div>
                <span class="badge bg-info text-dark">${book.edition}</span>
            </div>
            <div class="publisher-author">
                <div>
                    <span class="publisher">Publisher: <span class="badge bg-warning text-dark">${book.publisher}</span></span><br>
                    <span class="author">Author: <span class="badge bg-warning text-dark">${book.author}</span></span>
                </div>
                <div class="book-copies"><h4>${book.quantity}</h4><span>Copies</span></div>
                <div class="book-price"><h4>${book.price}<span>&nbsp;Rs</span></h4><span>Price</span></div>
                <div class="d-flex">
                    <a class="btn me-3 btn-primary">Edit</a>
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal">Delete</button>
                </div>
            </div>
            <div class="me-2">
                <div class="description-container">
                    <span class="fw-bold">Description:</span>
                    <span>${book.description || "No description available."}</span>
                </div>
                <a class="more-link read-more-link">Read more</a>
            </div>
        </div>
    `;

    return cardDiv;
}

function updatePaginationControls1(data) {
    const pagination = document.querySelector('.pagination1');
    paginationController(pagination, data);
}

function updatePaginationControls2(data) {
    const pagination = document.querySelector('.pagination2');
    paginationController(pagination, data);
}

function paginationController(pagination, data) {
    pagination.innerHTML = '';

    const firstPage = 0;
    const lastPage = data.totalPages - 1;
    const currentPage = data.number;
    const maxPagesToShow = 1; // Show 4 pages before and after the current page

    // Previous button
    const prevPage = currentPage > 0 ? currentPage - 1 : 0;
    pagination.appendChild(createPaginationItem(prevPage, '&laquo;'));

    // Calculate the start and end page indexes
    const startPage = Math.max(firstPage, currentPage - maxPagesToShow);
    const endPage = Math.min(lastPage, currentPage + maxPagesToShow);

    // First page
    if (startPage > firstPage) {
        pagination.appendChild(createPaginationItem(firstPage, '1'));
        if (startPage > firstPage + 1) {
            pagination.appendChild(createPaginationEllipsis());
        }
    }

    // Pages around the current page
    for (let i = startPage; i <= endPage; i++) {
        pagination.appendChild(createPaginationItem(i, i + 1, i === currentPage));
    }

    // Last page
    if (endPage < lastPage) {
        if (endPage < lastPage - 1) {
            pagination.appendChild(createPaginationEllipsis());
        }
        pagination.appendChild(createPaginationItem(lastPage, lastPage + 1));
    }

    // Next button
    const nextPage = currentPage < lastPage ? currentPage + 1 : lastPage;
    pagination.appendChild(createPaginationItem(nextPage, '&raquo;'));
}

function createPaginationItem(page, text, active = false) {
    const li = document.createElement('li');
    li.className = `page-item ${active ? 'active' : ''}`;

    const a = document.createElement('a');
    a.className = 'page-link';
    a.href = '#';
    a.dataset.page = page;
    a.innerHTML = text;

    li.appendChild(a);
    return li;
}

function createPaginationEllipsis() {
    const li = document.createElement('li');
    li.className = 'page-item disabled';

    const span = document.createElement('span');
    span.className = 'page-link';
    span.innerHTML = '...';

    li.appendChild(span);
    return li;
}

// Search input event listener
document.getElementById('search-button').addEventListener('click', search);
*/