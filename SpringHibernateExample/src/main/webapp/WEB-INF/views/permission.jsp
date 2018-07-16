<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />


</head>
<body>
 <div id="jstree">
    <!-- in this example the tree is populated from inline HTML -->
    <ul>
      <li>Root node 1
        <ul>
          <li id="child_node_1">Child node 1</li>
          <li>Child node 2</li>
        </ul>
      </li>
      <li>Root node 2</li>
    </ul>
  </div>
  <button>demo button</button>
  <script type="text/javascript" src="../../assets/js/jquery-1.10.2.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
  <script>
	$(function () { 
	    // 6 create an instance when the DOM is ready

	    // 7 bind to events triggered on the tree

	    $("#jstree").jstree({
	        "checkbox" : {
	          "keep_selected_style" : false
	        },
	        "plugins" : [ "checkbox" ]
	      });
	    $('#jstree').on("changed.jstree", function (e, data) {
		      console.log(data.selected);
		    });
	    // 8 interact with the tree - either way is OK
	    $('button').on('click', function () {
	      $('#jstree').jstree(true).select_node('child_node_1');
	      $('#jstree').jstree('select_node', 'child_node_1');
	      $.jstree.reference('#jstree').select_node('child_node_1');
	    });
	  });
</script>
</body>
</html>