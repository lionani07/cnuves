$(function(){	
	 var datepicker, config;
     config = {
         locale: 'pt-br',
         format: 'dd/mm/yyyy',
         uiLibrary: 'bootstrap4',
         size: 'small',         
     };     
     datepicker = $('.js-datepicker').datepicker(config); 
});