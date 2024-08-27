// add "active" class to the link whose id is "add-book-link" and remove the active class from the links present in unordered list whose id is "menuList"
const linkId = 'search-books-link'; // Change this to the desired link ID
window.onload = function() {
    // Call the setActiveLink function from main.js with a specific ID
    if (typeof window.setActiveMenuLink === 'function') {
        window.setActiveMenuLink(linkId);
    } else {
        console.error('setActiveMenuLink function is not available');
    }
};