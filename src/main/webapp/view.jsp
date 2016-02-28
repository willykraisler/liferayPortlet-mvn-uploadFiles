<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<portlet:defineObjects />
<portlet:actionURL name="upload" var="uploadFileURL"></portlet:actionURL>
<portlet:resourceURL var="actionUrl" />
<img src="<%=request.getContextPath()%>/images/logoFondoBlanco.jpg" id="bogotaImg">
<br>
<div id="principal">

	<div id="myAlert"></div>
	<table id="dynamicTable" class="display"></table>
	<div id="header-bog">
		<aui:form action="<%=uploadFileURL%>" enctype="multipart/form-data"
			method="post">
			<div class="form-group">
				<label for="usr">Seleccione la base de este mes:</label> <br>
				<aui:input type="file" name="fileupload" />
			</div>
			<aui:button name="Save" value="Save" type="submit" />
		</aui:form>
	</div>

	<aui:script use="io,aui-base,aui-io-request,aui-alert">
		$("#header-bog").hide();
		
		var A = AUI();	
		A.on('domready', function(event) {								
			// aui ajax call	
			A.io.request('${actionUrl}', {
				dataType : 'json',
				method : 'GET',
				on : {
					success : function() {					
						var data = this.get('responseData');					
						var dataStore = data.table;						
						if (dataStore.length > 0) {							
							$('#dynamicTable').DataTable({
								data : dataStore,
								columns : [ {
									title : "Certificado"
								}, {
									title : "Producto"
								}, {
									title : "Fecha Inicial"
								}, {
									title : "Fecha Final"
								}, {
									title : "Nombre y apellido"
								}, {
									title : "Cedula"
								} ]
							});
						}else{
							$("#myAlert").append("<div class='alert alert-warning fade in'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a><strong>Warning!</strong> No se encontraron registros en la base de datos.</div>");
						}
						
						if (data.isAdmin)
						 	$("#header-bog").show();
						
					},
					failure : function() {
						$("#myAlert").append(" <div class='alert alert-danger fade in'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a><strong>Error!</strong> Error al tratar de cargar datos - Comuniquese con Cardif soporte.</div>");
					}
				}
			});
		});
	</aui:script>
</div>



