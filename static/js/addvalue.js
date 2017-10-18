var host = "http://localhost:8080";

function retornarParaContribuir(){
  document.getElementsByName('addValue')[0].style.display="block"
  document.getElementsByName('addValueLoading')[0].style.display="none"
  document.getElementsByName('addValueResult')[0].style.display="none"
  document.getElementsByName('addValueResultError')[0].style.display="none"
}

function adicionarValor(){
  var bank = document.getElementsByName('addValue-banks')[0].value;
  var date = document.getElementsByName('addValue-date')[0].value;
  var value = document.getElementsByName('addValue-value')[0].value;
  if (bank==null || bank =="") {
    alert("Banco invalido! Por favor, tente novamente!");
    return false;
  }
  if (date==null || date =="") {
    alert("Data invalida! Por favor, tente novamente!");
    return false;
  }
  if (value==null || value =="") {
    alert("Valor invalido! Por favor, tente novamente!");
    return false;
  }
  var request = host + "/addValue?provider=" + bank + "&value=" + value + "&date=" + date
  document.getElementsByName('addValue')[0].style.display="none"
  document.getElementsByName('addValueLoading')[0].style.display="block"
  console.log(request);
  $.ajax({
    url: request,
    dataType: "text",
    success: function(data) {
      if (data == "ok") {
        document.getElementsByName('addValueLoading')[0].style.display="none"
        document.getElementsByName('addValueResult')[0].style.display="block"
      } else {
        document.getElementsByName('addValueLoading')[0].style.display="none"
        document.getElementsByName('addValueResultError')[0].style.display="block"
      }
    },
    error: function(thrownError) {
      document.getElementsByName('addValueLoading')[0].style.display="none"
      document.getElementsByName('addValueResultError')[0].style.display="block"
    }
  });

}
