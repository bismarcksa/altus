

dayName = new Array ("domingo", "segunda", "terça", "quarta", "quinta", "sexta", "sábado")
monName = new Array ("JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO")
now = new Date


function somaQuantidade() {
  
  var elements = document.getElementsByClassName('somaQuant');
  var sum = 0;
   
   alert("qtd de elementos " + elements.length);

   for (i=0;i< elements.length;i++) {
      sum = sum + parseFloat(elements[i].innerHTML);
     };
    
   alert(sum);
}
