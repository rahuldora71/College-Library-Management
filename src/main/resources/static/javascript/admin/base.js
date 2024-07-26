
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
    } else {
        console.error(`No link found with id: ${linkId}`);
    }
}

// Export the function to make it available globally
window.setActiveMenuLink = setActiveMenuLink;




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




