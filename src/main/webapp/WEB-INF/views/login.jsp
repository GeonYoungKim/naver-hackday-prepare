<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
function login() {
	
	var A = (document.getElementById('A').checked) ? 'A' : 'NO';
	var B = (document.getElementById('B').checked) ? 'B' : 'NO';
	var C = (document.getElementById('C').checked) ? 'C' : 'NO';
	var id=document.getElementById('id').value;
	
	$.ajax({
        url: "http://localhost:8080/ROOT/login",
        contentType: false,
        processData: false,
        contentType: "application/json",
        data: JSON.stringify({
    		userId : id,
    		A : A,
    		B : B,
    		C : C
    	}),                         // Setting the data attribute of ajax with file_data
        type: 'post',
        success : function(data) {
        	post("/ROOT/notice", {id: id});
        }
	});
}

function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}
</script>


</head>
<body>
	
		ID : <input type="text" id="id" /><br/><br/>
		 <input type="checkbox" id="A" value="A">A 
		 <input type="checkbox" id="B" value="B">B 
		 <input type="checkbox" id="C" value="C">C 
		 <button onclick="login()">가입</button>
	
</body>
</html>