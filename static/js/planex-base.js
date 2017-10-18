function getBanks(json){
  var selectSearch = document.getElementsByName('searchQuote-banks')[0];
  var selectAdd = document.getElementsByName('addValue-banks')[0];
  for (var i=0; i < json.length ; i++) {
    var optionSearch = document.createElement('option');
    optionSearch.text = json[i].fullName;
    optionSearch.value = json[i].id;
    var optionAdd = document.createElement('option');
    optionAdd.text = json[i].fullName;
    optionAdd.value = json[i].id;
    selectSearch.add(optionSearch);
    selectAdd.add(optionAdd);
    document.getElementById("searchQuoteLoading-label").innerHTML = "Aguarde...<br>Estamos buscando sua cotação...";
    document.getElementById("addValueLoading-lobel").innerHTML = "Obrigado por contribuir...<br>Estamos adicionando sua cotação..."
    //$('#banks').selectpicker('refresh')
  }
}
function loadBanks() {
  document.getElementsByName('searchQuoteLoading')[0].style.display="block"
  document.getElementsByName('addValueLoading')[0].style.display="block"
  document.getElementsByName('searchQuoteResultErrorLoad')[0].style.display="none"
  document.getElementsByName('addValueResultErrorLoad')[0].style.display="none"
  $.ajax({
    url: host + "/getProviders",
    dataType: "json",
    success: function(data) {
      document.getElementsByName('searchQuote')[0].style.display="block"
      document.getElementsByName('searchQuoteLoading')[0].style.display="none"
      document.getElementsByName('searchQuoteResult')[0].style.display="none"
      document.getElementsByName('searchQuoteResultError')[0].style.display="none"
      document.getElementsByName('addValue')[0].style.display="block"
      document.getElementsByName('addValueLoading')[0].style.display="none"
      document.getElementsByName('addValueResult')[0].style.display="none"
      document.getElementsByName('addValueResultError')[0].style.display="none"
      getBanks(data)
    },
    error : function(jqXHR, textStatus, errorThrown) {
      document.getElementsByName('searchQuoteLoading')[0].style.display="none"
      document.getElementsByName('addValueLoading')[0].style.display="none"
      document.getElementsByName('searchQuoteResultErrorLoad')[0].style.display="block"
      document.getElementsByName('addValueResultErrorLoad')[0].style.display="block"
      console.log(jqXHR)
      console.log(textStatus)
      console.log(errorThrown)
    }
  });
}
function today() {
  var date = new Date();
  var day = date.getDate();
  var month = date.getMonth() + 1;
  var year = date.getFullYear();
  if (month < 10) month = "0" + month;
  if (day < 10) day = "0" + day;
  return year + "-" + month + "-" + day;
}

loadBanks();
var today = today();
document.getElementsByName('searchQuote-date')[0].value = today;
document.getElementsByName('addValue-date')[0].value = today;
