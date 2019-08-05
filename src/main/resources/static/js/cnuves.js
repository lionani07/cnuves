$(function(){	
	$('[data-toggle=tooltip]').tooltip();
	$('.js-money').mask('#.##0,00', {reverse: true});
	$('.js-cpf').mask('000.000.000-00', {reverse: true});
	$('.js-telefone').mask('(00) 00000.0000');
	
	$(".js-efectuarPagamento").on("click", function(e){			
		e.preventDefault();		
		var row = $(this);
		var url = $(this).attr("href");	
		$.ajax({
			url: url,
			method: "get",
			success: function(msg){		
				row.siblings(".js-text-update").removeClass("d-none");
				row.siblings(".js-text-update").html('<i class="mdi mdi-currency-usd"></i> Pagamento Efectuado');				
				row.remove();
				$(".toast .toast-header").addClass("bg-success");	
				$(".toast .mr-auto span").text("Success!")
				$(".toast .toast-body").text(msg);
				$(".toast").toast("show");
			},
			error: function(error){				
				$(".toast .toast-header").addClass("bg-danger");	
				$(".toast .mr-auto span").text("Error!");
				$(".toast .toast-body").text(error.responseText);
				$(".toast").toast("show");
			}
			
		});
	});
	
	$("#filterPaciente").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#tablePacientes tr").filter(function() {
	      $(this).toggle($(this).find(".js-nome").text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	
});