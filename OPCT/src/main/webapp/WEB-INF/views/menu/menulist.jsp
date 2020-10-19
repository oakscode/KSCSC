<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<div class="row">
	<div class="panel panel-flat">
		<div class="panel-body">
			<div class="tabbable">
				<ul class="nav nav-tabs nav-tabs-highlight text-center">
					<li class="active">
						<a href="#centered-tab0" data-toggle="tab">Menus</a></li>
					<li><a href="#centered-tab3" data-toggle="tab">Add Single menu</a></li>
					<li><a href="#centered-tab1" data-toggle="tab">Add Mainmenu</a></li>
					<li><a href="#centered-tab2" data-toggle="tab">Add Submenu</a></li>
					

				</ul>

				<div class="tab-content">

					<div class="tab-pane active" id="centered-tab0">

						<table
							class="table datatable-basic table-bordered table-striped table-hover">
							<thead>
								<tr>
									<th>Menu id</th>
									<th>Title</th>
									<th>page</th>
									<th>Type</th>
									<th>icon class</th>
									<th>User type</th>
									<th>active</th>
									<th class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="m" items="${allmenus}">
									<tr>
										<td>${m.id_menu}</td>
										<td><a href="submenulist?id=${m.id_menu}">${m.title}</a></td>
										<td>${m.page}</td>
										<td>
										
											<c:set var = "type" scope = "session" value = "${m.type}"/>
     						    				<c:if test = "${type > 0}">
     						    					<span class="label bg-blue">Multiple</span>
     						    				</c:if>
     						    				<c:if test = "${type < 1}">
     						    					<span class="label bg-success">Single</span>
     						    				</c:if>
										
										
										</td>
										<td>${m.icon}</td>
										<td>${m.usertype_title}</td>
										<td>${m.active}</td>
										<td class="text-center">
											<ul class="icons-list">
												<li class="dropdown"><a href="#"
													class="dropdown-toggle" data-toggle="dropdown"> <i
														class="icon-menu9"></i>
												</a>

													<ul class="dropdown-menu dropdown-menu-right">
														<li><a href="#"><i class="icon-cog6"></i>Edit</a></li>
														<li><a href="deletemainmenu?id_menu=${m.id_menu}"><i
																class="icon-cancel-circle2"></i>Remove</a></li>
													</ul></li>
											</ul>
										</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>

					</div>
					<div class="tab-pane" id="centered-tab1">
						<!-- Registration form -->
						<form action="addmainmenu" method="post">
							<div class="row">
								<div class="col-lg-6 col-lg-offset-3">
									<div class="panel registration-form">
										<div class="panel-body">
											<div class="text-center">
												<h5 class="content-group-lg">Add Main menu</h5>
											</div>


											<div class="form-group has-feedback">
												<input type="text" class="form-control"
													placeholder="Choose Menu Title" name="title" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>
											</div>
											<div class="form-group has-feedback">
												<input type="text" class="form-control"
													placeholder="Bootstrap icon class" name="icon" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>
											</div>

											<div class="form-group">
												<label class="control-label col-lg-2"><a href="#">Usertype</a></label>
												<div class="col-lg-10">
													<select name="usertype" class="form-control">
														<c:forEach var="u" items="${usertype}">
															<option value="${u.id_usertype}">${u.title}</option>
														</c:forEach>
													</select>
												</div>
												<div>&nbsp</div>
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

					<div class="tab-pane" id="centered-tab2">

						<!-- Registration form -->
						<form action="addsubmenu" method="post">
							<div class="row">
								<div class="col-lg-6 col-lg-offset-3">
									<div class="panel registration-form">
										<div class="panel-body">
											<div class="text-center">
												<h5 class="content-group-lg">Add Sub menu</h5>
											</div>


											<div class="form-group has-feedback">
												<input type="text" class="form-control"
													placeholder="Choose Menu Title" name="title" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>

											</div>
											<div class="form-group has-feedback has-warning">
												<span class="help-block">Warning exclude special
													characters like space,/,*,#,"",etc.</span> <input type="text"
													class="form-control" placeholder="Page title" name="page"
													required="">
											</div>
											<div class="form-group has-feedback">
												<input type="text" class="form-control"
													placeholder="Description" name="description" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>

											</div>

											<div class="form-group">
												<label class="control-label col-lg-2"><a href="#">Mainmenu</a></label>
												<div class="col-lg-10">
													<select name="id_menu" class="form-control">
														<c:forEach var="u" items="${allmenus}">
															<option value="${u.id_menu}">${u.title}</option>
														</c:forEach>
													</select>
												</div>
												<div>&nbsp</div>
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
					<div class="tab-pane" id="centered-tab3">
						<!-- Registration form -->
						<form action="addsinglemenu" method="post">
							<div class="row">
								<div class="col-lg-6 col-lg-offset-3">
									<div class="panel registration-form">
										<div class="panel-body">
											<div class="text-center">
												<h5 class="content-group-lg">Add Single menu</h5>
											</div>


											<div class="form-group has-feedback">
												<input type="text" class="form-control"
													placeholder="Choose Menu Title" name="title" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>
											</div>
											<div class="form-group has-feedback">
												<input type="text" class="form-control"
													placeholder="Bootstrap icon class" name="icon" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>
											</div>
											<div class="form-group has-feedback">
											<span class="help-block">Warning exclude special
													characters like space,/,*,#,"",etc.</span> 
												<input type="text" class="form-control"
													placeholder="page" name="page" required="">
												<div class="form-control-feedback">
													<i class="icon-cog2 text-muted"></i>
												</div>
											</div>

											<div class="form-group">
												<label class="control-label col-lg-2"><a href="#">Usertype</a></label>
												<div class="col-lg-10">
													<select name="usertype" class="form-control">
														<c:forEach var="u" items="${usertype}">
															<option value="${u.id_usertype}">${u.title}</option>
														</c:forEach>
													</select>
												</div>
												<div>&nbsp</div>
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
</div>
