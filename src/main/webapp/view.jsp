<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<portlet:defineObjects />
<portlet:actionURL name="upload" var="uploadFileURL"></portlet:actionURL>
<portlet:resourceURL var="actionUrl" />


<img src="<%=request.getContextPath()%>/images/logoFondoBlanco.jpg"
	id="bogotaImg">
<div id="principal">
	<aui:form action="<%=uploadFileURL%>" enctype="multipart/form-data"
		method="post">
		<div class="form-group">
			<label for="usr">Seleccione la base de este mes:</label> <br>
			<aui:input type="file" name="fileupload" />
		</div>
		<aui:button name="Save" value="Save" type="submit" />
	</aui:form>

	<div id="myAlert"></div>
	
	<table id="dynamicTable" class="display"></table>
	

	<aui:script use="io,aui-base,aui-io-request">
		var A = AUI();	
		A.on('domready', function(event) {
			// aui ajax call	
			A.io.request('${actionUrl}', {
				dataType : 'json',
				method : 'GET',
				on : {
					success : function() {
						var data = this.get('responseData');
						if (data.length > 0) {
							$('#dynamicTable').DataTable({
								data : data,
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
						}else {
				
 						}
					},
					failure : function() {
				
												

					}

				}
			});
		});
		
// 		YUI().use(
// 				  'aui-alert',
// 				  function(Y) {
// 				    new Y.Alert(
// 				      {
// 				        boundingBox: '#myAlert',
// 				        bodyContent: 'This is an alert',
// 				        closeable: true,
// 				        cssClass: 'alert-info',
// 				        render: true
// 				      }
// 				    );
// 				  }
// 				);
		
		
	</aui:script>
</div>



