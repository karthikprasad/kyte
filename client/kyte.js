var PPSList = [];
//var PPSList = [{"c":"a", "p":0}, {"c":"b", "p":1}, {"c":"\x01", "p":1.25}, {"c":"c", "p":1.4375}, {"c":"d", "p":2}];
var NUM_USERS = 2;
var USER_ORDER = 1;
var MIN_PPS_VAL = 0;
var MAX_PPS_VAL = 100000;

//////////////////////////////////////////////////////////////////////////////////////////
// get cursor posiion
function getCursorPos() {
    var cursorPos = $("#page").prop("selectionStart");
    return cursorPos;
}
//////////////////////////////////////////////////////////////////////////////////////////

// set cursor position
function setSelectionRange(input, selectionStart, selectionEnd) {
    if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }
    else if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd("character", selectionEnd);
        range.moveStart("character", selectionStart);
        range.select();
    }
}
function setCursorPos(pos) {
    setSelectionRange($("#page")[0], pos, pos);
}
//////////////////////////////////////////////////////////////////////////////////////////
function getNthVisibleCharIndex(n) {
    // when n < 0; return the first visible char index
    // when n > num_visble_chras, return the index of the last visible char
    // if there are no visible chars, then visIndex field = -1
    console.log("n:" + n);
    var visIndex = -1;
    var i = 0;
    for (i = 0; i < PPSList.length; i++) {
        if (PPSList[i].c != "~") {
            visIndex++;
        }
        if (visIndex >= n) {
            return {"i":i, "visIndex":visIndex};
        }
    }
    return {"i":i-1, "visIndex":visIndex};
}

function getNthVisibleCharPPS(n) {
    // returns -1 if there is no visible charecter
    var index = getNthVisibleCharIndex(n);
    return PPSList[index.i].p;
}

function getVisibleIndexfromPPS(ppsVal) {
    var visIndex = -1;
    for (var i = 0; i < PPSList.length; i++) {
        if (PPSList[i].c != "~") {
            visIndex++;
        }
        if (PPSList[i].p == ppsVal) {
            return {"i":i, "visIndex":visIndex};
        }
    }
    return {"i":0, "visIndex":visIndex};

}

function getActualKeyPressed(event) {
    var keyPressed = event.keyCode || event.which;
    // allow numbers, alphabets and other visible chars
    var disallowedKeyCodes = [9,17,18,20,91,37,38,39,40]
    if (keyPressed == 8) {
        keyPressed = 8;
    }
    else if (keyPressed in disallowedKeyCodes) {
        keyPressed = 0;
    }
    // Handle lowercase alphabets
    else if (keyPressed >= 65 && keyPressed <= 90 && !event.shiftKey)
    {
        keyPressed += 32;
    }
    else {
        // handle numbers and symbols
        switch (keyPressed) {
            // handle numbers and symbols on top of number keys
            case 49: if (event.shiftKey) {keyPressed = 33;} else {keyPressed = 49}; break;  // ! 1
            case 50: if (event.shiftKey) {keyPressed = 64;} else {keyPressed = 50}; break;  // @ 2
            case 51: if (event.shiftKey) {keyPressed = 35;} else {keyPressed = 51}; break;  // # 3
            case 52: if (event.shiftKey) {keyPressed = 36;} else {keyPressed = 52}; break;  // $ 4
            case 53: if (event.shiftKey) {keyPressed = 37;} else {keyPressed = 53}; break;  // % 5
            case 54: if (event.shiftKey) {keyPressed = 94;} else {keyPressed = 54}; break;  // ^ 6
            case 55: if (event.shiftKey) {keyPressed = 38;} else {keyPressed = 55}; break;  // & 7
            case 56: if (event.shiftKey) {keyPressed = 42;} else {keyPressed = 56}; break;  // * 8
            case 57: if (event.shiftKey) {keyPressed = 40;} else {keyPressed = 57}; break;  // () 9
            case 48: if (event.shiftKey) {keyPressed = 41;} else {keyPressed = 48}; break;  // ) 0
            // handle symbol keys
            case 189: if (event.shiftKey) {keyPressed = 95;} else {keyPressed = 45}; break;  // _ -
            case 187: if (event.shiftKey) {keyPressed = 43;} else {keyPressed = 61}; break;  // + =
            case 219: if (event.shiftKey) {keyPressed = 123;} else {keyPressed = 91}; break;  // { [
            case 221: if (event.shiftKey) {keyPressed = 125;} else {keyPressed = 93}; break;  // } ]
            case 220: if (event.shiftKey) {keyPressed = 124;} else {keyPressed = 92}; break;  // | \
            case 186: if (event.shiftKey) {keyPressed = 58;} else {keyPressed = 59}; break;  // : ;
            case 222: if (event.shiftKey) {keyPressed = 34;} else {keyPressed = 39}; break;  // " '
            case 188: if (event.shiftKey) {keyPressed = 60;} else {keyPressed = 44}; break;  // < ,
            case 190: if (event.shiftKey) {keyPressed = 62;} else {keyPressed = 46}; break;  // > .
            case 191: if (event.shiftKey) {keyPressed = 63;} else {keyPressed = 47}; break;  // ? /
            case 13: keyPressed = "\n"; break;
            case 32: keyPressed = 32; break;
            default: keyPressed = 0;
        }
    }
    return keyPressed;
}
//////////////////////////////////////////////////////////////////////////////////////////

