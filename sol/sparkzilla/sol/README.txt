
######################################################################
   _____ _____        _____  _  __ ___________ _      _               
  / ____|  __ \ /\   |  __ \| |/ /|___  /_   _| |    | |        /\    
 | (___ | |__) /  \  | |__) | ' /    / /  | | | |    | |       /  \   
  \___ \|  ___/ /\ \ |  _  /|  <    / /   | | | |    | |      / /\ \  
  ____) | |  / ____ \| | \ \| . \  / /__ _| |_| |____| |____ / ____ \ 
 |_____/|_| /_/    \_\_|  \_\_|\_\/_____|_____|______|______/_/    \_\
######################################################################


Alexander Ivanov and Rahul Shahi


__________________________________________________________________
User Instructions:

Upon Starting Sparkzilla you will be greeted with the home page. 
You will have 3 base actions available to you plus some page specific actions.
To execute any action type the corresponding number and press enter.

Typing 1 will take you back to the previous page that you were on.
Typing 2 will let you enter the URL of a web-page and you will then be redirected there
Typing 3 will quit the browser

For specific pages you will have the ability to type additional numbers to 
access links, input text, and submit forms. For each of these type the number next to the 
element on the page that you want to interact with.


__________________________________________________________________
Design:

The main portion of the browser is based in the Browser class.
The runBrowser() method has a main loop that continues to read user input and display
the web-page until the user quits.
The Browser class also handles server communication in terms of sending and receiving data.

The HTML Parse handles parsing the HTML elements sent from the server. It converts the String
received by the buffered reader into a list of HTMLElement objects.

HTMLElement and the child classes are a data object to hold the HTML elements of the web-page
and their respective content.

Specifically the Form class has an additional method that extracts all of its internal inputs
that were given by the user. It then formats them in the way that can be sent to the server.



__________________________________________________________________
Features:

All features have been implemented.
No extra features have been implemented.
No known bugs.

__________________________________________________________________
Testing:


For testing we have setup Unit Tests and System Tests.

System Test information can be found in the "Testing.txt" file


Unit testing has been included for certain form elements, including setting the values of
TextInputs and converting form data into UTF-8 encoded string form.

__________________________________________________________________
Collaboration:

No collaboration for this project.