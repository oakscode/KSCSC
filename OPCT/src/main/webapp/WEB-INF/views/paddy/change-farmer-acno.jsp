<div class="panel panel-flat">
	<div class="panel-heading">
		<h5 class="panel-title">Change Account number of farmer</h5>

	</div>

	<div class="panel-body">

		<form class="form-horizontal form-validate-jquery"
			action="change-farmer-acno" 
			method="post"
			onsubmit="if(document.getElementById('agree').checked) { return true; } else { alert('Please indicate that you have read and agree to the Above details are correct'); return false; }">




			<fieldset class="content-group">
				<!-- Input group -->
				<div class="form-group">
					<label class="control-label col-lg-3">Farmer Register No <span
						class="text-danger">*</span></label>
					<div class="col-lg-3">
						<div class="input-group">
							<input id="regno" type="text" name="regno" class="form-control"
								required="required" placeholder="Input group validation">
							<div class="input-group-addon">
								<a onclick="loadData()"> Check </a>
							</div>
						</div>
					</div>
				</div>
				<!-- /input group -->


				<div id="FormDiv"></div>
				<p>${msg}</p>
		</form>
	</div>
</div>
<!-- /form validation -->


<script>
	function farmerdetails() {

		var regno = document.getElementById("regno").value;
		var jsonstring = "";

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("farmername").innerHTML = this.responseText;
				document.getElementById("accno").innerHTML = this.responseText;

				jsonstring = this.responseText;
				var myObj = JSON.parse(jsonstring);

				document.getElementById("farmername").innerHTML = myObj.farmername;
				document.getElementById("accno").innerHTML = myObj.accno;

				if (this.resposeText == "true")

					document.getElementById("farmername").innerHTML = myObj.farmername;
				document.getElementById("accno").innerHTML = myObj.accno;

			} else {
				document.getElementById("farmername").innerHTML = jsonstring;
			}
		};
		xhttp.open("GET", "farmerdetailsbyregno/" + regno, true);
		xhttp.send();
	}

	function loadData() {
		var regno = document.getElementById("regno").value;
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("FormDiv").innerHTML = this.responseText;
			}
		};
		xhttp.open("GET", "FarmerAcchangeDataForm/" + regno, true);
		xhttp.send();
	}
</script>