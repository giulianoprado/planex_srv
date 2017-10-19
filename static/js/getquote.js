var result;
var host = "http://planex.herokuapp.com/";
function retornarParaBusca(){
  document.getElementsByName('searchQuote')[0].style.display="block"
  document.getElementsByName('searchQuoteLoading')[0].style.display="none"
  document.getElementsByName('searchQuoteResult')[0].style.display="none"
  document.getElementsByName('searchQuoteResultError')[0].style.display="none"
}
function buscarCotacao(){
  var bank = document.getElementsByName('searchQuote-banks')[0].value;
  var date = document.getElementsByName('searchQuote-date')[0].value;
  if (bank==null || bank =="") {
    alert("Banco invalido! Por favor, tente novamente!");
    return false;
  }
  if (date==null || date =="") {
    alert("Data invalida! Por favor, tente novamente!");
    return false;
  }
  document.getElementsByName('searchQuote')[0].style.display="none"
  document.getElementsByName('searchQuoteLoading')[0].style.display="block"
  var request = host + "/getQuote?provider=" + bank + "&date=" + date
  console.log(request);
  $.ajax({
    url: request,
    dataType: "json",
    success: function(data) {
      console.log(data);
      document.getElementById("searchQuote-date-label").innerHTML = $('#searchQuote-date').val();
      document.getElementById("searchQuote-bank-label").innerHTML = $('#searchQuote-banks option:selected').text();
      document.getElementById("searchQuote-quote-label").innerHTML = "<span style=\"font-size:30px;\">R$</span>" + Math.round(data.quote*1000)/1000
      document.getElementById("searchQuote-spread-label").innerHTML = Math.round((data.spread-1)*1000)/10 + "%"
      document.getElementById("searchQuote-ptax-label").innerHTML = Math.round(data.ptax*1000)/1000
      document.getElementsByName('searchQuoteLoading')[0].style.display="none"
      document.getElementsByName('searchQuoteResult')[0].style.display="block"
    },
    error: function(thrownError) {
      document.getElementsByName('searchQuoteLoading')[0].style.display="none"
      document.getElementsByName('searchQuoteResultError')[0].style.display="block"
    }
  });

}
