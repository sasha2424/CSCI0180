██████████████████████████████████████████████████████████████████████████████████

   ▄██████▄  ███    █▄   ▄█   ▄███████▄   ▄█   ▄█        ▄█          ▄████████ 
  ███    ███ ███    ███ ███  ██▀     ▄██ ███  ███       ███         ███    ███ 
  ███    █▀  ███    ███ ███▌       ▄███▀ ███▌ ███       ███         ███    ███ 
 ▄███        ███    ███ ███▌  ▀█▀▄███▀▄▄ ███▌ ███       ███         ███    ███ 
▀▀███ ████▄  ███    ███ ███▌   ▄███▀   ▀ ███▌ ███       ███       ▀███████████ 
  ███    ███ ███    ███ ███  ▄███▀       ███  ███       ███         ███    ███ 
  ███    ███ ███    ███ ███  ███▄     ▄█ ███  ███▌    ▄ ███▌    ▄   ███    ███ 
  ████████▀  ████████▀  █▀    ▀████████▀ █▀   █████▄▄██ █████▄▄██   ███    █▀  
                                              ▀         ▀                      

██████████████████████████████████████████████████████████████████████████████████

Alexander Ivanov and Rahul Shahi


__________________________________________________________________
Instructions:

Start up the server.
Start up the Guizilla browser.

At this point you can use the mouse and keyboard to navigate the web.
The top of the window has:
 - Back button to go to the previous page.
 - Text field to go to a URL.
 - Button to go to the designated URL.
 - A quit button to exit the browser.
 
The pages can contain text, links, text fields, and buttons.




__________________________________________________________________
Design:

The main portion of the browser is based in the GUIBrowser class.
This renders the HTML retrieved from the server and handles connecting to pages
and sending form data. The Browser also handles the back-button functionality as
well as text fields.

The HTML Parse handles parsing the HTML elements sent from the server. It converts the String
received by the buffered reader into a list of HTMLElement objects.
These HTML objects are then rendered with a match clause.

HTMLElement and the child classes are data objects that hold the HTML elements of the web-page
and their respective content.

Specifically the Form class has an additional method that extracts all of its internal inputs
that were given by the user. It then formats them in the way that can be sent to the server.


The main portion of the server is in the Server class.
It handles user queries and returns the HTML page that the user requested.
This is done through reflection and calling methods in the various Page classes.

Each class is responsible for its own pages and the server simply sends this
information on to the client.

The server is also responsible for handling all of the internal errors and
displaying the corresponding pages.


__________________________________________________________________
Features:

All required features were implemented.

Additional features include:
 - Links can be displayed as buttons or as hyper-links
 - Links and Buttons can be configured to submit form data on click
   For example you can click one of several buttons to choose a number
   instead of having to type it onto a text field and pressing submit
 - Links and buttons can be horizontally or vertically grouped
 - All of the above features for buttons/links are independent and can
   be mixed and matched
 - The form submit button is blue
 - Our Search page could display more than 10 pages. The number of links
   is unlimited.
 - There is a helpful link to the Index on the start page of the browser
 

__________________________________________________________________
Testing:



__________________________________________________________________
Bugs:

No known bugs.
   \
    '-.__.-'
    /oo |--.--,--,--.
    \_.-'._i__i__i_.'
          """""""""  

__________________________________________________________________
Collaboration:

No collaboration for this project.






