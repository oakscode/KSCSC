<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="row">
	<div class="panel panel-flat">
		<div class="panel-body">
			<div class="tabbable">
				<ul class="nav nav-tabs nav-tabs-highlight text-center">
					<li class="active">
						<a href="#centered-tab1" data-toggle="tab">User Types</a></li>
					<li><a href="#centered-tab2" data-toggle="tab">Add New usertype</a></li>
				</ul>

				<div class="tab-content">
					<div class="tab-pane active" id="centered-tab1">
						<table id="example"
							class="table table-lg table-bordered">
							<thead>
								<tr>
									<th>Usertype id</th>
									<th>Title</th>
									<th class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="m" items="${usertype}">
									<tr>
										<td>${m.id_usertype}</td>
										<td><a href="#">${m.title}</a></td>

										<td class="text-center">
											<ul class="icons-list">
												<li class="dropdown"><a href="#"
													class="dropdown-toggle" data-toggle="dropdown"> <i
														class="icon-menu9"></i>
												</a>

													<ul class="dropdown-menu dropdown-menu-right">
														<li><a href="#"><i class="icon-cog6"></i>Edit</a></li>
														<li><a href="#"><i class="icon-cancel-circle2"></i>Remove</a></li>
													</ul></li>
											</ul>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>

					<div class="tab-pane" id="centered-tab2">
					<form action="addusertype" method="post">
							<div class="row">
								<div class="col-lg-6 col-lg-offset-3">
									<div class="panel registration-form">
										<div class="panel-body">

											<div class="form-group has-feedback">
												<input type="text" class="form-control"
													placeholder="Choose New usertype" name="title" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>

											</div>

											<div class="text-right">
												<button type="submit"
													class="btn bg-teal-400 btn-labeled btn-labeled-right ml-10">
													<b><i class="icon-plus3"></i></b> save
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>

					</div>

			</div>
		</div>
	</div>
</div>


