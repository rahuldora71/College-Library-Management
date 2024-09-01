

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
    document.getElementById('update-coverImageView').src =  '/upload.png';
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


document.addEventListener("DOMContentLoaded", function () {
    const ctx = document.getElementById('bookStatusChart').getContext('2d');

    // Initialize the chart with empty data
    const bookStatusChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Available', 'Borrowed', 'Lost'],
            datasets: [{
                label: 'Book Status',
                data: [0, 0, 0], // Initial empty data
                backgroundColor: ['#4CAF50', '#FF9800', '#F44336'], // Colors for each section
            }]

        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        }
    });

    // Fetch book status data from the backend
    fetch(baseUrl+'/librarian/home/count-books')
        .then(response => response.json())
        .then(data => {
            console.log(data)
            // Update chart data with the response
            bookStatusChart.data.datasets[0].data = [
                data['Available'], // Available books count
                data['Issued'],    // Borrowed books count (you may rename it accordingly)
                data['Lost']       // Lost books count
            ];
            document.getElementById("total_books").textContent= data['Available']+data['Issued']+data['Lost']

            // Re-render the chart with the updated data
            bookStatusChart.update();
        })
        .catch(error => {
            console.error('Error fetching book status data:', error);
        });
});



