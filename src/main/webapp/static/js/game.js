
var pjs = new PointJS('2D', 600, 400, { // 16:9
	backgroundColor : '#53769A' // if need
});

var log = pjs.system.log; // log = console.log;
var game = pjs.game; // Game Manager
var point = pjs.vector.point; // Constructor for Point
var camera = pjs.camera; // Camera Manager
var brush = pjs.brush; // Brush, used for simple drawing
var OOP = pjs.OOP; // Object's manager
var math = pjs.math; // More Math-methods
var levels = pjs.levels; // Levels manager

var mouse = pjs.mouseControl.initMouseControl();
var width = game.getWH().w; // width of scene viewport
var height = game.getWH().h; // height of scene viewport

pjs.system.setTitle('rotateForAngle'); // Set Title for Tab or Window

// Game Loop
game.newLoopFromConstructor('myGame', function() {
	// Constructor Game Loop

	var myText = game.newTextObject({
		positionC : point(game.getWH2().w, game.getWH2().h), // central
																// position of
																// text
		size : 50, // size text
		color : '#EAEAEA', // color text
		text : 'Hello, World!', // label
		font : 'Arial' // font family
	});

	this.update = function() {
		game.clear(); // clear screen

		myText.draw(); // drawing text
		myText.rotateForAngle(pjs.vector.getAngle2Points(myText.getPositionC(),
				mouse.getPosition()), 5);

	};
	
	

});

game.startLoop('myGame');