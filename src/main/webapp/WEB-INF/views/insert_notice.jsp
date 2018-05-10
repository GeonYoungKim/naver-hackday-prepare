<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<style>
.wrap {
    width: 300px;
    height: auto;
    position: relative;
    display: inline-block;
}
.wrap textarea {
    width: 100%;
    resize: none;
    min-height: 4.5em;
    line-height:1.6em;
    max-height: 9em;
}
.wrap span {
    position: absolute;
    bottom: 5px;
    right: 5px;
}
#counter {
  background:rgba(255,0,0,0.5);
  border-radius: 0.5em;
  padding: 0 .5em 0 .5em;
  font-size: 0.75em;
}
</style>

<script type="text/javascript">
$(document).ready(function() {
    $('#notice').on('keyup', function() {
        if($(this).val().length > 10) {
            $(this).val($(this).val().substring(0, 10));

        }
    });
});
</script>
<body>

<div class="wrap">
    <textarea id="content" maxlength="300"></textarea>
    <span id="counter">###</span>
</div>
</body>

</html>