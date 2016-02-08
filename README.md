# LibraryManagement_DB

This SQL programming project involves the creation of a database host application that interfaces with a SQL database 
that implements a Library Management System. Assume that the users of the system are librarians (not book borrowers).

1) GUI [25 points]

All interface with the Library (queries, updates, deletes, etc.) must be done from a graphical user
interface of your original design. Your GUI application will interface with the Library database via
an appropriate MySQL connector. Initial database creation and population may be done from
command line or other admin tool.

2) Book Search and Availability [25 points]

Using your GUI, be able to search for a book, given any combination of book_id, title,
and/or Author(s), which may be either author_name OR (radio_button?) any combination of
parts of an author's name. Your query should support substring matching. You should then

display:
book_id, title, author(s), branch_id

How many copies are owned by a specified branch
Book availability at each branch (i.e. How many copies not already checked out?).
The multiple copies at different branch locations should display on separate lines, to facilitate location specific checkout. 
However, all authors of a book should be displayed on the same line.

3) Book Loans [25 points]

Checking Out Books

Using your GUI, be able to check out a book, given the combination of
BOOK_COPIES(book_id, branch_id) and BORROWER(Card_no), i.e. create a new tuple
in BOOK_LOANS. Generate a new unique primary key for loan_id. The date_out should
be today’s date. The due_date should be 14 days after the date_out.
Each BORROWER is permitted a maximum of 3 BOOK_LOANS. If a BORROWER
already has 3 BOOK_LOANS, then the checkout (i.e. create new BOOK_LOANS tuple)
should fail and return a useful error message.
If the number of BOOK_LOANS for a given book at a branch already equals the
No_of_copies (i.e. There are no more book copies available at your library_branch), then
the checkout should fail and return a useful error message.

Checking In Books

Using your GUI, be able to check in a book. Be able to locate BOOK_LOANS tuples by
searching on any of book_id, Card_no, and/or any part of BORROWER name. Once
located, provide a way of selecting one of potentially multiple results and a button (or
menu item) to check in (i.e. enter a value for date_in in corresponding BOOK_LOANS
tuple).

4) Borrower Management [25 points]

Using your GUI, be able to create new borrowers in the system.
All name and address attributes are required to create a new account (i.e. value must be
not null).
You must devise a way to automatically generate new card_no primary keys for each
new tuple that uses a compatible format with the existing borrower IDs.
Borrowers are allowed to possess exactly one library card. If a new borrower is
attempted withe same fname, lname, and address, then your system should reject and
return a useful error message.
