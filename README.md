# My Personal Project - The Book Store

## About this Project

This application is going to act as a representation of
a company that includes *various book stores*. It is an
application to be used by a manager/owner of one or more book 
stores in order to keep track of their business 
and represent useful information about their various stores.
I am interested in this project because it offers a variety of
different potential **object types and methods** for 
me to work with while developing my Java skill set. Some of 
these object types *include*

- a book,
- a store branch,
- an enterprise.

The application will keep track of which books are available
in which branches, as well as details about each book such as 
its author, reserved status, price and rating. 
In addition to the inventory, it will also keep track of 
information about the books that have been sold from each 
branch.

Note: JSON persistence classes modelled around Serialization 
Demo (UBC CPSC 210).

## User Stories

- As a user, I want to be able to add a book to a branch's inventory 
and specify its title, author, rating, price and reserved 
status.
- As a user, I want to be able to view all the books in the company 
inventory.
- As a user, I want to be able to open up a new branch and specify
its address, inventory and books sold.
- As a user, I want to be able to view a list of all branches.
- As a user, I want to be able to select a branch and see all 
the books in that given branch's inventory.
- As a user, I want to be able to remove a book from the
enterprise's inventory.
- As a user, I want to be able to sell a book.
- As a user, I want to be able to view a list of all the books
that have been sold.
- As a user, I want to be able to mark a book as reserved so 
that it cannot be sold.
- As a user, I want to be able to select a book and change its
rating.
- As a user, I want to be able to see the total value of
the inventory in each of the store branches.
- As a user, I want to be able to see the total amount of
revenue each branch has done so far.
- As a user, I want to be able to select a book and change its
  price.
- As a user, I want to be able to have the option to save the
  entire state of my application to file before quitting.
- As a user, I want to be able to reload the entire state of my 
  application from file if I so choose.

## Instructions for Grader

- You can generate the first required action by selecting to add a new branch 
to the company and inputting the name and address.
- You can generate the second required action by selecting to edit a branch and
then adding a new book to said branch.
- You can locate the visual component by going to edit branch -->  edit book --> submit 
new rating. 5 images of stars will appear which also act as buttons for 
submitting a rating.
- You can save the state of the application by clicking the "Save" button on any
menu screen at any point after initialization.
- You can reload the state of the application by choosing the "Load from File" 
option when initializing the program.
- Additionally, all above user stories have been implemented in the GUI.