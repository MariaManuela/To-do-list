function deleteTask(elementName) {
	 $.ajax({
         url: "/delete?name=" + elementName,
         type: "DELETE",
         success: function (data) {
        	 if(data) {
        		 $("#task-list").load(location.href+" #task-list>*","");
        		 loadTasks();
        		
        	 }
         },
         error: function (data) {
             console.log("nup");
         }

     });
}

function check(element) {
	var parent = $(element).parent();
	var isDone = $(element).is(":checked");
	var name = parent.children(".task-name" ).text();
	 $.ajax({
         url: "/check?done=" + isDone + "&name=" + name,
         type: "PUT",
         success: function (data) {
        	 if(data){
        		 isDone ? parent.children(".task-name").addClass("done") : parent.children(".task-name").removeClass("done");
        	 }
         },
         error: function (data) {
             console.log("nup");
         }

     });
	
}

function editTask(index) {
	var parent = $('#editBtn'+index).parent();
	console.log(parent.children(".task-name" ).text());
	var oldName = parent.children(".task-name" ).text();

	parent.children().hide();
	parent.append('<div id="dynamic-form"> Edit name: <input id="input-name" type="text" value="' + oldName +'"> <button id="saveBtn">Save</button></div><button id=cancel>Cancel</button>')
	$('#cancel').click(function(){
		$('#cancel').remove();
		$('#dynamic-form').remove();
		parent.children().show();
	});
	$('#saveBtn').click(function(){
		var newName = $('#input-name').val();
		var editNameDTO = {
				"oldName": oldName,
				"newName": newName
		}
		$.ajax({
			url: "/edit",
			type: "PUT",
			contentType : "application/json",
			data: JSON.stringify(editNameDTO),
			success: function (data) {
				if(data) {
					$("#task-list").load(location.href+" #task-list>*","");
					loadTasks();
				}
			},
			error: function (data) {
				console.log("nup");
			}
			
		});
	});
}


function loadTasks() {
	$.ajax({
		type: "GET",
		url: "/tasks",
		dataType : "json",
		error: function(data) {
			console.log("Error")
		},
		success : function (data) {
			var index = 0;
			var date;
			var taskName;
			data.forEach(function (element) {
				date = element.startDate.substring(0, element.startDate.indexOf('T'));
				taskName = encodeURIComponent(element.name);
				console.log(element.done);
				$('#task-list').append('<div class=task><input id="done' + index +'" onclick="check(this)" type="checkbox"/><div class="task-name">' + element.name + '</div><div class="task-start-date">'
										+ date + '</div><button id = "editBtn' + index + 
						               '" type="button" onclick=editTask("'+ index +
						               '") class="edit-button" >' + "Edit" + '<button id = "deleteBtn' + index + 
							               ' type="button" onclick=deleteTask("'+ taskName +
							               '") class="delete-button" >' + "Delete" +
						               '</div>'
									  );
				if(element.done) {
					console.log("PE TRUE");
					$('#done'+index).attr('checked', true);
					$('#done'+index).parent().children(".task-name" ).addClass("done");
				}else {
					console.log("PE FALSE");
					$('#done'+index).attr('checked', false);
					$('#done'+index).parent().children(".task-name" ).removeClass("done");
				}
				index++;
			})
				$('.hide-input').hide();
			   
	
		}
			
	});
}



$(document).ready(
	
		
		function() {
			
			
			loadTasks();
			console.log($('#done'));
			
			$('.addBtn').click(submitForm);
			
			function submitForm() {
				var taskInfo = getTaskInfo();
		

				
				$.ajax({
					type: "POST",
					url: "/added",
					contentType : "application/json",
					data : JSON.stringify(getTaskInfo()),
					error: function(data) {
						console.log("Error")
						alert("Internal Server Error");
						
					},
					
					success : function(data) {
						 if(data) {
							 $("#task-list").load(location.href+" #task-list>*","");
						     loadTasks();
						     $('#task-name').val('');

						 }else {
							 alert("Error");
						 }
						
					}
				});
				
			 }

		
		function getTaskInfo() {
			return {
				"taskName" : $('#task-name').val()
				
			};
		}
		
		


		
});