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

## Phase 4: Task 2:

Here is a sample output of the event log being printed when
the application is closed:

-Tue Nov 28 17:18:35 PST 2023

-The book The Catcher in the Rye was added to Nanaimo Books.

Tue Nov 28 17:18:45 PST 2023

The branch Nanaimo Books was created.

Tue Nov 28 17:18:57 PST 2023

The reservation status of The Iliad was changed to false.

Tue Nov 28 17:19:08 PST 2023

The book The Iliad was added to Comox Books.

Tue Nov 28 17:19:16 PST 2023

The book The Iliad was sold from Comox Books.

Tue Nov 28 17:19:35 PST 2023

The reservation status of Invisible Man was changed to false.

Tue Nov 28 17:19:52 PST 2023

The book Invisible Man was added to Comox Books.

Tue Nov 28 17:20:10 PST 2023

The book Invisible Man was sold from Comox Books.

Tue Nov 28 17:20:31 PST 2023

The branch Comox Books was created.

## Phase 4: Task 3

There are a couple of ways that I could refactor my program design. 
One structure change I would consider is having the company hold a 
list of books directly instead of having a list of branches that then 
have a list of books. In this case, each book in the company would then
have a field of type Branch to keep track of which store that given book
is at instead of having each branch simply have a list of books. This 
would require a bidirectional relation between Book and Branch, as 
Branch would still have a list of Book but each Book would also need a
single field of Branch. The benefit of this design is that it would be 
easier to edit books directly without first selecting which branch you
would like to edit a book in. Additionally, it would make it allow for
someone to find a book in the company without knowing which branch it 
was located in. However, the design is currently set up with the idea
that different branches may have different mangers using the application,
and this refactoring would make it more difficult to change a branch
or even keep track of which branch you are making changes to. Also, the 
list of books in the entire would quickly become very large without the
current Branch structure, making it more difficult to scroll through the
list of all books.

Although this would also require adding some new functionality, I 
would consider refactoring Branch to be an abstract class
that has various types of stores that could be instantiated. For example,
a company may have some branches that operate more like libraries that 
loan out books, and some that revolve around one time purchases. This 
would allow for the program to be more adaptable and scalable to a 
large, intricate book company. However, the company currently assumes 
that all branches can be instantiated with the same functionality and 
thus it is currently unnecessary to make this change.