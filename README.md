#Point of Sale GUI (POS GUI)
Chae Jubb \\ ecj2122

##To use this software:
./$ java posTester

Follow Pop-up Instructions.  When selecting a menu source file, be sure to select a text file.
	(Anything that can be interpreted as a text file should work, but I only guarantee *.txt files)
	The file must be formatted in a specific way to allow the parser to interpret it without error:
		1) Each odd-numbered line should be text, the name of a dish
		2) Each even-numbered line should be the price of the preceding dish 

When using the GUI, the user adds a menu item to the order by clicking on the corresponding button.
	To submit the order, the user presses the ``Submit Order" button at which point a pop-up 
	confirms the order was submitted. At this same time, the order is recorded to a log file in the
	program root directory. Additional orders are appended to this file.
	
Exiting the GUI is simply done by closing the window (or selecting ``cancel" at one of the dialog
	pop-ups).
	
##Note on design choices:
I chose to arrange buttons / menu as I did (by not creating lasting button objects and identifying
	buttons by short name) in order to make the system more easily adaptable to a different number
	of menu options with indeterminate number of buttons.

Additionally, I created the system with abbreviated names on the buttons (if beyond a certain 
    length).  In doing so, I am making the reasonable assumption that the restaurant can use names
    that differ in the first 20 characters.  I additionally chose the default window size to be one
    that comfortably fits (while not maximized) on my 1920 x 1080 pixel display.  This easily fits 
    below a standard iPad resolution, a comparison point suggested on piazza.

Menu was constructed using a variation of the Singleton pattern.  Once the menu object is 
	successfully created, a new one cannot be created.  I added the extra boolean because the menu
	creation can fail (due to an improperly formated or non-existent file).  Thus, I only set that
	boolean to true once the menu has been successfully created.  I used this design pattern here
	because the menu should be kept constant throughout a session.

GUI Controller is a facade with which GUI interacts.  The GUI class is responsible for getting the
	menu source file name from the user and then constructing the GUI skeleton (the part of the GUI
	that is constant no matter the menu input).  The space left for the menu is then filled by the
	GUI controller.  This controller also reacts to action events in the GUI.  All the interactions
	between the GUI and other classes happen through the GUI controller.  Thus, a facade.  I used
	this pattern to consolidate and make more streamlined the GUI actions.
