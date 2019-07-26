$(function(){	
	 var datepicker, config;
     config = {
         locale: 'pt-br',
         format: 'dd/mm/yyyy',
         uiLibrary: 'bootstrap4'
     };     
     datepicker = $('#datepicker').datepicker(config);
});