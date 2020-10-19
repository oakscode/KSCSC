<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="panel panel-flat">
	<div class="panel-heading">
		<h5 class="panel-title">Submenu list</h5>
	</div>


	<table
		class="table datatable-basic table-bordered table-striped table-hover">
		<thead>
			<tr>
				<th>id_menu</th>
				<th>SubMenu id</th>
				<th>Title</th>
				<th>Page</th>
				<th>Description</th>
				<th>active</th>
				<th class="text-center">Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="m" items="${allmenus}">
				<tr>
				    <td>${m.id_menu}</td>
					<td>${m.id_menu_sub}</td>
					<td><a href="submenulist?id=${m.id_menu_sub}">${m.title}</a></td>
					<td>${m.page}</td>
					<td>${m.description}</td>
					<td>${m.active}</td>
					<td class="text-center">
						<ul class="icons-list">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"> <i class="icon-menu9"></i>
							</a>

								<ul class="dropdown-menu dropdown-menu-right">
									<li><a href="#"><i class="icon-cog6"></i>Edit</a></li>
									<li><a href="deletesubmenu?id_menu=${m.id_menu}&id_menu_sub=${m.id_menu_sub}"><i
											class="icon-cancel-circle2"></i>Remove</a></li>
								</ul></li>
						</ul>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>