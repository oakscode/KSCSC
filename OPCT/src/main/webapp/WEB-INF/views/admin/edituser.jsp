<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Vertical form options -->
					
					
					<span>${insertstatus}</span>
					<!-- Registration form -->
					<form action="adduser" method="post">
						<div class="row">
							<div class="col-lg-6 col-lg-offset-3">
								<div class="panel registration-form">
									<div class="panel-body">
										<div class="text-center">
											<h5 class="content-group-lg">Edit user<small class="display-block">All fields are required</small></h5>
										</div>


										<div class="form-group has-feedback">
											<input onkeyup='username_check();' id='username' type="text" class="form-control" placeholder="Choose username" name="username" required="">
											<div class="form-control-feedback">
												<i class="icon-user-plus text-muted"></i>
											</div>
											<span>status :</span><span id='msg'> </span>
											<div>
											</div>
											
										</div>

										<div class="row">
											<div class="col-md-6">
												<div class="form-group has-feedback">
													<input type="text" class="form-control" placeholder="First name" name="title" required="">
													<div class="form-control-feedback">
														<i class="icon-user-check text-muted"></i>
													</div>
												</div>
											</div>

											<div class="col-md-6">
												<div class="form-group has-feedback">
													<input type="text" class="form-control" placeholder="Employee code" name="empcode" required="">
													<div class="form-control-feedback">
														<i class="icon-user-check text-muted"></i>
													</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-md-6">
												<div class="form-group has-feedback">
													<input id='pass1' type="password" class="form-control" placeholder="Create password" required="">
													<div class="form-control-feedback">
														<i class="icon-user-lock text-muted"></i>
													</div>
												</div>
											</div>

											<div class="col-md-6">
												<div class="form-group has-feedback">
													<input onkeyup='password_match();' id='pass2' type="password" class="form-control" placeholder="Repeat password" name="password" required="">
													<div class="form-control-feedback">
														<i class="icon-user-lock text-muted"></i>
													</div>
													<span id='mismatch_message'></span>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="checkbox">
												<label>
													Change <a href="#">Usertype</a>
													<select data-placeholder="Select your state" class="select" name="id_usertype">
														
														<c:forEach var="u" items="${usertype}">
															<option value="${u.id_usertype}">${u.title}</option>
														</c:forEach>
													
													</select>
												</label>
											</div>
										</div>

										<div class="text-right">
											<!-- <button type="submit" class="btn bg-teal-400 btn-labeled btn-labeled-right ml-10"><b><i class="icon-plus3"></i></b> Update account</button> -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
                    <script>
                    function password_match()
                    {
                       var pass1=document.getElementById("pass1").value;
                       var pass2=document.getElementById("pass2").value;
                       if(pass1 == pass2)
                           {
                    	   document.getElementById("mismatch_message").innerHTML='<font color="green">Password match</font>'
                           }
                       else
                           {
                    	   document.getElementById("mismatch_message").innerHTML='<font color="red">Password not match</font>'
                           }
                    }

                    function username_check()
                    {
                       var username=document.getElementById("username").value;


                       var xhttp = new XMLHttpRequest();
                       xhttp.onreadystatechange = function() {
                         if (this.readyState == 4 && this.status == 200) {
                          document.getElementById("msg").innerHTML = this.responseText;

                          if(this.resposeText=="true")
                        	  document.getElementById("msg").innerHTML = this.responseText;
                         }
                         else{
                        	 document.getElementById("msg").innerHTML = this.responseText;
                             }
                       };
                       xhttp.open("GET", "ajax_usercheck?username="+username, true);
                       xhttp.send();
                    
                      
                    }
                    </script>