// function setHomeMenuLink(linkId) {
//     // Remove 'active' class from all links within the menuList
//     const homeMenu = document.getElementById('home_menu_list');
//     const links = homeMenu.getElementsByTagName('a');
//     for (let i = 0; i < links.length; i++) {
//         links[i].classList.remove('active');
//         console.log('links', links);
//     }
//
//     // Add 'active' class to the link with the specified id
//     const activeLink = document.getElementById(linkId);
//     if (activeLink) {
//         activeLink.classList.add('active');
//         console.log('activeLink', activeLink);
//         // activeLink.classList.add('active-custom');
//     } else {
//         console.error(`No link found with id: ${linkId}`);
//     }
// }