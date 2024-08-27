

// add "active" class to the link whose id is "add-book-link" and remove the active class from the links present in unordered list whose id is "menuList"
const linkId = 'add-book-link'; // Change this to the desired link ID
window.onload = function() {
    // Call the setActiveLink function from main.js with a specific ID
    if (typeof window.setActiveMenuLink === 'function') {
        window.setActiveMenuLink(linkId);
    } else {
        console.error('setActiveMenuLink function is not available');
    }
};








function updateDeleteButtonState() {
    const fileInput = document.getElementById('cover_photo');
    const deleteButton = document.getElementById('deletePhotoBtn');
    if (fileInput.files.length > 0) {
        deleteButton.disabled = false;
    } else {
        deleteButton.disabled = true;
    }
}
document.getElementById('deletePhotoBtn').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default behavior
    // Reset the image view to the default placeholder
    document.getElementById('coverImageView').src = /*[[@{/images/upload.png}]]*/ '/images/upload.png';
    // Clear the file input value
    document.getElementById('cover_photo').value = '';
    updateDeleteButtonState();
});
document.getElementById('cover_photo').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('coverImageView').src = e.target.result;
            updateDeleteButtonState();
        }
        reader.readAsDataURL(file);
    }else {
        updateDeleteButtonState();
    }
});

document.querySelector('.book-cover').addEventListener('click', function() {
    document.getElementById('cover_photo').click();
});
// Initial state check
updateDeleteButtonState();

