document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.querySelector('.sidebar');
    const menuToggle = document.getElementById('menuToggle');

    menuToggle.addEventListener('click', () => {
        sidebar.classList.toggle('expanded');
    });

    // Dummy book data
    let books = [

        // Add more book objects as needed
    ];

    function add_books(book_name, issue_date, days_left, image, admin) {
        books.push({
            title: book_name,
            issueDate: issue_date,
            daysLeft: days_left,
            image: image,
            admin: admin
        });
        renderBooks(books);
    }

    const booksList = document.getElementById('booksList');

    function renderBooks(books) {
        booksList.innerHTML = "";
        books.forEach(book => {
            const bookCard = document.createElement('div');
            bookCard.className = 'book-card card zoom-btn remove-zoom';
            bookCard.innerHTML = `
                <img  th:src="@{/images/img.png}" alt="${book.title}">
                <div class="book-info">
                    <h4>${book.title}</h4>
                    <p>Issue : ${book.issueDate}</p>
                    <p>Days Left: ${book.daysLeft}</p>
                    <p class="book-admin">Authorized by: ${book.admin}</p>
                </div>
                
            `;
            booksList.appendChild(bookCard);
        });
    }

    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
    add_books("The Hitchhiker's Guide to the Galaxy", "2018-01-01", 10, "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRnOoM0pRJ-fUw73d5Coll6F-1tgcS3PESh1ABpk5wjsADWmYiZjbhUONGRBkKhcISrwWoaAE0bqfVBU-rjL-e_0slLp0q080L8xCMwTQKtEsxCh6-TSaVyJg&usqp=CAE", "Admin");
});