function refreshPage() {
	if (PPSList.length === 0) return;
    // 1(a). get the current cursor position
    var currentCursorPos = getCursorPos();
    // 1(b). get the pps value of the nth visible char
    var prevCharPPSVal = getNthVisibleCharPPS(currentCursorPos-1);
    
    // 2. iterate over the pps list and populate the page
    var str = "";
    for (var i = 0; i < PPSList.length; i++) {
        if (PPSList[i].c != "~") {
            str += PPSList[i].c;
        }
    }
    //alert(JSON.stringify(PPSList));
    $("#page").val(str);

    // 3. set cursor position to the corrrect place!
    // do this getting the pos of the pps value
    var newCursorPos = 0;        
    // if the cursor was at the beginning, then there is no point in calculating thr new pos
    if (currentCursorPos > 0) {
        newCursorPos = getVisibleIndexfromPPS(prevCharPPSVal).visIndex + 1;
    }
    setCursorPos(newCursorPos);
}

//////////////////////////////////////////////////////////////////////////////////////////

function handleDelete() {
    var currentCursorPos = getCursorPos();
    var delIndex = getNthVisibleCharIndex(currentCursorPos).i;
    PPSList[delIndex].c = "~";
    return {"p":PPSList[delIndex].p, "c":PPSList[delIndex].c};
}

function handleInsert(charInserted) {
    var prevPPS = MIN_PPS_VAL;
    var nextPPS = MAX_PPS_VAL;
    
    // case when PPSList is empty is trivially handled
    //// taken care by the initializations
    // case when PPSList already has some values.
    if (PPSList.length > 0)
    {
        var currentCursorPos = getCursorPos();  // cursor would have moved 1 place ahead after insert
        // char added in the beginning
        if (currentCursorPos == 1)
        {
            prevPPS = MIN_PPS_VAL;
            nextPPS = PPSList[0].p;
        }
        else {
            var prevVisIndex = getNthVisibleCharIndex(currentCursorPos-2);

            // there are no visible charecters in the PPS, 
            if (prevVisIndex.visIndex == -1) {
                // insert at the right most end
                prevPPS = PPSList[PPSList.length-1].p;
                nextPPS = MAX_PPS_VAL;
            }
            else {
                prevPPS = PPSList[prevVisIndex.i].p;
                if (prevVisIndex.i == PPSList.length-1) {  // char added at the end
                    nextPPS = MAX_PPS_VAL;  // case when no more charecters exist beyond this point
                }
                else {  // char added in the middle
                    nextPPS = PPSList[prevVisIndex.i+1].p;
                }
            }
        }
    }
    // ppsrange to modify
    var newPPSrange = (nextPPS - prevPPS) / NUM_USERS;
    var newPPS = prevPPS + (USER_ORDER - 1)*newPPSrange + (newPPSrange/2);
    console.log("newPPS: " + newPPS);

    // add the object to the PPSList
    var insPos = 0;
    while (insPos < PPSList.length) {
        if (PPSList[insPos].p > newPPS)
        {
            break;
        }
        insPos++;
    }
    PPSList.splice(insPos, 0, {"p":newPPS, "c":charInserted});
    return {"p":newPPS, "c":charInserted};

}

// called on eevry key press
function updatePPSList(event) {
    var update;
    // determine the key pressed
    var keyPressed = event.keyCode || event.which;
    var keyPressed = getActualKeyPressed(event);
    console.log("keyPressed:" + keyPressed);
    
    // handle delete
    if (keyPressed == 8) { // backspace
        update = handleDelete();
    }
     // handle insert
    else if (keyPressed != 0) {
    	var charInserted;
    	if (keyPressed == 13) {
    		charInserted = "\n";
    	}
        charInserted = String.fromCharCode(keyPressed);
        update = handleInsert(charInserted);
    }
    console.log(JSON.stringify(PPSList));
    // refresh page
    refreshPage();
    // send the update to server
    sendUpdate(update);
}
//////////////////////////////////////////////////////////////////////////////////////////

function sendUpdate(update) {
	var requestUrl = "http://192.168.0.23:8380/CollabEdit.ClientServer/PerformOperation/insert/" + encodeURI(update.c) + "/" + update.p;
	console.log(requestUrl);
    $.ajax({
                type: "GET",
                url: requestUrl,
                //data: update,
                dataType: "json",
                success: function(response) {
                    if (response.message == "ERROR") {
                        alert("Problem with the server!");
                    }
                    console.log(response);
                }
            });
}

$("#page")
.attr("unselectable", "on")
.on("selectstart", false)
.on("keyup", updatePPSList);


//////////////////////////////////////////////////////////////////////////////////////////

function pollState() {
    $.ajax({
        url: "http://192.168.0.23:8380/CollabEdit.ClientServer/GetUpdatedPPS",
        type: "GET",
        success: function(data) {
            console.log(data);
            console.log(data.PPSList);
            PPSList = data.PPSList;
            NUM_USERS = 2/*data.NUM_USERS*/;
            USER_ORDER = data.USER_ORDER;
            refreshPage();
        },
        dataType: "json",
        //complete: setTimeout(function() {pollState()}, 1000),
        timeout: 1000
    })
};


// Register with server on signup
$(document).ready( function() {
    console.log('here');
    $.ajax({    type: "GET",
                url: "http://192.168.0.23:8380/CollabEdit.ClientServer/RegisterClient",
                dataType: "json",
                /*context: document.body,*/
                success: function(response) {
                    console.log('success');
                    console.log(response);
                    if (response.message == "ERROR")
                    {
                        alert("Problem connecting to the server!");
                        return;
                    }
                    PPSList = response.PPSList;
                    NUM_USERS = 2/*response.NUM_USERS*/;
                    USER_ORDER = response.USER_ORDER;
                    console.log(NUM_USERS);
                    alert(NUM_USERS);
                    console.log(USER_ORDER);
                    alert(USER_ORDER);
                }
            });
    // start event listeners
    //handleNewUser();
    //handleUpdatedPPSList();
    pollState();
});
