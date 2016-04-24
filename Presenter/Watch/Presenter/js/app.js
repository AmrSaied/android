( function () 
		{
	window.addEventListener( 'tizenhwkey', function( ev ) {
		if( ev.keyName === "back" ) {
			var page = document.getElementsByClassName( 'ui-page-active' )[0],
				pageid = page ? page.id : "";
			if( pageid === "main" ) {
				try {
					tizen.application.getCurrentApplication().exit();
				} catch (ignore) {
				}
			} else {
				window.history.back();
			}
		}
	}
	);
} () );
var webSocket = new WrapperWS();

function EnterIpAddress() {


 
  webSocket.send("PlayPowerPoint");
}

function PausePowerPoint() {


 
  webSocket.send("PausePowerPoint");
}

function PerviousPowerPoint() {



  webSocket.send("PerviousPowerPoint");
}

function NextPowerPoint() {



  webSocket.send("NextPowerPoint");
}

function PowerPowintSend() {


  var webSocket = new WrapperWS();
  webSocket.send("asdasdasdasd");
}

function SaveIpAddres (){
	// to store a value
	window.localStorage.setItem( 'IPADDRESS', item_value);

	// to retrieve a value
	item_value = window.localStorage.getItem( 'IPADDRESS' );

	// to delete a storage
	window.localStorage.removeItem( 'IPADDRESS' );
}

function WrapperWS() {
    if ("WebSocket" in window) {
        var ws = new WebSocket('ws://localhost:3339');
        var self = this;

        ws.onopen = function () {
            console.log("Opening a connection...");
            window.identified = false;
        };
        ws.onclose = function (evt) {
            console.log("I'm sorry. Bye!");
        };
        ws.onmessage = function (evt) {
        	 console.log(evt);
        };
        ws.onerror = function (evt) {
            console.log("ERR: " + evt.data);
        };

        this.write = function () {
            if (!window.identified) {
                connection.ident();
                console.debug("Wasn't identified earlier. It is now.");
            }
            ws.send(theText.value);
        };
        
        this.send = function (message, callback) {
            this.waitForConnection(function () {	
                ws.send(message);
                if (typeof callback !== 'undefined') {
                  callback();
                }
            }, 1000);
        };

        this.waitForConnection = function (callback, interval) {
            if (ws.readyState === 1) {
                callback();
            } else {
                var that = this;
                // optional: implement backoff for interval here
                setTimeout(function () {
                    that.waitForConnection(callback, interval);
                }, interval);
            }
        };

        this.ident = function () {
            var session = "Test";
            try {
                ws.send(session);
            } catch (error) {
                if (error instanceof InvalidStateError) {
                    // possibly still 'CONNECTING'
                    if (ws.readyState !== 1) {
                        var waitSend = setInterval(ws.send(session), 1000);
                    }
                }
            }
        window.identified = true;
            theText.value = "Hello!";
            say.click();
            theText.disabled = false;
        };

    };

}