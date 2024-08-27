const baseUrl = window.location.origin;


let serverUrl1= baseUrl+"/librarian/";
let deleteBookUrl =  baseUrl+"/librarian/home/delete-book/";
// main.js
function setActiveMenuLink(linkId) {
    // Remove 'active' class from all links within the menuList
    const menuList = document.getElementById('menuList');
    const links = menuList.getElementsByTagName('a');
    for (let i = 0; i < links.length; i++) {
        links[i].classList.remove('active');
    }

    // Add 'active' class to the link with the specified id
    const activeLink = document.getElementById(linkId);
    if (activeLink) {
        activeLink.classList.add('active');

        // activeLink.classList.add('active-custom');
    } else {
        console.error(`No link found with id: ${linkId}`);
    }
}

// Export the function to make it available globally
window.setActiveMenuLink = setActiveMenuLink;



function updateDeleteButtonState() {
    const fileInput = document.getElementById('update-cover_photo');
    const deleteButton = document.getElementById('update-deletePhotoBtn');
    if (fileInput.files.length > 0) {
        deleteButton.disabled = false;
    } else {
        deleteButton.disabled = true;
    }
}
document.getElementById('update-deletePhotoBtn').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default behavior
    // Reset the image view to the default placeholder
    document.getElementById('update-coverImageView').src = /*[[@{/images/upload.png}]]*/ '/images/upload.png';
    // Clear the file input value
    document.getElementById('update-cover_photo').value = '';
    updateDeleteButtonState();
});
document.getElementById('update-cover_photo').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('update-coverImageView').src = e.target.result;
            updateDeleteButtonState();
        }
        reader.readAsDataURL(file);
    }else {
        updateDeleteButtonState();
    }
});

document.querySelector('.book-cover').addEventListener('click', function() {
    document.getElementById('update-cover_photo').click();
});
// Initial state check
updateDeleteButtonState();



