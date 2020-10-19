<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="table-responsive">
					<table class="table table-lg table-bordered">
						<tbody>

							<tr>
								<th class="text-nowrap">Farmer name</th>
								<td colspan="4"><label id='farmername'>${farmername} </label></td>
							</tr>
							<tr>
								<th class="text-nowrap">Account No</th>
								<td colspan="4"><label id="accno">${accno} </label></td>
							</tr>
							<tr>
								<th class="text-nowrap">Bank</th>
								<td colspan="4"><label id="bank">${bank}</label></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- /List Data -->


				<!-- datatable -->
				<div class="panel panel-flat">


					<div class="panel-body">Paddy receipt sheet details</div>

					<div class="table-responsive">
						<table class="table bg-teal">
							<thead>
								<tr class="bg-teal-700">
									<th>PRS No.</th>
									<th>Payment Order No.</th>
									<th>Payment ArrOrder No.</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="m" items="${prsdetails}">
								<tr>
									<td>${m.prsno}</td>
									<td>${m.paymentOrderNumber}</td>
									<td>${m.paymentArrOrderNumber}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!-- /custom table color -->

				<!-- /datatable -->

				<div class="form-group">
					<div class="col-lg-9">
						<input type="checkbox" name="checkbox" value="check" id="agree" /> I have read and agree to the Above details are correct
					</div>
				</div>


				<!-- New Accno field -->
				<div class="form-group">
					<label class="control-label col-lg-3">New Account No<span
						class="text-danger">*</span></label>
					<div class="col-lg-9">
						<input type="text" name="newaccno" class="form-control"
							required="required" placeholder="Enter new Account Number">
					</div>
				</div>
				<!-- /New accno field -->
			</fieldset>


			<div class="text-right">
				<button type="submit" name="submit" value="submit" class="btn btn-primary">
					Submit <i class="icon-arrow-right14 position-right"></i>
				</button>
			</div>