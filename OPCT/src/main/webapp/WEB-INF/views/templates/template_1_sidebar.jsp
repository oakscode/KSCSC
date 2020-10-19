<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="sidebar sidebar-main sidebar-fixed">
				<div class="sidebar-content">

					<!-- Main navigation -->
					<div class="sidebar-category sidebar-category-visible">
						<div class="category-content no-padding">
							<ul class="navigation navigation-main navigation-accordion">

								<!-- Main -->
								<li class="navigation-header"><span>Main</span> <i class="icon-menu" title="Main pages"></i></li>
								
								<c:forEach var="menu" items="${menus}">
								 <li>
								 
								<c:set var = "type" scope = "session" value = "${menu.type}"/>
     						    <c:if test = "${type > 0}">
     						    	
     						    	<a href="#"><i class="${menu.icon}"></i> <span>${menu.title}</span></a>
									<ul>
										<c:forEach var="subMenu" items="${menu.menus_sub}">
										
											<li><a href="${subMenu.page}">${subMenu.title}</a></li>
										
										</c:forEach>
										<li class="navigation-divider"></li>
								   </ul>
         							
     							</c:if>
     							<c:if test = "${type < 1}">
     								<a href="${menu.page}"><i class="${menu.icon}"></i> <span>${menu.title}</span></a>
     							</c:if>
									
									
								   
								   
								   
								</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!-- /main navigation -->
				</div>
			</div>