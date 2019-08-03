$(function(){	
	 var datepicker, config;
     config = {
         locale: 'pt-br',
         format: 'dd/mm/yyyy',
         uiLibrary: 'bootstrap4',
         size: 'small', 
         minDate: function() {
             var date = new Date();
             date.setDate(date.getDate());
             return new Date(date.getFullYear(), date.getMonth(), date.getDate());
         },
     };
     datepicker = $('.js-datepickerAgenda').datepicker(config);
});