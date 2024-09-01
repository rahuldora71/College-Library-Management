// add "active" class to the link whose id is "add-book-link" and remove the active class from the links present in unordered list whose id is "menuList"
const linkId = 'add-user-link'; // Change this to the desired link ID
window.onload = function() {
    // Call the setActiveLink function from main.js with a specific ID
    if (typeof window.setActiveMenuLink === 'function') {
        window.setActiveMenuLink(linkId);
    } else {
        console.error('setActiveMenuLink function is not available');
    }
};



function updateDeleteButtonState() {
    const fileInput = document.getElementById('studentPhoto');
    const deleteButton = document.getElementById('deletePhotoBtn');
    if (fileInput.files.length > 0) {
        deleteButton.disabled = false;
    } else {
        deleteButton.disabled = true;
    }
}

document.getElementById('studentPhoto').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('studentImageView').src = e.target.result;
            updateDeleteButtonState();
        }
        reader.readAsDataURL(file);
    } else {
        updateDeleteButtonState();
    }
});

document.querySelector('.book-cover').addEventListener('click', function() {
    document.getElementById('studentPhoto').click();
});

document.getElementById('deletePhotoBtn').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default behavior
    // Reset the image view to the default placeholder
    document.getElementById('studentImageView').src = /*[[@{/images/upload.png}]]*/ '/upload.png';
    // Clear the file input value
    document.getElementById('studentPhoto').value = '';
    updateDeleteButtonState();
});

// Initial state check
updateDeleteButtonState();