function deleteEditBook(bookId) {
    console.log( " is trying to delete");

    fetch(deleteBookUrl + bookId, {
        method: 'GET' // Explicitly state the method, though GET is default
    })
        .then(response => {
            if (response.ok) {
                // Deletion was successful
                // Redirect to a new URL after 3 seconds
                setTimeout(function() {
                    window.location.href = serverUrl1+"add-book";
                }, 1000);
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


function handleFileChange(event, imageViewId, deleteButtonId) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById(imageViewId).src = e.target.result;
            updateDeleteButtonState(event.target, deleteButtonId);
        }
        reader.readAsDataURL(file);
    } else {
        updateDeleteButtonState(event.target, deleteButtonId);
    }
}



function handleImageClick(fileInputId) {
    document.getElementById(fileInputId).click();
}

function updateDeleteButtonState(fileInput, deleteButtonId) {
    const deleteButton = document.getElementById(deleteButtonId);
    if (fileInput.files.length > 0) {
        deleteButton.disabled = false;
    } else {
        deleteButton.disabled = true;
    }
}


function handleDeleteButtonClick(event, fileInputId, imageViewId, defaultImagePath) {
    event.preventDefault(); // Prevent the default behavior
    // Reset the image view to the default placeholder
    document.getElementById(imageViewId).src = defaultImagePath;
    // Clear the file input value
    document.getElementById(fileInputId).value = '';
    updateDeleteButtonState(document.getElementById(fileInputId), event.target.id);
}

// Event listeners for first cover photo
document.getElementById('cover_photo').addEventListener('change', function(event) {
    handleFileChange(event, 'coverImageView', 'deletePhotoBtn');
});

document.querySelector('#upload-image-div .book-cover').addEventListener('click', function() {
    handleImageClick('cover_photo');
});

document.getElementById('deletePhotoBtn').addEventListener('click', function(event) {
    handleDeleteButtonClick(event, 'cover_photo', 'coverImageView', '/images/upload.png');
});

// Event listeners for second cover photo
document.getElementById('update-cover_photo').addEventListener('change', function(event) {
    handleFileChange(event, 'update-coverImageView', 'update-deletePhotoBtn');
});

document.querySelector('#update-upload-image-div .book-cover').addEventListener('click', function() {
    handleImageClick('update-cover_photo');
});

document.getElementById('update-deletePhotoBtn').addEventListener('click', function(event) {
    handleDeleteButtonClick(event, 'update-cover_photo', 'update-coverImageView', '/images/upload.png');
});

// Initial state check
updateDeleteButtonState(document.getElementById('cover_photo'), 'deletePhotoBtn');
updateDeleteButtonState(document.getElementById('update-cover_photo'), 'update-deletePhotoBtn');
*/


// document.addEventListener('DOMContentLoaded', () => {
//     let sidebar = document.querySelector('.sidebar');
//     const menuToggle = document.getElementById('menuToggle');
//     const containerN = document.getElementsByClassName('container-n')[0]; // Target the first element
//     const navbarItemList = document.getElementById('navbarScrollItems');
//     const toggleButton = document.getElementById('toggleButton');
//     const navbar = document.getElementById('navbar');
//
//     if (menuToggle) {
//         menuToggle.addEventListener('click', () => {
//             sidebar.classList.toggle('expanded');
//         });
//     }
//
//     // Function to handle window resize
//     function handleResize() {
//         if (window.innerWidth <= 800) {
//             if (sidebar) {
//                 sidebar.remove();
//                 toggleButton.remove()
//                 sidebar = null; // Set sidebar to null after removal
//             }
//
//             navbarItemList.innerHTML = `
// <!--                <a class="nav-link active" aria-current="page" href="#">Home</a>-->
//                 <li class="menu-item"><i class="fas fa-home"></i><span class="menu-text">Home</span></li>
//                 <li class="menu-item"><i class="fas fa-book"></i><span class="menu-text">Add Books</span></li>
//                 <li class="menu-item"><i class="fas fa-book"></i><span class="menu-text">Add User</span></li>
//                 <li class="menu-item"><i class="fas fa-search"></i><span class="menu-text">Search</span></li>
//                 <li class="menu-item"><i class="fas fa-cog"></i><span class="menu-text">Settings</span></li>
//                 <li class="menu-item"><i class="fas fa-phone-alt"></i><span class="menu-text">Contact</span></li>
//                 <li class="menu-item"><i class="fas fa-sign-out-alt"></i><span class="menu-text">Logout</span></li>
//             `;
//         } else {
//             if (!sidebar) {
//                 navbar.insertAdjacentHTML('afterbegin'`
// <button class="navbar-toggler" type="button" id="toggleButton" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
//                     <span class="navbar-toggler-icon"></span>
//                 </button>
//                 `);
//                 containerN.insertAdjacentHTML('afterbegin', `
//                     <div class="sidebar">
//                         <div class="menu-toggle" id="menuToggle">
//                             <i class="fas fa-bars"></i>
//                         </div>
//                         <ul class="menu">
//                             <li class="menu-item"><i class="fas fa-home"></i><span class="menu-text">Home</span></li>
//                             <li class="menu-item"><i class="fas fa-book"></i><span class="menu-text">Add Books</span></li>
//                             <li class="menu-item"><i class="fas fa-book"></i><span class="menu-text">Add User</span></li>
//                             <li class="menu-item"><i class="fas fa-search"></i><span class="menu-text">Search</span></li>
//                             <li class="menu-item"><i class="fas fa-cog"></i><span class="menu-text">Settings</span></li>
//                             <li class="menu-item"><i class="fas fa-phone-alt"></i><span class="menu-text">Contact</span></li>
//                             <li class="menu-item"><i class="fas fa-sign-out-alt"></i><span class="menu-text">Logout</span></li>
//                         </ul>
//                     </div>
//                 `);
//
//                 sidebar = document.querySelector('.sidebar'); // Update the sidebar reference
//
//                 // Re-attach the event listener for the new menuToggle
//                 const newMenuToggle = document.getElementById('menuToggle');
//                 if (newMenuToggle) {
//                     newMenuToggle.addEventListener('click', () => {
//                         sidebar.classList.toggle('expanded');
//                     });
//                 }
//             }
//
//             navbarItemList.innerHTML = ``;
//         }
//     }
//
//     // Initial call to handleResize to set up the initial state
//     handleResize();
//
//     // Attach the handleResize function to the resize event
//     window.onresize = handleResize;
// });
//




