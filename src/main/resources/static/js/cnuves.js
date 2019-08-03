$(function(){	
	$('[data-toggle=tooltip]').tooltip();
	$('.js-money').mask('#.##0,00', {reverse: true});
	$('.js-cpf').mask('000.000.000-00', {reverse: true});
	 $('.js-telefone').mask('(00) 00000.0000');
	
});